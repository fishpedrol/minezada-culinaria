# Roadmap: estado real das etapas

Atualizado em 25/10/2026. "Feito" só quando há evidência em [TESTES.md](TESTES.md).

| Etapa | Estado | Evidência / o que falta |
|---|---|---|
| 0. Auditoria e preparação | Feito | Mod de referência lido (versões, build, padrões, armadilhas da API 26.3) e não alterado; projeto novo em `C:\Users\Cliente\Documents\minezada-culinaria` com git próprio |
| 1. Fundação compilável | Feito | `gradlew build` passa; o servidor dedicado de desenvolvimento sobe com o mod (Forge 66.0.13) |
| 2. Ingredientes e agricultura | Feito | 14 lavouras com sementes na grama, loot como o trigo; 5 árvores frutíferas com muda, worldgen e regeneração. GameTests `crop_grows_and_drops`, `fruit_harvest_regrows`, `fruit_leaves_persist`, `sapling_grows_fruit_tree` |
| 3. Equipamentos e armazenamento | Feito | Geladeira, armário e despensa persistentes; fogão, forno, bancada, liquidificador, pia, tábua e moedor. GameTests `storage_persists`, `station_persistence`, `station_keeps_items_on_close`, `fridge_chills` |
| 4. Sistema de processamento | Feito | Cortar, lavar, moer, misturar/bater, amassar/abrir, ferver, fritar, assar, bater no liquidificador e gelar. GameTests `stove_cooks_recipe`, `oven_bakes_with_fuel`, `counter_assembles_dish`, `cutting_board_cuts`, `sink_washes_and_fills`, `mill_grinds`, `remainders_returned`, `station_status_messages` |
| 5. Livro de receitas | Feito (lógica e tela) | Catálogo sincronizado do servidor; busca, 13 filtros, "tenho / preciso" com inventário e móveis, origem, etapas, navegação para os preparos. Visto no cliente pelo teste visual (TESTES.md) |
| 6. Catálogo completo | Feito | 100 pratos + 54 preparos; contagem conferida no gerador, no JUnit (`CatalogDataTest`) e em jogo (`catalog_has_100_dishes`) |
| 7. Equipamentos restantes e acabamento | Feito, com ressalvas | Texturas, modelos, blockstates e traduções pt_br/en_us de tudo; ressalvas abaixo |
| 8. Testes e estabilização | Feito no que dá para automatizar | JUnit, GameTests, servidor dedicado e teste visual no cliente; multiplayer com dois jogadores de verdade não foi feito |

## Ressalvas e pendências (em ordem)

1. **Multiplayer com duas pessoas:** o servidor dedicado sobe com o mod e toda a lógica é do servidor, mas ninguém entrou com dois clientes
   ao mesmo tempo. Teste sugerido: os dois abrindo o mesmo fogão, um tirando a saída enquanto o outro coloca ingredientes; os dois colhendo
   a mesma folha; o livro de cada um mostrando os móveis por perto.
2. **Balanceamento** depois de jogar: chance de sementes na grama (10%), velocidade das frutas (1 em 2 por random tick), raridade das
   árvores (1 a cada 14 chunks por bioma), fome e saturação dos pratos, tempos de preparo. Os três primeiros estão no config
   (`config/culinaria-common.toml`).
3. **Visual:** os móveis são blocos cheios com textura (bancada, fogão, forno, geladeira, armário, despensa) ou modelos simples (pia, tábua,
   moedor, liquidificador). A geladeira tem 1 bloco de altura. Não há animação de manivela nem de panela.
4. **Livro:** a navegação usa só o mouse (sem atalhos de teclado além da busca); a contagem dos móveis próximos é tirada quando o livro abre
   (fechar e abrir de novo atualiza).
5. **Funil:** as estações aceitam funil nas entradas permitidas (`canPlaceItem`), mas não há `WorldlyContainer` com faces; o funil por baixo
   pode puxar de qualquer slot.
6. **Compatibilidade com outros mods** (por exemplo Serene Seasons, que o dono usa): as lavouras não estão nas tags de estação dele.
