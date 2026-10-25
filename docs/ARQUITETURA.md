# Arquitetura do Culinária

## 1. Origem: auditoria do mod de referência

O projeto de referência `C:\Users\Cliente\Documents\minezada` (mod "Cultivo", modid `cultivo`) foi só lido, nunca alterado. O que se
encontrou nos arquivos reais e foi reaproveitado:

| Item | No Cultivo | No Culinária |
|---|---|---|
| Minecraft | 26.3 (mappings `26.3-20260918.230105`) | 26.3 (o mesmo) |
| Forge | `26.3-66.0.13` no `build.gradle`; `mods.toml` `[66.0.2,)` | O mesmo build e a mesma faixa |
| ForgeGradle | `net.minecraftforge.gradle` `[7.0.17,8)` | O mesmo |
| Gradle | wrapper 9.7.1 | O mesmo wrapper (copiado) |
| Java | toolchain 25 (JDK em `C:\Users\Cliente\Documents\jdk\jdk-25.0.4.1+1`) | O mesmo |
| Testes | JUnit 5.12.2 + Gson 2.13.1; GameTests com `forge.enabledGameTestNamespaces` | O mesmo esquema |
| Recursos | Gerados por Node (`tools/gen` + pixel art em `tools/art`) em `src/generated/resources` | O mesmo padrão, com o catálogo em `tools/gen/content.mjs` |
| Pack format | 121 | 121 |

**Compatibilidade de versão:** o Cultivo já declara exatamente Minecraft 26.3 e Forge 26.3-66.0.13, e foi compilado e testado com eles
(docs do Cultivo, `TECHNICAL_RESEARCH.md` e `NEXT_SESSION.md`). Não houve diferença de versão a registrar: o Culinária usa a mesma
combinação. A confirmação no novo projeto: `./gradlew build` baixou/usou `net.minecraftforge:forge:26.3-66.0.13` e o log do servidor mostra
o Forge 66.0.13 carregando o mod (veja [TESTES.md](TESTES.md)). O loader é o Forge (não NeoForge nem Fabric).

**Padrões copiados do Cultivo** (com as armadilhas do 26.3 que ele já tinha descoberto): `Identifier` no lugar de `ResourceLocation`;
`Properties.setId(...)`; `useItemOn`/`useWithoutItem`; `saveAdditional(ValueOutput)`/`loadAdditional(ValueInput)`; drop de conteúdo em
`preRemoveSideEffects`; `BlockEntityType` com `Set` de blocos; receitas com `MapCodec` + `StreamCodec` e `PlacementInfo`; loot com
`condition`/`modifier`/`match_block`; `LootTableLoadEvent` para injetar pools; `placeItemBackInInventory` para entregar itens; GameTests em
`Registries.TEST_FUNCTION` com um `test_instance` por teste; `@Mod.EventBusSubscriber(value = Dist.CLIENT)` para isolar o cliente; e o
teste visual automático por datapack de cena + prints.

**O que o Cultivo não tinha e foi pesquisado nas fontes do Forge 26.3** (`forge-26.3-66.0.13-sources.jar`): menus e telas
(`AbstractContainerMenu`, `IForgeMenuType`, `MenuScreens.register`, `GuiGraphicsExtractor`, `extractBackground`), rede (`ChannelBuilder` →
`SimpleChannel`, `NetworkDirection.PLAY_TO_CLIENT`, `OnDatapackSyncEvent`), configuração (`ForgeConfigSpec`), comida
(`FoodProperties.Builder`, `Consumables.DEFAULT_DRINK`, `usingConvertsTo`), combustível (`DataComponents.COOKING_FUEL` + `ResolvableInt`),
folhas (`LeavesBlock` + `AmbientLeavesBlockSoundPlayer`), mudas (`TreeGrower` com `ResourceKey<Feature>`) e o `BlockEntityRenderer` de item
(`ItemModelResolver` + `ItemStackRenderState`, como a fogueira).

## 2. Pacotes

```
io.github.peixeangola.culinaria
├── Culinaria              @Mod: registros, config, rede, eventos (sementes na grama, sincronia do catálogo)
├── CulinariaConfig        config comum (crescimento das frutas, sementes na grama, velocidade da cozinha)
├── content/               GeneratedContent (GERADO): tabela de itens, lavouras, árvores e móveis
├── core/                  regras puras, sem Minecraft (JUnit): Station, CookingStatus, CookingMatcher, FruitRules, SinkRules
├── recipe/                CookingRecipe (tipo culinaria:cooking, uma receita para todas as estações) e CookingInput
├── catalog/               RecipeCatalog (lista ordenada das receitas, servidor e cópia do cliente) e NearbyCounts
├── registry/              ModBlocks, ModItems, ModBlockEntities, ModMenus, ModRecipes, ModTags, ModCreativeTabs
├── block/                 lavoura, folha frutífera, móveis, estações e as BlockEntities
├── menu/                  CookingStationMenu, StorageMenu, KitchenSlots (papel e posição de cada slot)
├── item/                  DishItem, HintItem, HintBlockItem, RecipeBookItem
├── network/               ModNetwork (catálogo → cliente; contagem dos móveis próximos)
├── world/                 GrassSeeds (pool de sementes nas loot tables da grama)
├── gametest/              CulinariaGameTests (18 GameTests)
└── client/                só no cliente: telas, renderer da tábua/moedor, CulinariaClientEvents
```

`src/generated/java` e `src/generated/resources` saem de `node tools/gen/generate.mjs` e são versionados.

## 3. Decisões principais

**Uma receita para todas as estações.** `culinaria:cooking` tem `station`, `utensil` opcional, até 6 ingredientes com quantidade,
`container` opcional, resultado, `time` e `category`. Vantagens: um só codec/serializer, um só catálogo para o livro, e o livro mostra
qualquer etapa do mesmo jeito. As receitas são JSON declarativos em `data/culinaria/recipe/cooking/`, então um datapack pode mudar ou
acrescentar pratos.

