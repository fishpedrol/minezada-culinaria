package io.github.peixeangola.culinaria.core;

/**
 * Por que a estação com interface está parada (ou trabalhando). O servidor decide e manda o ordinal ao cliente pelo
 * ContainerData; o cliente só mostra o texto.
 */
public enum CookingStatus {
    IDLE("idle"),
    NO_RECIPE("no_recipe"),
    MORE_NEEDED("more_needed"),
    NEEDS_UTENSIL("needs_utensil"),
    NEEDS_CONTAINER("needs_container"),
    NO_FUEL("no_fuel"),
    OUTPUT_FULL("output_full"),
    LEFTOVERS_FULL("leftovers_full"),
    WORKING("working");

    private final String id;

    CookingStatus(String id) {
        this.id = id;
    }

    public String translationKey() {
        return "culinaria.status." + id;
    }

    /** Situações em que o jogador precisa fazer algo (aparecem em vermelho). */
    public boolean isProblem() {
        return this != IDLE && this != WORKING;
    }

    public static CookingStatus byOrdinal(int ordinal) {
        CookingStatus[] values = values();
        return ordinal >= 0 && ordinal < values.length ? values[ordinal] : IDLE;
    }
}
