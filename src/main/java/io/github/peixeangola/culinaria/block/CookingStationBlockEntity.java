package io.github.peixeangola.culinaria.block;

import io.github.peixeangola.culinaria.CulinariaConfig;
import io.github.peixeangola.culinaria.catalog.RecipeCatalog;
import io.github.peixeangola.culinaria.core.CookingMatcher;
import io.github.peixeangola.culinaria.core.CookingStatus;
import io.github.peixeangola.culinaria.core.Station;
import io.github.peixeangola.culinaria.menu.CookingStationMenu;
import io.github.peixeangola.culinaria.menu.KitchenSlots;
import io.github.peixeangola.culinaria.recipe.CookingRecipe;
import io.github.peixeangola.culinaria.registry.ModBlockEntities;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.component.CookingFuel;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.loot.providers.number.ints.ResolvableInt;
import org.jspecify.annotations.Nullable;

/**
 * Inventário e processamento das estações com interface. Slots: 0–5 ingredientes, 6 utensílio (não é gasto), 7
 * recipiente (um por unidade produzida), 8 combustível (fogão e forno), 9 saída e 10–11 sobras (baldes e frascos que os
 * ingredientes devolvem). Tudo é decidido no tick do servidor:
 * <ol>
 *   <li>acha a receita da estação cujos ingredientes são exatamente os tipos dos slots ({@link RecipeCatalog#find});</li>
 *   <li>confere utensílio, recipiente, espaço na saída e nas sobras e, no fogão e no forno, o combustível;</li>
 *   <li>avança o progresso; no fim gasta os ingredientes e o recipiente, põe o prato na saída e as sobras nos slots delas.</li>
 * </ol>
 * Se algo falta, o progresso fica parado (não se perde) e o motivo vai para o cliente pelo ContainerData. Trocar os
 * ingredientes por outra receita zera o progresso. Os itens só saem pela interface, por funil nas entradas permitidas
 * ou ao quebrar o bloco (caem no chão).
 */
public class CookingStationBlockEntity extends BaseContainerBlockEntity {
    public static final int INGREDIENT_SLOTS = 6;
    public static final int UTENSIL = 6;
    public static final int CONTAINER = 7;
    public static final int FUEL = 8;
    public static final int OUTPUT = 9;
    public static final int LEFTOVER_A = 10;
    public static final int LEFTOVER_B = 11;
    public static final int SIZE = 12;

    /** Índices do ContainerData sincronizado com a interface. */
    public static final int DATA_PROGRESS = 0;
    public static final int DATA_TOTAL = 1;
    public static final int DATA_BURN = 2;
    public static final int DATA_BURN_TOTAL = 3;
    public static final int DATA_STATUS = 4;
    public static final int DATA_RECIPE = 5;
    public static final int DATA_SIZE = 6;

