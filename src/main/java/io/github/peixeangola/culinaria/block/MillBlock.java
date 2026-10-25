package io.github.peixeangola.culinaria.block;

import io.github.peixeangola.culinaria.catalog.RecipeCatalog;
import io.github.peixeangola.culinaria.core.Station;
import io.github.peixeangola.culinaria.recipe.CookingRecipe;
import io.github.peixeangola.culinaria.registry.ModBlockEntities;
import io.github.peixeangola.culinaria.registry.ModTags;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

/**
 * Moedor manual. Clique com o que se moi (tag culinaria:station/mill) para pôr a pilha no moedor; clique de mão vazia
 * gira a manivela: cada volta conta e, completadas as voltas da receita, uma unidade vira o resultado, que vai para o
 * inventário. Agachado com a mão vazia pega de volta o que sobrou.
 */
public class MillBlock extends FurnitureBlock implements EntityBlock {
    private static final VoxelShape SHAPE = Shapes.or(Block.box(2, 0, 2, 14, 6, 14), Block.box(3, 6, 3, 13, 10, 13), Block.box(7, 10, 7, 9, 16, 9));

    public MillBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player,
                                          InteractionHand hand, BlockHitResult hit) {
        if (!(level.getBlockEntity(pos) instanceof MillBlockEntity mill) || !stack.is(ModTags.MILLABLE)) {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }
        ItemStack inside = mill.item();
        if (!inside.isEmpty() && (!ItemStack.isSameItemSameComponents(inside, stack) || inside.getCount() >= inside.getMaxStackSize())) {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }
        if (!level.isClientSide()) {
            int moved = inside.isEmpty() ? stack.getCount() : Math.min(stack.getCount(), inside.getMaxStackSize() - inside.getCount());
            ItemStack merged = inside.isEmpty() ? stack.copyWithCount(moved) : inside.copyWithCount(inside.getCount() + moved);
            int work = mill.work();
            mill.setItem(merged);
            mill.setWork(work);
            stack.shrink(moved);
            level.playSound(null, pos, SoundEvents.GRAVEL_PLACE, SoundSource.BLOCKS, 0.5F, 1.2F);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!(level.getBlockEntity(pos) instanceof MillBlockEntity mill) || mill.item().isEmpty()) {
            return InteractionResult.PASS;
        }
        if (level instanceof ServerLevel serverLevel) {
            if (player.isShiftKeyDown()) {
                Handouts.give(player, mill.take());
            } else {
                turn(serverLevel, pos, mill, player);
            }
        }
        return InteractionResult.SUCCESS;
    }

    private static void turn(ServerLevel level, BlockPos pos, MillBlockEntity mill, Player player) {
        Optional<RecipeCatalog.Entry> entry = RecipeCatalog.server(level.getServer()).single(Station.MILL, mill.item().getItem());
        if (entry.isEmpty()) {
            Handouts.tell(player, Component.translatable("culinaria.mill.not_millable"));
            return;
        }
        CookingRecipe recipe = entry.get().recipe();
        int turns = mill.work() + 1;
        level.playSound(null, pos, SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 0.5F, 0.9F + level.getRandom().nextFloat() * 0.2F);
        if (turns >= recipe.time()) {
            ItemStack rest = mill.item().copy();
            rest.shrink(1);
            mill.setItem(rest);
            Handouts.give(player, recipe.resultStack());
            level.sendParticles(ParticleTypes.WHITE_ASH, pos.getX() + 0.5D, pos.getY() + 0.7D, pos.getZ() + 0.5D, 6, 0.2D, 0.1D, 0.2D, 0.0D);
        } else {
            mill.setWork(turns);
        }
        Handouts.tell(player, Component.translatable("culinaria.mill.progress", Math.min(turns, recipe.time()), recipe.time()));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MillBlockEntity(pos, state);
    }

    /** O moedor: a pilha posta e as voltas da unidade atual. */
    public static class MillBlockEntity extends SingleItemBlockEntity {
        public MillBlockEntity(BlockPos pos, BlockState state) {
            super(ModBlockEntities.MILL.get(), pos, state);
        }
    }
}
