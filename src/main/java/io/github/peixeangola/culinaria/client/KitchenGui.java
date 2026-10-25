package io.github.peixeangola.culinaria.client;

import net.minecraft.client.gui.GuiGraphicsExtractor;

/**
 * Desenho no estilo das interfaces vanilla feito só com retângulos: painel cinza com bisel, molduras de slot, seta de
 * progresso e chama. Assim as telas não dependem de texturas próprias.
 */
final class KitchenGui {
    static final int PANEL = 0xFFC6C6C6;
    static final int LIGHT = 0xFFFFFFFF;
    static final int DARK = 0xFF555555;
    static final int BLACK = 0xFF000000;
    static final int SLOT = 0xFF8B8B8B;
    static final int SLOT_DARK = 0xFF373737;
    static final int TEXT = 0xFF404040;
    static final int RED = 0xFFB02E26;
    static final int GREEN = 0xFF2E7D32;

    private KitchenGui() {}

    static void panel(GuiGraphicsExtractor g, int x, int y, int w, int h) {
        g.fill(x + 1, y, x + w - 1, y + h, BLACK);
        g.fill(x, y + 1, x + w, y + h - 1, BLACK);
        g.fill(x + 1, y + 1, x + w - 1, y + h - 1, PANEL);
        g.fill(x + 1, y + 1, x + w - 2, y + 3, LIGHT);
        g.fill(x + 1, y + 1, x + 3, y + h - 2, LIGHT);
        g.fill(x + 2, y + h - 3, x + w - 1, y + h - 1, DARK);
        g.fill(x + w - 3, y + 2, x + w - 1, y + h - 1, DARK);
        g.fill(x + 3, y + 3, x + w - 3, y + h - 3, PANEL);
    }

    /** Moldura de slot 18x18; (x, y) é o canto do item (16x16), como no Slot. */
    static void slot(GuiGraphicsExtractor g, int x, int y) {
        g.fill(x - 1, y - 1, x + 17, y + 17, SLOT);
        g.fill(x - 1, y - 1, x + 16, y, SLOT_DARK);
        g.fill(x - 1, y - 1, x, y + 16, SLOT_DARK);
        g.fill(x, y + 16, x + 17, y + 17, LIGHT);
        g.fill(x + 16, y, x + 17, y + 17, LIGHT);
    }

    /** Slot grande de saída (26x26 em volta do item). */
    static void bigSlot(GuiGraphicsExtractor g, int x, int y) {
        g.fill(x - 5, y - 5, x + 21, y + 21, SLOT);
        g.fill(x - 5, y - 5, x + 20, y - 4, SLOT_DARK);
        g.fill(x - 5, y - 5, x - 4, y + 20, SLOT_DARK);
        g.fill(x - 4, y + 20, x + 21, y + 21, LIGHT);
        g.fill(x + 20, y - 4, x + 21, y + 21, LIGHT);
    }

    /** Seta 24x16 que enche da esquerda para a direita. */
    static void arrow(GuiGraphicsExtractor g, int x, int y, float progress) {
        int filled = Math.round(progress * 24);
        for (int i = 0; i < 24; i++) {
            int half = i < 16 ? 2 : 8 - (i - 16);
            int color = i < filled ? LIGHT : SLOT;
            g.fill(x + i, y + 8 - half, x + i + 1, y + 8 + half, color);
        }
    }

    /** Chama 14x14 que diminui conforme o combustível acaba. */
    static void flame(GuiGraphicsExtractor g, int x, int y, float left, boolean lit) {
        g.fill(x + 3, y + 2, x + 11, y + 14, SLOT);
        if (lit) {
            int height = Math.max(1, Math.round(left * 12));
            g.fill(x + 3, y + 14 - height, x + 11, y + 14, 0xFFE8641A);
            g.fill(x + 5, y + 14 - Math.max(1, height / 2), x + 9, y + 14, 0xFFF6C04A);
        }
    }
}