    private NonNullList<ItemStack> items = NonNullList.withSize(SIZE, ItemStack.EMPTY);
    private int progress;
    private int total;
    private int burnTime;
    private int burnTotal;
    private CookingStatus status = CookingStatus.IDLE;
    private int recipeIndex = -1;
    private @Nullable Identifier currentRecipe;

    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case DATA_PROGRESS -> progress;
                case DATA_TOTAL -> total;
                case DATA_BURN -> burnTime;
                case DATA_BURN_TOTAL -> burnTotal;
                case DATA_STATUS -> status.ordinal();
                case DATA_RECIPE -> recipeIndex;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            // só o servidor escreve; o lado do cliente usa um SimpleContainerData
        }

        @Override
        public int getCount() {
            return DATA_SIZE;
        }
    };

    public CookingStationBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COOKING_STATION.get(), pos, state);
    }

    public Station station() {
        return ((CookingStationBlock) getBlockState().getBlock()).station();
    }

    public CookingStatus status() {
        return status;
    }

    public int progress() {
        return progress;
    }

    public int burnTime() {
        return burnTime;
    }

    public ContainerData data() {
        return data;
    }

    // ---------------------------------------------------------------- tick do servidor

    public void serverTick(ServerLevel level) {
        int before = progress;
        int burnBefore = burnTime;
        if (burnTime > 0) {
            burnTime--;
        }
        RecipeCatalog catalog = RecipeCatalog.server(level.getServer());
        RecipeCatalog.Lookup lookup = catalog.find(station(), items.subList(0, INGREDIENT_SLOTS));
        RecipeCatalog.Entry entry = lookup.entry();
        recipeIndex = entry == null ? -1 : catalog.indexOf(entry);
        CookingStatus next;
        if (entry == null) {
            next = hasIngredients() ? CookingStatus.NO_RECIPE : CookingStatus.IDLE;
            progress = 0;
            total = 0;
            currentRecipe = null;
        } else {
            CookingRecipe recipe = entry.recipe();
            if (!entry.id().equals(currentRecipe)) {
                progress = 0;
                currentRecipe = entry.id();
            }
            total = scaledTime(recipe.time());
            next = lookup.result() == CookingMatcher.Result.NOT_ENOUGH ? CookingStatus.MORE_NEEDED : missingFor(recipe);
            if (next == CookingStatus.WORKING && station().usesFuel() && burnTime <= 0 && !lightFuel(level)) {
                next = CookingStatus.NO_FUEL;
            }
            if (next == CookingStatus.WORKING) {
                progress++;
                if (progress >= total) {
                    craft(level, recipe);
                    progress = 0;
                }
            }
        }
        status = next;
        if (station().usesFuel()) {
            updateLit(level, burnTime > 0);
        }
        if (progress != before || burnTime != burnBefore) {
            setChanged();
        }
    }

    private int scaledTime(int time) {
        return Math.max(1, (int) Math.ceil(time / CulinariaConfig.cookingSpeed()));
    }

    private boolean hasIngredients() {
        for (int i = 0; i < INGREDIENT_SLOTS; i++) {
            if (!items.get(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /** O que impede a receita de andar agora (sem contar o combustível); WORKING se nada. */
    CookingStatus missingFor(CookingRecipe recipe) {
        if (recipe.utensil().isPresent() && !items.get(UTENSIL).is(recipe.utensil().get())) {
            return CookingStatus.NEEDS_UTENSIL;
        }
        if (recipe.container().isPresent()) {
            ItemStack container = items.get(CONTAINER);
            if (!container.is(recipe.container().get()) || container.getCount() < recipe.resultCount()) {
                return CookingStatus.NEEDS_CONTAINER;
            }
        }
        ItemStack output = items.get(OUTPUT);
        ItemStack result = recipe.resultStack();
        if (!CookingMatcher.outputAccepts(output.isEmpty(), ItemStack.isSameItemSameComponents(output, result), output.getCount(),
            result.getCount(), result.getMaxStackSize())) {
            return CookingStatus.OUTPUT_FULL;
        }
        if (!leftoversFit(leftoversOf(recipe))) {
            return CookingStatus.LEFTOVERS_FULL;
        }
        return CookingStatus.WORKING;
    }

    /** O que os ingredientes deixam depois de usados (o balde do leite, o frasco do mel...). */
    static List<ItemStack> leftoversOf(CookingRecipe recipe) {
        List<ItemStack> leftovers = new ArrayList<>();
        for (CookingRecipe.CountedItem ingredient : recipe.ingredients()) {
            ItemStackTemplate remainder = new ItemStack(ingredient.item()).getCraftingRemainder();
            if (remainder != null) {
                ItemStack stack = remainder.create();
                stack.setCount(stack.getCount() * ingredient.count());
                leftovers.add(stack);
            }
        }
        return leftovers;
    }

    /** Simula pôr as sobras nos dois slots delas, sem mexer em nada. */
    private boolean leftoversFit(List<ItemStack> leftovers) {
        ItemStack a = items.get(LEFTOVER_A).copy();
        ItemStack b = items.get(LEFTOVER_B).copy();
        for (ItemStack leftover : leftovers) {
            ItemStack rest = merge(a, leftover.copy());
            if (a.isEmpty() && !rest.isEmpty()) {
                a = rest;
                rest = ItemStack.EMPTY;
            }
            rest = merge(b, rest);
            if (b.isEmpty() && !rest.isEmpty()) {
                b = rest;
                rest = ItemStack.EMPTY;
            }
            if (!rest.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /** Junta {@code adding} em {@code target} (mesmo item) e devolve o que não coube. */
    private static ItemStack merge(ItemStack target, ItemStack adding) {
        if (adding.isEmpty() || target.isEmpty() || !ItemStack.isSameItemSameComponents(target, adding)) {
            return adding;
        }
        int moved = Math.min(adding.getCount(), target.getMaxStackSize() - target.getCount());
        target.grow(moved);
        adding.shrink(moved);
        return adding;
    }

    private void storeLeftover(ItemStack leftover) {
        for (int slot : new int[] {LEFTOVER_A, LEFTOVER_B}) {
            ItemStack current = items.get(slot);
            if (current.isEmpty()) {
                items.set(slot, leftover);
                return;
            }
            leftover = merge(current, leftover);
            if (leftover.isEmpty()) {
                return;
            }
        }
        if (!leftover.isEmpty() && level != null) {
            // não acontece (leftoversFit foi conferido), mas nada some: cai em cima da estação
            Containers.dropItemStack(level, worldPosition.getX() + 0.5D, worldPosition.getY() + 1.0D, worldPosition.getZ() + 0.5D, leftover);
        }
    }

    private void craft(ServerLevel level, CookingRecipe recipe) {
        for (CookingRecipe.CountedItem ingredient : recipe.ingredients()) {
            int remaining = ingredient.count();
            for (int i = 0; i < INGREDIENT_SLOTS && remaining > 0; i++) {
                ItemStack stack = items.get(i);
                if (stack.is(ingredient.item())) {
                    int taken = Math.min(remaining, stack.getCount());
                    stack.shrink(taken);
                    remaining -= taken;
                }
            }
        }
        recipe.container().ifPresent(container -> items.get(CONTAINER).shrink(recipe.resultCount()));
        ItemStack output = items.get(OUTPUT);
        if (output.isEmpty()) {
            items.set(OUTPUT, recipe.resultStack());
        } else {
            output.grow(recipe.resultCount());
        }
        leftoversOf(recipe).forEach(this::storeLeftover);
        setChanged();
        double x = worldPosition.getX() + 0.5D;
        double y = worldPosition.getY() + 1.0D;
        double z = worldPosition.getZ() + 0.5D;
        level.sendParticles(ParticleTypes.HAPPY_VILLAGER, x, y, z, 6, 0.25D, 0.1D, 0.25D, 0.0D);
        level.playSound(null, worldPosition, SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 0.6F, 1.3F);
    }

    // ---------------------------------------------------------------- combustível

    /** Acende um combustível do slot, se houver (a lava deixa o balde no slot, como na fornalha). */
    private boolean lightFuel(ServerLevel level) {
        ItemStack fuel = items.get(FUEL);
        int duration = burnDuration(level, fuel);
        if (duration <= 0) {
            return false;
        }
        burnTime = duration;
        burnTotal = duration;
        ItemStackTemplate remainder = fuel.getCraftingRemainder();
        fuel.shrink(1);
        if (fuel.isEmpty() && remainder != null) {
            items.set(FUEL, remainder.create());
        }
        setChanged();
        return true;
    }

    public int burnDuration(ServerLevel level, ItemStack fuel) {
        if (fuel.isEmpty() || !fuel.has(DataComponents.COOKING_FUEL)) {
            return 0;
        }
        int vanilla = ResolvableInt.getFromItem(fuel, DataComponents.COOKING_FUEL, CookingFuel::burnTime, getLootContext(level), 0);
        return net.minecraftforge.event.ForgeEventFactory.getItemBurnTime(fuel, vanilla, RecipeType.SMELTING);
    }

    public static boolean isFuel(ItemStack stack) {
        return stack.has(DataComponents.COOKING_FUEL);
    }

    private void updateLit(ServerLevel level, boolean lit) {
        BlockState state = level.getBlockState(worldPosition);
        if (level.getBlockEntity(worldPosition) == this && state.hasProperty(FueledStationBlock.LIT)
            && state.getValue(FueledStationBlock.LIT) != lit) {
            level.setBlock(worldPosition, state.setValue(FueledStationBlock.LIT, lit), Block.UPDATE_ALL);
        }
    }

    // ---------------------------------------------------------------- Container

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
        return SIZE;
    }

    /** Funis e o shift-clique respeitam o papel de cada slot; saída e sobras nunca recebem. */
    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return KitchenSlots.mayPlace(station(), slot, stack);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.culinaria." + station().id());
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new CookingStationMenu(containerId, inventory, this, data, station());
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, items);
        output.putInt("progress", progress);
        output.putInt("burn_time", burnTime);
        output.putInt("burn_total", burnTotal);
        if (currentRecipe != null) {
            output.putString("recipe", currentRecipe.toString());
        }
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        items = NonNullList.withSize(SIZE, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, items);
        progress = input.getIntOr("progress", 0);
        burnTime = input.getIntOr("burn_time", 0);
        burnTotal = input.getIntOr("burn_total", 0);
        currentRecipe = input.getString("recipe").map(Identifier::tryParse).orElse(null);
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        super.preRemoveSideEffects(pos, state);
        if (level != null) {
            Containers.dropContents(level, pos, this);
        }
    }

    /** Para os GameTests: põe uma pilha num slot como a interface faria. */
    public void put(int slot, ItemStack stack) {
        setItem(slot, stack);
    }

    /** Item atual do slot (para os GameTests e o renderer). */
    public ItemStack stackIn(int slot) {
        return items.get(slot);
    }

    /** Receita reconhecida agora (índice no catálogo), ou -1. */
    public int recipeIndex() {
        return recipeIndex;
    }

    static boolean is(ItemStack stack, Item item) {
        return stack.is(item);
    }
}
