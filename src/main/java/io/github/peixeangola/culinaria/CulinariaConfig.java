package io.github.peixeangola.culinaria;

import io.github.peixeangola.culinaria.core.FruitRules;
import net.minecraftforge.common.ForgeConfigSpec;

/** Configuração comum (config/culinaria-common.toml), carregada na inicialização. */
public final class CulinariaConfig {
    public static final ForgeConfigSpec SPEC;
    private static final ForgeConfigSpec.IntValue FRUIT_GROWTH_CHANCE;
    private static final ForgeConfigSpec.DoubleValue GRASS_SEED_CHANCE;
    private static final ForgeConfigSpec.DoubleValue COOKING_SPEED;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("fruit_trees");
        FRUIT_GROWTH_CHANCE = builder
            .comment("A cada random tick, uma folha frutífera avança uma idade (flor, fruto verde, maduro) com chance de 1 em N.",
                "Com o random tick padrão (3), N = 2 leva cerca de 7 minutos da colheita até a fruta madura de novo.")
            .defineInRange("growth_chance", FruitRules.DEFAULT_GROWTH_CHANCE, 1, 1000);
        builder.pop();
        builder.push("crops");
        GRASS_SEED_CHANCE = builder
            .comment("Chance de uma grama quebrada soltar uma semente do Culinária (além do trigo vanilla).")
            .defineInRange("grass_seed_chance", 0.10D, 0.0D, 1.0D);
        builder.pop();
        builder.push("kitchen");
        COOKING_SPEED = builder
            .comment("Multiplicador da velocidade da bancada, fogão, forno e liquidificador (2.0 = metade do tempo).")
            .defineInRange("cooking_speed", 1.0D, 0.1D, 10.0D);
        builder.pop();
        SPEC = builder.build();
    }

    private CulinariaConfig() {}

    public static int fruitGrowthChance() {
        return SPEC.isLoaded() ? FRUIT_GROWTH_CHANCE.get() : FruitRules.DEFAULT_GROWTH_CHANCE;
    }

    public static float grassSeedChance() {
        return SPEC.isLoaded() ? GRASS_SEED_CHANCE.get().floatValue() : 0.10F;
    }

    public static double cookingSpeed() {
        return SPEC.isLoaded() ? COOKING_SPEED.get() : 1.0D;
    }
}
