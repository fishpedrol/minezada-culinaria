// GERADO por tools/gen/generate.mjs a partir de tools/gen/content.mjs. Não edite à mão.
package io.github.peixeangola.culinaria.content;

import java.util.List;

/** Tabela de conteúdo gerada: itens simples, lavouras, árvores frutíferas e móveis do Culinária. */
public final class GeneratedContent {
    public enum Kind { TOOL, INGREDIENT, DISH }

    /**
     * Item simples. nutrition 0 = não é comida. container: o que sobra depois de comer (null = nada). drink: bebe em vez
     * de comer. category: categoria do prato (null fora dos pratos).
     */
    public record ItemDef(String id, Kind kind, int nutrition, float saturation, String container, boolean drink, int stackSize,
                          int durability, boolean hint, String category) {}

    /** Lavoura: bloco, produto colhido e semente (o BlockItem que planta). */
    public record CropDef(String block, String product, String seeds) {}

    /** Árvore frutífera: folhas, muda, fruta (ID completo) e feature da árvore. */
    public record TreeDef(String id, String leaves, String sapling, String fruit, String feature) {}

    /** Móvel: station é a estação de culinária (null no armário/despensa); rows de inventário nos guarda-volumes. */
    public record FurnitureDef(String id, String station, int rows, boolean hint) {}

