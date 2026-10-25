# Plano de testes e resultados observados

Ambiente: PC do dono, Windows 11, Temurin 25.0.4.1+1 (`C:\Users\Cliente\Documents\jdk\jdk-25.0.4.1+1`), Node 24.19.0, Forge 26.3-66.0.13.
Só entra aqui o que foi rodado de verdade, com o comando e a saída. Data: 25/10/2026.

## 1. Geração e validação do catálogo

| Comando | Resultado |
|---|---|
| `node tools/gen/generate.mjs` | `303 texturas, 808 arquivos JSON, 154 receitas (100 pratos)`. O gerador também valida: 100 pratos, categorias não vazias, ingredientes conhecidos, itens vanilla com origem documentada, regras de cada estação, cadeia viável de toda receita, todo ingrediente do mod usado, nenhum par de receitas com os mesmos ingredientes na mesma estação |
| `node tools/gen/docs.mjs` | `docs: 100 pratos, 154 receitas, 99 ingredientes distintos` |

## 2. Compilação e JUnit

| Comando | Resultado |
|---|---|
| `.\gradlew.bat build` (primeira fundação, só a classe @Mod) | `BUILD SUCCESSFUL in 29s` |
| `.\gradlew.bat build` (mod completo) | `BUILD SUCCESSFUL`; `CoreRulesTest` 7 testes e `CatalogDataTest` 13 testes, 0 falhas, 0 erros (XML em `build/test-results/test`) |

O que o JUnit cobre:
- **`CoreRulesTest`**: casamento exato de receitas (tipos iguais, quantidade mínima, item estranho trava), espaço na saída, idades e
  colheita das folhas frutíferas, água da pia (gasto, ligação, balde só com ligação), tipos das estações.
- **`CatalogDataTest`** (lê os JSON gerados, como o jogo): exatamente 100 pratos distintos; 11 categorias com 8–10 pratos cada; todo item
  referenciado existe; regras de cada estação (um item nas de clique, até 6 nas de interface, panela/frigideira no fogão, forma no forno);
  nenhum par de receitas iguais por estação; toda receita alcançável a partir das fontes do mundo (as mesmas do "onde conseguir" do livro);
  todo ingrediente usado; pratos com fome e saturação coerentes; todo item com `items/*.json`, modelo, textura e nome em pt_br e en_us;
  todo blockstate aponta para modelo existente que só usa texturas `block/`; todo bloco com loot table; as duas línguas com as mesmas
  chaves; árvores com folhas que dão e que não dão fruto, biome modifier e receita da muda; um `test_instance` por GameTest.

## 3. GameTests (servidor de jogo sem tela)

`.\gradlew.bat runGameTestServer` → **`All 21 required tests passed :)`** (20 do Culinária + `minecraft:always_pass`). Log:
`Forge mod loading, version 66.0.13, for MC 26.3`.

