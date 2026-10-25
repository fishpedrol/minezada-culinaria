package io.github.peixeangola.culinaria.block;

import io.github.peixeangola.culinaria.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

/**
 * Geladeira, armário e despensa: inventário que fica salvo no bloco e cai no chão ao quebrar. A geladeira só aceita
 * comida e ingredientes (tag culinaria:fridge_storable) e gela com o tempo as sobremesas que pedem geladeira.
 */
public class StorageBlock extends FurnitureBlock implements EntityBlock {
    private final int rows;
    private final boolean fridge;

    public StorageBlock(int rows, boolean fridge, Properties properties) {
        super(properties);
        this.rows = rows;
        this.fridge = fridge;
    }

    public int rows() {
        return rows;
    }

    public boolean isFridge() {
        return fridge;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (player instanceof ServerPlayer serverPlayer && level.getBlockEntity(pos) instanceof StorageBlockEntity storage) {
            serverPlayer.openMenu(storage, buf -> {
                buf.writeVarInt(rows);
                buf.writeBoolean(fridge);
            });
            level.playSound(null, pos, fridge ? SoundEvents.IRON_DOOR_OPEN : SoundEvents.BARREL_OPEN, SoundSource.BLOCKS, 0.5F, 1.1F);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new StorageBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (!fridge || level.isClientSide() || type != ModBlockEntities.STORAGE.get()) {
            return null;
        }
        return (tickLevel, pos, tickState, entity) -> ((StorageBlockEntity) entity).fridgeTick((ServerLevel) tickLevel);
    }
}
