package io.github.peixeangola.culinaria.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.peixeangola.culinaria.content.GeneratedContent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Consistência do catálogo como o jogo vai carregar: lê os JSON gerados em src/generated/resources (não o gerador) e
 * confere a contagem de pratos, as cadeias de produção, as estações, os recursos e as traduções.
 */
class CatalogDataTest {
    private static final Path RES = Path.of("src/generated/resources");
    private static final Path ASSETS = RES.resolve("assets/culinaria");
    private static final Path DATA = RES.resolve("data/culinaria");
    private static final Set<String> MENU_STATIONS = Set.of("counter", "stove", "oven", "blender");
    private static final Set<String> CLICK_STATIONS = Set.of("cutting_board", "sink", "mill", "fridge");

    private static final List<JsonObject> recipes = new ArrayList<>();
    private static final Set<String> modItems = new HashSet<>();
    private static JsonObject ptBr;
    private static JsonObject enUs;

    @BeforeAll
    static void load() throws IOException {
        try (Stream<Path> files = Files.walk(DATA.resolve("recipe/cooking"))) {
            for (Path file : files.filter(f -> f.toString().endsWith(".json")).toList()) {
                recipes.add(read(file));
            }
        }
        GeneratedContent.ITEMS.forEach(i -> modItems.add("culinaria:" + i.id()));
        GeneratedContent.CROPS.forEach(c -> modItems.add("culinaria:" + c.seeds()));
        GeneratedContent.TREES.forEach(t -> {
            modItems.add("culinaria:" + t.sapling());
            modItems.add("culinaria:" + t.leaves());
        });
        GeneratedContent.FURNITURE.forEach(f -> modItems.add("culinaria:" + f.id()));
        ptBr = read(ASSETS.resolve("lang/pt_br.json"));
        enUs = read(ASSETS.resolve("lang/en_us.json"));
    }

    private static JsonObject read(Path file) throws IOException {
        return JsonParser.parseString(Files.readString(file, StandardCharsets.UTF_8)).getAsJsonObject();
    }

    private static boolean isDish(JsonObject recipe) {
        return !recipe.get("category").getAsString().equals(GeneratedContent.PREP_CATEGORY);
    }

    private static String result(JsonObject recipe) {
        return recipe.getAsJsonObject("result").get("id").getAsString();
    }

    private static List<String> ingredients(JsonObject recipe) {
        List<String> list = new ArrayList<>();
        recipe.getAsJsonArray("ingredients").forEach(e -> list.add(e.getAsJsonObject().get("item").getAsString()));
        return list;
    }

    @Test
    void exactlyOneHundredDistinctDishes() {
        List<JsonObject> dishes = recipes.stream().filter(CatalogDataTest::isDish).toList();
        assertEquals(100, dishes.size());
        assertEquals(100, dishes.stream().map(CatalogDataTest::result).distinct().count());
        assertEquals(100, GeneratedContent.DISH_COUNT);
        assertEquals(100, GeneratedContent.ITEMS.stream().filter(i -> i.kind() == GeneratedContent.Kind.DISH).count());
    }

    @Test
    void elevenCategoriesAllUsedAndBalanced() {
        Map<String, Integer> perCategory = new HashMap<>();
        recipes.stream().filter(CatalogDataTest::isDish).forEach(r -> perCategory.merge(r.get("category").getAsString(), 1, Integer::sum));
        assertEquals(new TreeSet<>(GeneratedContent.CATEGORIES), new TreeSet<>(perCategory.keySet()));
        assertEquals(11, perCategory.size());
        perCategory.forEach((cat, n) -> assertTrue(n >= 8 && n <= 10, cat + " tem " + n + " pratos"));
    }

    @Test
    void everyReferencedItemExists() {
        for (JsonObject recipe : recipes) {
            List<String> refs = new ArrayList<>(ingredients(recipe));
            refs.add(result(recipe));
            if (recipe.has("utensil")) {
                refs.add(recipe.get("utensil").getAsString());
            }
            if (recipe.has("container")) {
                refs.add(recipe.get("container").getAsString());
            }
            for (String ref : refs) {
                assertTrue(ref.startsWith("minecraft:") || modItems.contains(ref), "item desconhecido " + ref + " em " + result(recipe));
            }
        }
    }

