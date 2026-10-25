package io.github.peixeangola.culinaria.catalog;

import io.github.peixeangola.culinaria.core.CookingMatcher;
import io.github.peixeangola.culinaria.core.Station;
import io.github.peixeangola.culinaria.recipe.CookingRecipe;
import io.github.peixeangola.culinaria.registry.ModRecipes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jspecify.annotations.Nullable;

/**
 * As receitas culinaria:cooking carregadas, numa lista ordenada pelo ID. O servidor monta a lista a partir do
 * RecipeManager; o cliente recebe a mesma lista pelo pacote do catálogo (o 26.3 não manda receitas ao cliente), na mesma
 * ordem, e por isso o índice de uma receita (usado no ContainerData das estações) vale dos dois lados.
 */
public final class RecipeCatalog {
    /** Uma receita e o seu ID (o nome do arquivo, como culinaria:cooking/tomato_soup). */
    public record Entry(Identifier id, CookingRecipe recipe) {}

    private static volatile @Nullable RecipeCatalog server;
    private static volatile RecipeCatalog client = new RecipeCatalog(List.of());

    private final List<Entry> entries;

    public RecipeCatalog(List<Entry> entries) {
        List<Entry> sorted = new ArrayList<>(entries);
        sorted.sort(Comparator.comparing(e -> e.id().toString()));
        this.entries = List.copyOf(sorted);
    }

    public List<Entry> entries() {
        return entries;
    }

    public int size() {
        return entries.size();
    }

    public @Nullable Entry get(int index) {
        return index >= 0 && index < entries.size() ? entries.get(index) : null;
    }

    public int indexOf(Entry entry) {
        return entries.indexOf(entry);
    }

    public List<Entry> forStation(Station station) {
        return entries.stream().filter(e -> e.recipe().station() == station).toList();
    }

    /** Receitas de prato (as 100). */
    public List<Entry> dishes() {
        return entries.stream().filter(e -> e.recipe().isDish()).toList();
    }

    /** Primeira receita que produz o item (para o livro mostrar como fazer um ingrediente). */
    public Optional<Entry> producing(Item item) {
        return entries.stream().filter(e -> e.recipe().result() == item).findFirst();
    }

    /** Receita de estação de clique (tábua, pia, moedor, geladeira) para um item. */
    public Optional<Entry> single(Station station, Item item) {
        return entries.stream().filter(e -> e.recipe().station() == station && e.recipe().acceptsSingle(item)).findFirst();
    }

    /** Resultado da busca numa estação com interface. */
    public record Lookup(CookingMatcher.Result result, @Nullable Entry entry) {
        public static final Lookup NONE = new Lookup(CookingMatcher.Result.NO_MATCH, null);
    }

    /**
     * A receita da estação cujos ingredientes são exatamente os tipos dos slots. Com quantidade suficiente vem MATCH;
     * com os tipos certos mas pouca quantidade, NOT_ENOUGH (a estação mostra o que falta).
     */
    public Lookup find(Station station, List<ItemStack> slots) {
        Map<Item, Integer> present = CookingRecipe.tally(slots);
        if (present.isEmpty()) {
            return Lookup.NONE;
        }
        Lookup partial = Lookup.NONE;
        for (Entry entry : entries) {
            if (entry.recipe().station() != station) {
                continue;
            }
            CookingMatcher.Result result = CookingMatcher.match(entry.recipe().needs(), present);
            if (result == CookingMatcher.Result.MATCH) {
                return new Lookup(result, entry);
            }
            if (result == CookingMatcher.Result.NOT_ENOUGH && partial.entry() == null) {
                partial = new Lookup(result, entry);
            }
        }
        return partial;
    }

    // ---------------------------------------------------------------- instâncias

    /** Catálogo do servidor, montado na primeira vez e refeito depois de cada /reload. */
    public static RecipeCatalog server(MinecraftServer minecraftServer) {
        RecipeCatalog current = server;
        if (current == null) {
            List<Entry> list = new ArrayList<>();
            for (RecipeHolder<?> holder : minecraftServer.getRecipeManager().getRecipes()) {
                if (holder.value() instanceof CookingRecipe recipe && holder.value().getType() == ModRecipes.COOKING.get()) {
                    list.add(new Entry(holder.id().identifier(), recipe));
                }
            }
            current = new RecipeCatalog(list);
            server = current;
        }
        return current;
    }

    /** Esquece o catálogo do servidor (receitas recarregadas ou servidor parado). */
    public static void invalidateServer() {
        server = null;
    }

    /** Cópia do cliente, recebida do servidor. */
    public static RecipeCatalog client() {
        return client;
    }

    public static void setClient(RecipeCatalog catalog) {
        client = catalog;
    }
}
