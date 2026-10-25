package io.github.peixeangola.culinaria;

import io.github.peixeangola.culinaria.catalog.RecipeCatalog;
import io.github.peixeangola.culinaria.gametest.CulinariaGameTests;
import io.github.peixeangola.culinaria.network.ModNetwork;
import io.github.peixeangola.culinaria.registry.ModBlockEntities;
import io.github.peixeangola.culinaria.registry.ModBlocks;
import io.github.peixeangola.culinaria.registry.ModCreativeTabs;
import io.github.peixeangola.culinaria.registry.ModItems;
import io.github.peixeangola.culinaria.registry.ModMenus;
import io.github.peixeangola.culinaria.registry.ModRecipes;
import io.github.peixeangola.culinaria.world.GrassSeeds;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// O valor precisa coincidir com o modId em META-INF/mods.toml
@Mod(Culinaria.MOD_ID)
public final class Culinaria {
    public static final String MOD_ID = "culinaria";

    public Culinaria(FMLJavaModLoadingContext context) {
        var modBusGroup = context.getModBusGroup();

        ModBlocks.BLOCKS.register(modBusGroup);
        ModItems.ITEMS.register(modBusGroup);
        ModBlockEntities.BLOCK_ENTITIES.register(modBusGroup);
        ModMenus.MENUS.register(modBusGroup);
        ModRecipes.TYPES.register(modBusGroup);
        ModRecipes.SERIALIZERS.register(modBusGroup);
        ModCreativeTabs.CREATIVE_TABS.register(modBusGroup);
        CulinariaGameTests.TESTS.register(modBusGroup);
        context.registerConfig(ModConfig.Type.COMMON, CulinariaConfig.SPEC);
        ModNetwork.init();

        // Sementes do mod na grama (só no servidor, ao carregar as loot tables)
        LootTableLoadEvent.BUS.addListener(GrassSeeds::onLootTableLoad);
        // Catálogo de receitas: refeito depois de /reload e mandado a quem entra (ou a todos, no /reload)
        OnDatapackSyncEvent.BUS.addListener(Culinaria::onDatapackSync);
        ServerStoppedEvent.BUS.addListener(event -> RecipeCatalog.invalidateServer());
    }

    private static void onDatapackSync(OnDatapackSyncEvent event) {
        if (event.getPlayer() == null) {
            RecipeCatalog.invalidateServer();
        }
        for (ServerPlayer player : event.getPlayers()) {
            ModNetwork.sendCatalog(player);
        }
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
