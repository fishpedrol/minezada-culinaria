package io.github.peixeangola.culinaria.core;

/**
 * Números das árvores frutíferas. Cada folha que dá fruto passa por 4 idades: 0 sem nada, 1 florida, 2 fruto verde e
 * 3 fruto maduro. Só a idade 3 se colhe; colher volta para 0 sem quebrar a folha.
 */
public final class FruitRules {
    public static final int MAX_AGE = 3;
    /** Padrão do config: 1 em 2 random ticks avança uma idade (~7 minutos de 0 a 3 com o random tick padrão). */
    public static final int DEFAULT_GROWTH_CHANCE = 2;

    private FruitRules() {}

    public static boolean canHarvest(boolean fruiting, int age) {
        return fruiting && age >= MAX_AGE;
    }

    public static boolean grows(boolean fruiting, int age) {
        return fruiting && age < MAX_AGE;
    }

    /** Idade depois de um random tick que sorteou {@code roll} em [0, chance). */
    public static int nextAge(boolean fruiting, int age, int roll) {
        return grows(fruiting, age) && roll == 0 ? age + 1 : age;
    }

    /** Frutas por colheita: 1, ou 2 quando o sorteio em [0, 4) dá 0 (25%). */
    public static int harvestCount(int roll) {
        return roll == 0 ? 2 : 1;
    }
}
