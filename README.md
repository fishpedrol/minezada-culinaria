# Culinária

Mod de culinária de sobrevivência para **Minecraft 26.3 + Forge 26.3-66.0.13** (Java 25), modid `culinaria`.

Explorar → conseguir recursos → plantar e colher → obter ingredientes → guardar → preparar → cozinhar → servir → comer ou levar.

- **14 lavouras** (tomate, cebola, alho, alface, milho, arroz, feijão, pimentão, manjericão, salsinha, morango, café, mandioca e couve), com
  sementes que caem da grama.
- **5 árvores frutíferas** (macieira, laranjeira, limoeiro, pessegueiro e cerejeira) que nascem no mundo, crescem de muda e **voltam a dar
  fruto** depois da colheita, com o fruto visível na folha.
- **Cozinha:** fogão e forno (com combustível), bancada de cozinha, liquidificador, pia, tábua de corte e moedor manual.
- **Móveis:** geladeira (só comida, e gela mousse e sorvete com o tempo), armário (18 espaços) e despensa (36 espaços).
- **Utensílios e louça:** faca, panela, frigideira, forma, rolo de massa, batedor, prato e xícara (voltam sujos e são lavados na pia).
- **Livro de receitas** com busca, categorias, "tenho / preciso" (inventário + móveis por perto), onde conseguir cada ingrediente e as etapas.
- **100 pratos** em 11 categorias, mais 54 preparos de ingredientes (farinha, massas, molhos, queijo, manteiga...). Catálogo completo em
  [docs/CATALOGO_RECEITAS.md](docs/CATALOGO_RECEITAS.md); fontes dos ingredientes em [docs/INGREDIENTES.md](docs/INGREDIENTES.md).

## Requisitos

| Item | Versão |
|---|---|
| Minecraft | 26.3 |
| Forge | 26.3-66.0.13 no build; o `mods.toml` aceita `[66.0.2,)` |
| Java | JDK 25 (o build usa toolchain 25; o plugin foojay baixa um se faltar) |
| Gradle | wrapper 9.7.1 (já no projeto) |
| Node.js | 22 ou mais novo, só para regenerar recursos e documentação (sem dependências npm) |

## Compilar

No Windows (PowerShell), na pasta do projeto:

```powershell
$env:JAVA_HOME = "C:\Users\Cliente\Documents\jdk\jdk-25.0.4.1+1"; $env:Path = "$env:JAVA_HOME\bin;$env:Path"
.\gradlew.bat build
```

O jar sai em `build\libs\culinaria-0.1.0.jar`. No Linux/macOS use `./gradlew build` com o `JAVA_HOME` apontando para um JDK 25.

## Instalar

1. Instale o Forge 26.3 (66.0.2 ou mais novo) pelo instalador oficial.
2. Copie `build\libs\culinaria-0.1.0.jar` para `%APPDATA%\.minecraft\mods`.
3. Abra o perfil do Forge 26.3 no launcher. Para jogar em dupla, os dois precisam do mesmo jar (no servidor dedicado, ponha o jar em `mods`
   do servidor também).

## Executar e testar no ambiente de desenvolvimento

| Comando | O que faz |
|---|---|
| `.\gradlew.bat runClient` | Abre o jogo com o mod |
| `.\gradlew.bat runServer` | Servidor dedicado de desenvolvimento (pasta `run`) |
| `.\gradlew.bat build` | Compila e roda os testes JUnit |
| `.\gradlew.bat runGameTestServer` | Roda os 18 GameTests do mod num servidor sem tela |
| `node tools\gen\generate.mjs` | Regenera `src\generated` (recursos, dados e a tabela Java) a partir de `tools\gen\content.mjs` |
| `node tools\gen\docs.mjs` | Regenera o catálogo de receitas e o de ingredientes em `docs\` |
| `node tools\visual-probe\make-world.mjs` | Cria `run\world` para o teste visual |
| `powershell -ExecutionPolicy Bypass -File tools\visual-probe\run-probe.ps1 -OutDir <pasta>` | Teste visual automático no cliente (prints) |

Para mudar conteúdo (itens, receitas, textos, cores das texturas), edite `tools/gen/content.mjs`, rode `node tools/gen/generate.mjs` e
`node tools/gen/docs.mjs`, e versione a saída. O gerador recusa o catálogo se ele tiver erro (receita sem cadeia viável, ingrediente sem
uso, dois pratos com os mesmos ingredientes na mesma estação, número de pratos diferente de 100...).

## Como jogar (resumo)

1. Quebre grama para achar sementes; plante em terra arada perto de água, como o trigo.
2. Procure árvores frutíferas (planícies, florestas, savanas, selvas, campos floridos). Clique direito nas folhas com fruta madura.
   Muda: cai das folhas, ou bancada de trabalho com muda de carvalho + fruta.
3. Monte a cozinha (receitas de bancada de trabalho em [docs/INGREDIENTES.md](docs/INGREDIENTES.md) e no livro). Faça o **livro de receitas**
   (livro + trigo) e siga as etapas de cada prato.
4. **Tábua:** clique com o ingrediente, depois clique com a faca até cortar. **Moedor:** ponha o ingrediente e gire com a mão vazia
   (agachado devolve). **Pia:** encha com balde de água (ou ponha uma fonte de água logo abaixo dela para ter água infinita); clique com
   arroz, feijão, farinha de mandioca ou louça suja.
5. **Bancada, fogão, forno, liquidificador:** coloque exatamente os ingredientes da receita nos 6 slots, o utensílio, o recipiente e, no
   fogão e no forno, combustível. A linha de status embaixo diz o que falta.
6. **Geladeira:** guarde o creme de mousse ou de sorvete e espere gelar.

Documentação técnica: [docs/ARQUITETURA.md](docs/ARQUITETURA.md). Estado e próximos passos: [docs/ROADMAP.md](docs/ROADMAP.md). Testes
e resultados observados: [docs/TESTES.md](docs/TESTES.md). Continuidade para a próxima sessão: [docs/PROXIMA_SESSAO.md](docs/PROXIMA_SESSAO.md).
