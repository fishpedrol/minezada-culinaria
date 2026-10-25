package io.github.peixeangola.culinaria.registry;

import io.github.peixeangola.culinaria.Culinaria;
import io.github.peixeangola.culinaria.block.CookingStationBlock;
import io.github.peixeangola.culinaria.block.CulinaryCropBlock;
import io.github.peixeangola.culinaria.block.CuttingBoardBlock;
import io.github.peixeangola.culinaria.block.FruitLeavesBlock;
import io.github.peixeangola.culinaria.block.FueledStationBlock;
import io.github.peixeangola.culinaria.block.MillBlock;
import io.github.peixeangola.culinaria.block.SinkBlock;
import io.github.peixeangola.culinaria.block.StorageBlock;
import io.github.peixeangola.culinaria.content.GeneratedContent;
import io.github.peixeangola.culinaria.core.Station;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/** Blocos do Culinária, montados a partir da tabela gerada ({@link GeneratedContent}). */
public final class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Culinaria.MOD_ID);

    public static final Map<String, RegistryObject<Block>> CROPS = new LinkedHashMap<>();
    public static final Map<String, RegistryObject<Block>> LEAVES = new LinkedHashMap<>();
    public static final Map<String, RegistryObject<Block>> SAPLINGS = new LinkedHashMap<>();
    public static final Map<String, RegistryObject<Block>> FURNITURE = new LinkedHashMap<>();

    static {
        for (GeneratedContent.CropDef crop : GeneratedContent.CROPS) {
            // Mesmas propriedades do trigo: sem colisão, random ticks, quebra instantânea, som de lavoura
            CROPS.put(crop.block(), BLOCKS.register(crop.block(), () -> new CulinaryCropBlock(() -> ModItems.item(crop.seeds()),
                BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).setId(BLOCKS.key(crop.block())))));
        }
        for (GeneratedContent.TreeDef tree : GeneratedContent.TREES) {
            LEAVES.put(tree.leaves(), BLOCKS.register(tree.leaves(), () -> new FruitLeavesBlock(
                () -> BuiltInRegistries.ITEM.getValue(Identifier.parse(tree.fruit())),
                BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).setId(BLOCKS.key(tree.leaves())))));
            TreeGrower grower = new TreeGrower(Culinaria.MOD_ID + "_" + tree.id(),
                WeightedList.of(ResourceKey.create(Registries.FEATURE, Culinaria.id(tree.feature()))), WeightedList.of(), WeightedList.of(), null);
            SAPLINGS.put(tree.sapling(), BLOCKS.register(tree.sapling(), () -> new SaplingBlock(grower,
                BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).setId(BLOCKS.key(tree.sapling())))));
        }
        for (GeneratedContent.FurnitureDef def : GeneratedContent.FURNITURE) {
            FURNITURE.put(def.id(), BLOCKS.register(def.id(), () -> furniture(def)));
        }
    }

    private ModBlocks() {}

    private static Block furniture(GeneratedContent.FurnitureDef def) {
        Optional<Station> station = Optional.ofNullable(def.station()).flatMap(Station::byId);
        BlockBehaviour.Properties wood = BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).strength(2.0F).sound(SoundType.WOOD)
            .noOcclusion().setId(BLOCKS.key(def.id()));
        BlockBehaviour.Properties metal = BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(3.0F).sound(SoundType.METAL)
            .requiresCorrectToolForDrops().noOcclusion().setId(BLOCKS.key(def.id()));
        BlockBehaviour.Properties stone = BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(2.5F).sound(SoundType.STONE)
            .requiresCorrectToolForDrops().noOcclusion().setId(BLOCKS.key(def.id()));
        if (station.isEmpty()) {
            return new StorageBlock(def.rows(), false, wood);
        }
        return switch (station.get()) {
            case CUTTING_BOARD -> new CuttingBoardBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).strength(0.8F)
                .sound(SoundType.WOOD).noOcclusion().pushReaction(PushReaction.POPPED).setId(BLOCKS.key(def.id())));
            case SINK -> new SinkBlock(stone);
            case MILL -> new MillBlock(stone);
            case COUNTER -> new CookingStationBlock(Station.COUNTER, wood);
            case BLENDER -> new CookingStationBlock(Station.BLENDER, metal);
            case STOVE -> new FueledStationBlock(Station.STOVE,
                metal.lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 13 : 0));
            case OVEN -> new FueledStationBlock(Station.OVEN,
                stone.lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 13 : 0));
            case FRIDGE -> new StorageBlock(def.rows(), true, metal);
        };
    }

    public static Block block(String id) {
        return BuiltInRegistries.BLOCK.getValue(Culinaria.id(id));
    }

    /** Feature da árvore frutífera (data/culinaria/worldgen/feature/&lt;fruta&gt;_tree.json). */
    public static ResourceKey<Feature> treeFeature(GeneratedContent.TreeDef tree) {
        return ResourceKey.create(Registries.FEATURE, Culinaria.id(tree.feature()));
    }
}
