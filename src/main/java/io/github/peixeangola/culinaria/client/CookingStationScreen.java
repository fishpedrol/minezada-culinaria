package io.github.peixeangola.culinaria.client;

import io.github.peixeangola.culinaria.block.CookingStationBlockEntity;
import io.github.peixeangola.culinaria.catalog.RecipeCatalog;
import io.github.peixeangola.culinaria.core.CookingStatus;
import io.github.peixeangola.culinaria.menu.CookingStationMenu;
import io.github.peixeangola.culinaria.menu.KitchenSlots;
import io.github.peixeangola.culinaria.recipe.CookingRecipe;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

/**
 * Tela da bancada, do fogão, do forno e do liquidificador. Mostra os slots com rótulo, a seta de progresso, a chama do
 * combustível, a prévia do prato reconhecido no slot de saída e, embaixo, o status que o servidor mandou (o que falta ou o
 * que está sendo preparado). Passar o mouse no status mostra o texto inteiro.
 */
public class CookingStationScreen extends AbstractContainerScreen<CookingStationMenu> {
    public CookingStationScreen(CookingStationMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, KitchenSlots.IMAGE_WIDTH, KitchenSlots.IMAGE_HEIGHT);
        this.inventoryLabelY = KitchenSlots.INVENTORY_TOP - 11;
    }

    private RecipeCatalog.@Nullable Entry recipe() {
        return RecipeCatalog.client().get(menu.recipeIndex());
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        int x = leftPos;
        int y = topPos;
        KitchenGui.panel(graphics, x, y, imageWidth, imageHeight);
        for (Slot slot : menu.slots) {
            int containerSlot = menu.slots.indexOf(slot) < menu.slots.size() - 36 ? menu.containerSlot(menu.slots.indexOf(slot)) : -2;
            if (containerSlot == CookingStationBlockEntity.OUTPUT) {
                KitchenGui.bigSlot(graphics, x + slot.x, y + slot.y);
            } else {
                KitchenGui.slot(graphics, x + slot.x, y + slot.y);
            }
        }
        KitchenGui.arrow(graphics, x + KitchenSlots.ARROW_X, y + KitchenSlots.ARROW_Y, menu.progress());
        if (menu.station().usesFuel()) {
            KitchenGui.flame(graphics, x + KitchenSlots.FLAME_X, y + KitchenSlots.FLAME_Y, menu.burnLeft(), menu.isLit());
        }
        // Prévia do prato reconhecido, apagada, quando a saída está vazia
        RecipeCatalog.Entry entry = recipe();
        Slot output = slotFor(CookingStationBlockEntity.OUTPUT);
        if (entry != null && output != null && !output.hasItem()) {
            graphics.fakeItem(entry.recipe().resultStack(), x + output.x, y + output.y);
            graphics.fill(x + output.x, y + output.y, x + output.x + 16, y + output.y + 16, 0x88C6C6C6);
        }
        // Ícones fantasmas do papel dos slots vazios de utensílio e recipiente
        ghost(graphics, CookingStationBlockEntity.UTENSIL, entry == null ? null : entry.recipe().utensil().orElse(null));
        ghost(graphics, CookingStationBlockEntity.CONTAINER, entry == null ? null : entry.recipe().container().orElse(null));
    }

    private void ghost(GuiGraphicsExtractor graphics, int containerSlot, @Nullable Item item) {
        Slot slot = slotFor(containerSlot);
        if (slot == null || slot.hasItem() || item == null) {
            return;
        }
        graphics.fakeItem(new ItemStack(item), leftPos + slot.x, topPos + slot.y);
        graphics.fill(leftPos + slot.x, topPos + slot.y, leftPos + slot.x + 16, topPos + slot.y + 16, 0xAA8B8B8B);
    }

    private @Nullable Slot slotFor(int containerSlot) {
        for (int i = 0; i < menu.slots.size() - 36; i++) {
            if (menu.containerSlot(i) == containerSlot) {
                return menu.slots.get(i);
            }
        }
        return null;
    }

    private Component statusText() {
        CookingStatus status = menu.status();
        RecipeCatalog.Entry entry = recipe();
        if (entry == null) {
            return Component.translatable(status.translationKey());
        }
        CookingRecipe recipe = entry.recipe();
        Component dish = Component.translatable(recipe.result().getDescriptionId());
        return switch (status) {
            case WORKING -> Component.translatable(status.translationKey(), dish);
            case MORE_NEEDED -> Component.translatable(status.translationKey(), dish);
            case NEEDS_UTENSIL -> Component.translatable(status.translationKey(),
                Component.translatable(recipe.utensil().map(Item::getDescriptionId).orElse("")));
            case NEEDS_CONTAINER -> Component.translatable(status.translationKey(),
                Component.translatable(recipe.container().map(Item::getDescriptionId).orElse("")), recipe.resultCount());
            default -> Component.translatable(status.translationKey());
        };
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        super.extractLabels(graphics, mouseX, mouseY);
        int textColor = menu.status().isProblem() ? KitchenGui.RED : menu.status() == CookingStatus.WORKING ? KitchenGui.GREEN : KitchenGui.TEXT;
        String text = font.plainSubstrByWidth(statusText().getString(), imageWidth - 16);
        graphics.text(font, text, 8, KitchenSlots.STATUS_Y, textColor, false);
    }

    @Override
    protected void extractTooltip(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        super.extractTooltip(graphics, mouseX, mouseY);
        int relX = mouseX - leftPos;
        int relY = mouseY - topPos;
        if (relY >= KitchenSlots.STATUS_Y - 1 && relY <= KitchenSlots.STATUS_Y + 9 && relX >= 6 && relX <= imageWidth - 6) {
            graphics.setTooltipForNextFrame(font, statusText(), mouseX, mouseY);
            return;
        }
        if (hoveredSlot != null && !hoveredSlot.hasItem()) {
            int index = menu.slots.indexOf(hoveredSlot);
            int containerSlot = index < menu.slots.size() - 36 ? menu.containerSlot(index) : -1;
            String key = switch (containerSlot) {
                case CookingStationBlockEntity.UTENSIL -> "culinaria.gui.utensil";
                case CookingStationBlockEntity.CONTAINER -> "culinaria.gui.container";
                case CookingStationBlockEntity.FUEL -> "culinaria.gui.fuel";
                case CookingStationBlockEntity.LEFTOVER_A, CookingStationBlockEntity.LEFTOVER_B -> "culinaria.gui.leftovers";
                default -> containerSlot >= 0 && containerSlot < CookingStationBlockEntity.INGREDIENT_SLOTS ? "culinaria.gui.ingredients" : null;
            };
            if (key != null) {
                List<Component> lines = new ArrayList<>();
                lines.add(Component.translatable(key));
                graphics.setComponentTooltipForNextFrame(font, lines, mouseX, mouseY);
            }
        }
    }
}
