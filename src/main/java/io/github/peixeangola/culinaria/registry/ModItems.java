package io.github.peixeangola.culinaria.registry;

import io.github.peixeangola.culinaria.Culinaria;
import io.github.peixeangola.culinaria.content.GeneratedContent;
import io.github.peixeangola.culinaria.item.DishItem;
import io.github.peixeangola.culinaria.item.HintBlockItem;
import io.github.peixeangola.culinaria.item.HintItem;
import io.github.peixeangola.culinaria.item.RecipeBookItem;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.Consumables;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Itens do Culinária. Os simples (utensílios, ingredientes, pratos) saem de {@link GeneratedContent#ITEMS}; sementes,
 * mudas, folhas e móveis são os BlockItems dos blocos de {@link ModBlocks}. A ordem de registro põe os utensílios (prato
 * sujo, xícara suja) antes dos pratos, que os devolvem ao serem comidos.
 */
public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Culinaria.MOD_ID);

    /** Todos os itens do mod pelo ID, na ordem de registro (também a ordem das abas criativas). */
    public static final Map<String, RegistryObject<Item>> BY_ID = new LinkedHashMap<>();

    static {
        for (GeneratedContent.ItemDef def : GeneratedContent.ITEMS) {
            BY_ID.put(def.id(), ITEMS.register(def.id(), () -> simple(def)));
        }
        for (GeneratedContent.CropDef crop : GeneratedContent.CROPS) {
            // useItemDescriptionPrefix: a semente é o BlockItem da lavoura, mas usa item.culinaria.<semente>
            BY_ID.put(crop.seeds(), ITEMS.register(crop.seeds(), () -> new BlockItem(ModBlocks.CROPS.get(crop.block()).get(),
                new Item.Properties().setId(ITEMS.key(crop.seeds())).useItemDescriptionPrefix())));
        }
        for (GeneratedContent.TreeDef tree : GeneratedContent.TREES) {
            BY_ID.put(tree.sapling(), ITEMS.register(tree.sapling(), () -> new BlockItem(ModBlocks.SAPLINGS.get(tree.sapling()).get(),
                new Item.Properties().setId(ITEMS.key(tree.sapling())).useBlockDescriptionPrefix())));
            BY_ID.put(tree.leaves(), ITEMS.register(tree.leaves(), () -> new BlockItem(ModBlocks.LEAVES.get(tree.leaves()).get(),
                new Item.Properties().setId(ITEMS.key(tree.leaves())).useBlockDescriptionPrefix())));
        }
        for (GeneratedContent.FurnitureDef def : GeneratedContent.FURNITURE) {
            BY_ID.put(def.id(), ITEMS.register(def.id(), () -> new HintBlockItem(ModBlocks.FURNITURE.get(def.id()).get(),
                new Item.Properties().setId(ITEMS.key(def.id())).useBlockDescriptionPrefix(), "tooltip.culinaria." + def.id())));
        }
    }

    private ModItems() {}

    private static Item simple(GeneratedContent.ItemDef def) {
        Item.Properties properties = new Item.Properties().setId(ITEMS.key(def.id())).stacksTo(def.stackSize());
        if (def.durability() > 0) {
            properties.durability(def.durability());
        }
        if (def.nutrition() > 0) {
            FoodProperties food = new FoodProperties.Builder().nutrition(def.nutrition()).saturationModifier(def.saturation()).build();
            if (def.drink()) {
                properties.food(food, Consumables.DEFAULT_DRINK);
            } else {
                properties.food(food);
            }
            if (def.container() != null) {
                properties.usingConvertsTo(BuiltInRegistries.ITEM.getValue(Identifier.parse(def.container())));
            }
        }
        if (def.id().equals("recipe_book")) {
            return new RecipeBookItem(properties);
        }
        if (def.kind() == GeneratedContent.Kind.DISH) {
            return new DishItem(properties, def.category(), def.container());
        }
        return def.hint() ? new HintItem(properties, "tooltip.culinaria." + def.id()) : new Item(properties);
    }

    /** Item do mod pelo ID (depois do registro). */
    public static Item item(String id) {
        return BuiltInRegistries.ITEM.getValue(Culinaria.id(id));
    }
}
