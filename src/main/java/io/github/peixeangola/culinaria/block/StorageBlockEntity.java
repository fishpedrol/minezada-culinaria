package io.github.peixeangola.culinaria.block;

import io.github.peixeangola.culinaria.catalog.RecipeCatalog;
import io.github.peixeangola.culinaria.core.Station;
import io.github.peixeangola.culinaria.menu.StorageMenu;
import io.github.peixeangola.culinaria.recipe.CookingRecipe;
import io.github.peixeangola.culinaria.registry.ModBlockEntities;
import io.github.peixeangola.culinaria.registry.ModTags;
import java.util.Arrays;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

/**
 * Inventário dos móveis (9 × linhas do bloco). Na geladeira, a cada segundo, cada slot com uma receita de geladeira
 * (station fridge, um ingrediente) soma tempo; ao completar, a pilha inteira vira o resultado (uma unidade por
 * unidade). Trocar o item do slot zera o tempo daquele slot.
 */
public class StorageBlockEntity extends BaseContainerBlockEntity {
    /** A geladeira confere uma vez por segundo. */
    public static final int FRIDGE_INTERVAL = 20;

    private NonNullList<ItemStack> items;
    private int[] chill;

    public StorageBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STORAGE.get(), pos, state);
        int size = ((StorageBlock) state.getBlock()).rows() * 9;
        items = NonNullList.withSize(size, ItemStack.EMPTY);
        chill = new int[size];
    }

    public int rows() {
        return ((StorageBlock) getBlockState().getBlock()).rows();
    }

    public boolean isFridge() {
        return ((StorageBlock) getBlockState().getBlock()).isFridge();
    }

    public int chillTicks(int slot) {
        return chill[slot];
    }

    public void fridgeTick(ServerLevel level) {
        if (level.getGameTime() % FRIDGE_INTERVAL != 0) {
            return;
        }
        RecipeCatalog catalog = RecipeCatalog.server(level.getServer());
        boolean changed = false;
        for (int slot = 0; slot < items.size(); slot++) {
            ItemStack stack = items.get(slot);
            Optional<RecipeCatalog.Entry> entry = stack.isEmpty() ? Optional.empty() : catalog.single(Station.FRIDGE, stack.getItem());
            if (entry.isEmpty()) {
                if (chill[slot] != 0) {
                    chill[slot] = 0;
                    changed = true;
                }
                continue;
            }
            CookingRecipe recipe = entry.get().recipe();
            chill[slot] += FRIDGE_INTERVAL;
            changed = true;
            if (chill[slot] >= recipe.time()) {
                ItemStack result = recipe.resultStack();
                result.setCount(Math.min(result.getMaxStackSize(), stack.getCount() * recipe.resultCount()));
                items.set(slot, result);
                chill[slot] = 0;
            }
        }
        if (changed) {
            setChanged();
        }
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        ItemStack before = items.get(slot);
        if (!ItemStack.isSameItem(before, stack)) {
            chill[slot] = 0;
        }
        super.setItem(slot, stack);
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return !isFridge() || stack.is(ModTags.FRIDGE_STORABLE);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    protected Component getDefaultName() {
        return getBlockState().getBlock().getName();
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return StorageMenu.server(containerId, inventory, this);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, items);
        output.putIntArray("chill", chill);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        items = NonNullList.withSize(items.size(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, items);
        int[] saved = input.getIntArray("chill").orElse(new int[0]);
        chill = Arrays.copyOf(saved, items.size());
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        super.preRemoveSideEffects(pos, state);
        if (level != null) {
            Containers.dropContents(level, pos, this);
        }
    }
}
