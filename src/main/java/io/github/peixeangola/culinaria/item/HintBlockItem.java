package io.github.peixeangola.culinaria.item;

import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

/** Item de móvel com uma dica do que ele faz no tooltip. */
public class HintBlockItem extends BlockItem {
    private final String hintKey;

    public HintBlockItem(Block block, Properties properties, String hintKey) {
        super(block, properties);
        this.hintKey = hintKey;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip,
                                TooltipFlag flag) {
        tooltip.accept(Component.translatable(hintKey).withStyle(ChatFormatting.GRAY));
    }
}