| GameTest | O que confere |
|---|---|
| `crop_grows_and_drops` | Semente plantada por clique em terra arada; lavoura nova só devolve a semente; cresce até 7 com random ticks; madura dá 1–3 tomates e sementes |
| `fruit_harvest_regrows` | Clique na folha madura solta 1–2 laranjas sem quebrar a folha, volta à idade 0; segundo clique não duplica; a fruta volta a amadurecer; colher segurando um item também funciona |
| `fruit_leaves_persist` | Folha sem fruto nunca cresce; madura não passa de 3; quebrada madura solta limão; florida não solta |
| `sapling_grows_fruit_tree` | Muda de pessegueiro vira árvore com tronco de carvalho, ≥ 10 folhas e algumas (não todas) com fruto |
| `grass_drops_seeds` | 600 gramas quebradas dão 20–120 sementes do mod (10% configurado) de ≥ 5 espécies |
| `stove_cooks_recipe` | Sopa de tomate: acende, cozinha, gasta exatamente os ingredientes e 2 tigelas, mantém a panela, devolve o balde, gasta 1 carvão, rende 2; vazio fica parado |
| `station_status_messages` | Status `NO_RECIPE`, `NEEDS_UTENSIL` (sem panela e com frigideira), `NEEDS_CONTAINER`, `NO_FUEL`, `MORE_NEEDED`, `OUTPUT_FULL`; nada gasto nem combustível queimado parado |
| `station_keeps_items_on_close` | Fechar o menu não mexe nos itens; acabar o combustível no meio guarda o progresso |
| `station_persistence` | Fogão salvo e recarregado com slots, progresso e combustível; quebrado derruba conteúdo, panela e o fogão |
| `oven_bakes_with_fuel` | Forno sem combustível avisa; com carvão vegetal assa 2 pães franceses e fica aceso |
| `counter_assembles_dish` | Bancada monta arroz com feijão no prato; creme de leite exige batedor e devolve o balde |
| `remainders_returned` | Sobras cheias param a estação; chá de limão com mel devolve balde e frasco nos 2 slots de sobras |
| `station_hopper_faces` | Funil: ingredientes por cima, combustível pelos lados, só prato e sobras saem por baixo |
| `cutting_board_cuts` | Põe 1 tomate; 2 cortes com a faca dão 2 fatias e gastam 2 de durabilidade; mão vazia devolve; terra não entra |
| `sink_washes_and_fills` | Sem água não lava; balde enche 8; pia sem ligação não enche balde (sem duplicação); lavar gasta 1; lava prato sujo; com água embaixo não gasta e enche balde |
| `mill_grinds` | Entram 2 trigos; 4 voltas dão 1 farinha; o restante volta inteiro (veja a limitação abaixo) |
| `fridge_chills` | Geladeira de 27 aceita tomate e recusa terra; o creme de mousse vira mousse só no tempo da receita; queijo não muda |
| `storage_persists` | Armário (18) e despensa (36) salvam/recarregam; quebrados derrubam tudo |
| `catalog_has_100_dishes` | O catálogo carregado em jogo tem 100 pratos distintos, todos comida, nas 11 categorias, e as receitas de preparo |
| `dish_returns_container` | Comer devolve prato sujo, tigela, frasco e xícara suja, e mata a fome |