    @Test
    void stationRulesHold() {
        Map<String, String> signatures = new HashMap<>();
        for (JsonObject recipe : recipes) {
            String station = recipe.get("station").getAsString();
            assertTrue(MENU_STATIONS.contains(station) || CLICK_STATIONS.contains(station), "estação " + station);
            List<String> items = ingredients(recipe);
            if (CLICK_STATIONS.contains(station)) {
                assertEquals(1, items.size(), result(recipe) + ": um ingrediente por vez em " + station);
                assertTrue(!recipe.has("container"), result(recipe) + ": " + station + " sem recipiente");
            } else {
                assertTrue(items.size() <= 6, result(recipe) + ": mais de 6 ingredientes");
            }
            if (station.equals("stove")) {
                String utensil = recipe.get("utensil").getAsString();
                assertTrue(utensil.equals("culinaria:cooking_pot") || utensil.equals("culinaria:frying_pan"), result(recipe));
            }
            if (station.equals("oven")) {
                assertEquals("culinaria:baking_tray", recipe.get("utensil").getAsString(), result(recipe));
            }
            String signature = station + "|" + new TreeSet<>(items);
            String previous = signatures.put(signature, result(recipe));
            assertTrue(previous == null, "mesmos ingredientes em " + station + ": " + previous + " e " + result(recipe));
        }
    }

    /** Fontes cruas: o que tem "onde conseguir" no livro. A partir delas toda receita precisa ser alcançável. */
    @Test
    void everyRecipeIsReachableFromWorldSources() {
        Set<String> obtainable = new HashSet<>();
        for (String key : ptBr.keySet()) {
            if (key.startsWith("culinaria.source.")) {
                String rest = key.substring("culinaria.source.".length());
                int dot = rest.indexOf('.');
                obtainable.add(rest.substring(0, dot) + ":" + rest.substring(dot + 1));
            }
        }
        obtainable.add("minecraft:bowl");
        obtainable.add("minecraft:glass_bottle");
        boolean changed = true;
        while (changed) {
            changed = false;
            for (JsonObject recipe : recipes) {
                if (!obtainable.contains(result(recipe)) && obtainable.containsAll(ingredients(recipe))) {
                    obtainable.add(result(recipe));
                    changed = true;
                }
            }
        }
        for (JsonObject recipe : recipes) {
            assertTrue(obtainable.contains(result(recipe)), "sem cadeia viável: " + result(recipe));
        }
    }

    @Test
    void everyModIngredientIsUsedSomewhere() {
        Set<String> used = new HashSet<>();
        recipes.forEach(r -> used.addAll(ingredients(r)));
        for (GeneratedContent.ItemDef def : GeneratedContent.ITEMS) {
            if (def.kind() == GeneratedContent.Kind.INGREDIENT) {
                assertTrue(used.contains("culinaria:" + def.id()), "ingrediente sem uso: " + def.id());
            }
        }
    }

    @Test
    void dishesAreFoodWithSensibleValues() {
        for (GeneratedContent.ItemDef def : GeneratedContent.ITEMS) {
            if (def.kind() == GeneratedContent.Kind.DISH) {
                assertTrue(def.nutrition() >= 2 && def.nutrition() <= 20, def.id() + " fome " + def.nutrition());
                assertTrue(def.saturation() > 0 && def.saturation() <= 1.2F, def.id() + " saturação " + def.saturation());
                assertTrue(def.category() != null, def.id() + " sem categoria");
            }
        }
    }

    @Test
    void everyItemHasModelTextureAndTranslations() throws IOException {
        for (String id : modItems) {
            String path = id.substring("culinaria:".length());
            Path itemDef = ASSETS.resolve("items/" + path + ".json");
            assertTrue(Files.exists(itemDef), "sem items/" + path + ".json");
            String model = read(itemDef).getAsJsonObject("model").get("model").getAsString();
            Path modelFile = ASSETS.resolve("models/" + model.substring("culinaria:".length()) + ".json");
            assertTrue(Files.exists(modelFile), "modelo ausente " + model);
            assertTexturesExist(read(modelFile), model);
            boolean named = ptBr.has("item.culinaria." + path) || ptBr.has("block.culinaria." + path);
            assertTrue(named, "sem nome em pt_br: " + path);
            assertTrue(enUs.has("item.culinaria." + path) || enUs.has("block.culinaria." + path), "sem nome em en_us: " + path);
        }
    }

