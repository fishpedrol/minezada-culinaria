package io.github.peixeangola.culinaria.item;

import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jspecify.annotations.Nullable;

/**
 * Prato pronto: comida (ou bebida) com a categoria no tooltip e o que sobra depois de consumir (prato e xícara voltam
 * sujos, para lavar na pia; tigela e frasco voltam como no vanilla).
 */
public class DishItem extends Item {
    private final String category;
    private final @Nullable String returns;

    public DishItem(Properties properties, String category, @Nullable String returns) {
        super(properties);
        this.category = category;
        this.returns = returns;
    }

    public String category() {
        return category;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip,
                                TooltipFlag flag) {
        tooltip.accept(Component.translatable("tooltip.culinaria.dish_category", Component.translatable("culinaria.category." + category))
            .withStyle(ChatFormatting.GOLD));
        if (returns != null) {
            Item item = BuiltInRegistries.ITEM.getValue(Identifier.parse(returns));
            tooltip.accept(Component.translatable("tooltip.culinaria.returns", Component.translatable(item.getDescriptionId())).withStyle(ChatFormatting.GRAY));
        }
    }
}
