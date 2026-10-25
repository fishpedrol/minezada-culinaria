package io.github.peixeangola.culinaria.block;

import io.github.peixeangola.culinaria.core.Station;
import io.github.peixeangola.culinaria.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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
 * Bancada, fogão, forno e liquidificador: o clique abre a interface com os slots. O processamento acontece no tick do
 * servidor ({@link CookingStationBlockEntity}); fechar a interface ou sair do jogo não mexe nos itens guardados.
 */
public class CookingStationBlock extends FurnitureBlock implements EntityBlock {
    private final Station station;

    public CookingStationBlock(Station station, Properties properties) {
        super(properties);
        this.station = station;
    }

    public Station station() {
        return station;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (player instanceof ServerPlayer serverPlayer && level.getBlockEntity(pos) instanceof CookingStationBlockEntity station) {
            serverPlayer.openMenu(station, buf -> buf.writeUtf(this.station.id()));
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CookingStationBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide() || type != ModBlockEntities.COOKING_STATION.get()) {
            return null;
        }
        return (tickLevel, pos, tickState, entity) -> ((CookingStationBlockEntity) entity).serverTick((ServerLevel) tickLevel);
    }
}
