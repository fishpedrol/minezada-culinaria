package io.github.peixeangola.culinaria.gametest;

import io.github.peixeangola.culinaria.Culinaria;
import io.github.peixeangola.culinaria.block.CookingStationBlockEntity;
import io.github.peixeangola.culinaria.block.CuttingBoardBlock;
import io.github.peixeangola.culinaria.block.FruitLeavesBlock;
import io.github.peixeangola.culinaria.block.FueledStationBlock;
import io.github.peixeangola.culinaria.block.FurnitureBlock;
import io.github.peixeangola.culinaria.block.MillBlock;
import io.github.peixeangola.culinaria.block.SinkBlock;
import io.github.peixeangola.culinaria.block.StorageBlockEntity;
import io.github.peixeangola.culinaria.catalog.RecipeCatalog;
import io.github.peixeangola.culinaria.content.GeneratedContent;
import io.github.peixeangola.culinaria.core.CookingStatus;
import io.github.peixeangola.culinaria.core.Station;
import io.github.peixeangola.culinaria.item.DishItem;
import io.github.peixeangola.culinaria.menu.CookingStationMenu;
import io.github.peixeangola.culinaria.recipe.CookingRecipe;
import io.github.peixeangola.culinaria.registry.ModBlocks;
import io.github.peixeangola.culinaria.registry.ModItems;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmlandBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.DeferredRegister;

/**
 * GameTests do Culinária. Cada função tem um data/culinaria/test_instance/&lt;nome&gt;.json gerado (estrutura
 * forge:empty9x9x9). Rodar com {@code ./gradlew runGameTestServer}. As verificações são síncronas: os ticks das
 * estações são chamados direto, e os cliques passam pelo ServerPlayerGameMode como os de um jogador de verdade.
 */
public final class CulinariaGameTests {
    public static final DeferredRegister<Consumer<GameTestHelper>> TESTS = DeferredRegister.create(Registries.TEST_FUNCTION, Culinaria.MOD_ID);

    private static final BlockPos FLOOR = new BlockPos(4, 1, 4);
    private static final BlockPos SPOT = FLOOR.above();

    static {
        TESTS.register("crop_grows_and_drops", () -> CulinariaGameTests::cropGrowsAndDrops);
        TESTS.register("fruit_harvest_regrows", () -> CulinariaGameTests::fruitHarvestRegrows);
        TESTS.register("fruit_leaves_persist", () -> CulinariaGameTests::fruitLeavesRules);
        TESTS.register("stove_cooks_recipe", () -> CulinariaGameTests::stoveCooksRecipe);
        TESTS.register("station_status_messages", () -> CulinariaGameTests::stationStatusMessages);
        TESTS.register("station_keeps_items_on_close", () -> CulinariaGameTests::stationKeepsItemsOnClose);
        TESTS.register("station_persistence", () -> CulinariaGameTests::stationPersistence);
        TESTS.register("oven_bakes_with_fuel", () -> CulinariaGameTests::ovenBakesWithFuel);
        TESTS.register("counter_assembles_dish", () -> CulinariaGameTests::counterAssemblesDish);
        TESTS.register("cutting_board_cuts", () -> CulinariaGameTests::cuttingBoardCuts);
        TESTS.register("sink_washes_and_fills", () -> CulinariaGameTests::sinkWashesAndFills);
        TESTS.register("mill_grinds", () -> CulinariaGameTests::millGrinds);
        TESTS.register("fridge_chills", () -> CulinariaGameTests::fridgeChills);
        TESTS.register("storage_persists", () -> CulinariaGameTests::storagePersists);
        TESTS.register("catalog_has_100_dishes", () -> CulinariaGameTests::catalogHas100Dishes);
        TESTS.register("dish_returns_container", () -> CulinariaGameTests::dishReturnsContainer);
        TESTS.register("sapling_grows_fruit_tree", () -> CulinariaGameTests::saplingGrowsFruitTree);
        TESTS.register("remainders_returned", () -> CulinariaGameTests::remaindersReturned);
    }

    private CulinariaGameTests() {}

    // ---------------------------------------------------------------- utilidades

    private static final class Failure extends RuntimeException {
        Failure(String message) {
            super(message);
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new Failure(message);
        }
    }

    private static void run(GameTestHelper helper, Runnable body) {
        try {
            body.run();
            helper.succeed();
        } catch (Failure failure) {
            helper.fail(Component.literal(failure.getMessage()));
        }
    }

    private static Item item(String id) {
        return ModItems.item(id);
    }

    private static ItemStack stack(String id, int count) {
        return new ItemStack(item(id), count);
    }

    private static ServerPlayer player(GameTestHelper helper) {
        return (ServerPlayer) helper.makeMockServerPlayer(GameType.SURVIVAL);
    }

