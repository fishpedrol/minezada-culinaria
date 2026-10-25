package io.github.peixeangola.culinaria.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

/** Regras puras: casamento de receitas, saída, árvores frutíferas e pia. Valores esperados são literais da regra. */
class CoreRulesTest {
    private static final List<CookingMatcher.Need<String>> SOUP = List.of(
        new CookingMatcher.Need<>("tomato", 3), new CookingMatcher.Need<>("onion", 1), new CookingMatcher.Need<>("water", 1));

    @Test
    void exactIngredientsMatch() {
        assertEquals(CookingMatcher.Result.MATCH, CookingMatcher.match(SOUP, Map.of("tomato", 3, "onion", 1, "water", 1)));
        assertEquals(CookingMatcher.Result.MATCH, CookingMatcher.match(SOUP, Map.of("tomato", 64, "onion", 9, "water", 1)));
    }

    @Test
    void missingQuantityIsNotEnough() {
        assertEquals(CookingMatcher.Result.NOT_ENOUGH, CookingMatcher.match(SOUP, Map.of("tomato", 2, "onion", 1, "water", 1)));
    }

    @Test
    void extraOrMissingTypeDoesNotMatch() {
        assertEquals(CookingMatcher.Result.NO_MATCH, CookingMatcher.match(SOUP, Map.of("tomato", 3, "onion", 1)));
        assertEquals(CookingMatcher.Result.NO_MATCH, CookingMatcher.match(SOUP, Map.of("tomato", 3, "onion", 1, "water", 1, "dirt", 1)));
        assertEquals(CookingMatcher.Result.NO_MATCH, CookingMatcher.match(SOUP, Map.of("tomato", 3, "garlic", 1, "water", 1)));
    }

    @Test
    void outputAcceptsOnlySameItemWithRoom() {
        assertTrue(CookingMatcher.outputAccepts(true, false, 0, 2, 16));
        assertTrue(CookingMatcher.outputAccepts(false, true, 14, 2, 16));
        assertFalse(CookingMatcher.outputAccepts(false, true, 15, 2, 16));
        assertFalse(CookingMatcher.outputAccepts(false, false, 1, 1, 64));
    }

    @Test
    void fruitGrowsOnlyOnFruitingLeavesUpToThree() {
        assertEquals(1, FruitRules.nextAge(true, 0, 0));
        assertEquals(0, FruitRules.nextAge(true, 0, 1));
        assertEquals(3, FruitRules.nextAge(true, 3, 0));
        assertEquals(0, FruitRules.nextAge(false, 0, 0));
        assertTrue(FruitRules.canHarvest(true, 3));
        assertFalse(FruitRules.canHarvest(true, 2));
        assertFalse(FruitRules.canHarvest(false, 3));
        assertEquals(2, FruitRules.harvestCount(0));
        assertEquals(1, FruitRules.harvestCount(3));
    }

    @Test
    void sinkWaterRules() {
        assertFalse(SinkRules.canWash(0, false));
        assertTrue(SinkRules.canWash(1, false));
        assertTrue(SinkRules.canWash(0, true));
        assertEquals(7, SinkRules.afterWash(8, false));
        assertEquals(0, SinkRules.afterWash(0, false));
        assertEquals(5, SinkRules.afterWash(5, true));
        assertFalse(SinkRules.canFillBucket(false));
        assertTrue(SinkRules.canFillBucket(true));
        assertEquals(8, SinkRules.MAX_WATER);
    }

    @Test
    void stationsHaveExpectedKinds() {
        assertTrue(Station.STOVE.usesFuel() && Station.OVEN.usesFuel());
        assertFalse(Station.COUNTER.usesFuel() || Station.BLENDER.usesFuel());
        assertEquals(Station.Kind.BOARD, Station.byId("cutting_board").orElseThrow().kind());
        assertEquals(4, java.util.Arrays.stream(Station.values()).filter(Station::hasMenu).count());
    }
}
