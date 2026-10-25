package io.github.peixeangola.culinaria.menu;

import io.github.peixeangola.culinaria.block.StorageBlockEntity;
import io.github.peixeangola.culinaria.registry.ModMenus;
import io.github.peixeangola.culinaria.registry.ModTags;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/**
 * Menu dos móveis de guardar, com o layout do baú vanilla (9 colunas, até 6 linhas). Na geladeira os slots só aceitam
 * o que a tag culinaria:fridge_storable lista, inclusive pelo shift-clique e por funis.
 */
public class StorageMenu extends AbstractContainerMenu {
    private final Container container;
    private final int rows;
    private final boolean fridge;

    /** Lado do cliente: linhas e geladeira vêm no pacote de abertura. */
    public static StorageMenu client(int containerId, Inventory inventory, FriendlyByteBuf extra) {
        int rows = extra.readVarInt();
        boolean fridge = extra.readBoolean();
        return new StorageMenu(containerId, inventory, new SimpleContainer(rows * 9), rows, fridge);
    }

    public static StorageMenu server(int containerId, Inventory inventory, StorageBlockEntity storage) {
        return new StorageMenu(containerId, inventory, storage, storage.rows(), storage.isFridge());
    }

    public StorageMenu(int containerId, Inventory inventory, Container container, int rows, boolean fridge) {
        super(ModMenus.STORAGE.get(), containerId);
        checkContainerSize(container, rows * 9);
        this.container = container;
        this.rows = rows;
        this.fridge = fridge;
        container.startOpen(inventory.player);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(container, col + row * 9, 8 + col * 18, 18 + row * 18) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return !StorageMenu.this.fridge || stack.is(ModTags.FRIDGE_STORABLE);
                    }
                });
            }
        }
        addStandardInventorySlots(inventory, 8, 32 + rows * 18);
    }

    public int rows() {
        return rows;
    }

    public boolean isFridge() {
        return fridge;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index);
        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }
        ItemStack stack = slot.getItem();
        ItemStack original = stack.copy();
        int size = rows * 9;
        if (index < size) {
            if (!moveItemStackTo(stack, size, slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else if (!moveItemStackTo(stack, 0, size, false)) {
            return ItemStack.EMPTY;
        }
        if (stack.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }
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
