package io.github.peixeangola.culinaria.registry;

import io.github.peixeangola.culinaria.Culinaria;
import io.github.peixeangola.culinaria.recipe.CookingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/** Tipo e serializer culinaria:cooking. */
public final class ModRecipes {
    public static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, Culinaria.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
        DeferredRegister.create(Registries.RECIPE_SERIALIZER, Culinaria.MOD_ID);

    public static final RegistryObject<RecipeType<CookingRecipe>> COOKING = TYPES.register("cooking", () -> new RecipeType<>() {
        @Override
        public String toString() {
            return Culinaria.MOD_ID + ":cooking";
        }
    });
    public static final RegistryObject<RecipeSerializer<CookingRecipe>> COOKING_SERIALIZER = SERIALIZERS.register("cooking",
        () -> new RecipeSerializer<>(CookingRecipe.CODEC, CookingRecipe.STREAM_CODEC));

    private ModRecipes() {}
}
