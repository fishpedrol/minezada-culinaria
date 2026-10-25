package io.github.peixeangola.culinaria.block;

import io.github.peixeangola.culinaria.CulinariaConfig;
import io.github.peixeangola.culinaria.core.FruitRules;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.sounds.AmbientLeavesBlockSoundPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

/**
 * Folha de árvore frutífera. Só as folhas com fruiting=true dão fruto (1 em 4 na árvore gerada); elas passam pelas
 * idades de {@link FruitRules} com random ticks e mostram flor, fruto verde e fruto maduro no modelo. O clique direito
 * na folha madura colhe sem quebrar nada e a folha recomeça da idade 0. Tudo fica no blockstate, então salva com o
 * mundo sem BlockEntity e não há como colher duas vezes a mesma fruta: o servidor confere a idade antes de soltar.
 */
public class FruitLeavesBlock extends LeavesBlock {
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, FruitRules.MAX_AGE);
    public static final BooleanProperty FRUITING = BooleanProperty.create("fruiting");

    private final Supplier<? extends Item> fruit;

    public FruitLeavesBlock(Supplier<? extends Item> fruit, Properties properties) {
        super(AmbientLeavesBlockSoundPlayer.noAmbientSound(), properties);
        this.fruit = fruit;
        registerDefaultState(defaultBlockState().setValue(AGE, 0).setValue(FRUITING, false));
    }

    public Item fruit() {
        return fruit.get();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AGE, FRUITING);
    }

    /** Folha posta à mão (com tesoura) volta a dar fruto, como a da árvore. */
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        return state == null ? null : state.setValue(FRUITING, true).setValue(AGE, 0);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return FruitRules.grows(state.getValue(FRUITING), state.getValue(AGE)) || super.isRandomlyTicking(state);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);
        BlockState now = level.getBlockState(pos);
        if (!now.is(this)) {
            return; // a folha se desfez
        }
        int age = now.getValue(AGE);
        int next = FruitRules.nextAge(now.getValue(FRUITING), age, random.nextInt(CulinariaConfig.fruitGrowthChance()));
        if (next != age) {
            level.setBlock(pos, now.setValue(AGE, next), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player,
                                          InteractionHand hand, BlockHitResult hit) {
        // Com qualquer item na mão também colhe, exceto quando o item tem um uso próprio no bloco (o vanilla tenta depois)
        return FruitRules.canHarvest(state.getValue(FRUITING), state.getValue(AGE))
            ? harvest(state, level, pos, player)
            : InteractionResult.TRY_WITH_EMPTY_HAND;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        return FruitRules.canHarvest(state.getValue(FRUITING), state.getValue(AGE))
            ? harvest(state, level, pos, player)
            : InteractionResult.PASS;
    }

    private InteractionResult harvest(BlockState state, Level level, BlockPos pos, Player player) {
        if (level instanceof ServerLevel serverLevel) {
            int count = FruitRules.harvestCount(serverLevel.getRandom().nextInt(4));
            popResource(serverLevel, pos, new ItemStack(fruit(), count));
            serverLevel.setBlock(pos, state.setValue(AGE, 0), Block.UPDATE_CLIENTS);
            serverLevel.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F,
                0.8F + serverLevel.getRandom().nextFloat() * 0.4F);
        }
        return InteractionResult.SUCCESS;
    }
}
