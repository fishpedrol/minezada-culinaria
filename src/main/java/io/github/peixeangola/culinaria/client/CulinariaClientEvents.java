package io.github.peixeangola.culinaria.client;

import io.github.peixeangola.culinaria.Culinaria;
import io.github.peixeangola.culinaria.item.RecipeBookItem;
import io.github.peixeangola.culinaria.registry.ModBlockEntities;
import io.github.peixeangola.culinaria.registry.ModMenus;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Registros só do cliente. O Forge acha esta classe pela anotação e só a carrega com {@link Dist#CLIENT}; o código comum
 * nunca a cita, então o servidor dedicado não carrega nada de net.minecraft.client.
 */
@Mod.EventBusSubscriber(modid = Culinaria.MOD_ID, value = Dist.CLIENT)
public final class CulinariaClientEvents {
    private CulinariaClientEvents() {}

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenus.COOKING_STATION.get(), CookingStationScreen::new);
            MenuScreens.register(ModMenus.STORAGE.get(), StorageScreen::new);
        });
        RecipeBookItem.opener = RecipeBookScreen::open;
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.CUTTING_BOARD.get(), context -> new SingleItemRenderer<>(context, true, 0.12F));
        event.registerBlockEntityRenderer(ModBlockEntities.MILL.get(), context -> new SingleItemRenderer<>(context, false, 0.78F));
    }
}
