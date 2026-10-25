package io.github.peixeangola.culinaria.core;

import java.util.Arrays;
import java.util.Optional;

/**
 * As estações de culinária. BOARD, SINK e MILL funcionam por clique no bloco com um item por vez; MENU tem interface com
 * slots (ingredientes, utensílio, recipiente, combustível, saída e sobras); FRIDGE gela com o tempo o que está guardado.
 */
public enum Station {
    CUTTING_BOARD("cutting_board", Kind.BOARD, false),
    SINK("sink", Kind.SINK, false),
    MILL("mill", Kind.MILL, false),
    COUNTER("counter", Kind.MENU, false),
    STOVE("stove", Kind.MENU, true),
    OVEN("oven", Kind.MENU, true),
    BLENDER("blender", Kind.MENU, false),
    FRIDGE("fridge", Kind.FRIDGE, false);

    public enum Kind { BOARD, SINK, MILL, MENU, FRIDGE }

    private final String id;
    private final Kind kind;
    private final boolean fuel;

    Station(String id, Kind kind, boolean fuel) {
        this.id = id;
        this.kind = kind;
        this.fuel = fuel;
    }

    public String id() {
        return id;
    }

    public Kind kind() {
        return kind;
    }

    /** Queima combustível (fogão e forno). */
    public boolean usesFuel() {
        return fuel;
    }

    public boolean hasMenu() {
        return kind == Kind.MENU;
    }

    public String translationKey() {
        return "culinaria.station." + id;
    }

    public static Optional<Station> byId(String id) {
        return Arrays.stream(values()).filter(s -> s.id.equals(id)).findFirst();
    }
}
