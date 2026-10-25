package io.github.peixeangola.culinaria.registry;

import io.github.peixeangola.culinaria.Culinaria;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * Tags geradas a partir das receitas. As tags chegam ao cliente e as receitas não, então é por elas que o cliente prevê
 * o clique nas estações de clique; o servidor decide pela receita.
 */
public final class ModTags {
    public static final TagKey<Item> FRIDGE_STORABLE = item("fridge_storable");
    public static final TagKey<Item> CUTTABLE = item("station/cutting_board");
    public static final TagKey<Item> WASHABLE = item("station/sink");
    public static final TagKey<Item> MILLABLE = item("station/mill");

    private ModTags() {}

    private static TagKey<Item> item(String path) {
        return TagKey.create(Registries.ITEM, Culinaria.id(path));
    }
}
