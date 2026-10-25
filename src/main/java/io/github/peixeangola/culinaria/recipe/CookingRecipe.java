package io.github.peixeangola.culinaria.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.peixeangola.culinaria.content.GeneratedContent;
import io.github.peixeangola.culinaria.core.CookingMatcher;
import io.github.peixeangola.culinaria.core.Station;
import io.github.peixeangola.culinaria.registry.ModRecipes;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

/**
 * Receita de culinária (tipo culinaria:cooking), uma só para todas as estações. JSON:
 * <pre>{"type":"culinaria:cooking","station":"stove","utensil":"culinaria:cooking_pot",
 *  "ingredients":[{"item":"culinaria:tomato","count":3}],"container":"minecraft:bowl",
 *  "result":{"id":"culinaria:tomato_soup","count":2},"time":220,"category":"soups"}</pre>
 * time é em ticks nas estações com interface e na geladeira, em cortes na tábua e em voltas no moedor. O recipiente é
 * gasto um por unidade produzida. Ingredientes com sobra (balde, frasco) devolvem a sobra.
 */
public record CookingRecipe(Station station, Optional<Item> utensil, List<CountedItem> ingredients, Optional<Item> container,
                            Item result, int resultCount, int time, String category) implements Recipe<CookingInput> {

    /** Um ingrediente e a quantidade gasta por preparo. */
    public record CountedItem(Item item, int count) {
        public static final Codec<CountedItem> CODEC = RecordCodecBuilder.create(i -> i.group(
            BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(CountedItem::item),
            Codec.intRange(1, 64).optionalFieldOf("count", 1).forGetter(CountedItem::count)
        ).apply(i, CountedItem::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, CountedItem> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.registry(Registries.ITEM), CountedItem::item,
            ByteBufCodecs.VAR_INT, CountedItem::count,
            CountedItem::new);
    }

    private record Result(Item item, int count) {
        static final Codec<Result> CODEC = RecordCodecBuilder.create(i -> i.group(
            BuiltInRegistries.ITEM.byNameCodec().fieldOf("id").forGetter(Result::item),
            Codec.intRange(1, 64).optionalFieldOf("count", 1).forGetter(Result::count)
        ).apply(i, Result::new));
    }

    public static final Codec<Station> STATION_CODEC = Codec.STRING.comapFlatMap(
        id -> Station.byId(id).map(DataResult::success).orElseGet(() -> DataResult.error(() -> "estação desconhecida: " + id)),
        Station::id);

    public static final MapCodec<CookingRecipe> CODEC = RecordCodecBuilder.<CookingRecipe>mapCodec(i -> i.group(
        STATION_CODEC.fieldOf("station").forGetter(CookingRecipe::station),
        BuiltInRegistries.ITEM.byNameCodec().optionalFieldOf("utensil").forGetter(CookingRecipe::utensil),
        CountedItem.CODEC.listOf(1, 6).fieldOf("ingredients").forGetter(CookingRecipe::ingredients),
        BuiltInRegistries.ITEM.byNameCodec().optionalFieldOf("container").forGetter(CookingRecipe::container),
        Result.CODEC.fieldOf("result").forGetter(r -> new Result(r.result(), r.resultCount())),
        Codec.intRange(1, 72_000).fieldOf("time").forGetter(CookingRecipe::time),
        Codec.STRING.fieldOf("category").forGetter(CookingRecipe::category)
    ).apply(i, (station, utensil, ingredients, container, result, time, category) ->
        new CookingRecipe(station, utensil, ingredients, container, result.item(), result.count(), time, category)))
        .validate(CookingRecipe::validate);

    public static final StreamCodec<RegistryFriendlyByteBuf, CookingRecipe> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.STRING_UTF8.map(id -> Station.byId(id).orElseThrow(), Station::id), CookingRecipe::station,
        ByteBufCodecs.optional(ByteBufCodecs.registry(Registries.ITEM)), CookingRecipe::utensil,
        CountedItem.STREAM_CODEC.apply(ByteBufCodecs.list()), CookingRecipe::ingredients,
        ByteBufCodecs.optional(ByteBufCodecs.registry(Registries.ITEM)), CookingRecipe::container,
        ByteBufCodecs.registry(Registries.ITEM), CookingRecipe::result,
        ByteBufCodecs.VAR_INT, CookingRecipe::resultCount,
        ByteBufCodecs.VAR_INT, CookingRecipe::time,
        ByteBufCodecs.STRING_UTF8, CookingRecipe::category,
        CookingRecipe::new);

    /** Estações de clique processam um item por vez e não usam recipiente; ingredientes sem repetição. */
    private static DataResult<CookingRecipe> validate(CookingRecipe recipe) {
        if (!recipe.station().hasMenu() && (recipe.ingredients().size() != 1 || recipe.ingredients().getFirst().count() != 1)) {
            return DataResult.error(() -> recipe.station().id() + " aceita um único item por receita");
        }
        if (recipe.ingredients().stream().map(CountedItem::item).distinct().count() != recipe.ingredients().size()) {
            return DataResult.error(() -> "ingrediente repetido na receita de " + recipe.result());
        }
        return DataResult.success(recipe);
    }

    /** Os ingredientes como pedidos para o {@link CookingMatcher}. */
    public List<CookingMatcher.Need<Item>> needs() {
        return ingredients.stream().map(c -> new CookingMatcher.Need<>(c.item(), c.count())).toList();
    }

    /** Soma dos itens por tipo, ignorando slots vazios. */
    public static Map<Item, Integer> tally(List<ItemStack> stacks) {
        Map<Item, Integer> present = new LinkedHashMap<>();
        for (ItemStack stack : stacks) {
            if (!stack.isEmpty()) {
                present.merge(stack.getItem(), stack.getCount(), Integer::sum);
            }
        }
        return present;
    }

    public CookingMatcher.Result match(List<ItemStack> stacks) {
        return CookingMatcher.match(needs(), tally(stacks));
    }

    /** A estação de clique aceita este item (receita de um item só). */
    public boolean acceptsSingle(Item item) {
        return ingredients.getFirst().item() == item;
    }

    /** Prato do catálogo das 100 (os preparos de ingrediente ficam de fora). */
    public boolean isDish() {
        return !category.equals(GeneratedContent.PREP_CATEGORY);
    }

    public ItemStack resultStack() {
        return new ItemStack(result, resultCount);
    }

    @Override
    public boolean matches(CookingInput input, Level level) {
        return match(input.items()) == CookingMatcher.Result.MATCH;
    }

    @Override
    public ItemStack assemble(CookingInput input) {
        return resultStack();
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public String group() {
        return "";
    }

    @Override
    public RecipeSerializer<CookingRecipe> getSerializer() {
        return ModRecipes.COOKING_SERIALIZER.get();
    }

    @Override
    public RecipeType<CookingRecipe> getType() {
        return ModRecipes.COOKING.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        // Com NOT_PLACEABLE o RecipeManager descarta a receita dos índices
        return PlacementInfo.create(ingredients.stream().map(c -> Ingredient.of(c.item())).toList());
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }
}
