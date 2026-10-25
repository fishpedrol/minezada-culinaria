# Guia para Claude

Este repositório é o "Culinária", mod de culinária para Minecraft 26.3 + Forge 26.3-66.0.13 (Java 25), modid `culinaria`. Comece por
[docs/PROXIMA_SESSAO.md](docs/PROXIMA_SESSAO.md) (ambiente, como verificar, regras) e depois [docs/ARQUITETURA.md](docs/ARQUITETURA.md),
[docs/ROADMAP.md](docs/ROADMAP.md) e [docs/TESTES.md](docs/TESTES.md).

O conteúdo (itens, receitas, textos, cores) fica em `tools/gen/content.mjs`; `node tools/gen/generate.mjs` escreve `src/generated` e
`node tools/gen/docs.mjs` escreve os catálogos em `docs/`. Nunca edite `src/generated` à mão. O projeto de referência
`C:\Users\Cliente\Documents\minezada` é só leitura. Atualize TESTES.md somente com evidência real (comando e saída).