    /** Clique direito no bloco com o que estiver na mão principal, como um jogador. */
    private static InteractionResult click(GameTestHelper helper, ServerPlayer player, BlockPos rel) {
        BlockPos pos = helper.absolutePos(rel);
        BlockHitResult hit = new BlockHitResult(Vec3.atCenterOf(pos).add(0, 0.4D, 0), Direction.UP, pos, false);
        return player.gameMode.useItemOn(player, helper.getLevel(), player.getMainHandItem(), InteractionHand.MAIN_HAND, hit);
    }

    private static int countItems(GameTestHelper helper, BlockPos rel, Item item) {
        BlockPos pos = helper.absolutePos(rel);
        int total = 0;
        for (ItemEntity entity : helper.getLevel().getEntitiesOfClass(ItemEntity.class, new AABB(pos).inflate(2.0D))) {
            if (entity.getItem().is(item)) {
                total += entity.getItem().getCount();
            }
        }
        return total;
    }

    private static void clearItems(GameTestHelper helper, BlockPos rel) {
        helper.getLevel().getEntitiesOfClass(ItemEntity.class, new AABB(helper.absolutePos(rel)).inflate(3.0D)).forEach(ItemEntity::discard);
    }

    private static int countInInventory(ServerPlayer player, Item item) {
        int total = 0;
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.is(item)) {
                total += stack.getCount();
            }
        }
        return total;
    }

    private static CookingStationBlockEntity station(GameTestHelper helper, String block) {
        helper.setBlock(FLOOR, Blocks.STONE.defaultBlockState());
        helper.setBlock(SPOT, ModBlocks.block(block).defaultBlockState());
        BlockEntity entity = helper.getLevel().getBlockEntity(helper.absolutePos(SPOT));
        check(entity instanceof CookingStationBlockEntity, "estação sem BlockEntity: " + block);
        return (CookingStationBlockEntity) entity;
    }

    private static void tick(GameTestHelper helper, CookingStationBlockEntity station, int ticks) {
        for (int i = 0; i < ticks; i++) {
            station.serverTick(helper.getLevel());
        }
    }

    private static CookingRecipe recipe(GameTestHelper helper, String name) {
        return RecipeCatalog.server(helper.getLevel().getServer()).entries().stream()
            .filter(e -> e.id().getPath().equals("cooking/" + name)).findFirst()
            .orElseThrow(() -> new Failure("receita ausente: " + name)).recipe();
    }

    /** Põe a sopa de tomate completa no fogão (sem combustível). */
    private static void loadTomatoSoup(CookingStationBlockEntity stove) {
        stove.put(0, stack("tomato", 3));
        stove.put(1, stack("chopped_onion", 1));
        stove.put(2, stack("basil", 1));
        stove.put(3, new ItemStack(Items.WATER_BUCKET));
        stove.put(CookingStationBlockEntity.UTENSIL, stack("cooking_pot", 1));
        stove.put(CookingStationBlockEntity.CONTAINER, new ItemStack(Items.BOWL, 2));
    }

    // ---------------------------------------------------------------- lavouras e árvores

    /** Tomate plantado em terra arada cresce até a idade 7 com random ticks e solta tomates e sementes. */
    public static void cropGrowsAndDrops(GameTestHelper helper) {
        run(helper, () -> {
            ServerLevel level = helper.getLevel();
            helper.setBlock(FLOOR, Blocks.FARMLAND.defaultBlockState().setValue(FarmlandBlock.MOISTURE, FarmlandBlock.MAX_MOISTURE));
            ServerPlayer player = player(helper);
            player.setItemInHand(InteractionHand.MAIN_HAND, stack("tomato_seeds", 2));
            BlockPos soil = helper.absolutePos(FLOOR);
            InteractionResult planted = player.gameMode.useItemOn(player, level, player.getMainHandItem(), InteractionHand.MAIN_HAND,
                new BlockHitResult(Vec3.atCenterOf(soil).add(0, 0.5D, 0), Direction.UP, soil, false));
            check(planted.consumesAction(), "semente não plantou: " + planted);
            BlockPos pos = helper.absolutePos(SPOT);
            BlockState young = level.getBlockState(pos);
            check(young.is(ModBlocks.CROPS.get("tomato_crop").get()), "lavoura ausente: " + young);
            check(Block.getDrops(young, level, pos, null).stream().allMatch(s -> s.is(item("tomato_seeds"))), "lavoura nova só devolve a semente");
            for (int i = 0; i < 4000 && ((CropBlock) young.getBlock()).getAge(level.getBlockState(pos)) < 7; i++) {
                level.getBlockState(pos).randomTick(level, pos, level.getRandom());
            }
            BlockState ripe = level.getBlockState(pos);
            check(((CropBlock) ripe.getBlock()).getAge(ripe) == 7, "não amadureceu: " + ripe);
            List<ItemStack> drops = Block.getDrops(ripe, level, pos, null);
            int tomatoes = drops.stream().filter(s -> s.is(item("tomato"))).mapToInt(ItemStack::getCount).sum();
            int seeds = drops.stream().filter(s -> s.is(item("tomato_seeds"))).mapToInt(ItemStack::getCount).sum();
            check(tomatoes >= 1 && tomatoes <= 3, "tomates colhidos: " + tomatoes);
            check(seeds >= 1, "sementes de volta: " + seeds);
        });
    }

    private static BlockState leaves(String tree, int age, boolean fruiting) {
        return ModBlocks.LEAVES.get(tree + "_leaves").get().defaultBlockState()
            .setValue(FruitLeavesBlock.AGE, age).setValue(FruitLeavesBlock.FRUITING, fruiting).setValue(LeavesBlock.PERSISTENT, true);
    }

    /** Colher a laranja madura não quebra a folha, volta à idade 0, não colhe duas vezes e a fruta volta a crescer. */
    public static void fruitHarvestRegrows(GameTestHelper helper) {
        run(helper, () -> {
            ServerLevel level = helper.getLevel();
            helper.setBlock(SPOT, leaves("orange", 3, true));
            ServerPlayer player = player(helper);
            check(click(helper, player, SPOT).consumesAction(), "colheita não aceitou o clique");
            BlockPos pos = helper.absolutePos(SPOT);
            check(level.getBlockState(pos).is(ModBlocks.LEAVES.get("orange_leaves").get()), "a folha foi quebrada");
            check(level.getBlockState(pos).getValue(FruitLeavesBlock.AGE) == 0, "a folha não voltou à idade 0");
            int first = countItems(helper, SPOT, item("orange"));
            check(first >= 1 && first <= 2, "laranjas colhidas: " + first);
            click(helper, player, SPOT);
            check(countItems(helper, SPOT, item("orange")) == first, "colheu de novo uma folha vazia (duplicação)");
            for (int i = 0; i < 2000 && level.getBlockState(pos).getValue(FruitLeavesBlock.AGE) < 3; i++) {
                level.getBlockState(pos).randomTick(level, pos, level.getRandom());
            }
            check(level.getBlockState(pos).getValue(FruitLeavesBlock.AGE) == 3, "a fruta não voltou a amadurecer");
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.STICK));
            check(click(helper, player, SPOT).consumesAction(), "segurando um item também colhe");
            check(countItems(helper, SPOT, item("orange")) > first, "segunda colheita não soltou fruta");
            clearItems(helper, SPOT);
        });
    }

    /** Folha sem fruto nunca cresce, a madura não passa de 3 e a folha madura quebrada solta a fruta. */
    public static void fruitLeavesRules(GameTestHelper helper) {
        run(helper, () -> {
            ServerLevel level = helper.getLevel();
            BlockPos pos = helper.absolutePos(SPOT);
            helper.setBlock(SPOT, leaves("lemon", 0, false));
            for (int i = 0; i < 500; i++) {
                level.getBlockState(pos).randomTick(level, pos, level.getRandom());
            }
            check(level.getBlockState(pos).getValue(FruitLeavesBlock.AGE) == 0, "folha sem fruto cresceu");
            helper.setBlock(SPOT, leaves("lemon", 3, true));
            for (int i = 0; i < 200; i++) {
                level.getBlockState(pos).randomTick(level, pos, level.getRandom());
            }
            check(level.getBlockState(pos).getValue(FruitLeavesBlock.AGE) == 3, "idade passou de 3");
            List<ItemStack> drops = Block.getDrops(level.getBlockState(pos), level, pos, null);
            check(drops.stream().anyMatch(s -> s.is(item("lemon"))), "folha madura quebrada não soltou limão");
            helper.setBlock(SPOT, leaves("lemon", 1, true));
            check(Block.getDrops(level.getBlockState(pos), level, pos, null).stream().noneMatch(s -> s.is(item("lemon"))),
                "folha florida não pode soltar limão");
        });
    }

    /** Muda de pessegueiro vira árvore com tronco de carvalho e folhas de pessegueiro, algumas com fruto. */
    public static void saplingGrowsFruitTree(GameTestHelper helper) {
        run(helper, () -> {
            ServerLevel level = helper.getLevel();
            helper.setBlock(FLOOR, Blocks.DIRT.defaultBlockState());
            helper.setBlock(SPOT, ModBlocks.SAPLINGS.get("peach_sapling").get().defaultBlockState());
            BlockPos pos = helper.absolutePos(SPOT);
            SaplingBlock sapling = (SaplingBlock) ModBlocks.SAPLINGS.get("peach_sapling").get();
            for (int i = 0; i < 20 && level.getBlockState(pos).is(sapling); i++) {
                sapling.advanceTree(level, pos, level.getBlockState(pos), level.getRandom());
            }
            check(level.getBlockState(pos).is(Blocks.OAK_LOG), "a muda não virou árvore: " + level.getBlockState(pos));
            int leaves = 0;
            int fruiting = 0;
            for (BlockPos p : BlockPos.betweenClosed(pos.offset(-3, 0, -3), pos.offset(3, 7, 3))) {
                BlockState state = level.getBlockState(p);
                if (state.is(ModBlocks.LEAVES.get("peach_leaves").get())) {
                    leaves++;
                    if (state.getValue(FruitLeavesBlock.FRUITING)) {
                        fruiting++;
                    }
                }
            }
            check(leaves >= 10, "poucas folhas: " + leaves);
            check(fruiting >= 1 && fruiting < leaves, "folhas com fruto: " + fruiting + " de " + leaves);
        });
    }

    // ---------------------------------------------------------------- estações com interface

    /** Sopa de tomate no fogão: gasta os ingredientes e as tigelas, devolve o balde, queima carvão e rende 2 sopas. */
    public static void stoveCooksRecipe(GameTestHelper helper) {
        run(helper, () -> {
            CookingStationBlockEntity stove = station(helper, "stove");
            loadTomatoSoup(stove);
            stove.put(CookingStationBlockEntity.FUEL, new ItemStack(Items.COAL, 2));
            int time = recipe(helper, "tomato_soup").time();
            tick(helper, stove, 1);
            check(stove.status() == CookingStatus.WORKING, "status ao começar: " + stove.status());
            check(helper.getLevel().getBlockState(helper.absolutePos(SPOT)).getValue(FueledStationBlock.LIT), "fogão não acendeu");
            tick(helper, stove, time + 2);
            check(stove.stackIn(CookingStationBlockEntity.OUTPUT).is(item("tomato_soup")) && stove.stackIn(CookingStationBlockEntity.OUTPUT).getCount() == 2,
                "saída: " + stove.stackIn(CookingStationBlockEntity.OUTPUT));
            for (int i = 0; i < CookingStationBlockEntity.INGREDIENT_SLOTS; i++) {
                check(stove.stackIn(i).isEmpty(), "sobrou ingrediente no slot " + i + ": " + stove.stackIn(i));
            }
            check(stove.stackIn(CookingStationBlockEntity.CONTAINER).isEmpty(), "tigelas não foram gastas");
            check(stove.stackIn(CookingStationBlockEntity.UTENSIL).is(item("cooking_pot")), "a panela não pode ser gasta");
            check(stove.stackIn(CookingStationBlockEntity.LEFTOVER_A).is(Items.BUCKET), "o balde não voltou: " + stove.stackIn(CookingStationBlockEntity.LEFTOVER_A));
            check(stove.stackIn(CookingStationBlockEntity.FUEL).getCount() == 1, "carvão gasto: " + stove.stackIn(CookingStationBlockEntity.FUEL));
            tick(helper, stove, 5);
            check(stove.stackIn(CookingStationBlockEntity.OUTPUT).getCount() == 2, "fez sopa sem ingredientes");
            check(stove.status() == CookingStatus.IDLE, "status vazio: " + stove.status());
        });
    }

    /** Cada motivo de parada aparece no status e nada é gasto enquanto a estação está parada. */
    public static void stationStatusMessages(GameTestHelper helper) {
        run(helper, () -> {
            CookingStationBlockEntity stove = station(helper, "stove");
            stove.put(0, stack("tomato", 3));
            stove.put(1, new ItemStack(Items.DIRT));
            tick(helper, stove, 1);
            check(stove.status() == CookingStatus.NO_RECIPE, "terra no fogão: " + stove.status());
            stove.put(1, ItemStack.EMPTY);
            loadTomatoSoup(stove);
            stove.put(CookingStationBlockEntity.UTENSIL, ItemStack.EMPTY);
            tick(helper, stove, 1);
            check(stove.status() == CookingStatus.NEEDS_UTENSIL, "sem panela: " + stove.status());
            stove.put(CookingStationBlockEntity.UTENSIL, stack("frying_pan", 1));
            tick(helper, stove, 1);
            check(stove.status() == CookingStatus.NEEDS_UTENSIL, "frigideira no lugar da panela: " + stove.status());
            stove.put(CookingStationBlockEntity.UTENSIL, stack("cooking_pot", 1));
            stove.put(CookingStationBlockEntity.CONTAINER, new ItemStack(Items.BOWL, 1));
            tick(helper, stove, 1);
            check(stove.status() == CookingStatus.NEEDS_CONTAINER, "1 tigela para 2 sopas: " + stove.status());
            stove.put(CookingStationBlockEntity.CONTAINER, new ItemStack(Items.BOWL, 2));
            tick(helper, stove, 1);
            check(stove.status() == CookingStatus.NO_FUEL, "sem combustível: " + stove.status());
            stove.put(0, stack("tomato", 2));
            tick(helper, stove, 1);
            check(stove.status() == CookingStatus.MORE_NEEDED, "2 tomates para 3: " + stove.status());
            stove.put(0, stack("tomato", 3));
            stove.put(CookingStationBlockEntity.OUTPUT, new ItemStack(Items.DIRT));
            stove.put(CookingStationBlockEntity.FUEL, new ItemStack(Items.COAL));
            tick(helper, stove, 1);
            check(stove.status() == CookingStatus.OUTPUT_FULL, "saída ocupada: " + stove.status());
            check(stove.stackIn(0).getCount() == 3 && stove.stackIn(CookingStationBlockEntity.CONTAINER).getCount() == 2, "gastou algo parado");
            check(stove.stackIn(CookingStationBlockEntity.FUEL).getCount() == 1, "queimou combustível sem poder cozinhar");
        });
    }

    /** Fechar a interface não tira nada da estação, e tirar o combustível no meio guarda o progresso. */
    public static void stationKeepsItemsOnClose(GameTestHelper helper) {
        run(helper, () -> {
            CookingStationBlockEntity stove = station(helper, "stove");
            ServerPlayer player = player(helper);
            CookingStationMenu menu = new CookingStationMenu(1, player.getInventory(), stove, stove.data(), Station.STOVE);
            loadTomatoSoup(stove);
            stove.put(CookingStationBlockEntity.FUEL, new ItemStack(Items.STICK, 1));
            tick(helper, stove, 40);
            int progress = stove.progress();
            check(progress > 0, "não começou a cozinhar");
            menu.removed(player);
            check(stove.stackIn(0).getCount() == 3 && stove.stackIn(CookingStationBlockEntity.CONTAINER).getCount() == 2, "fechar a tela mexeu nos itens");
            tick(helper, stove, 200);
            check(stove.status() == CookingStatus.NO_FUEL, "o graveto devia ter acabado: " + stove.status());
            check(stove.progress() > 0, "progresso perdido sem combustível");
            check(countInInventory(player, item("tomato")) == 0, "o jogador recebeu itens ao fechar");
        });
    }

    /** O fogão salvo e recarregado mantém slots, progresso e combustível; quebrado, derruba tudo e a si mesmo. */
    public static void stationPersistence(GameTestHelper helper) {
        run(helper, () -> {
            ServerLevel level = helper.getLevel();
            CookingStationBlockEntity stove = station(helper, "stove");
            loadTomatoSoup(stove);
            stove.put(CookingStationBlockEntity.FUEL, new ItemStack(Items.COAL, 3));
            tick(helper, stove, 50);
            CompoundTag saved = stove.saveWithFullMetadata(level.registryAccess());
            BlockPos pos = helper.absolutePos(SPOT);
            BlockEntity loaded = BlockEntity.loadStatic(pos, level.getBlockState(pos), saved, level.registryAccess());
            check(loaded instanceof CookingStationBlockEntity, "não recarregou");
            CookingStationBlockEntity copy = (CookingStationBlockEntity) loaded;
            check(copy.progress() == stove.progress() && copy.progress() > 0, "progresso: " + copy.progress());
            check(copy.burnTime() == stove.burnTime() && copy.burnTime() > 0, "combustível aceso: " + copy.burnTime());
            for (int i = 0; i < CookingStationBlockEntity.SIZE; i++) {
                check(ItemStack.matches(copy.stackIn(i), stove.stackIn(i)), "slot " + i + " não persistiu");
            }
            level.destroyBlock(pos, true);
            check(countItems(helper, SPOT, item("tomato")) == 3, "tomates no chão: " + countItems(helper, SPOT, item("tomato")));
            check(countItems(helper, SPOT, item("cooking_pot")) == 1, "a panela tem de cair");
            check(countItems(helper, SPOT, item("stove")) == 1, "o fogão tem de cair");
            clearItems(helper, SPOT);
        });
    }

    /** Pão francês no forno: massa + forma + carvão vegetal; o forno acende e rende 2 pães. */
    public static void ovenBakesWithFuel(GameTestHelper helper) {
        run(helper, () -> {
            CookingStationBlockEntity oven = station(helper, "oven");
            oven.put(0, stack("bread_dough", 1));
            oven.put(CookingStationBlockEntity.UTENSIL, stack("baking_tray", 1));
            tick(helper, oven, 1);
            check(oven.status() == CookingStatus.NO_FUEL, "sem combustível: " + oven.status());
            oven.put(CookingStationBlockEntity.FUEL, new ItemStack(Items.CHARCOAL));
            tick(helper, oven, recipe(helper, "bread_roll").time() + 1);
            check(oven.stackIn(CookingStationBlockEntity.OUTPUT).is(item("bread_roll")) && oven.stackIn(CookingStationBlockEntity.OUTPUT).getCount() == 2,
                "saída do forno: " + oven.stackIn(CookingStationBlockEntity.OUTPUT));
            check(helper.getLevel().getBlockState(helper.absolutePos(SPOT)).getValue(FueledStationBlock.LIT), "forno apagado com carvão queimando");
        });
    }

    /** Bancada: arroz com feijão no prato (sem combustível) e creme de leite batido devolvendo o balde. */
    public static void counterAssemblesDish(GameTestHelper helper) {
        run(helper, () -> {
            CookingStationBlockEntity counter = station(helper, "counter");
            counter.put(0, stack("cooked_rice", 1));
            counter.put(1, stack("cooked_beans", 1));
            counter.put(CookingStationBlockEntity.CONTAINER, stack("plate", 1));
            tick(helper, counter, recipe(helper, "rice_and_beans").time() + 1);
            check(counter.stackIn(CookingStationBlockEntity.OUTPUT).is(item("rice_and_beans")), "saída: " + counter.stackIn(CookingStationBlockEntity.OUTPUT));
            check(counter.stackIn(CookingStationBlockEntity.CONTAINER).isEmpty(), "o prato não foi usado");
            counter.put(CookingStationBlockEntity.OUTPUT, ItemStack.EMPTY);
            counter.put(0, new ItemStack(Items.MILK_BUCKET));
            counter.put(1, ItemStack.EMPTY);
            tick(helper, counter, 1);
            check(counter.status() == CookingStatus.NEEDS_UTENSIL, "creme sem batedor: " + counter.status());
            counter.put(CookingStationBlockEntity.UTENSIL, stack("whisk", 1));
            tick(helper, counter, recipe(helper, "cream").time() + 1);
            check(counter.stackIn(CookingStationBlockEntity.OUTPUT).is(item("cream")) && counter.stackIn(CookingStationBlockEntity.OUTPUT).getCount() == 2,
                "creme: " + counter.stackIn(CookingStationBlockEntity.OUTPUT));
            check(counter.stackIn(CookingStationBlockEntity.LEFTOVER_A).is(Items.BUCKET), "balde do leite");
        });
    }

    /** Chá de limão com mel devolve o balde e o frasco nos dois slots de sobras; sobras cheias param a estação. */
    public static void remaindersReturned(GameTestHelper helper) {
        run(helper, () -> {
            CookingStationBlockEntity stove = station(helper, "stove");
            stove.put(0, stack("lemon", 1));
            stove.put(1, new ItemStack(Items.HONEY_BOTTLE));
            stove.put(2, new ItemStack(Items.WATER_BUCKET));
            stove.put(CookingStationBlockEntity.UTENSIL, stack("cooking_pot", 1));
            stove.put(CookingStationBlockEntity.CONTAINER, stack("cup", 3));
            stove.put(CookingStationBlockEntity.FUEL, new ItemStack(Items.COAL));
            stove.put(CookingStationBlockEntity.LEFTOVER_A, new ItemStack(Items.DIRT));
            stove.put(CookingStationBlockEntity.LEFTOVER_B, new ItemStack(Items.COBBLESTONE));
            tick(helper, stove, 1);
            check(stove.status() == CookingStatus.LEFTOVERS_FULL, "sobras cheias: " + stove.status());
            stove.put(CookingStationBlockEntity.LEFTOVER_A, ItemStack.EMPTY);
            stove.put(CookingStationBlockEntity.LEFTOVER_B, ItemStack.EMPTY);
            tick(helper, stove, recipe(helper, "lemon_honey_tea").time() + 2);
            check(stove.stackIn(CookingStationBlockEntity.OUTPUT).is(item("lemon_honey_tea")) && stove.stackIn(CookingStationBlockEntity.OUTPUT).getCount() == 3,
                "chá: " + stove.stackIn(CookingStationBlockEntity.OUTPUT));
            Set<Item> leftovers = new HashSet<>(List.of(stove.stackIn(CookingStationBlockEntity.LEFTOVER_A).getItem(),
                stove.stackIn(CookingStationBlockEntity.LEFTOVER_B).getItem()));
            check(leftovers.equals(Set.of(Items.BUCKET, Items.GLASS_BOTTLE)), "sobras: " + leftovers);
        });
    }

    // ---------------------------------------------------------------- estações de clique

    private static void place(GameTestHelper helper, String block) {
        helper.setBlock(FLOOR, Blocks.STONE.defaultBlockState());
        helper.setBlock(SPOT, ModBlocks.block(block).defaultBlockState().setValue(FurnitureBlock.FACING, Direction.NORTH));
    }

    /** Tábua: põe 1 tomate, dois cortes com a faca dão 2 fatias e gastam 2 de durabilidade; mão vazia devolve. */
    public static void cuttingBoardCuts(GameTestHelper helper) {
        run(helper, () -> {
            place(helper, "cutting_board");
            ServerPlayer player = player(helper);
            player.setItemInHand(InteractionHand.MAIN_HAND, stack("tomato", 2));
            check(click(helper, player, SPOT).consumesAction(), "não pôs o tomate");
            CuttingBoardBlock.CuttingBoardBlockEntity board = (CuttingBoardBlock.CuttingBoardBlockEntity) helper.getLevel().getBlockEntity(helper.absolutePos(SPOT));
            check(board.item().is(item("tomato")) && board.item().getCount() == 1, "tábua: " + board.item());
            check(player.getMainHandItem().getCount() == 1, "tirou mais de um tomate da mão");
            ItemStack knife = stack("kitchen_knife", 1);
            player.setItemInHand(InteractionHand.MAIN_HAND, knife);
            click(helper, player, SPOT);
            check(board.item().is(item("tomato")) && board.work() == 1, "primeiro corte: " + board.work());
            click(helper, player, SPOT);
            check(board.item().isEmpty(), "tomate não saiu da tábua");
            check(countInInventory(player, item("sliced_tomato")) == 2, "fatias: " + countInInventory(player, item("sliced_tomato")));
            check(player.getMainHandItem().getDamageValue() == 2, "durabilidade da faca: " + player.getMainHandItem().getDamageValue());
            player.setItemInHand(InteractionHand.MAIN_HAND, stack("onion", 1));
            click(helper, player, SPOT);
            player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            click(helper, player, SPOT);
            check(board.item().isEmpty() && countInInventory(player, item("onion")) == 1, "mão vazia não devolveu a cebola");
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.DIRT));
            click(helper, player, SPOT);
            check(board.item().isEmpty(), "terra não pode ir para a tábua");
        });
    }

    /** Pia: sem água não lava; o balde enche 8; lavar gasta 1; com água embaixo não gasta e enche balde. */
    public static void sinkWashesAndFills(GameTestHelper helper) {
        run(helper, () -> {
            ServerLevel level = helper.getLevel();
            place(helper, "sink");
            BlockPos pos = helper.absolutePos(SPOT);
            ServerPlayer player = player(helper);
            player.setItemInHand(InteractionHand.MAIN_HAND, stack("rice", 3));
            click(helper, player, SPOT);
            check(player.getMainHandItem().getCount() == 3 && countInInventory(player, item("washed_rice")) == 0, "lavou sem água");
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.WATER_BUCKET));
            click(helper, player, SPOT);
            check(level.getBlockState(pos).getValue(SinkBlock.WATER) == 8, "água: " + level.getBlockState(pos).getValue(SinkBlock.WATER));
            check(player.getMainHandItem().is(Items.BUCKET), "o balde não esvaziou");
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BUCKET));
            click(helper, player, SPOT);
            check(player.getMainHandItem().is(Items.BUCKET), "encheu balde de uma pia sem ligação (duplicação de água)");
            player.setItemInHand(InteractionHand.MAIN_HAND, stack("rice", 3));
            click(helper, player, SPOT);
            check(countInInventory(player, item("washed_rice")) == 1 && player.getMainHandItem().getCount() == 2, "lavagem de arroz");
            check(level.getBlockState(pos).getValue(SinkBlock.WATER) == 7, "a lavagem tem de gastar 1 de água");
            player.setItemInHand(InteractionHand.MAIN_HAND, stack("dirty_plate", 1));
            click(helper, player, SPOT);
            check(countInInventory(player, item("plate")) == 1, "o prato sujo não foi lavado");
            helper.setBlock(FLOOR, Blocks.WATER.defaultBlockState());
            player.setItemInHand(InteractionHand.MAIN_HAND, stack("beans", 2));
            click(helper, player, SPOT);
            check(level.getBlockState(pos).getValue(SinkBlock.WATER) == 6 || level.getBlockState(pos).getValue(SinkBlock.WATER) == 6, "água antes da ligação");
            int before = level.getBlockState(pos).getValue(SinkBlock.WATER);
            click(helper, player, SPOT);
            check(level.getBlockState(pos).getValue(SinkBlock.WATER) == before, "pia ligada não pode gastar água");
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BUCKET));
            click(helper, player, SPOT);
            check(player.getMainHandItem().is(Items.WATER_BUCKET), "pia ligada enche balde");
        });
    }

    /** Moedor: 2 trigos entram; 4 voltas dão 1 farinha; o que sobrou volta inteiro. */
    public static void millGrinds(GameTestHelper helper) {
        run(helper, () -> {
            place(helper, "mill");
            ServerPlayer player = player(helper);
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.WHEAT, 2));
            click(helper, player, SPOT);
            MillBlock.MillBlockEntity mill = (MillBlock.MillBlockEntity) helper.getLevel().getBlockEntity(helper.absolutePos(SPOT));
            check(mill.item().is(Items.WHEAT) && mill.item().getCount() == 2, "moedor: " + mill.item());
            player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            int turns = recipe(helper, "flour").time();
            for (int i = 0; i < turns; i++) {
                click(helper, player, SPOT);
            }
            check(countInInventory(player, item("flour")) == 1, "farinha: " + countInInventory(player, item("flour")));
            check(mill.item().getCount() == 1, "trigo restante: " + mill.item());
            // O jogador simulado do GameTest não guarda o agachar (no jogo ele vem do pacote de input); o gesto agachado só
            // chama take(), então a devolução é conferida direto na BlockEntity.
            ItemStack back = mill.take();
            check(back.is(Items.WHEAT) && back.getCount() == 1 && mill.item().isEmpty(), "devolução do trigo: " + back);
        });
    }

    // ---------------------------------------------------------------- armazenamento

    /** Geladeira: o creme de mousse vira mousse depois do tempo da receita; terra não entra. */
    public static void fridgeChills(GameTestHelper helper) {
        run(helper, () -> {
            place(helper, "fridge");
            StorageBlockEntity fridge = (StorageBlockEntity) helper.getLevel().getBlockEntity(helper.absolutePos(SPOT));
            check(fridge.getContainerSize() == 27, "geladeira com " + fridge.getContainerSize());
            check(!fridge.canPlaceItem(0, new ItemStack(Items.DIRT)), "terra na geladeira");
            check(fridge.canPlaceItem(0, stack("tomato", 1)), "tomate tem de caber na geladeira");
            fridge.setItem(0, stack("lemon_mousse_mix", 3));
            fridge.setItem(1, stack("cheese", 2));
            int steps = recipe(helper, "lemon_mousse").time() / StorageBlockEntity.FRIDGE_INTERVAL;
            for (int i = 0; i < steps - 1; i++) {
                fridge.chillStep(helper.getLevel());
            }
            check(fridge.getItem(0).is(item("lemon_mousse_mix")), "gelou antes do tempo");
            fridge.chillStep(helper.getLevel());
            check(fridge.getItem(0).is(item("lemon_mousse")) && fridge.getItem(0).getCount() == 3, "mousse: " + fridge.getItem(0));
            check(fridge.getItem(1).is(item("cheese")) && fridge.getItem(1).getCount() == 2, "queijo não pode mudar: " + fridge.getItem(1));
        });
    }

    /** Armário e despensa guardam e recarregam o inventário; quebrados, derrubam tudo. */
    public static void storagePersists(GameTestHelper helper) {
        run(helper, () -> {
            ServerLevel level = helper.getLevel();
            for (String id : List.of("cabinet", "pantry")) {
                place(helper, id);
                BlockPos pos = helper.absolutePos(SPOT);
                StorageBlockEntity storage = (StorageBlockEntity) level.getBlockEntity(pos);
                check(storage.getContainerSize() == (id.equals("cabinet") ? 18 : 36), id + " com " + storage.getContainerSize());
                storage.setItem(0, stack("flour", 10));
                storage.setItem(storage.getContainerSize() - 1, stack("cooking_pot", 1));
                CompoundTag saved = storage.saveWithFullMetadata(level.registryAccess());
                StorageBlockEntity copy = (StorageBlockEntity) BlockEntity.loadStatic(pos, level.getBlockState(pos), saved, level.registryAccess());
                check(copy != null && copy.getItem(0).getCount() == 10 && copy.getItem(copy.getContainerSize() - 1).is(item("cooking_pot")), id + " não persistiu");
                level.destroyBlock(pos, true);
                check(countItems(helper, SPOT, item("flour")) == 10 && countItems(helper, SPOT, item(id)) == 1, id + " não derrubou tudo");
                clearItems(helper, SPOT);
            }
        });
    }

    // ---------------------------------------------------------------- catálogo e pratos

    /** O catálogo carregado tem exatamente 100 pratos comíveis, 11 categorias e só ingredientes registrados. */
    public static void catalogHas100Dishes(GameTestHelper helper) {
        run(helper, () -> {
            RecipeCatalog catalog = RecipeCatalog.server(helper.getLevel().getServer());
            List<RecipeCatalog.Entry> dishes = catalog.dishes();
            check(dishes.size() == 100, "pratos carregados: " + dishes.size());
            Set<String> categories = new HashSet<>();
            Set<Item> results = new HashSet<>();
            for (RecipeCatalog.Entry entry : dishes) {
                CookingRecipe recipe = entry.recipe();
                categories.add(recipe.category());
                check(results.add(recipe.result()), "prato repetido: " + recipe.result());
                check(recipe.result() instanceof DishItem, "resultado não é prato: " + recipe.result());
                check(new ItemStack(recipe.result()).has(DataComponents.FOOD), "prato não é comida: " + recipe.result());
                for (CookingRecipe.CountedItem ingredient : recipe.ingredients()) {
                    check(ingredient.item() != Items.AIR, "ingrediente desconhecido em " + entry.id());
                }
            }
            check(categories.equals(Set.copyOf(GeneratedContent.CATEGORIES)), "categorias: " + categories);
            check(catalog.size() > 100, "faltam as receitas de preparo: " + catalog.size());
        });
    }

    /** Comer devolve o recipiente: prato sujo, tigela, frasco e xícara suja; e mata a fome. */
    public static void dishReturnsContainer(GameTestHelper helper) {
        run(helper, () -> {
            ServerLevel level = helper.getLevel();
            ServerPlayer player = player(helper);
            record Case(String dish, Item expected) {}
            for (Case c : List.of(new Case("grilled_steak", item("dirty_plate")), new Case("tomato_soup", Items.BOWL),
                new Case("orange_juice", Items.GLASS_BOTTLE), new Case("coffee", item("dirty_cup")))) {
                player.getFoodData().setFoodLevel(2);
                ItemStack food = stack(c.dish(), 1);
                ItemStack left = food.finishUsingItem(level, player);
                check(left.is(c.expected()), c.dish() + " devolveu " + left);
                check(player.getFoodData().getFoodLevel() > 2, c.dish() + " não alimentou");
            }
        });
    }
}
