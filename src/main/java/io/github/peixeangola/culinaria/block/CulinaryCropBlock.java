package io.github.peixeangola.culinaria.block;

import java.util.function.Supplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;

/**
 * Lavoura do Culinária: o mesmo ciclo do trigo (terra arada, luz, idades 0–7, farinha de osso, colheita pela loot table
 * gerada), só com a semente própria. Crescimento e colheita rodam no servidor, pelos mecanismos vanilla.
 */
public class CulinaryCropBlock extends CropBlock {
    private final Supplier<? extends Item> seeds;

    public CulinaryCropBlock(Supplier<? extends Item> seeds, Properties properties) {
        super(properties);
        this.seeds = seeds;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return seeds.get();
    }
}
