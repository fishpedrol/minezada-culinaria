package io.github.peixeangola.culinaria.catalog;

import java.util.Map;
import net.minecraft.world.item.Item;

/** Última contagem de ingredientes nos móveis próximos que o servidor mandou ao cliente (para o livro de receitas). */
public final class NearbyCounts {
    private static volatile Map<Item, Integer> counts = Map.of();

    private NearbyCounts() {}

    public static Map<Item, Integer> get() {
        return counts;
    }

    public static void set(Map<Item, Integer> newCounts) {
        counts = Map.copyOf(newCounts);
    }

    public static int of(Item item) {
        return counts.getOrDefault(item, 0);
    }
}
