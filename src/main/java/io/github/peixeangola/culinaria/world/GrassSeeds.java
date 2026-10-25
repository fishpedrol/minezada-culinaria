package io.github.peixeangola.culinaria.world;

import io.github.peixeangola.culinaria.CulinariaConfig;
import io.github.peixeangola.culinaria.content.GeneratedContent;
import io.github.peixeangola.culinaria.registry.ModItems;
import java.util.Set;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.minecraftforge.event.LootTableLoadEvent;

/**
 * Sementes do Culinária na grama: um pool a mais nas loot tables da grama baixa e alta, com a chance do config, uma
 * semente por vez; as raras (café e mandioca) pesam 1 e as demais 3. O evento só roda no servidor ao carregar as loot
 * tables (como o SeedLootInjector do mod de referência, porque no 26.3 o evento não dá o registro de tabelas).
 */
public final class GrassSeeds {
    public static final Set<String> TARGETS = Set.of("minecraft:blocks/short_grass", "minecraft:blocks/tall_grass", "minecraft:blocks/fern");
    public static final String POOL_NAME = "culinaria:grass_seeds";

    private GrassSeeds() {}

    public static LootPool pool() {
        LootPool.Builder pool = LootPool.lootPool()
            .name(POOL_NAME)
            .setRolls(ContextIntProviders.exactly(1))
            .when(LootItemRandomChanceCondition.randomChance(CulinariaConfig.grassSeedChance()));
        for (String seeds : GeneratedContent.GRASS_SEEDS_COMMON) {
            pool.add(LootItem.lootTableItem(ModItems.item(seeds)).setWeight(3));
        }
        for (String seeds : GeneratedContent.GRASS_SEEDS_RARE) {
            pool.add(LootItem.lootTableItem(ModItems.item(seeds)).setWeight(1));
        }
        return pool.build();
    }

    public static boolean isTarget(Identifier table) {
        return TARGETS.contains(table.toString());
    }

    public static void onLootTableLoad(LootTableLoadEvent event) {
        LootTable table = event.getTable();
        if (table != null && isTarget(event.getName())) {
            table.addPool(pool());
        }
    }
}
