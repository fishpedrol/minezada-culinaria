package io.github.peixeangola.culinaria.core;

import java.util.List;
import java.util.Map;

/**
 * Regra de casamento das receitas, sem Minecraft (testada no JUnit). Uma receita casa quando os tipos de ingrediente
 * presentes são exatamente os da receita (nada sobrando, nada faltando) e cada um tem pelo menos a quantidade pedida.
 * Assim nunca há duas receitas possíveis para o mesmo conteúdo, e um ingrediente estranho trava a estação com aviso.
 */
public final class CookingMatcher {
    private CookingMatcher() {}

    /** Um ingrediente pedido: chave (o item) e quantidade por preparo. */
    public record Need<K>(K key, int count) {}

    public enum Result { MATCH, NOT_ENOUGH, NO_MATCH }

    /**
     * @param needs   ingredientes da receita (chaves distintas)
     * @param present soma das quantidades por chave nos slots de ingrediente (só chaves com quantidade &gt; 0)
     */
    public static <K> Result match(List<Need<K>> needs, Map<K, Integer> present) {
        if (present.size() != needs.size()) {
            return Result.NO_MATCH;
        }
        boolean enough = true;
        for (Need<K> need : needs) {
            Integer have = present.get(need.key());
            if (have == null) {
                return Result.NO_MATCH;
            }
            if (have < need.count()) {
                enough = false;
            }
        }
        return enough ? Result.MATCH : Result.NOT_ENOUGH;
    }

    /**
     * Quanto cabe na saída: vazia aceita até o máximo da pilha; com o mesmo item, o que falta para encher. Outro item: 0.
     */
    public static boolean outputAccepts(boolean empty, boolean sameItem, int current, int adding, int maxStack) {
        if (empty) {
            return adding <= maxStack;
        }
        return sameItem && current + adding <= maxStack;
    }
}
