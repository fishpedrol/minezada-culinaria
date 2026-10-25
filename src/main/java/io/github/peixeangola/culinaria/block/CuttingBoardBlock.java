package io.github.peixeangola.culinaria.block;

import io.github.peixeangola.culinaria.catalog.RecipeCatalog;
import io.github.peixeangola.culinaria.core.Station;
import io.github.peixeangola.culinaria.recipe.CookingRecipe;
import io.github.peixeangola.culinaria.registry.ModBlockEntities;
import io.github.peixeangola.culinaria.registry.ModItems;
import io.github.peixeangola.culinaria.registry.ModTags;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
 * Tábua de corte. Clique com um ingrediente que se corta (tag culinaria:station/cutting_board) para pôr um na tábua;
 * clique com a faca para cortar: cada clique é um corte e gasta 1 de durabilidade; no último corte o resultado vai
 * para o inventário. Mão vazia pega o ingrediente de volta.
 */
public class CuttingBoardBlock extends FurnitureBlock implements EntityBlock {
    private static final Map<Direction, VoxelShape> SHAPES = Shapes.rotateHorizontal(Block.box(4, 0, 2, 12, 2, 14));

    public CuttingBoardBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player,
                                          InteractionHand hand, BlockHitResult hit) {
        if (!(level.getBlockEntity(pos) instanceof CuttingBoardBlockEntity board)) {
            return InteractionResult.PASS;
        }
        if (stack.is(ModItems.item("kitchen_knife"))) {
            if (board.item().isEmpty()) {
                Handouts.tell(player, Component.translatable("culinaria.board.empty"));
                return InteractionResult.SUCCESS;
            }
            if (level instanceof ServerLevel serverLevel) {
                cut(serverLevel, pos, board, player, stack, hand);
            }
            return InteractionResult.SUCCESS;
        }
        if (board.item().isEmpty() && stack.is(ModTags.CUTTABLE)) {
            if (!level.isClientSide()) {
                board.setItem(stack.split(1));
                level.playSound(null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 0.6F, 1.4F);
                Handouts.tell(player, Component.translatable("culinaria.board.needs_knife"));
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.TRY_WITH_EMPTY_HAND;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!(level.getBlockEntity(pos) instanceof CuttingBoardBlockEntity board) || board.item().isEmpty()) {
            return InteractionResult.PASS;
        }
        if (!level.isClientSide()) {
            Handouts.give(player, board.take());
        }
        return InteractionResult.SUCCESS;
    }

    private static void cut(ServerLevel level, BlockPos pos, CuttingBoardBlockEntity board, Player player, ItemStack knife,
                            InteractionHand hand) {
        Optional<RecipeCatalog.Entry> entry = RecipeCatalog.server(level.getServer()).single(Station.CUTTING_BOARD, board.item().getItem());
        if (entry.isEmpty()) {
            Handouts.tell(player, Component.translatable("culinaria.board.not_cuttable"));
            return;
        }
        CookingRecipe recipe = entry.get().recipe();
        int cuts = board.work() + 1;
        knife.hurtAndBreak(1, player, hand);
        level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 0.7F, 1.3F + level.getRandom().nextFloat() * 0.3F);
        if (cuts >= recipe.time()) {
            board.take();
            Handouts.give(player, recipe.resultStack());
            level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.3F, 1.2F);
        } else {
            board.setWork(cuts);
            Handouts.tell(player, Component.translatable("culinaria.board.placed", cuts, recipe.time()));
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CuttingBoardBlockEntity(pos, state);
    }

    /** A tábua: o ingrediente posto e quantos cortes já levou. */
    public static class CuttingBoardBlockEntity extends SingleItemBlockEntity {
        public CuttingBoardBlockEntity(BlockPos pos, BlockState state) {
            super(ModBlockEntities.CUTTING_BOARD.get(), pos, state);
        }
    }
}