**Teste negativo** (evidência de que os testes pegam defeito): com `SinkRules.canFillBucket` devolvendo sempre `true` e a colheita sem
voltar a folha para a idade 0, o mesmo comando deu `2 required tests failed :(` com `sink_washes_and_fills` ("encheu balde de uma pia sem
ligação (duplicação de água)") e `fruit_harvest_regrows` ("a folha não voltou à idade 0"). O código foi restaurado e voltou a
`All 21 required tests passed :)`.

## 4. Servidor dedicado

`node tools/visual-probe/make-world.mjs` (sobe o `runServer`, espera o `Done`, manda `stop`): `Forge mod loading, version 66.0.13`,
`Done (0.314s)!`, saída 0, `run/world` criado. O servidor dedicado carrega o mod sem erro (nenhuma classe de cliente carregada no servidor).
O aviso `No key layers in MapLike[{}]` vem do `level-type=flat` sem `generator-settings` (conhecido; o mundo sai normal).

**Reinício de verdade** (`node tools/visual-probe/restart-check.mjs`: grava, desliga, liga de novo e lê pelo console, com random tick 0).
Saída (igual nas duas rodadas feitas):

```
== gravação: servidor saiu (0)
== leitura: servidor saiu (0)
100, 120, 100 has the following block data: [{count: 7, Slot: 0b, id: "culinaria:cheese"}, {count: 2, Slot: 26b, id: "culinaria:feijoada"}]
101, 120, 100 has the following block data: [{count: 3, Slot: 0b, id: "culinaria:tomato"}, {count: 1, Slot: 6b, id: "culinaria:cooking_pot"}, {count: 5, Slot: 8b, id: "minecraft:coal"}]
CHECK folha idade 2
CHECK lavoura idade 4 ok
CHECK pia agua 5 ok
```

Geladeira (slots 0 e 26), fogão (ingredientes, panela, carvão intacto porque não havia receita), folha frutífera, lavoura e água da pia voltam
iguais depois de desligar e ligar o servidor. Na primeira tentativa, com o random tick padrão, a folha não estava mais na idade 2: a fruta
cresceu nos segundos entre gravar e desligar, que é o comportamento esperado; por isso a verificação desliga o random tick.

## 5. Cliente de verdade (teste visual automático)

`powershell -ExecutionPolicy Bypass -File tools\visual-probe\run-probe.ps1 -OutDir <pasta>`: abre o `runClient` num mundo de teste com o
datapack de cena e tira prints. Rodada 1: **24 de 24 prints**, `log-problems.txt` sem nenhum erro de modelo, textura, som ou código (só os
avisos do Forge criando o `forge-client.toml` na primeira vez). Visto nos prints:

- **Cozinha:** os 10 móveis com modelo e textura; tábua mostrando o tomate posto (renderer), moedor com o trigo, pia com água, fogão aceso.
- **Lavouras e árvores:** as 14 lavouras maduras e os 8 estágios do tomate; as 5 árvores com frutos coloridos visíveis nas folhas.
- **Fogão:** status verde "Preparando: Sopa de tomate", prévia da sopa na saída, seta enchendo, chama do combustível.
- **Bancada:** status vermelho "Falta o recipiente: Prato (1)" com o prato fantasma no slot de recipiente e a prévia do prato.
- **Geladeira:** inventário de 27 com os itens postos.
- **Tábua:** 2 cliques com a faca → 2 tomates fatiados no inventário e a faca com a barra de durabilidade.
- **Livro:** lista "100 receitas", os 13 filtros, detalhe com equipamento, utensílio, recipiente, tempo, ingredientes "tenho / preciso"
  (preparos em azul, clicáveis), etapas; busca; filtro "Sopas e caldos" (9); "Preparos" (54).
- **Dicas:** feijoada com "Categoria: Refeições principais" e "Depois de comer, devolve: Tigela".
- **Colheita:** folha madura de laranjeira com frutos → depois do clique direito, a mesma folha sem frutos.

Achados da rodada 1 e corrigidos: a chama do fogão sobrepunha a grade de ingredientes (movida para o lado do combustível); a seleção do livro
não acompanhava a troca de categoria (agora abre o primeiro da lista); a origem dos ingredientes crus aparecia só no tooltip (agora também
numa linha cinza). **Rodada 2** (depois das correções): 24 de 24 prints, `log-problems.txt` vazio nas duas seções (fora o
mesmo aviso do `forge-client.toml`); a chama aparece ao lado do combustível, o filtro "Sopas e caldos" abre um prato da própria lista e o
livro mostra "Encha um balde em qualquer água..." embaixo do balde de água e "1 / 3" para a cebola picada que estava no inventário.

## 6. Ainda não testado

- **Dois jogadores ao mesmo tempo** (servidor dedicado ou LAN com dois clientes). Toda a lógica é do servidor e os menus usam a
  sincronização vanilla, mas isso não foi visto com duas pessoas.
- **Agachar no moedor com o jogador simulado:** o GameTest confere a devolução pela BlockEntity, porque o jogador simulado do 26.3 não guarda
  o agachar; o gesto em si (agachado + mão vazia) não foi clicado no cliente.
- **Funil de verdade** encostado nas estações (só as regras de face foram testadas).
- **Árvores nascendo num mundo novo** explorado a pé (o worldgen carrega sem erro e a feature é colocada pelo comando `place feature` no
  teste visual; a raridade nos biomas não foi medida).
- **Mão esquerda, sons ouvidos, outras resoluções e escalas de GUI.**
