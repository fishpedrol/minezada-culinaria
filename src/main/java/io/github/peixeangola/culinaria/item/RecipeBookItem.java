package io.github.peixeangola.culinaria.item;

import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

/**
 * Livro de receitas: um item (fácil de levar e de dar ao amigo) que abre a tela do livro no cliente. A tela é do pacote
 * client e chega aqui por {@link #opener}, preenchido só no cliente físico; o servidor dedicado nunca carrega classe de
 * tela.
 */
public class RecipeBookItem extends Item {
    /** Abre a tela do livro; o cliente troca este valor no setup. */
    public static volatile Runnable opener = () -> {};

    public RecipeBookItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) {
            opener.run();
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip,
                                TooltipFlag flag) {
        tooltip.accept(Component.translatable("tooltip.culinaria.recipe_book").withStyle(ChatFormatting.GRAY));
    }
}
