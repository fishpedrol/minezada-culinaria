package io.github.peixeangola.culinaria.menu;

import io.github.peixeangola.culinaria.block.CookingStationBlockEntity;
import io.github.peixeangola.culinaria.core.CookingStatus;
import io.github.peixeangola.culinaria.core.Station;
import io.github.peixeangola.culinaria.registry.ModMenus;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/**
 * Menu das estações com interface. Só os slots que a estação usa entram no menu (a bancada e o liquidificador não têm
 * combustível; o liquidificador não tem utensílio). O servidor usa o inventário da BlockEntity; o cliente, uma cópia
 * vazia que o vanilla sincroniza, mais o ContainerData com progresso, combustível, status e a receita reconhecida.
 */
public class CookingStationMenu extends AbstractContainerMenu {
    private final Container container;
    private final ContainerData data;
    private final Station station;
    /** Índice no container de cada slot do menu que é da estação (os demais são do jogador). */
    private final List<Integer> stationSlots = new ArrayList<>();

    /** Lado do cliente: a estação vem no pacote de abertura. */
    public CookingStationMenu(int containerId, Inventory inventory, FriendlyByteBuf extra) {
        this(containerId, inventory, new SimpleContainer(CookingStationBlockEntity.SIZE),
            new SimpleContainerData(CookingStationBlockEntity.DATA_SIZE), Station.byId(extra.readUtf()).orElse(Station.COUNTER));
    }

    public CookingStationMenu(int containerId, Inventory inventory, Container container, ContainerData data, Station station) {
        super(ModMenus.COOKING_STATION.get(), containerId);
        checkContainerSize(container, CookingStationBlockEntity.SIZE);
        checkContainerDataCount(data, CookingStationBlockEntity.DATA_SIZE);
        this.container = container;
        this.data = data;
        this.station = station;
        container.startOpen(inventory.player);
        for (int slot = 0; slot < CookingStationBlockEntity.SIZE; slot++) {
            if (!usesSlot(station, slot)) {
                continue;
            }
            int index = slot;
            addSlot(new Slot(container, slot, KitchenSlots.x(slot), KitchenSlots.y(slot)) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return KitchenSlots.mayPlace(station, index, stack);
                }

                @Override
                public int getMaxStackSize() {
                    return index == CookingStationBlockEntity.UTENSIL ? 1 : super.getMaxStackSize();
                }
            });
            stationSlots.add(slot);
        }
        addStandardInventorySlots(inventory, 8, KitchenSlots.INVENTORY_TOP);
        addDataSlots(data);
    }

    public static boolean usesSlot(Station station, int slot) {
        return switch (slot) {
            case CookingStationBlockEntity.UTENSIL -> KitchenSlots.hasUtensilSlot(station);
            case CookingStationBlockEntity.FUEL -> station.usesFuel();
            default -> true;
        };
    }

    public Station station() {
        return station;
    }

    public CookingStatus status() {
        return CookingStatus.byOrdinal(data.get(CookingStationBlockEntity.DATA_STATUS));
    }

    public int recipeIndex() {
        return data.get(CookingStationBlockEntity.DATA_RECIPE);
    }

    public float progress() {
        int total = data.get(CookingStationBlockEntity.DATA_TOTAL);
        return total <= 0 ? 0.0F : Math.min(1.0F, data.get(CookingStationBlockEntity.DATA_PROGRESS) / (float) total);
    }

    public float burnLeft() {
        int total = data.get(CookingStationBlockEntity.DATA_BURN_TOTAL);
        return total <= 0 ? 0.0F : Math.min(1.0F, data.get(CookingStationBlockEntity.DATA_BURN) / (float) total);
    }

    public boolean isLit() {
        return data.get(CookingStationBlockEntity.DATA_BURN) > 0;
    }

    /** Slot do container de um slot do menu da estação, ou -1 se for do jogador. */
    public int containerSlot(int menuSlot) {
        return menuSlot < stationSlots.size() ? stationSlots.get(menuSlot) : -1;
    }

    /**
     * Shift-clique: da estação vai para o inventário; do inventário vai para o slot do papel do item (combustível,
     * utensílio, recipiente) e, se não for nenhum desses, para os ingredientes.
     */
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index);
        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }
        ItemStack stack = slot.getItem();
        ItemStack original = stack.copy();
        int stationCount = stationSlots.size();
        if (index < stationCount) {
            if (!moveItemStackTo(stack, stationCount, slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else {
            boolean moved = false;
            for (int special : new int[] {CookingStationBlockEntity.FUEL, CookingStationBlockEntity.UTENSIL, CookingStationBlockEntity.CONTAINER}) {
                int menuIndex = stationSlots.indexOf(special);
                if (menuIndex >= 0 && KitchenSlots.mayPlace(station, special, stack)) {
                    moved = moveItemStackTo(stack, menuIndex, menuIndex + 1, false);
                    break;
                }
            }
            if (!moved && !moveItemStackTo(stack, 0, CookingStationBlockEntity.INGREDIENT_SLOTS, false)) {
                return ItemStack.EMPTY;
            }
        }
        if (stack.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }
        if (stack.getCount() == original.getCount()) {
            return ItemStack.EMPTY;
        }
        slot.onTake(player, stack);
        return original;
    }

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        container.stopOpen(player);
    }
}
