package io.github.peixeangola.culinaria.block;

import io.github.peixeangola.culinaria.core.Station;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

/**
 * Fogão e forno: como a estação comum, mais o estado lit (aceso enquanto queima combustível), com luz, chama na frente,
 * fumaça e o estalo da fornalha. Uma subclasse porque o blockstate é montado no construtor de Block.
 */
public class FueledStationBlock extends CookingStationBlock {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public FueledStationBlock(Station station, Properties properties) {
        super(station, properties);
        registerDefaultState(defaultBlockState().setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LIT);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (!state.getValue(LIT)) {
            return;
        }
        double x = pos.getX() + 0.5D;
        double y = pos.getY();
        double z = pos.getZ() + 0.5D;
        if (random.nextDouble() < 0.1D) {
            level.playLocalSound(x, y, z, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
        }
        Direction facing = state.getValue(FACING);
        double side = random.nextDouble() * 0.6D - 0.3D;
        double dx = facing.getAxis() == Direction.Axis.X ? facing.getStepX() * 0.52D : side;
        double dz = facing.getAxis() == Direction.Axis.Z ? facing.getStepZ() * 0.52D : side;
        level.addParticle(ParticleTypes.SMOKE, x + dx, y + 0.4D, z + dz, 0.0D, 0.0D, 0.0D);
        if (station() == Station.STOVE) {
            level.addParticle(ParticleTypes.SMOKE, x + side, y + 1.05D, z - side, 0.0D, 0.02D, 0.0D);
        }
    }
}
