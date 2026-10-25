package io.github.peixeangola.culinaria.block;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.Prediction;

/** O que as estações de clique entregam na mão do jogador, e os avisos na barra de ação. */
final class Handouts {
    private Handouts() {}

    /**
     * Põe a pilha no inventário e derruba aos pés o que não couber, também no criativo (Inventory#add zera a pilha no
     * criativo com o inventário cheio, e o resultado sumiria; padrão herdado do mod de referência).
     */
    static void give(Player player, ItemStack stack) {
        if (!stack.isEmpty()) {
            player.getInventory().placeItemBackInInventory(stack, false, Prediction.PREDICTED);
        }
    }

    /** Aviso curto acima da barra de itens (só no servidor, que é quem sabe o motivo). */
    static void tell(Player player, Component message) {
        if (!player.level().isClientSide()) {
            player.sendOverlayMessage(message);
        }
    }
}
