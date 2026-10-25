# Orientação para a próxima sessão

Atualizado em 25/10/2026. Para retomar o projeto sem a conversa anterior.

## Estado

O mod **Culinária** (modid `culinaria`, versão 0.1.0) está implementado para Minecraft 26.3 + Forge 26.3-66.0.13, Java 25, na pasta
`C:\Users\Cliente\Documents\minezada-culinaria` (git próprio, branch `main`). O mod de referência `C:\Users\Cliente\Documents\minezada` foi
só lido. O que existe, o que foi testado e o que falta: [ROADMAP.md](ROADMAP.md) e [TESTES.md](TESTES.md).

## Ambiente

- JDK 25 em `C:\Users\Cliente\Documents\jdk\jdk-25.0.4.1+1` (o `java` do PATH é um Java 8; sempre defina o `JAVA_HOME`). No PowerShell:
  `$env:JAVA_HOME = "C:\Users\Cliente\Documents\jdk\jdk-25.0.4.1+1"; $env:Path = "$env:JAVA_HOME\bin;$env:Path"`.
- Node 24 (só para os geradores). Git instalado.
- Fontes do Minecraft/Forge para consultar a API: `.gradle\mavenizer\repo\net\minecraftforge\forge\26.3-66.0.13\forge-26.3-66.0.13-sources.jar`
  (extraia num diretório temporário e use grep). JSON vanilla: `...\net\minecraft\client-extra\26.3-20260918.230105\client-extra-26.3-20260918.230105.jar`.
- `run\eula.txt` foi copiado do projeto de referência (EULA já aceita pelo dono lá). `run\world` sai de `node tools\visual-probe\make-world.mjs`.

## Verificar o ponto de partida (nesta ordem, nunca dois Gradle ao mesmo tempo)

1. `node tools/gen/generate.mjs` → `303 texturas, 808 arquivos JSON, 154 receitas (100 pratos)`; `git status` sem mudança.
2. `node tools/gen/docs.mjs` → `docs: 100 pratos, 154 receitas, 99 ingredientes distintos`.
3. `.\gradlew.bat build` → `BUILD SUCCESSFUL`, 20 testes JUnit, 0 falhas (XML em `build\test-results\test`).
4. `.\gradlew.bat runGameTestServer` → `All 21 required tests passed :)` (20 do Culinária + `minecraft:always_pass`).
5. Se mexer em assets ou telas: `powershell -ExecutionPolicy Bypass -File tools\visual-probe\run-probe.ps1 -OutDir <pasta>` e olhe os 24
   prints e o `log-problems.txt`.

## Regras de trabalho

- **Conteúdo só no catálogo:** itens, receitas, cores das texturas e textos ficam em `tools/gen/content.mjs`; `generate.mjs` escreve
  `src/generated` (inclusive `GeneratedContent.java`) e recusa catálogo inválido. Não edite `src/generated` à mão. Depois rode `docs.mjs`.
- **Formas de textura** em `tools/art/shapes.mjs` (motor em `tools/art/pixel.mjs`). Folha de contato: `node tools/art/contact-sheet.mjs
  src/generated/resources/assets/culinaria/textures/item <saída.png> 3`.
- **Servidor decide tudo** que mexe em inventário ou mundo; o cliente só prevê cliques por tags (`culinaria:station/*`) e mostra o que o
  `ContainerData`/BlockEntity manda.
- **Código de cliente** só em `client/`, alcançado por `CulinariaClientEvents` (anotado com `Dist.CLIENT`) e pelo `RecipeBookItem.opener`.
- **GameTest novo:** registre em `CulinariaGameTests` e ponha o nome em `GAME_TESTS` do `generate.mjs` (gera o `test_instance`).
- **Testes honestos:** valores esperados literais; regra nova ganha teste; um teste negativo (quebrar a regra e ver falhar) vale como
  evidência. Atualize [TESTES.md](TESTES.md) só com o comando e a saída real.
- Estilo: comentários em português, identificadores em inglês, commits em inglês.

## Mapa rápido do código

Veja [ARQUITETURA.md](ARQUITETURA.md), seção 2. Pontos de entrada: `Culinaria` (registros e eventos), `CookingStationBlockEntity#serverTick`
(o processamento), `RecipeCatalog#find` (casamento), `FruitLeavesBlock` (árvores), `RecipeBookScreen` (livro), `ModNetwork` (pacotes).

## Próximos passos sugeridos

1. Jogar em dupla (servidor dedicado ou LAN) e anotar problemas de sincronia (ROADMAP.md, item 1).
2. Ajustar o balanço pelos números do config e do `content.mjs`.
3. Melhorias visuais opcionais: geladeira de 2 blocos, animação da manivela, vapor na panela, ícone do slot vazio por papel.
4. Compatibilidade com Serene Seasons (tags de estação das lavouras), como o Cultivo faz.
