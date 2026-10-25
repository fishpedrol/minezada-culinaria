package io.github.peixeangola.culinaria.block;

import io.github.peixeangola.culinaria.catalog.RecipeCatalog;
import io.github.peixeangola.culinaria.core.SinkRules;
import io.github.peixeangola.culinaria.core.Station;
import io.github.peixeangola.culinaria.registry.ModTags;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Pia. Guarda água no blockstate ({@link SinkRules}): o balde de água enche, cada lavagem gasta 1. Com uma fonte de água
 * logo abaixo ela é ligada à água: não gasta e enche baldes. Lava o que a tag culinaria:station/sink lista (arroz,
 * feijão, farinha de mandioca para o polvilho e a louça suja), um item por clique, e o resultado vai para o inventário.
 */
public class SinkBlock extends FurnitureBlock {
    public static final IntegerProperty WATER = IntegerProperty.create("water", 0, SinkRules.MAX_WATER);
    private static final VoxelShape SHAPE = Shapes.join(Shapes.block(), Block.box(2, 10, 2, 14, 16, 14), BooleanOp.ONLY_FIRST);

    public SinkBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(WATER, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATER);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    /** Fonte de água logo abaixo da pia. */
    public static boolean isConnected(BlockGetter level, BlockPos pos) {
        FluidState below = level.getFluidState(pos.below());
        return below.is(FluidTags.WATER) && below.isSource();
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player,
                                          InteractionHand hand, BlockHitResult hit) {
        int water = state.getValue(WATER);
        boolean connected = isConnected(level, pos);
        if (stack.is(Items.WATER_BUCKET)) {
            if (water >= SinkRules.MAX_WATER) {
                return InteractionResult.TRY_WITH_EMPTY_HAND;
            }
            if (!level.isClientSide()) {
                level.setBlock(pos, state.setValue(WATER, SinkRules.MAX_WATER), Block.UPDATE_ALL);
                player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.BUCKET)));
                level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return InteractionResult.SUCCESS;
        }
        if (stack.is(Items.BUCKET)) {
            if (!SinkRules.canFillBucket(connected)) {
                Handouts.tell(player, Component.translatable("culinaria.sink.water", water));
                return InteractionResult.SUCCESS;
            }
            if (!level.isClientSide()) {
                player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.WATER_BUCKET)));
                level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return InteractionResult.SUCCESS;
        }
        if (stack.is(ModTags.WASHABLE)) {
            if (!SinkRules.canWash(water, connected)) {
                Handouts.tell(player, Component.translatable("culinaria.sink.empty"));
                return InteractionResult.SUCCESS;
            }
            if (level instanceof ServerLevel serverLevel) {
                wash(serverLevel, state, pos, player, stack, water, connected);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.TRY_WITH_EMPTY_HAND;
    }

    private static void wash(ServerLevel level, BlockState state, BlockPos pos, Player player, ItemStack stack, int water, boolean connected) {
        Optional<RecipeCatalog.Entry> entry = RecipeCatalog.server(level.getServer()).single(Station.SINK, stack.getItem());
        if (entry.isEmpty()) {
            Handouts.tell(player, Component.translatable("culinaria.sink.not_washable"));
            return;
        }
        stack.shrink(1);
        Handouts.give(player, entry.get().recipe().resultStack());
        int after = SinkRules.afterWash(water, connected);
        if (after != water) {
            level.setBlock(pos, state.setValue(WATER, after), Block.UPDATE_ALL);
        }
        level.playSound(null, pos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 0.4F, 1.2F + level.getRandom().nextFloat() * 0.3F);
        level.sendParticles(ParticleTypes.SPLASH, pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, 8, 0.25D, 0.05D, 0.25D, 0.0D);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        Handouts.tell(player, isConnected(level, pos)
            ? Component.translatable("culinaria.sink.infinite")
            : Component.translatable("culinaria.sink.water", state.getValue(WATER)));
        return InteractionResult.SUCCESS;
    }
}
