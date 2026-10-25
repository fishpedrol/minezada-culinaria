package io.github.peixeangola.culinaria.registry;

import io.github.peixeangola.culinaria.Culinaria;
import io.github.peixeangola.culinaria.menu.CookingStationMenu;
import io.github.peixeangola.culinaria.menu.StorageMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/** Menus com dados extras na abertura (a estação; linhas e filtro dos móveis). */
public final class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, Culinaria.MOD_ID);

    public static final RegistryObject<MenuType<CookingStationMenu>> COOKING_STATION =
        MENUS.register("cooking_station", () -> IForgeMenuType.create(CookingStationMenu::new));
    public static final RegistryObject<MenuType<StorageMenu>> STORAGE = MENUS.register("storage", () -> IForgeMenuType.create(StorageMenu::client));

    private ModMenus() {}
}
