package io.github.peixeangola.culinaria.client;

import io.github.peixeangola.culinaria.catalog.NearbyCounts;
import io.github.peixeangola.culinaria.catalog.RecipeCatalog;
import io.github.peixeangola.culinaria.content.GeneratedContent;
import io.github.peixeangola.culinaria.core.Station;
import io.github.peixeangola.culinaria.network.ModNetwork;
import io.github.peixeangola.culinaria.recipe.CookingRecipe;
import io.github.peixeangola.culinaria.registry.ModItems;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.locale.Language;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

/**
 * O livro de receitas. Lê o catálogo que o servidor mandou ({@link RecipeCatalog#client()}), então mostra exatamente as
 * receitas carregadas. Tem busca pelo nome, filtro por categoria (as 11 dos pratos, "Preparos" e "Todas") e o filtro
 * "só o que dá para fazer". O detalhe de uma receita mostra a estação, o utensílio, o recipiente, o tempo, cada
 * ingrediente com "tenho / preciso" (inventário + móveis do Culinária num raio de 8 blocos), de onde vem cada
 * ingrediente cru, as etapas a partir das matérias-primas e, com um clique, a receita de um ingrediente preparado.
 */
public class RecipeBookScreen extends Screen {
    private static final int LIST_WIDTH = 150;
    private static final int ROW = 18;
    private static final int HEADER = 48;

    private final List<String> categories = new ArrayList<>();
    private @Nullable String category;
    private boolean onlyMakeable;
    private String query = "";
    private List<RecipeCatalog.Entry> visible = List.of();
    private RecipeCatalog.@Nullable Entry selected;
    private final Deque<RecipeCatalog.Entry> history = new ArrayDeque<>();
    private int listScroll;
    private int detailScroll;
    private int left;
    private int top;
    private int bookWidth;
    private int bookHeight;
    private @Nullable EditBox search;
    /** Linhas clicáveis do detalhe nesta tela (ingrediente → receita que o produz). */
    private final List<ClickRow> clickRows = new ArrayList<>();

    private record ClickRow(int x0, int y0, int x1, int y1, RecipeCatalog.@Nullable Entry target, List<Component> tooltip) {}

    public RecipeBookScreen() {
        super(Component.translatable("culinaria.book.title"));
        categories.add(null);
        categories.addAll(GeneratedContent.CATEGORIES);
        categories.add(GeneratedContent.PREP_CATEGORY);
    }

