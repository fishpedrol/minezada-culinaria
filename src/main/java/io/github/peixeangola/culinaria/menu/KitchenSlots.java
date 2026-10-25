package io.github.peixeangola.culinaria.menu;

import io.github.peixeangola.culinaria.block.CookingStationBlockEntity;
import io.github.peixeangola.culinaria.core.Station;
import io.github.peixeangola.culinaria.registry.ModItems;
import java.util.List;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

/**
 * Papel de cada slot das estações com interface (vale para a interface, o shift-clique e os funis) e a posição dele na
 * tela. Os utensílios aceitos dependem da estação; recipientes são prato, xícara, tigela e frasco.
 */
public final class KitchenSlots {
    private KitchenSlots() {}

    public static List<Item> utensils(Station station) {
        return switch (station) {
            case COUNTER -> List.of(ModItems.item("rolling_pin"), ModItems.item("whisk"));
            case STOVE -> List.of(ModItems.item("cooking_pot"), ModItems.item("frying_pan"));
            case OVEN -> List.of(ModItems.item("baking_tray"));
            default -> List.of();
        };
    }

    public static boolean isUtensil(Station station, ItemStack stack) {
        return utensils(station).stream().anyMatch(stack::is);
    }

    public static boolean isContainer(ItemStack stack) {
        return stack.is(ModItems.item("plate")) || stack.is(ModItems.item("cup")) || stack.is(Items.BOWL) || stack.is(Items.GLASS_BOTTLE);
    }

    public static boolean hasUtensilSlot(Station station) {
        return !utensils(station).isEmpty();
    }

    public static boolean mayPlace(Station station, int slot, ItemStack stack) {
        if (slot < CookingStationBlockEntity.INGREDIENT_SLOTS) {
            return true;
        }
        return switch (slot) {
            case CookingStationBlockEntity.UTENSIL -> isUtensil(station, stack);
            case CookingStationBlockEntity.CONTAINER -> isContainer(stack);
            case CookingStationBlockEntity.FUEL -> station.usesFuel() && CookingStationBlockEntity.isFuel(stack);
            default -> false;
        };
    }

    // ---------------------------------------------------------------- posições na tela (relativas ao painel)
    public static final int IMAGE_WIDTH = 176;
    public static final int IMAGE_HEIGHT = 188;
    public static final int INVENTORY_TOP = 106;
    public static final int ARROW_X = 84;
    public static final int ARROW_Y = 27;
    public static final int FLAME_X = 81;
    public static final int FLAME_Y = 64;
    public static final int STATUS_Y = 84;

    public static int x(int slot) {
        if (slot < CookingStationBlockEntity.INGREDIENT_SLOTS) {
            return 22 + (slot % 3) * 18;
        }
        return switch (slot) {
            case CookingStationBlockEntity.UTENSIL -> 22;
            case CookingStationBlockEntity.CONTAINER -> 40;
            case CookingStationBlockEntity.FUEL -> 62;
            case CookingStationBlockEntity.OUTPUT -> 120;
            case CookingStationBlockEntity.LEFTOVER_A -> 120;
            default -> 138;
        };
    }

    public static int y(int slot) {
        if (slot < CookingStationBlockEntity.INGREDIENT_SLOTS) {
            return 18 + (slot / 3) * 18;
        }
        return switch (slot) {
            case CookingStationBlockEntity.UTENSIL, CookingStationBlockEntity.CONTAINER, CookingStationBlockEntity.FUEL -> 64;
            case CookingStationBlockEntity.OUTPUT -> 27;
            default -> 64;
        };
    }
}
