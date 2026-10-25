package io.github.peixeangola.culinaria.core;

/**
 * Água da pia: um balde enche 8 usos; cada lavagem gasta 1. Com uma fonte de água logo abaixo da pia ela é ligada à
 * água e não gasta nada (e enche baldes). Sem a fonte, não enche balde: assim um balde nunca vira dois.
 */
public final class SinkRules {
    public static final int MAX_WATER = 8;

    private SinkRules() {}

    public static boolean canWash(int water, boolean connected) {
        return connected || water > 0;
    }

    public static int afterWash(int water, boolean connected) {
        return connected ? water : Math.max(0, water - 1);
    }

    public static boolean canFillBucket(boolean connected) {
        return connected;
    }
}