**Casamento exato.** A estação procura a receita cujos tipos de ingrediente são *exatamente* os dos slots (nada sobrando, nada faltando) e
cada um com a quantidade mínima (`CookingMatcher`). O gerador e o `CatalogDataTest` garantem que não há duas receitas com o mesmo conjunto
na mesma estação. Resultado: nunca há ambiguidade, e um item estranho trava a estação com o aviso "Nenhuma receita com estes ingredientes".

**Status explicado.** O servidor calcula por que a estação parou (`CookingStatus`: falta utensílio, recipiente, combustível, quantidade,
saída cheia, sobras cheias) e manda pelo `ContainerData` junto com o índice da receita reconhecida. O cliente mostra o texto e a prévia do
prato. O progresso não se perde quando algo falta; só zera se os ingredientes passarem a ser de outra receita.

**Sem perda nem duplicação.** Tudo que muda inventário acontece no servidor: tick das estações, cliques na tábua, pia e moedor, colheita das
frutas. As sobras (balde do leite, frasco do mel) vão para dois slots próprios e a estação para se não couberem. A pia só enche balde se
estiver ligada a uma fonte de água logo abaixo (senão 1 balde viraria 8). A folha frutífera confere a idade no servidor antes de soltar a
fruta e volta para 0 no mesmo tick.

**Catálogo no cliente.** O 26.3 não manda receitas ao cliente. O `ModNetwork.Catalog` manda a lista inteira ao entrar e depois de cada
`/reload` (`OnDatapackSyncEvent`), na mesma ordem do servidor (por ID). O livro lê essa lista: ele reflete exatamente o que o servidor
carregou, inclusive receitas de datapacks. As estações de clique preveem o clique por tags geradas (`culinaria:station/<estação>`), que
chegam ao cliente.

**Árvores frutíferas por blockstate.** A folha (`FruitLeavesBlock`, subclasse de `LeavesBlock`) tem `fruiting` e `age` 0–3. Só 1 em 4
folhas da árvore gerada dá fruto (`weighted` no `foliage_provider`). O crescimento é por random tick (chance configurável), a folha decai
como a vanilla e tudo salva com o chunk, sem BlockEntity. O tronco é de carvalho; a muda usa o `SaplingBlock` vanilla com um `TreeGrower`
próprio que aponta para `data/culinaria/worldgen/feature/<fruta>_tree.json`. As árvores nascem no mundo por `forge:add_features`.

**Louça reutilizável.** Prato e xícara voltam sujos (`usingConvertsTo`) e são lavados na pia: é a função de jogo da pia, junto com lavar
arroz, deixar feijão de molho e decantar o polvilho. Tigela e frasco voltam como no vanilla. Os utensílios (panela, frigideira, forma, rolo,
batedor) ficam no slot de utensílio e não se gastam; a faca gasta durabilidade.

**Livro como item.** O livro de receitas é um item (fácil de levar e de dar ao amigo). A tela fica no pacote `client` e chega ao item por um
`Runnable` preenchido no setup do cliente, então o servidor dedicado nunca carrega classe de tela.

**Interfaces sem textura própria.** As telas das estações e do livro são desenhadas com retângulos no estilo vanilla (`KitchenGui`); os
móveis de guardar usam a textura do baú vanilla. Menos arquivos para quebrar e o visual combina com o jogo.

**Texturas originais geradas.** Toda textura sai de `tools/art/shapes.mjs` (formas de pixel art 16×16 com sombra e contorno automáticos)
com as cores do catálogo. `node tools/art/contact-sheet.mjs <pasta> <saída.png>` monta uma folha de contato para revisar.

## 4. Fluxo de dados

```
tools/gen/content.mjs ──► generate.mjs ──► src/generated/resources (JSON, PNG, lang) + src/generated/java (GeneratedContent)
                     └──► docs.mjs     ──► docs/CATALOGO_RECEITAS.md, docs/INGREDIENTES.md

servidor: RecipeManager ──► RecipeCatalog.server ──► estações (tick/clique) ──► ContainerData/BlockEntity ──► cliente (tela, renderer)
                                              └──► ModNetwork.Catalog ──► RecipeCatalog.client ──► livro de receitas e telas
cliente (livro aberto) ──► NearbyRequest ──► servidor soma os móveis em 8 blocos ──► Nearby ──► "tenho / preciso"
```

## 5. Armadilhas do 26.3 encontradas aqui (além das do Cultivo)

- `would_survive` no `placed_feature` recebe o estado como string (`"minecraft:oak_sapling"`), não `{Name, Properties}`.
- A condição de loot é `minecraft:match_block` (`blocks`, `state`); `block_state_property` não existe mais.
- `Minecraft#setScreen` não existe: use `minecraft.gui.setScreen(screen)`. `I18n.exists` não existe: use `Language.getInstance().has(key)`.
- `Item#getName()` pede a pilha; para o nome de um item use `Component.translatable(item.getDescriptionId())`.
- `ServerPlayer#sendOverlayMessage` com o jogador simulado do GameTest (sem conexão) dá `NullPointerException`: confira `connection`.
- O jogador simulado do GameTest não guarda o agachar (`setShiftKeyDown` não vale no servidor sem pacote de input).
- `AmbientLeavesBlockSoundPlayer` fica em `net.minecraft.world.level.block.sounds`; `ResolvableInt` em
  `net.minecraft.world.level.storage.loot.providers.number.ints`.
- Configured features agora são `worldgen/feature` e a chave é `ResourceKey<Feature>`; o comando é `place feature <id>`.