    public static final List<ItemDef> ITEMS = List.of(
        new ItemDef("kitchen_knife", Kind.TOOL, 0, 0F, null, false, 1, 256, true, null),
        new ItemDef("cooking_pot", Kind.TOOL, 0, 0F, null, false, 1, 0, true, null),
        new ItemDef("frying_pan", Kind.TOOL, 0, 0F, null, false, 1, 0, true, null),
        new ItemDef("baking_tray", Kind.TOOL, 0, 0F, null, false, 1, 0, true, null),
        new ItemDef("rolling_pin", Kind.TOOL, 0, 0F, null, false, 1, 0, true, null),
        new ItemDef("whisk", Kind.TOOL, 0, 0F, null, false, 1, 0, true, null),
        new ItemDef("plate", Kind.TOOL, 0, 0F, null, false, 64, 0, true, null),
        new ItemDef("dirty_plate", Kind.TOOL, 0, 0F, null, false, 64, 0, true, null),
        new ItemDef("cup", Kind.TOOL, 0, 0F, null, false, 64, 0, true, null),
        new ItemDef("dirty_cup", Kind.TOOL, 0, 0F, null, false, 64, 0, true, null),
        new ItemDef("recipe_book", Kind.TOOL, 0, 0F, null, false, 1, 0, true, null),
        new ItemDef("tomato", Kind.INGREDIENT, 2, 0.3F, null, false, 64, 0, false, null),
        new ItemDef("onion", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("garlic", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("lettuce", Kind.INGREDIENT, 1, 0.3F, null, false, 64, 0, false, null),
        new ItemDef("corn", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("rice", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("beans", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("bell_pepper", Kind.INGREDIENT, 2, 0.3F, null, false, 64, 0, false, null),
        new ItemDef("basil", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("parsley", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("strawberry", Kind.INGREDIENT, 2, 0.2F, null, false, 64, 0, false, null),
        new ItemDef("coffee_beans", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("cassava", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("kale", Kind.INGREDIENT, 1, 0.3F, null, false, 64, 0, false, null),
        new ItemDef("orange", Kind.INGREDIENT, 4, 0.3F, null, false, 64, 0, false, null),
        new ItemDef("lemon", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("peach", Kind.INGREDIENT, 4, 0.3F, null, false, 64, 0, false, null),
        new ItemDef("cherry", Kind.INGREDIENT, 2, 0.2F, null, false, 64, 0, false, null),
        new ItemDef("sliced_tomato", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("chopped_onion", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("minced_garlic", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("chopped_lettuce", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("pepper_strips", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("potato_sticks", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("carrot_slices", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("beef_cubes", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("chicken_pieces", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("cod_fillet", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("salmon_fillet", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("raw_bacon", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("ham", Kind.INGREDIENT, 3, 0.4F, null, false, 64, 0, false, null),
        new ItemDef("bread_slice", Kind.INGREDIENT, 2, 0.3F, null, false, 64, 0, false, null),
        new ItemDef("peeled_cassava", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("apple_slices", Kind.INGREDIENT, 2, 0.3F, null, false, 64, 0, false, null),
        new ItemDef("washed_rice", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("soaked_beans", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("tapioca_starch", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("flour", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("cornmeal", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("cassava_flour", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("cocoa_powder", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("ground_coffee", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("breadcrumbs", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("minced_beef", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("raw_sausage", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("bread_dough", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("pasta_dough", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("raw_pasta", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("pizza_base", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("pie_crust", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("cake_batter", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("pancake_batter", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("cream", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("butter", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("mayonnaise", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("lemon_mousse_mix", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("ice_cream_mix", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("salt", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("cheese", Kind.INGREDIENT, 3, 0.4F, null, false, 64, 0, false, null),
        new ItemDef("tomato_sauce", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("bolognese_sauce", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("cooked_pasta", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("cooked_rice", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("cooked_beans", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("condensed_milk", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("doce_de_leite", Kind.INGREDIENT, 2, 0.2F, null, false, 64, 0, false, null),
        new ItemDef("chocolate", Kind.INGREDIENT, 3, 0.3F, null, false, 64, 0, false, null),
        new ItemDef("beef_patty", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("roasted_coffee", Kind.INGREDIENT, 0, 0F, null, false, 64, 0, false, null),
        new ItemDef("bread_roll", Kind.DISH, 5, 0.6F, null, false, 16, 0, false, "breads_pasta"),
        new ItemDef("cheese_bread", Kind.DISH, 4, 0.6F, null, false, 16, 0, false, "breads_pasta"),
        new ItemDef("garlic_bread", Kind.DISH, 6, 0.6F, null, false, 16, 0, false, "breads_pasta"),
        new ItemDef("spaghetti_sugo", Kind.DISH, 10, 0.7F, "culinaria:dirty_plate", false, 16, 0, false, "breads_pasta"),
        new ItemDef("spaghetti_bolognese", Kind.DISH, 12, 0.8F, "culinaria:dirty_plate", false, 16, 0, false, "breads_pasta"),
        new ItemDef("lasagna", Kind.DISH, 13, 0.8F, "culinaria:dirty_plate", false, 16, 0, false, "breads_pasta"),
        new ItemDef("pizza_margherita", Kind.DISH, 12, 0.7F, null, false, 16, 0, false, "breads_pasta"),
        new ItemDef("pizza_calabresa", Kind.DISH, 13, 0.7F, null, false, 16, 0, false, "breads_pasta"),
        new ItemDef("mac_and_cheese", Kind.DISH, 11, 0.7F, "minecraft:bowl", false, 16, 0, false, "breads_pasta"),
        new ItemDef("gnocchi", Kind.DISH, 11, 0.7F, "culinaria:dirty_plate", false, 16, 0, false, "breads_pasta"),
        new ItemDef("fried_egg", Kind.DISH, 5, 0.6F, "culinaria:dirty_plate", false, 16, 0, false, "breakfast"),
        new ItemDef("scrambled_eggs", Kind.DISH, 7, 0.6F, "culinaria:dirty_plate", false, 16, 0, false, "breakfast"),
        new ItemDef("omelette", Kind.DISH, 9, 0.7F, "culinaria:dirty_plate", false, 16, 0, false, "breakfast"),
        new ItemDef("pancakes", Kind.DISH, 9, 0.6F, "culinaria:dirty_plate", false, 16, 0, false, "breakfast"),
        new ItemDef("tapioca", Kind.DISH, 6, 0.5F, null, false, 16, 0, false, "breakfast"),
        new ItemDef("bacon_and_eggs", Kind.DISH, 10, 0.8F, "culinaria:dirty_plate", false, 16, 0, false, "breakfast"),
        new ItemDef("pao_na_chapa", Kind.DISH, 6, 0.6F, null, false, 16, 0, false, "breakfast"),
        new ItemDef("misto_quente", Kind.DISH, 9, 0.7F, null, false, 16, 0, false, "breakfast"),
        new ItemDef("grilled_steak", Kind.DISH, 10, 0.9F, "culinaria:dirty_plate", false, 16, 0, false, "meat"),
        new ItemDef("beef_skewer", Kind.DISH, 7, 0.8F, null, false, 16, 0, false, "meat"),
        new ItemDef("roast_chicken", Kind.DISH, 12, 0.9F, "culinaria:dirty_plate", false, 16, 0, false, "meat"),
        new ItemDef("fried_chicken", Kind.DISH, 11, 0.8F, "culinaria:dirty_plate", false, 16, 0, false, "meat"),
        new ItemDef("meatballs", Kind.DISH, 12, 0.8F, "minecraft:bowl", false, 16, 0, false, "meat"),
        new ItemDef("roast_pork", Kind.DISH, 12, 0.9F, "culinaria:dirty_plate", false, 16, 0, false, "meat"),
        new ItemDef("hamburger", Kind.DISH, 13, 0.8F, null, false, 16, 0, false, "meat"),
        new ItemDef("pot_roast", Kind.DISH, 13, 0.9F, "minecraft:bowl", false, 16, 0, false, "meat"),
        new ItemDef("herb_lamb_chops", Kind.DISH, 10, 0.9F, "culinaria:dirty_plate", false, 16, 0, false, "meat"),
        new ItemDef("roast_rabbit", Kind.DISH, 11, 0.9F, "culinaria:dirty_plate", false, 16, 0, false, "meat"),
        new ItemDef("grilled_salmon", Kind.DISH, 10, 0.9F, "culinaria:dirty_plate", false, 16, 0, false, "fish"),
        new ItemDef("honey_salmon", Kind.DISH, 10, 0.8F, "culinaria:dirty_plate", false, 16, 0, false, "fish"),
        new ItemDef("fried_fish", Kind.DISH, 10, 0.8F, "culinaria:dirty_plate", false, 16, 0, false, "fish"),
        new ItemDef("fish_and_chips", Kind.DISH, 12, 0.8F, "culinaria:dirty_plate", false, 16, 0, false, "fish"),
        new ItemDef("moqueca", Kind.DISH, 13, 0.9F, "minecraft:bowl", false, 16, 0, false, "fish"),
        new ItemDef("sushi", Kind.DISH, 3, 0.7F, null, false, 16, 0, false, "fish"),
        new ItemDef("bacalhoada", Kind.DISH, 13, 0.9F, "culinaria:dirty_plate", false, 16, 0, false, "fish"),
        new ItemDef("poke_bowl", Kind.DISH, 11, 0.8F, "minecraft:bowl", false, 16, 0, false, "fish"),
        new ItemDef("rice_and_beans", Kind.DISH, 10, 0.8F, "culinaria:dirty_plate", false, 16, 0, false, "mains"),
        new ItemDef("feijoada", Kind.DISH, 16, 1F, "minecraft:bowl", false, 16, 0, false, "mains"),
        new ItemDef("prato_feito", Kind.DISH, 20, 1F, "culinaria:dirty_plate", false, 16, 0, false, "mains"),
        new ItemDef("chicken_stroganoff", Kind.DISH, 13, 0.9F, "culinaria:dirty_plate", false, 16, 0, false, "mains"),
        new ItemDef("mushroom_risotto", Kind.DISH, 12, 0.8F, "culinaria:dirty_plate", false, 16, 0, false, "mains"),
        new ItemDef("galinhada", Kind.DISH, 14, 0.9F, "culinaria:dirty_plate", false, 16, 0, false, "mains"),
        new ItemDef("escondidinho", Kind.DISH, 14, 0.9F, "culinaria:dirty_plate", false, 16, 0, false, "mains"),
        new ItemDef("stuffed_peppers", Kind.DISH, 12, 0.8F, "culinaria:dirty_plate", false, 16, 0, false, "mains"),
        new ItemDef("baiao_de_dois", Kind.DISH, 14, 0.9F, "culinaria:dirty_plate", false, 16, 0, false, "mains"),
        new ItemDef("roast_beef_potatoes", Kind.DISH, 14, 1F, "culinaria:dirty_plate", false, 16, 0, false, "mains"),
        new ItemDef("tomato_soup", Kind.DISH, 7, 0.6F, "minecraft:bowl", false, 16, 0, false, "soups"),
        new ItemDef("chicken_soup", Kind.DISH, 9, 0.7F, "minecraft:bowl", false, 16, 0, false, "soups"),
        new ItemDef("vegetable_soup", Kind.DISH, 8, 0.6F, "minecraft:bowl", false, 16, 0, false, "soups"),
        new ItemDef("pumpkin_soup", Kind.DISH, 8, 0.7F, "minecraft:bowl", false, 16, 0, false, "soups"),
        new ItemDef("onion_soup", Kind.DISH, 9, 0.7F, "minecraft:bowl", false, 16, 0, false, "soups"),
        new ItemDef("caldo_verde", Kind.DISH, 10, 0.7F, "minecraft:bowl", false, 16, 0, false, "soups"),
        new ItemDef("bean_broth", Kind.DISH, 9, 0.7F, "minecraft:bowl", false, 16, 0, false, "soups"),
        new ItemDef("corn_cream", Kind.DISH, 8, 0.6F, "minecraft:bowl", false, 16, 0, false, "soups"),
        new ItemDef("fish_soup", Kind.DISH, 9, 0.7F, "minecraft:bowl", false, 16, 0, false, "soups"),
        new ItemDef("fries", Kind.DISH, 6, 0.5F, null, false, 16, 0, false, "sides"),
        new ItemDef("mashed_potatoes", Kind.DISH, 7, 0.6F, "minecraft:bowl", false, 16, 0, false, "sides"),
        new ItemDef("farofa", Kind.DISH, 6, 0.6F, "minecraft:bowl", false, 16, 0, false, "sides"),
        new ItemDef("couve_refogada", Kind.DISH, 5, 0.6F, "culinaria:dirty_plate", false, 16, 0, false, "sides"),
        new ItemDef("vinaigrette", Kind.DISH, 4, 0.5F, "minecraft:bowl", false, 16, 0, false, "sides"),
        new ItemDef("cuscuz", Kind.DISH, 7, 0.6F, "culinaria:dirty_plate", false, 16, 0, false, "sides"),
        new ItemDef("polenta", Kind.DISH, 7, 0.6F, "minecraft:bowl", false, 16, 0, false, "sides"),
        new ItemDef("corn_on_the_cob", Kind.DISH, 5, 0.6F, null, false, 16, 0, false, "sides"),
        new ItemDef("stuffed_baked_potato", Kind.DISH, 9, 0.7F, "culinaria:dirty_plate", false, 16, 0, false, "sides"),
        new ItemDef("arroz_a_grega", Kind.DISH, 8, 0.6F, "culinaria:dirty_plate", false, 16, 0, false, "sides"),
        new ItemDef("garden_salad", Kind.DISH, 5, 0.6F, "minecraft:bowl", false, 16, 0, false, "vegetables"),
        new ItemDef("caprese", Kind.DISH, 6, 0.6F, "culinaria:dirty_plate", false, 16, 0, false, "vegetables"),
        new ItemDef("ratatouille", Kind.DISH, 8, 0.7F, "culinaria:dirty_plate", false, 16, 0, false, "vegetables"),
        new ItemDef("grilled_vegetables", Kind.DISH, 7, 0.6F, "culinaria:dirty_plate", false, 16, 0, false, "vegetables"),
        new ItemDef("stuffed_mushrooms", Kind.DISH, 8, 0.7F, "culinaria:dirty_plate", false, 16, 0, false, "vegetables"),
        new ItemDef("potato_salad", Kind.DISH, 8, 0.7F, "minecraft:bowl", false, 16, 0, false, "vegetables"),
        new ItemDef("roasted_pumpkin", Kind.DISH, 7, 0.6F, "culinaria:dirty_plate", false, 16, 0, false, "vegetables"),
        new ItemDef("bruschetta", Kind.DISH, 5, 0.6F, null, false, 16, 0, false, "vegetables"),
        new ItemDef("chocolate_cake", Kind.DISH, 4, 0.4F, null, false, 16, 0, false, "desserts"),
        new ItemDef("carrot_cake", Kind.DISH, 5, 0.4F, null, false, 16, 0, false, "desserts"),
        new ItemDef("corn_cake", Kind.DISH, 4, 0.4F, null, false, 16, 0, false, "desserts"),
        new ItemDef("strawberry_cake", Kind.DISH, 5, 0.4F, null, false, 16, 0, false, "desserts"),
        new ItemDef("pudim", Kind.DISH, 6, 0.5F, "culinaria:dirty_plate", false, 16, 0, false, "desserts"),
        new ItemDef("lemon_mousse", Kind.DISH, 5, 0.4F, null, false, 16, 0, false, "desserts"),
        new ItemDef("strawberry_ice_cream", Kind.DISH, 4, 0.3F, null, false, 16, 0, false, "desserts"),
        new ItemDef("fruit_salad", Kind.DISH, 7, 0.5F, "minecraft:bowl", false, 16, 0, false, "desserts"),
        new ItemDef("rice_pudding", Kind.DISH, 7, 0.5F, "minecraft:bowl", false, 16, 0, false, "desserts"),
        new ItemDef("apple_pie", Kind.DISH, 10, 0.5F, null, false, 16, 0, false, "sweets"),
        new ItemDef("lemon_pie", Kind.DISH, 9, 0.5F, null, false, 16, 0, false, "sweets"),
        new ItemDef("chicken_pot_pie", Kind.DISH, 13, 0.8F, null, false, 16, 0, false, "sweets"),
        new ItemDef("cherry_pie", Kind.DISH, 9, 0.5F, null, false, 16, 0, false, "sweets"),
        new ItemDef("brigadeiro", Kind.DISH, 2, 0.3F, null, false, 16, 0, false, "sweets"),
        new ItemDef("chocolate_chip_cookies", Kind.DISH, 2, 0.2F, null, false, 16, 0, false, "sweets"),
        new ItemDef("churros", Kind.DISH, 4, 0.4F, null, false, 16, 0, false, "sweets"),
        new ItemDef("rabanada", Kind.DISH, 5, 0.5F, null, false, 16, 0, false, "sweets"),
        new ItemDef("peach_in_syrup", Kind.DISH, 6, 0.4F, "minecraft:bowl", false, 16, 0, false, "sweets"),
        new ItemDef("orange_juice", Kind.DISH, 4, 0.4F, "minecraft:glass_bottle", true, 16, 0, false, "drinks"),
        new ItemDef("lemonade", Kind.DISH, 3, 0.3F, "minecraft:glass_bottle", true, 16, 0, false, "drinks"),
        new ItemDef("strawberry_milkshake", Kind.DISH, 5, 0.4F, "minecraft:glass_bottle", true, 16, 0, false, "drinks"),
        new ItemDef("fruit_smoothie", Kind.DISH, 6, 0.5F, "minecraft:glass_bottle", true, 16, 0, false, "drinks"),
        new ItemDef("sugarcane_juice", Kind.DISH, 4, 0.3F, "minecraft:glass_bottle", true, 16, 0, false, "drinks"),
        new ItemDef("coffee", Kind.DISH, 2, 0.4F, "culinaria:dirty_cup", true, 16, 0, false, "drinks"),
        new ItemDef("cafe_com_leite", Kind.DISH, 4, 0.5F, "culinaria:dirty_cup", true, 16, 0, false, "drinks"),
        new ItemDef("hot_chocolate", Kind.DISH, 5, 0.5F, "culinaria:dirty_cup", true, 16, 0, false, "drinks"),
        new ItemDef("lemon_honey_tea", Kind.DISH, 3, 0.4F, "culinaria:dirty_cup", true, 16, 0, false, "drinks"));

    public static final List<CropDef> CROPS = List.of(
        new CropDef("tomato_crop", "tomato", "tomato_seeds"),
        new CropDef("onion_crop", "onion", "onion_seeds"),
        new CropDef("garlic_crop", "garlic", "garlic_seeds"),
        new CropDef("lettuce_crop", "lettuce", "lettuce_seeds"),
        new CropDef("corn_crop", "corn", "corn_seeds"),
        new CropDef("rice_crop", "rice", "rice_seeds"),
        new CropDef("beans_crop", "beans", "beans_seeds"),
        new CropDef("bell_pepper_crop", "bell_pepper", "bell_pepper_seeds"),
        new CropDef("basil_crop", "basil", "basil_seeds"),
        new CropDef("parsley_crop", "parsley", "parsley_seeds"),
        new CropDef("strawberry_crop", "strawberry", "strawberry_seeds"),
        new CropDef("coffee_crop", "coffee_beans", "coffee_beans_seeds"),
        new CropDef("cassava_crop", "cassava", "cassava_seeds"),
        new CropDef("kale_crop", "kale", "kale_seeds"));

    public static final List<TreeDef> TREES = List.of(
        new TreeDef("apple", "apple_leaves", "apple_sapling", "minecraft:apple", "apple_tree"),
        new TreeDef("orange", "orange_leaves", "orange_sapling", "culinaria:orange", "orange_tree"),
        new TreeDef("lemon", "lemon_leaves", "lemon_sapling", "culinaria:lemon", "lemon_tree"),
        new TreeDef("peach", "peach_leaves", "peach_sapling", "culinaria:peach", "peach_tree"),
        new TreeDef("cherry", "cherry_leaves", "cherry_sapling", "culinaria:cherry", "cherry_tree"));

    public static final List<FurnitureDef> FURNITURE = List.of(
        new FurnitureDef("cutting_board", "cutting_board", 0, true),
        new FurnitureDef("sink", "sink", 0, true),
        new FurnitureDef("mill", "mill", 0, true),
        new FurnitureDef("counter", "counter", 0, true),
        new FurnitureDef("stove", "stove", 0, true),
        new FurnitureDef("oven", "oven", 0, true),
        new FurnitureDef("blender", "blender", 0, true),
        new FurnitureDef("fridge", "fridge", 3, true),
        new FurnitureDef("cabinet", null, 2, true),
        new FurnitureDef("pantry", null, 4, true));

    /** Sementes que caem da grama: as raras pesam 1, as demais 3. */
    public static final List<String> GRASS_SEEDS_COMMON = List.of("tomato_seeds", "onion_seeds", "garlic_seeds", "lettuce_seeds", "corn_seeds", "rice_seeds", "beans_seeds", "bell_pepper_seeds", "basil_seeds", "parsley_seeds", "strawberry_seeds", "kale_seeds");
    public static final List<String> GRASS_SEEDS_RARE = List.of("coffee_beans_seeds", "cassava_seeds");

    /** Categorias dos pratos, na ordem do livro, e a dos preparos. */
    public static final List<String> CATEGORIES = List.of("breads_pasta", "breakfast", "meat", "fish", "mains", "soups", "sides", "vegetables", "desserts", "sweets", "drinks");
    public static final String PREP_CATEGORY = "prep";
    public static final List<String> CATEGORY_ICONS = List.of("pizza_margherita", "fried_egg", "roast_chicken", "grilled_salmon", "feijoada", "tomato_soup", "fries", "garden_salad", "chocolate_cake", "apple_pie", "orange_juice");

    /** Nomes dos GameTests (um test_instance gerado para cada). */
    public static final List<String> GAME_TESTS = List.of("crop_grows_and_drops", "fruit_harvest_regrows", "fruit_leaves_persist", "stove_cooks_recipe", "station_status_messages", "station_keeps_items_on_close", "station_persistence", "oven_bakes_with_fuel", "counter_assembles_dish", "cutting_board_cuts", "sink_washes_and_fills", "mill_grinds", "fridge_chills", "storage_persists", "catalog_has_100_dishes", "dish_returns_container", "sapling_grows_fruit_tree", "remainders_returned");

    public static final int DISH_COUNT = 100;

    private GeneratedContent() {}
}
