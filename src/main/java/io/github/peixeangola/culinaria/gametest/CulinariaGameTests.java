package io.github.peixeangola.culinaria.gametest;

import io.github.peixeangola.culinaria.Culinaria;
import java.util.function.Consumer;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.registries.DeferredRegister;

/** GameTests do Culinária (preenchidos na etapa de testes). */
public final class CulinariaGameTests {
    public static final DeferredRegister<Consumer<GameTestHelper>> TESTS = DeferredRegister.create(Registries.TEST_FUNCTION, Culinaria.MOD_ID);

    private CulinariaGameTests() {}
}