    /** Abre o livro e pede ao servidor a contagem dos móveis próximos. */
    public static void open() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.getConnection() != null) {
            ModNetwork.sendToServer(new ModNetwork.NearbyRequest());
        }
        minecraft.gui.setScreen(new RecipeBookScreen());
    }

    @Override
    protected void init() {
        bookWidth = Math.min(width - 16, 400);
        bookHeight = Math.min(height - 16, 250);
        left = (width - bookWidth) / 2;
        top = (height - bookHeight) / 2;
        search = new EditBox(font, left + 8, top + 18, LIST_WIDTH - 8, 14, Component.translatable("culinaria.book.search"));
        search.setHint(Component.translatable("culinaria.book.search").withStyle(ChatFormatting.DARK_GRAY));
        search.setValue(query);
        search.setResponder(value -> {
            query = value;
            listScroll = 0;
            refresh();
        });
        addRenderableWidget(search);
        addRenderableWidget(Button.builder(Component.translatable("culinaria.book.can_make"), b -> {
            onlyMakeable = !onlyMakeable;
            listScroll = 0;
            refresh();
        }).bounds(left + 8, top + bookHeight - 22, LIST_WIDTH - 8, 16).build());
        addRenderableWidget(Button.builder(Component.translatable("culinaria.book.back"), b -> back())
            .bounds(left + bookWidth - 58, top + 4, 50, 14).build());
        refresh();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    // ---------------------------------------------------------------- dados

    private RecipeCatalog catalog() {
        return RecipeCatalog.client();
    }

    private void refresh() {
        String needle = query.toLowerCase(Locale.ROOT).trim();
        List<RecipeCatalog.Entry> list = new ArrayList<>();
        for (RecipeCatalog.Entry entry : catalog().entries()) {
            CookingRecipe recipe = entry.recipe();
            if (category != null && !recipe.category().equals(category)) {
                continue;
            }
            if (category == null && !recipe.isDish()) {
                continue; // "Todas" lista os pratos; os preparos ficam na própria aba
            }
            if (!needle.isEmpty() && !name(recipe.result()).getString().toLowerCase(Locale.ROOT).contains(needle)) {
                continue;
            }
            if (onlyMakeable && !canMakeNow(recipe)) {
                continue;
            }
            list.add(entry);
        }
        list.sort((a, b) -> name(a.recipe().result()).getString().compareToIgnoreCase(name(b.recipe().result()).getString()));
        visible = list;
        if (selected == null && !visible.isEmpty()) {
            selected = visible.getFirst();
        }
    }

    private static Component name(Item item) {
        return Component.translatable(item.getDescriptionId());
    }

    /** Quanto do item o jogador tem: inventário + móveis próximos (contagem do servidor). */
    private int have(Item item) {
        int count = NearbyCounts.of(item);
        if (minecraft != null && minecraft.player != null) {
            Inventory inventory = minecraft.player.getInventory();
            for (int i = 0; i < inventory.getContainerSize(); i++) {
                ItemStack stack = inventory.getItem(i);
                if (stack.is(item)) {
                    count += stack.getCount();
                }
            }
        }
        return count;
    }

    private boolean canMakeNow(CookingRecipe recipe) {
        for (CookingRecipe.CountedItem ingredient : recipe.ingredients()) {
            if (have(ingredient.item()) < ingredient.count()) {
                return false;
            }
        }
        return true;
    }

    private static String sourceKey(Item item) {
        return "culinaria.source." + BuiltInRegistries.ITEM.getKey(item).toString().replace(':', '.');
    }

    /** Matéria-prima: tem origem documentada (lavoura, árvore, animal, vanilla), então o livro não desce mais. */
    private static boolean isRaw(Item item) {
        return Language.getInstance().has(sourceKey(item));
    }

    private Optional<RecipeCatalog.Entry> producing(Item item) {
        return isRaw(item) ? Optional.empty() : catalog().producing(item);
    }

    /** Etapas: os preparos dos ingredientes (das folhas para a raiz, sem repetir) e por fim a própria receita. */
    private List<RecipeCatalog.Entry> steps(RecipeCatalog.Entry entry) {
        List<RecipeCatalog.Entry> steps = new ArrayList<>();
        collectSteps(entry, new HashSet<>(), steps, 0);
        steps.add(entry);
        return steps;
    }

    private void collectSteps(RecipeCatalog.Entry entry, Set<Item> seen, List<RecipeCatalog.Entry> steps, int depth) {
        if (depth > 8) {
            return;
        }
        for (CookingRecipe.CountedItem ingredient : entry.recipe().ingredients()) {
            if (!seen.add(ingredient.item())) {
                continue;
            }
            Optional<RecipeCatalog.Entry> before = producing(ingredient.item());
            if (before.isPresent()) {
                collectSteps(before.get(), seen, steps, depth + 1);
                steps.add(before.get());
            }
        }
    }

    private void select(RecipeCatalog.Entry entry) {
        if (selected != null && selected != entry) {
            history.push(selected);
        }
        selected = entry;
        detailScroll = 0;
    }

    private void back() {
        if (!history.isEmpty()) {
            selected = history.pop();
            detailScroll = 0;
        }
    }

    // ---------------------------------------------------------------- desenho

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        // capa e páginas
        graphics.fill(left - 4, top - 4, left + bookWidth + 4, top + bookHeight + 4, 0xFF7A3A1E);
        graphics.fill(left - 2, top - 2, left + bookWidth + 2, top + bookHeight + 2, 0xFF9A4A26);
        graphics.fill(left, top, left + bookWidth, top + bookHeight, 0xFFF4EAD0);
        graphics.fill(left + LIST_WIDTH + 2, top + 2, left + LIST_WIDTH + 3, top + bookHeight - 2, 0xFFC8B48A);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        graphics.text(font, title, left + 8, top + 6, 0xFF6A2A10, false);
        drawCategories(graphics, mouseX, mouseY);
        drawList(graphics, mouseX, mouseY);
        drawDetail(graphics, mouseX, mouseY);
        super.extractRenderState(graphics, mouseX, mouseY, a);
    }

    private int categoryX(int index) {
        return left + LIST_WIDTH + 10 + index * 18;
    }

    private void drawCategories(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        int y = top + 22;
        for (int i = 0; i < categories.size(); i++) {
            String cat = categories.get(i);
            int x = categoryX(i);
            boolean active = cat == null ? category == null : cat.equals(category);
            graphics.fill(x - 1, y - 1, x + 17, y + 17, active ? 0xFFE0B060 : 0xFFE8DCC0);
            graphics.item(new ItemStack(categoryIcon(i)), x, y);
            if (mouseX >= x && mouseX < x + 16 && mouseY >= y && mouseY < y + 16) {
                graphics.setTooltipForNextFrame(font, categoryName(cat), mouseX, mouseY);
            }
        }
    }

    private Item categoryIcon(int index) {
        String cat = categories.get(index);
        if (cat == null) {
            return ModItems.item("recipe_book");
        }
        if (cat.equals(GeneratedContent.PREP_CATEGORY)) {
            return ModItems.item("flour");
        }
        return ModItems.item(GeneratedContent.CATEGORY_ICONS.get(GeneratedContent.CATEGORIES.indexOf(cat)));
    }

    private static Component categoryName(@Nullable String cat) {
        return cat == null ? Component.translatable("culinaria.book.all") : Component.translatable("culinaria.category." + cat);
    }

    private int listRows() {
        return Math.max(1, (bookHeight - HEADER - 28) / ROW);
    }

    private void drawList(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        int x = left + 6;
        int y = top + HEADER - 10;
        graphics.text(font, Component.translatable("culinaria.book.count", visible.size()), x + 2, y, 0xFF8A6A40, false);
        y += 10;
        if (catalog().size() == 0) {
            graphics.textWithWordWrap(font, Component.translatable("culinaria.book.loading"), x + 2, y + 4, LIST_WIDTH - 10, 0xFFB02E26);
            return;
        }
        if (visible.isEmpty()) {
            graphics.text(font, Component.translatable("culinaria.book.empty"), x + 2, y + 4, 0xFF8A6A40, false);
        }
        int rows = listRows();
        listScroll = Math.max(0, Math.min(listScroll, Math.max(0, visible.size() - rows)));
        for (int i = 0; i < rows && listScroll + i < visible.size(); i++) {
            RecipeCatalog.Entry entry = visible.get(listScroll + i);
            int rowY = y + i * ROW;
            boolean hover = mouseX >= x && mouseX < x + LIST_WIDTH - 6 && mouseY >= rowY && mouseY < rowY + ROW;
            if (entry == selected) {
                graphics.fill(x, rowY, x + LIST_WIDTH - 6, rowY + ROW, 0xFFE0C890);
            } else if (hover) {
                graphics.fill(x, rowY, x + LIST_WIDTH - 6, rowY + ROW, 0xFFECE0C0);
            }
            graphics.item(entry.recipe().resultStack(), x + 1, rowY + 1);
            boolean ready = canMakeNow(entry.recipe());
            String label = font.plainSubstrByWidth(name(entry.recipe().result()).getString(), LIST_WIDTH - 32);
            graphics.text(font, label, x + 20, rowY + 5, ready ? 0xFF2E7D32 : 0xFF3A2A1A, false);
        }
        if (visible.size() > rows) {
            int barHeight = Math.max(8, rows * ROW * rows / visible.size());
            int barY = y + (rows * ROW - barHeight) * listScroll / Math.max(1, visible.size() - rows);
            graphics.fill(x + LIST_WIDTH - 5, barY, x + LIST_WIDTH - 3, barY + barHeight, 0xFFA08050);
        }
    }

    private void drawDetail(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        clickRows.clear();
        RecipeCatalog.Entry entry = selected;
        int x = left + LIST_WIDTH + 10;
        int width = bookWidth - LIST_WIDTH - 18;
        int areaTop = top + HEADER;
        int areaBottom = top + bookHeight - 6;
        if (entry == null) {
            return;
        }
        CookingRecipe recipe = entry.recipe();
        graphics.enableScissor(x - 2, areaTop, x + width + 2, areaBottom);
        int y = areaTop - detailScroll;
        // cabeçalho do prato
        graphics.item(recipe.resultStack(), x, y);
        graphics.text(font, name(recipe.result()).copy().withStyle(ChatFormatting.BOLD), x + 20, y, 0xFF3A2A1A, false);
        graphics.text(font, Component.translatable("culinaria.book.makes", recipe.resultCount()).append(" · ")
            .append(categoryName(recipe.category())), x + 20, y + 10, 0xFF8A6A40, false);
        y += 24;
        y = line(graphics, Component.translatable("culinaria.book.station", Component.translatable(recipe.station().translationKey())), x, y, width);
        if (recipe.utensil().isPresent()) {
            y = line(graphics, Component.translatable("culinaria.book.utensil", name(recipe.utensil().get())), x, y, width);
        }
        if (recipe.container().isPresent()) {
            y = line(graphics, Component.translatable("culinaria.book.container", name(recipe.container().get()), recipe.resultCount()), x, y, width);
        }
        y = line(graphics, Component.translatable("culinaria.book.time", timeText(recipe)), x, y, width);
        y += 4;
        // ingredientes
        graphics.text(font, Component.translatable("culinaria.book.ingredients").withStyle(ChatFormatting.UNDERLINE), x, y, 0xFF6A2A10, false);
        y += 12;
        boolean all = true;
        List<CookingRecipe.CountedItem> rows = new ArrayList<>(recipe.ingredients());
        recipe.container().ifPresent(c -> rows.add(new CookingRecipe.CountedItem(c, recipe.resultCount())));
        for (CookingRecipe.CountedItem ingredient : rows) {
            int have = have(ingredient.item());
            boolean ok = have >= ingredient.count();
            all &= ok;
            graphics.item(new ItemStack(ingredient.item()), x, y);
            Optional<RecipeCatalog.Entry> made = producing(ingredient.item());
            Component label = name(ingredient.item()).copy().withStyle(made.isPresent() ? ChatFormatting.BLUE : ChatFormatting.BLACK);
            String text = font.plainSubstrByWidth(label.getString(), width - 70);
            graphics.text(font, made.isPresent() ? Component.literal(text).withStyle(ChatFormatting.UNDERLINE) : Component.literal(text),
                x + 20, y + 4, made.isPresent() ? 0xFF2A4AA0 : 0xFF3A2A1A, false);
            String counts = have + " / " + ingredient.count();
            graphics.text(font, counts, x + width - font.width(counts), y + 4, ok ? 0xFF2E7D32 : 0xFFB02E26, false);
            List<Component> tooltip = new ArrayList<>();
            tooltip.add(name(ingredient.item()));
            if (Language.getInstance().has(sourceKey(ingredient.item()))) {
                tooltip.add(Component.translatable("culinaria.book.source", Component.translatable(sourceKey(ingredient.item())))
                    .withStyle(ChatFormatting.GRAY));
            }
            if (made.isPresent()) {
                tooltip.add(Component.translatable("culinaria.book.made_by").withStyle(ChatFormatting.BLUE));
            }
            if (!ok) {
                tooltip.add(Component.translatable("culinaria.book.missing", ingredient.count() - have).withStyle(ChatFormatting.RED));
            }
            if (y + 16 > areaTop && y < areaBottom) {
                clickRows.add(new ClickRow(x, y, x + width, y + 16, made.orElse(null), tooltip));
            }
            y += 17;
        }
        y = line(graphics, Component.translatable(all ? "culinaria.book.ready" : "culinaria.book.nearby")
            .withStyle(all ? ChatFormatting.DARK_GREEN : ChatFormatting.GRAY), x, y + 2, width);
        y += 6;
        // etapas
        graphics.text(font, Component.translatable("culinaria.book.steps").withStyle(ChatFormatting.UNDERLINE), x, y, 0xFF6A2A10, false);
        y += 12;
        List<RecipeCatalog.Entry> steps = steps(entry);
        for (int i = 0; i < steps.size(); i++) {
            CookingRecipe step = steps.get(i).recipe();
            Component text = Component.translatable("culinaria.book.step", i + 1, Component.translatable("culinaria.verb." + step.station().id()),
                name(step.result()), Component.translatable(step.station().translationKey()));
            y = line(graphics, text, x, y, width);
        }
        int contentHeight = y + detailScroll - areaTop;
        detailScroll = Math.max(0, Math.min(detailScroll, Math.max(0, contentHeight - (areaBottom - areaTop))));
        graphics.disableScissor();
        for (ClickRow row : clickRows) {
            if (mouseX >= row.x0() && mouseX < row.x1() && mouseY >= Math.max(row.y0(), areaTop) && mouseY < Math.min(row.y1(), areaBottom)) {
                graphics.setComponentTooltipForNextFrame(font, row.tooltip(), mouseX, mouseY);
            }
        }
    }

    private Component timeText(CookingRecipe recipe) {
        Station station = recipe.station();
        return switch (station.kind()) {
            case BOARD -> Component.translatable("culinaria.unit.cuts", recipe.time());
            case MILL -> Component.translatable("culinaria.unit.turns", recipe.time());
            case SINK -> Component.translatable("culinaria.unit.wash");
            default -> Component.translatable("culinaria.unit.ticks", String.format(Locale.ROOT, "%.1f", recipe.time() / 20.0F));
        };
    }

    private int line(GuiGraphicsExtractor graphics, Component text, int x, int y, int width) {
        List<FormattedCharSequence> lines = font.split(text, width);
        for (FormattedCharSequence line : lines) {
            graphics.text(font, line, x, y, 0xFF3A2A1A, false);
            y += 10;
        }
        return y;
    }

    // ---------------------------------------------------------------- entrada

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if (super.mouseClicked(event, doubleClick)) {
            return true;
        }
        double mx = event.x();
        double my = event.y();
        int catY = top + 22;
        for (int i = 0; i < categories.size(); i++) {
            int x = categoryX(i);
            if (mx >= x && mx < x + 16 && my >= catY && my < catY + 16) {
                category = categories.get(i);
                listScroll = 0;
                refresh();
                return true;
            }
        }
        int listX = left + 6;
        int listY = top + HEADER;
        if (mx >= listX && mx < listX + LIST_WIDTH - 6 && my >= listY) {
            int index = listScroll + (int) ((my - listY) / ROW);
            if ((my - listY) / ROW < listRows() && index >= 0 && index < visible.size()) {
                select(visible.get(index));
                return true;
            }
        }
        for (ClickRow row : clickRows) {
            if (row.target() != null && mx >= row.x0() && mx < row.x1() && my >= row.y0() && my < row.y1()) {
                select(row.target());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (mouseX < left + LIST_WIDTH) {
            listScroll = Math.max(0, listScroll - (int) Math.signum(scrollY));
        } else {
            detailScroll = Math.max(0, detailScroll - (int) (Math.signum(scrollY) * 12));
        }
        return true;
    }

    /** Para os testes de lógica: a lista visível no momento. */
    List<RecipeCatalog.Entry> visibleEntries() {
        return visible;
    }

    /** Contagem por item usada no detalhe (inventário + móveis). */
    Map<Item, Integer> haveAll(List<Item> items) {
        Map<Item, Integer> counts = new HashMap<>();
        items.forEach(item -> counts.put(item, have(item)));
        return counts;
    }
}
