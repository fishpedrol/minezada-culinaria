package io.github.peixeangola.culinaria.block;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.slf4j.Logger;

/**
 * Uma pilha e um contador de trabalho (cortes na tábua, voltas no moedor), salvos no mundo e mandados ao cliente a cada
 * mudança: o cliente desenha o item e prevê o clique sem esperar o servidor. Ao quebrar o bloco a pilha cai no chão.
 */
public abstract class SingleItemBlockEntity extends BlockEntity {
    private static final Logger LOGGER = LogUtils.getLogger();

    private ItemStack item = ItemStack.EMPTY;
    private int work;

    protected SingleItemBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ItemStack item() {
        return item;
    }

    public int work() {
        return work;
    }

    public void setItem(ItemStack stack) {
        item = stack;
        work = 0;
        changed();
    }

    public void setWork(int work) {
        this.work = work;
        changed();
    }

    /** Tira a pilha inteira (vazia se não havia). */
    public ItemStack take() {
        ItemStack taken = item;
        item = ItemStack.EMPTY;
        work = 0;
        changed();
        return taken;
    }

    private void changed() {
        setChanged();
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        if (!item.isEmpty()) {
            output.store("item", ItemStack.CODEC, item);
        }
        output.putInt("work", work);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        item = input.read("item", ItemStack.CODEC).orElse(ItemStack.EMPTY);
        work = input.getIntOr("work", 0);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(problemPath(), LOGGER)) {
            TagValueOutput output = TagValueOutput.createWithContext(reporter, registries);
            saveAdditional(output);
            return output.buildResult();
        }
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        super.preRemoveSideEffects(pos, state);
        if (level != null && !item.isEmpty()) {
            Containers.dropItemStack(level, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, item);
            item = ItemStack.EMPTY;
        }
    }
}
