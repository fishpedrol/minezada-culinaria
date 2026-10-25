package io.github.peixeangola.culinaria.registry;

import io.github.peixeangola.culinaria.Culinaria;
import io.github.peixeangola.culinaria.block.CookingStationBlock;
import io.github.peixeangola.culinaria.block.CookingStationBlockEntity;
import io.github.peixeangola.culinaria.block.CuttingBoardBlock;
import io.github.peixeangola.culinaria.block.MillBlock;
import io.github.peixeangola.culinaria.block.StorageBlock;
import io.github.peixeangola.culinaria.block.StorageBlockEntity;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
        DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Culinaria.MOD_ID);

    /** Bancada, fogão, forno e liquidificador: uma BlockEntity só; a estação vem do bloco. */
    public static final RegistryObject<BlockEntityType<CookingStationBlockEntity>> COOKING_STATION =
        BLOCK_ENTITIES.register("cooking_station", () -> new BlockEntityType<>(CookingStationBlockEntity::new, furnitureOf(CookingStationBlock.class)));

    /** Geladeira, armário e despensa. */
    public static final RegistryObject<BlockEntityType<StorageBlockEntity>> STORAGE =
        BLOCK_ENTITIES.register("storage", () -> new BlockEntityType<>(StorageBlockEntity::new, furnitureOf(StorageBlock.class)));

    public static final RegistryObject<BlockEntityType<CuttingBoardBlock.CuttingBoardBlockEntity>> CUTTING_BOARD =
        BLOCK_ENTITIES.register("cutting_board", () -> new BlockEntityType<>(CuttingBoardBlock.CuttingBoardBlockEntity::new,
            Set.of(ModBlocks.FURNITURE.get("cutting_board").get())));

    public static final RegistryObject<BlockEntityType<MillBlock.MillBlockEntity>> MILL =
        BLOCK_ENTITIES.register("mill", () -> new BlockEntityType<>(MillBlock.MillBlockEntity::new, Set.of(ModBlocks.FURNITURE.get("mill").get())));

    private ModBlockEntities() {}

    private static Set<Block> furnitureOf(Class<? extends Block> type) {
        return ModBlocks.FURNITURE.values().stream().map(RegistryObject::get).filter(type::isInstance).collect(Collectors.toUnmodifiableSet());
    }
}