    private static void assertTexturesExist(JsonObject model, String name) {
        if (!model.has("textures")) {
            return;
        }
        for (Map.Entry<String, JsonElement> texture : model.getAsJsonObject("textures").entrySet()) {
            String ref = texture.getValue().getAsString();
            if (ref.startsWith("culinaria:")) {
                Path png = ASSETS.resolve("textures/" + ref.substring("culinaria:".length()) + ".png");
                assertTrue(Files.exists(png), name + " usa textura ausente " + ref);
            }
        }
    }

    @Test
    void everyBlockstateModelExistsAndBlockModelsUseBlockTextures() throws IOException {
        try (Stream<Path> files = Files.list(ASSETS.resolve("blockstates"))) {
            for (Path file : files.toList()) {
                for (Map.Entry<String, JsonElement> variant : read(file).getAsJsonObject("variants").entrySet()) {
                    String model = variant.getValue().getAsJsonObject().get("model").getAsString();
                    Path modelFile = ASSETS.resolve("models/" + model.substring("culinaria:".length()) + ".json");
                    assertTrue(Files.exists(modelFile), file.getFileName() + " → " + model);
                    JsonObject json = read(modelFile);
                    assertTexturesExist(json, model);
                    if (json.has("textures")) {
                        json.getAsJsonObject("textures").entrySet().forEach(t -> assertTrue(!t.getValue().getAsString().contains(":item/"),
                            model + " usa textura do atlas de itens (o 26.3 recusa)"));
                    }
                }
            }
        }
    }

    @Test
    void everyBlockHasLootTable() {
        List<String> blocks = new ArrayList<>();
        GeneratedContent.CROPS.forEach(c -> blocks.add(c.block()));
        GeneratedContent.TREES.forEach(t -> {
            blocks.add(t.leaves());
            blocks.add(t.sapling());
        });
        GeneratedContent.FURNITURE.forEach(f -> blocks.add(f.id()));
        for (String block : blocks) {
            assertTrue(Files.exists(DATA.resolve("loot_table/blocks/" + block + ".json")), "sem loot table: " + block);
        }
    }

    @Test
    void translationsMatchBetweenLanguagesAndUiKeysExist() {
        assertEquals(new TreeSet<>(ptBr.keySet()), new TreeSet<>(enUs.keySet()));
        for (String key : List.of("culinaria.book.title", "culinaria.book.search", "culinaria.status.no_recipe", "culinaria.status.needs_utensil",
            "culinaria.status.no_fuel", "culinaria.sink.empty", "culinaria.board.needs_knife", "culinaria.mill.progress")) {
            assertTrue(ptBr.has(key), "falta " + key);
        }
        for (String category : GeneratedContent.CATEGORIES) {
            assertTrue(ptBr.has("culinaria.category." + category), "categoria sem nome: " + category);
        }
    }

    @Test
    void fruitTreesGenerateWithFruitingLeavesAndBiomeModifiers() throws IOException {
        for (GeneratedContent.TreeDef tree : GeneratedContent.TREES) {
            String feature = Files.readString(DATA.resolve("worldgen/feature/" + tree.feature() + ".json"));
            assertTrue(feature.contains("\"fruiting\": \"true\"") && feature.contains("\"fruiting\": \"false\""), tree.id());
            assertTrue(Files.exists(DATA.resolve("worldgen/placed_feature/" + tree.id() + "_tree_wild.json")), tree.id());
            try (Stream<Path> modifiers = Files.list(DATA.resolve("forge/biome_modifier"))) {
                assertTrue(modifiers.anyMatch(p -> p.getFileName().toString().startsWith(tree.id() + "_trees_")), "sem biome modifier: " + tree.id());
            }
            assertTrue(Files.exists(DATA.resolve("recipe/" + tree.sapling() + ".json")), "muda sem receita de bancada: " + tree.id());
        }
    }

    @Test
    void gameTestInstancesMatchFunctions() {
        for (String test : GeneratedContent.GAME_TESTS) {
            assertTrue(Files.exists(DATA.resolve("test_instance/" + test + ".json")), test);
        }
    }
}
