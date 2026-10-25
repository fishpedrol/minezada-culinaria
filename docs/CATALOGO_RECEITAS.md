# Catálogo de receitas

Gerado por `node tools/gen/docs.mjs` a partir de `tools/gen/content.mjs`, a mesma fonte dos JSON que o jogo carrega
(`data/culinaria/recipe/cooking/`). **100 pratos** em 11 categorias, mais 54 receitas de preparo
de ingredientes (que não contam nas 100).

Como ler: *ID* é o identificador técnico (`culinaria:<id>`); *Equipamento* é a estação e o utensílio que
precisa estar no slot de utensílio; *Servir em* é o recipiente gasto (um por unidade); *Etapas* parte das matérias-primas. Ingredientes marcados com
(P) são preparados em outra receita (veja a seção de preparos ou `INGREDIENTES.md`).

| Categoria | Pratos |
|---|---|
| Pães e massas | 10 |
| Café da manhã | 8 |
| Carnes e aves | 10 |
| Peixes | 8 |
| Refeições principais | 10 |
| Sopas e caldos | 9 |
| Acompanhamentos | 10 |
| Pratos vegetais | 8 |
| Bolos e sobremesas | 9 |
| Tortas e doces | 9 |
| Bebidas | 9 |
| **Total** | **100** |

## Pães e massas

### 1. Pão francês

- **ID:** `culinaria:bread_roll` · **Categoria:** Pães e massas · **Rende:** 2
- **Ingredientes:** 1× Massa de pão (P)
- **Origem:** Massa de pão: Preparo: Bancada de cozinha
- **Intermediários:** Massa de pão
- **Equipamento:** Forno + Forma de assar · **Tempo:** 10.0 s (200 ticks)
- **Servir em:** nada (comida de mão)
- **Resultado:** 2× Pão francês, fome 5 e saturação 0.6 cada
- **Etapas:**
  1. Moa farinha de trigo — Moedor manual
  2. Cozinhe sal — Fogão com panela
  3. Prepare massa de pão — Bancada de cozinha
  4. Asse pão francês — Forno com forma de assar

### 2. Pão de queijo

- **ID:** `culinaria:cheese_bread` · **Categoria:** Pães e massas · **Rende:** 4
- **Ingredientes:** 2× Polvilho (P), 1× Queijo (P), 1× Ovo, 1× Balde de leite
- **Origem:** Polvilho: Preparo: Pia; Queijo: Preparo: Fogão; Ovo: Galinhas botam ovos sozinhas; Balde de leite: Ordenhe uma vaca ou cabra com um balde (o balde volta vazio)
- **Intermediários:** Polvilho, Queijo
- **Equipamento:** Forno + Forma de assar · **Tempo:** 11.0 s (220 ticks)
- **Servir em:** nada (comida de mão)
- **Resultado:** 4× Pão de queijo, fome 4 e saturação 0.6 cada
- **Etapas:**
  1. Corte mandioca descascada — Tábua de corte
  2. Moa farinha de mandioca — Moedor manual
  3. Lave polvilho — Pia
  4. Cozinhe queijo — Fogão com panela
  5. Asse pão de queijo — Forno com forma de assar

### 3. Pão de alho

- **ID:** `culinaria:garlic_bread` · **Categoria:** Pães e massas · **Rende:** 2
- **Ingredientes:** 1× Pão francês (P), 1× Alho picado (P), 1× Manteiga (P)
- **Origem:** Pão francês: Preparo: Forno; Alho picado: Preparo: Tábua de corte; Manteiga: Preparo: Bancada de cozinha
- **Intermediários:** Pão francês, Alho picado, Manteiga
- **Equipamento:** Forno + Forma de assar · **Tempo:** 8.0 s (160 ticks)
- **Servir em:** nada (comida de mão)
- **Resultado:** 2× Pão de alho, fome 6 e saturação 0.6 cada
- **Etapas:**
  1. Moa farinha de trigo — Moedor manual
  2. Cozinhe sal — Fogão com panela
  3. Prepare massa de pão — Bancada de cozinha
  4. Asse pão francês — Forno com forma de assar
  5. Corte alho picado — Tábua de corte
  6. Prepare creme de leite — Bancada de cozinha com batedor de arame
  7. Prepare manteiga — Bancada de cozinha com batedor de arame
  8. Asse pão de alho — Forno com forma de assar

### 4. Espaguete ao sugo

- **ID:** `culinaria:spaghetti_sugo` · **Categoria:** Pães e massas · **Rende:** 1
- **Ingredientes:** 1× Macarrão cozido (P), 1× Molho de tomate (P), 1× Manjericão
- **Origem:** Macarrão cozido: Preparo: Fogão; Molho de tomate: Preparo: Fogão; Manjericão: Lavoura de manjericão (sementes de manjericão da grama)
- **Intermediários:** Macarrão cozido, Molho de tomate
- **Equipamento:** Bancada de cozinha · **Tempo:** 5.0 s (100 ticks)
- **Servir em:** Prato (volta sujo) × 1
- **Resultado:** 1× Espaguete ao sugo, fome 10 e saturação 0.7 cada
- **Etapas:**
  1. Moa farinha de trigo — Moedor manual
  2. Prepare massa de macarrão — Bancada de cozinha
  3. Prepare macarrão cru — Bancada de cozinha com rolo de massa
  4. Cozinhe sal — Fogão com panela
  5. Cozinhe macarrão cozido — Fogão com panela
  6. Corte cebola picada — Tábua de corte
  7. Corte alho picado — Tábua de corte
  8. Cozinhe molho de tomate — Fogão com panela
  9. Prepare espaguete ao sugo — Bancada de cozinha

### 5. Espaguete à bolonhesa

- **ID:** `culinaria:spaghetti_bolognese` · **Categoria:** Pães e massas · **Rende:** 1
- **Ingredientes:** 1× Macarrão cozido (P), 1× Molho à bolonhesa (P)
- **Origem:** Macarrão cozido: Preparo: Fogão; Molho à bolonhesa: Preparo: Fogão
- **Intermediários:** Macarrão cozido, Molho à bolonhesa
- **Equipamento:** Bancada de cozinha · **Tempo:** 5.0 s (100 ticks)
- **Servir em:** Prato (volta sujo) × 1
- **Resultado:** 1× Espaguete à bolonhesa, fome 12 e saturação 0.8 cada
- **Etapas:**
  1. Moa farinha de trigo — Moedor manual
  2. Prepare massa de macarrão — Bancada de cozinha
  3. Prepare macarrão cru — Bancada de cozinha com rolo de massa
  4. Cozinhe sal — Fogão com panela
  5. Cozinhe macarrão cozido — Fogão com panela
  6. Moa carne moída — Moedor manual
  7. Corte cebola picada — Tábua de corte
  8. Corte alho picado — Tábua de corte
  9. Cozinhe molho de tomate — Fogão com panela
  10. Cozinhe molho à bolonhesa — Fogão com panela
  11. Prepare espaguete à bolonhesa — Bancada de cozinha

### 6. Lasanha

- **ID:** `culinaria:lasagna` · **Categoria:** Pães e massas · **Rende:** 2
- **Ingredientes:** 2× Macarrão cru (P), 1× Molho à bolonhesa (P), 2× Queijo (P)
- **Origem:** Macarrão cru: Preparo: Bancada de cozinha; Molho à bolonhesa: Preparo: Fogão; Queijo: Preparo: Fogão
- **Intermediários:** Macarrão cru, Molho à bolonhesa, Queijo
- **Equipamento:** Forno + Forma de assar · **Tempo:** 15.0 s (300 ticks)
- **Servir em:** Prato (volta sujo) × 2
- **Resultado:** 2× Lasanha, fome 13 e saturação 0.8 cada
- **Etapas:**
  1. Moa farinha de trigo — Moedor manual
  2. Prepare massa de macarrão — Bancada de cozinha
  3. Prepare macarrão cru — Bancada de cozinha com rolo de massa
  4. Moa carne moída — Moedor manual
  5. Corte cebola picada — Tábua de corte
  6. Corte alho picado — Tábua de corte
  7. Cozinhe sal — Fogão com panela
  8. Cozinhe molho de tomate — Fogão com panela
  9. Cozinhe molho à bolonhesa — Fogão com panela
  10. Cozinhe queijo — Fogão com panela
  11. Asse lasanha — Forno com forma de assar

### 7. Pizza marguerita

- **ID:** `culinaria:pizza_margherita` · **Categoria:** Pães e massas · **Rende:** 1
- **Ingredientes:** 1× Disco de pizza (P), 1× Molho de tomate (P), 1× Queijo (P), 1× Manjericão
- **Origem:** Disco de pizza: Preparo: Bancada de cozinha; Molho de tomate: Preparo: Fogão; Queijo: Preparo: Fogão; Manjericão: Lavoura de manjericão (sementes de manjericão da grama)
- **Intermediários:** Disco de pizza, Molho de tomate, Queijo
- **Equipamento:** Forno + Forma de assar · **Tempo:** 12.0 s (240 ticks)
- **Servir em:** nada (comida de mão)
- **Resultado:** 1× Pizza marguerita, fome 12 e saturação 0.7 cada
- **Etapas:**
  1. Moa farinha de trigo — Moedor manual
  2. Cozinhe sal — Fogão com panela
  3. Prepare massa de pão — Bancada de cozinha
  4. Prepare disco de pizza — Bancada de cozinha com rolo de massa
  5. Corte cebola picada — Tábua de corte
  6. Corte alho picado — Tábua de corte
  7. Cozinhe molho de tomate — Fogão com panela
  8. Cozinhe queijo — Fogão com panela
  9. Asse pizza marguerita — Forno com forma de assar

### 8. Pizza de calabresa

- **ID:** `culinaria:pizza_calabresa` · **Categoria:** Pães e massas · **Rende:** 1
- **Ingredientes:** 1× Disco de pizza (P), 1× Molho de tomate (P), 1× Queijo (P), 1× Linguiça crua (P)
- **Origem:** Disco de pizza: Preparo: Bancada de cozinha; Molho de tomate: Preparo: Fogão; Queijo: Preparo: Fogão; Linguiça crua: Preparo: Moedor manual
- **Intermediários:** Disco de pizza, Molho de tomate, Queijo, Linguiça crua
- **Equipamento:** Forno + Forma de assar · **Tempo:** 12.0 s (240 ticks)
- **Servir em:** nada (comida de mão)
- **Resultado:** 1× Pizza de calabresa, fome 13 e saturação 0.7 cada
- **Etapas:**
  1. Moa farinha de trigo — Moedor manual
  2. Cozinhe sal — Fogão com panela
  3. Prepare massa de pão — Bancada de cozinha
  4. Prepare disco de pizza — Bancada de cozinha com rolo de massa
  5. Corte cebola picada — Tábua de corte
  6. Corte alho picado — Tábua de corte
  7. Cozinhe molho de tomate — Fogão com panela
  8. Cozinhe queijo — Fogão com panela
  9. Moa linguiça crua — Moedor manual
  10. Asse pizza de calabresa — Forno com forma de assar

### 9. Macarrão com queijo

- **ID:** `culinaria:mac_and_cheese` · **Categoria:** Pães e massas · **Rende:** 1
- **Ingredientes:** 1× Macarrão cozido (P), 2× Queijo (P), 1× Balde de leite, 1× Manteiga (P)
- **Origem:** Macarrão cozido: Preparo: Fogão; Queijo: Preparo: Fogão; Balde de leite: Ordenhe uma vaca ou cabra com um balde (o balde volta vazio); Manteiga: Preparo: Bancada de cozinha
- **Intermediários:** Macarrão cozido, Queijo, Manteiga
- **Equipamento:** Fogão + Panela · **Tempo:** 10.0 s (200 ticks)
- **Servir em:** Tigela (volta) × 1
- **Resultado:** 1× Macarrão com queijo, fome 11 e saturação 0.7 cada
- **Etapas:**
  1. Moa farinha de trigo — Moedor manual
  2. Prepare massa de macarrão — Bancada de cozinha
  3. Prepare macarrão cru — Bancada de cozinha com rolo de massa
  4. Cozinhe sal — Fogão com panela
  5. Cozinhe macarrão cozido — Fogão com panela
  6. Cozinhe queijo — Fogão com panela
  7. Prepare creme de leite — Bancada de cozinha com batedor de arame
  8. Prepare manteiga — Bancada de cozinha com batedor de arame
  9. Cozinhe macarrão com queijo — Fogão com panela

### 10. Nhoque ao sugo

- **ID:** `culinaria:gnocchi` · **Categoria:** Pães e massas · **Rende:** 1
- **Ingredientes:** 2× Batata, 1× Farinha de trigo (P), 1× Ovo, 1× Molho de tomate (P)
- **Origem:** Batata: Lavoura de batata; aldeias e zumbis; Farinha de trigo: Preparo: Moedor manual; Ovo: Galinhas botam ovos sozinhas; Molho de tomate: Preparo: Fogão
- **Intermediários:** Farinha de trigo, Molho de tomate
- **Equipamento:** Fogão + Panela · **Tempo:** 12.0 s (240 ticks)
- **Servir em:** Prato (volta sujo) × 1
- **Resultado:** 1× Nhoque ao sugo, fome 11 e saturação 0.7 cada
- **Etapas:**
  1. Moa farinha de trigo — Moedor manual
  2. Corte cebola picada — Tábua de corte
  3. Corte alho picado — Tábua de corte
  4. Cozinhe sal — Fogão com panela
  5. Cozinhe molho de tomate — Fogão com panela
  6. Cozinhe nhoque ao sugo — Fogão com panela

## Café da manhã

### 11. Ovo frito

- **ID:** `culinaria:fried_egg` · **Categoria:** Café da manhã · **Rende:** 1
- **Ingredientes:** 1× Ovo, 1× Manteiga (P)
- **Origem:** Ovo: Galinhas botam ovos sozinhas; Manteiga: Preparo: Bancada de cozinha
- **Intermediários:** Manteiga
- **Equipamento:** Fogão + Frigideira · **Tempo:** 5.0 s (100 ticks)
- **Servir em:** Prato (volta sujo) × 1
- **Resultado:** 1× Ovo frito, fome 5 e saturação 0.6 cada
- **Etapas:**
  1. Prepare creme de leite — Bancada de cozinha com batedor de arame
  2. Prepare manteiga — Bancada de cozinha com batedor de arame
  3. Cozinhe ovo frito — Fogão com frigideira

### 12. Ovos mexidos

- **ID:** `culinaria:scrambled_eggs` · **Categoria:** Café da manhã · **Rende:** 1
- **Ingredientes:** 2× Ovo, 1× Manteiga (P), 1× Sal (P)
- **Origem:** Ovo: Galinhas botam ovos sozinhas; Manteiga: Preparo: Bancada de cozinha; Sal: Preparo: Fogão
- **Intermediários:** Manteiga, Sal
- **Equipamento:** Fogão + Frigideira · **Tempo:** 6.0 s (120 ticks)
- **Servir em:** Prato (volta sujo) × 1
- **Resultado:** 1× Ovos mexidos, fome 7 e saturação 0.6 cada
- **Etapas:**
  1. Prepare creme de leite — Bancada de cozinha com batedor de arame
  2. Prepare manteiga — Bancada de cozinha com batedor de arame
  3. Cozinhe sal — Fogão com panela
  4. Cozinhe ovos mexidos — Fogão com frigideira

### 13. Omelete

- **ID:** `culinaria:omelette` · **Categoria:** Café da manhã · **Rende:** 1
- **Ingredientes:** 2× Ovo, 1× Queijo (P), 1× Tomate fatiado (P), 1× Cebola picada (P)
- **Origem:** Ovo: Galinhas botam ovos sozinhas; Queijo: Preparo: Fogão; Tomate fatiado: Preparo: Tábua de corte; Cebola picada: Preparo: Tábua de corte
- **Intermediários:** Queijo, Tomate fatiado, Cebola picada
- **Equipamento:** Fogão + Frigideira · **Tempo:** 8.0 s (160 ticks)
- **Servir em:** Prato (volta sujo) × 1
- **Resultado:** 1× Omelete, fome 9 e saturação 0.7 cada
- **Etapas:**
  1. Cozinhe queijo — Fogão com panela
  2. Corte tomate fatiado — Tábua de corte
  3. Corte cebola picada — Tábua de corte
  4. Cozinhe omelete — Fogão com frigideira

### 14. Panquecas com mel

- **ID:** `culinaria:pancakes` · **Categoria:** Café da manhã · **Rende:** 1
- **Ingredientes:** 1× Massa de panqueca (P), 1× Manteiga (P), 1× Frasco de mel
- **Origem:** Massa de panqueca: Preparo: Bancada de cozinha; Manteiga: Preparo: Bancada de cozinha; Frasco de mel: Use um frasco de vidro numa colmeia cheia (o frasco volta)
- **Intermediários:** Massa de panqueca, Manteiga
- **Equipamento:** Fogão + Frigideira · **Tempo:** 8.0 s (160 ticks)
- **Servir em:** Prato (volta sujo) × 1
- **Resultado:** 1× Panquecas com mel, fome 9 e saturação 0.6 cada
- **Etapas:**
  1. Moa farinha de trigo — Moedor manual
  2. Prepare massa de panqueca — Bancada de cozinha com batedor de arame
  3. Prepare creme de leite — Bancada de cozinha com batedor de arame
  4. Prepare manteiga — Bancada de cozinha com batedor de arame
  5. Cozinhe panquecas com mel — Fogão com frigideira

### 15. Tapioca com queijo

- **ID:** `culinaria:tapioca` · **Categoria:** Café da manhã · **Rende:** 1
- **Ingredientes:** 2× Polvilho (P), 1× Queijo (P)
- **Origem:** Polvilho: Preparo: Pia; Queijo: Preparo: Fogão
- **Intermediários:** Polvilho, Queijo
- **Equipamento:** Fogão + Frigideira · **Tempo:** 6.0 s (120 ticks)
- **Servir em:** nada (comida de mão)
- **Resultado:** 1× Tapioca com queijo, fome 6 e saturação 0.5 cada
- **Etapas:**
  1. Corte mandioca descascada — Tábua de corte
  2. Moa farinha de mandioca — Moedor manual
  3. Lave polvilho — Pia
  4. Cozinhe queijo — Fogão com panela
  5. Cozinhe tapioca com queijo — Fogão com frigideira

### 16. Ovos com bacon

- **ID:** `culinaria:bacon_and_eggs` · **Categoria:** Café da manhã · **Rende:** 1
- **Ingredientes:** 2× Bacon cru (P), 2× Ovo
- **Origem:** Bacon cru: Preparo: Tábua de corte; Ovo: Galinhas botam ovos sozinhas
- **Intermediários:** Bacon cru
- **Equipamento:** Fogão + Frigideira · **Tempo:** 8.0 s (160 ticks)
- **Servir em:** Prato (volta sujo) × 1
- **Resultado:** 1× Ovos com bacon, fome 10 e saturação 0.8 cada
- **Etapas:**
  1. Corte bacon cru — Tábua de corte
  2. Cozinhe ovos com bacon — Fogão com frigideira

### 17. Pão na chapa

- **ID:** `culinaria:pao_na_chapa` · **Categoria:** Café da manhã · **Rende:** 1
- **Ingredientes:** 1× Pão francês (P), 1× Manteiga (P)
- **Origem:** Pão francês: Preparo: Forno; Manteiga: Preparo: Bancada de cozinha
- **Intermediários:** Pão francês, Manteiga
- **Equipamento:** Fogão + Frigideira · **Tempo:** 5.0 s (100 ticks)
- **Servir em:** nada (comida de mão)
- **Resultado:** 1× Pão na chapa, fome 6 e saturação 0.6 cada
- **Etapas:**
  1. Moa farinha de trigo — Moedor manual
  2. Cozinhe sal — Fogão com panela
  3. Prepare massa de pão — Bancada de cozinha
  4. Asse pão francês — Forno com forma de assar
  5. Prepare creme de leite — Bancada de cozinha com batedor de arame
  6. Prepare manteiga — Bancada de cozinha com batedor de arame
  7. Cozinhe pão na chapa — Fogão com frigideira

### 18. Misto-quente

- **ID:** `culinaria:misto_quente` · **Categoria:** Café da manhã · **Rende:** 1
- **Ingredientes:** 2× Fatia de pão (P), 1× Presunto (P), 1× Queijo (P)
- **Origem:** Fatia de pão: Preparo: Tábua de corte; Presunto: Preparo: Tábua de corte; Queijo: Preparo: Fogão
- **Intermediários:** Fatia de pão, Presunto, Queijo
- **Equipamento:** Fogão + Frigideira · **Tempo:** 6.0 s (120 ticks)
- **Servir em:** nada (comida de mão)
- **Resultado:** 1× Misto-quente, fome 9 e saturação 0.7 cada
- **Etapas:**
  1. Corte fatia de pão — Tábua de corte
  2. Corte presunto — Tábua de corte
  3. Cozinhe queijo — Fogão com panela
  4. Cozinhe misto-quente — Fogão com frigideira

## Carnes e aves

### 19. Bife grelhado

- **ID:** `culinaria:grilled_steak` · **Categoria:** Carnes e aves · **Rende:** 1
- **Ingredientes:** 1× Carne bovina crua, 1× Sal (P), 1× Alho picado (P)
- **Origem:** Carne bovina crua: Vacas; Sal: Preparo: Fogão; Alho picado: Preparo: Tábua de corte
- **Intermediários:** Sal, Alho picado
- **Equipamento:** Fogão + Frigideira · **Tempo:** 8.0 s (160 ticks)
- **Servir em:** Prato (volta sujo) × 1
- **Resultado:** 1× Bife grelhado, fome 10 e saturação 0.9 cada
- **Etapas:**
  1. Cozinhe sal — Fogão com panela
  2. Corte alho picado — Tábua de corte
  3. Cozinhe bife grelhado — Fogão com frigideira

### 20. Espetinho de carne

- **ID:** `culinaria:beef_skewer` · **Categoria:** Carnes e aves · **Rende:** 2
- **Ingredientes:** 2× Carne em cubos (P), 1× Cebola picada (P), 1× Pimentão em tiras (P), 1× Graveto
- **Origem:** Carne em cubos: Preparo: Tábua de corte; Cebola picada: Preparo: Tábua de corte; Pimentão em tiras: Preparo: Tábua de corte; Graveto: Bancada de trabalho: 2 tábuas
- **Intermediários:** Carne em cubos, Cebola picada, Pimentão em tiras
- **Equipamento:** Fogão + Frigideira · **Tempo:** 8.0 s (160 ticks)
- **Servir em:** nada (comida de mão)
- **Resultado:** 2× Espetinho de carne, fome 7 e saturação 0.8 cada
- **Etapas:**
  1. Corte carne em cubos — Tábua de corte
  2. Corte cebola picada — Tábua de corte
  3. Corte pimentão em tiras — Tábua de corte
  4. Cozinhe espetinho de carne — Fogão com frigideira

### 21. Frango assado

- **ID:** `culinaria:roast_chicken` · **Categoria:** Carnes e aves · **Rende:** 1
- **Ingredientes:** 1× Frango cru, 1× Alho picado (P), 1× Manteiga (P), 1× Sal (P), 1× Salsinha
- **Origem:** Frango cru: Galinhas; Alho picado: Preparo: Tábua de corte; Manteiga: Preparo: Bancada de cozinha; Sal: Preparo: Fogão; Salsinha: Lavoura de salsinha (sementes de salsinha da grama)
- **Intermediários:** Alho picado, Manteiga, Sal
- **Equipamento:** Forno + Forma de assar · **Tempo:** 15.0 s (300 ticks)
- **Servir em:** Prato (volta sujo) × 1
- **Resultado:** 1× Frango assado, fome 12 e saturação 0.9 cada
- **Etapas:**
  1. Corte alho picado — Tábua de corte
  2. Prepare creme de leite — Bancada de cozinha com batedor de arame
  3. Prepare manteiga — Bancada de cozinha com batedor de arame
  4. Cozinhe sal — Fogão com panela
  5. Asse frango assado — Forno com forma de assar

### 22. Frango frito

- **ID:** `culinaria:fried_chicken` · **Categoria:** Carnes e aves · **Rende:** 1
- **Ingredientes:** 2× Frango em pedaços (P), 1× Farinha de trigo (P), 1× Ovo, 1× Manteiga (P)
- **Origem:** Frango em pedaços: Preparo: Tábua de corte; Farinha de trigo: Preparo: Moedor manual; Ovo: Galinhas botam ovos sozinhas; Manteiga: Preparo: Bancada de cozinha
- **Intermediários:** Frango em pedaços, Farinha de trigo, Manteiga
- **Equipamento:** Fogão + Frigideira · **Tempo:** 10.0 s (200 ticks)
- **Servir em:** Prato (volta sujo) × 1
- **Resultado:** 1× Frango frito, fome 11 e saturação 0.8 cada
- **Etapas:**
  1. Corte frango em pedaços — Tábua de corte
  2. Moa farinha de trigo — Moedor manual
  3. Prepare creme de leite — Bancada de cozinha com batedor de arame
  4. Prepare manteiga — Bancada de cozinha com batedor de arame
  5. Cozinhe frango frito — Fogão com frigideira

### 23. Almôndegas ao molho

- **ID:** `culinaria:meatballs` · **Categoria:** Carnes e aves · **Rende:** 1
- **Ingredientes:** 2× Carne moída (P), 1× Ovo, 1× Farinha de rosca (P), 1× Molho de tomate (P)
- **Origem:** Carne moída: Preparo: Moedor manual; Ovo: Galinhas botam ovos sozinhas; Farinha de rosca: Preparo: Moedor manual; Molho de tomate: Preparo: Fogão
- **Intermediários:** Carne moída, Farinha de rosca, Molho de tomate
- **Equipamento:** Fogão + Panela · **Tempo:** 12.0 s (240 ticks)
- **Servir em:** Tigela (volta) × 1
- **Resultado:** 1× Almôndegas ao molho, fome 12 e saturação 0.8 cada
- **Etapas:**
  1. Moa carne moída — Moedor manual
  2. Moa farinha de rosca — Moedor manual
  3. Corte cebola picada — Tábua de corte
  4. Corte alho picado — Tábua de corte
  5. Cozinhe sal — Fogão com panela
  6. Cozinhe molho de tomate — Fogão com panela
  7. Cozinhe almôndegas ao molho — Fogão com panela

### 24. Lombo assado com laranja

- **ID:** `culinaria:roast_pork` · **Categoria:** Carnes e aves · **Rende:** 2
- **Ingredientes:** 2× Costeleta de porco crua, 1× Alho picado (P), 1× Laranja, 1× Sal (P)
- **Origem:** Costeleta de porco crua: Porcos; Alho picado: Preparo: Tábua de corte; Laranja: Colheita da laranjeira (clique direito na folha madura); Sal: Preparo: Fogão
- **Intermediários:** Alho picado, Sal
- **Equipamento:** Forno + Forma de assar · **Tempo:** 15.0 s (300 ticks)
- **Servir em:** Prato (volta sujo) × 2
- **Resultado:** 2× Lombo assado com laranja, fome 12 e saturação 0.9 cada
- **Etapas:**
  1. Corte alho picado — Tábua de corte
  2. Cozinhe sal — Fogão com panela
  3. Asse lombo assado com laranja — Forno com forma de assar

### 25. Hambúrguer

- **ID:** `culinaria:hamburger` · **Categoria:** Carnes e aves · **Rende:** 1
- **Ingredientes:** 1× Pão francês (P), 1× Hambúrguer de carne (P), 1× Queijo (P), 1× Alface picada (P), 1× Tomate fatiado (P)
- **Origem:** Pão francês: Preparo: Forno; Hambúrguer de carne: Preparo: Fogão; Queijo: Preparo: Fogão; Alface picada: Preparo: Tábua de corte; Tomate fatiado: Preparo: Tábua de corte
- **Intermediários:** Pão francês, Hambúrguer de carne, Queijo, Alface picada, Tomate fatiado
- **Equipamento:** Bancada de cozinha · **Tempo:** 5.0 s (100 ticks)
- **Servir em:** nada (comida de mão)
- **Resultado:** 1× Hambúrguer, fome 13 e saturação 0.8 cada
- **Etapas:**
  1. Moa farinha de trigo — Moedor manual
  2. Cozinhe sal — Fogão com panela
  3. Prepare massa de pão — Bancada de cozinha
  4. Asse pão francês — Forno com forma de assar
  5. Moa carne moída — Moedor manual
  6. Cozinhe hambúrguer de carne — Fogão com frigideira
  7. Cozinhe queijo — Fogão com panela
  8. Corte alface picada — Tábua de corte
  9. Corte tomate fatiado — Tábua de corte
  10. Prepare hambúrguer — Bancada de cozinha

### 26. Carne de panela

- **ID:** `culinaria:pot_roast` · **Categoria:** Carnes e aves · **Rende:** 1
- **Ingredientes:** 2× Carne em cubos (P), 1× Batata, 1× Cenoura, 1× Cebola picada (P), 1× Balde de água
- **Origem:** Carne em cubos: Preparo: Tábua de corte; Batata: Lavoura de batata; aldeias e zumbis; Cenoura: Lavoura de cenoura; aldeias e zumbis; Cebola picada: Preparo: Tábua de corte; Balde de água: Encha um balde em qualquer água (o balde volta vazio)
- **Intermediários:** Carne em cubos, Cebola picada
- **Equipamento:** Fogão + Panela · **Tempo:** 15.0 s (300 ticks)
- **Servir em:** Tigela (volta) × 1
- **Resultado:** 1× Carne de panela, fome 13 e saturação 0.9 cada
- **Etapas:**
  1. Corte carne em cubos — Tábua de corte
  2. Corte cebola picada — Tábua de corte
  3. Cozinhe carne de panela — Fogão com panela

### 27. Costeletas de cordeiro com ervas

- **ID:** `culinaria:herb_lamb_chops` · **Categoria:** Carnes e aves · **Rende:** 1
- **Ingredientes:** 1× Carne de carneiro crua, 1× Alho picado (P), 1× Salsinha, 1× Manteiga (P)
- **Origem:** Carne de carneiro crua: Ovelhas; Alho picado: Preparo: Tábua de corte; Salsinha: Lavoura de salsinha (sementes de salsinha da grama); Manteiga: Preparo: Bancada de cozinha
- **Intermediários:** Alho picado, Manteiga
- **Equipamento:** Fogão + Frigideira · **Tempo:** 9.0 s (180 ticks)
- **Servir em:** Prato (volta sujo) × 1
- **Resultado:** 1× Costeletas de cordeiro com ervas, fome 10 e saturação 0.9 cada
- **Etapas:**
  1. Corte alho picado — Tábua de corte
  2. Prepare creme de leite — Bancada de cozinha com batedor de arame
  3. Prepare manteiga — Bancada de cozinha com batedor de arame
  4. Cozinhe costeletas de cordeiro com ervas — Fogão com frigideira

### 28. Coelho assado

- **ID:** `culinaria:roast_rabbit` · **Categoria:** Carnes e aves · **Rende:** 1
- **Ingredientes:** 1× Coelho cru, 1× Cenoura, 1× Cebola picada (P), 1× Sal (P)
- **Origem:** Coelho cru: Coelhos; Cenoura: Lavoura de cenoura; aldeias e zumbis; Cebola picada: Preparo: Tábua de corte; Sal: Preparo: Fogão
- **Intermediários:** Cebola picada, Sal
- **Equipamento:** Forno + Forma de assar · **Tempo:** 14.0 s (280 ticks)
- **Servir em:** Prato (volta sujo) × 1
- **Resultado:** 1× Coelho assado, fome 11 e saturação 0.9 cada
- **Etapas:**
  1. Corte cebola picada — Tábua de corte
  2. Cozinhe sal — Fogão com panela
  3. Asse coelho assado — Forno com forma de assar

## Peixes

### 29. Salmão grelhado com limão

- **ID:** `culinaria:grilled_salmon` · **Categoria:** Peixes · **Rende:** 1
- **Ingredientes:** 1× Filé de salmão (P), 1× Limão, 1× Manteiga (P), 1× Sal (P)
- **Origem:** Filé de salmão: Preparo: Tábua de corte; Limão: Colheita da limoeiro (clique direito na folha madura); Manteiga: Preparo: Bancada de cozinha; Sal: Preparo: Fogão
- **Intermediários:** Filé de salmão, Manteiga, Sal
- **Equipamento:** Fogão + Frigideira · **Tempo:** 8.0 s (160 ticks)
- **Servir em:** Prato (volta sujo) × 1
- **Resultado:** 1× Salmão grelhado com limão, fome 10 e saturação 0.9 cada
- **Etapas:**
  1. Corte filé de salmão — Tábua de corte
  2. Prepare creme de leite — Bancada de cozinha com batedor de arame
  3. Prepare manteiga — Bancada de cozinha com batedor de arame
  4. Cozinhe sal — Fogão com panela
  5. Cozinhe salmão grelhado com limão — Fogão com frigideira

### 30. Salmão ao mel

- **ID:** `culinaria:honey_salmon` · **Categoria:** Peixes · **Rende:** 1
- **Ingredientes:** 1× Filé de salmão (P), 1× Frasco de mel, 1× Alho picado (P)
- **Origem:** Filé de salmão: Preparo: Tábua de corte; Frasco de mel: Use um frasco de vidro numa colmeia cheia (o frasco volta); Alho picado: Preparo: Tábua de corte
- **Intermediários:** Filé de salmão, Alho picado
- **Equipamento:** Fogão + Frigideira · **Tempo:** 8.0 s (160 ticks)
- **Servir em:** Prato (volta sujo) × 1
- **Resultado:** 1× Salmão ao mel, fome 10 e saturação 0.8 cada
- **Etapas:**
  1. Corte filé de salmão — Tábua de corte
  2. Corte alho picado — Tábua de corte
  3. Cozinhe salmão ao mel — Fogão com frigideira

### 31. Peixe frito

- **ID:** `culinaria:fried_fish` · **Categoria:** Peixes · **Rende:** 1
- **Ingredientes:** 2× Filé de bacalhau (P), 1× Farinha de trigo (P), 1× Ovo, 1× Manteiga (P)
- **Origem:** Filé de bacalhau: Preparo: Tábua de corte; Farinha de trigo: Preparo: Moedor manual; Ovo: Galinhas botam ovos sozinhas; Manteiga: Preparo: Bancada de cozinha
- **Intermediários:** Filé de bacalhau, Farinha de trigo, Manteiga
- **Equipamento:** Fogão + Frigideira · **Tempo:** 9.0 s (180 ticks)
- **Servir em:** Prato (volta sujo) × 1
- **Resultado:** 1× Peixe frito, fome 10 e saturação 0.8 cada
- **Etapas:**
  1. Corte filé de bacalhau — Tábua de corte
  2. Moa farinha de trigo — Moedor manual
  3. Prepare creme de leite — Bancada de cozinha com batedor de arame
  4. Prepare manteiga — Bancada de cozinha com batedor de arame
  5. Cozinhe peixe frito — Fogão com frigideira

### 32. Peixe com fritas

- **ID:** `culinaria:fish_and_chips` · **Categoria:** Peixes · **Rende:** 1
- **Ingredientes:** 1× Filé de bacalhau (P), 2× Batata em palitos (P), 1× Farinha de trigo (P), 1× Sal (P)
- **Origem:** Filé de bacalhau: Preparo: Tábua de corte; Batata em palitos: Preparo: Tábua de corte; Farinha de trigo: Preparo: Moedor manual; Sal: Preparo: Fogão
- **Intermediários:** Filé de bacalhau, Batata em palitos, Farinha de trigo, Sal
- **Equipamento:** Fogão + Frigideira · **Tempo:** 10.0 s (200 ticks)
- **Servir em:** Prato (volta sujo) × 1
- **Resultado:** 1× Peixe com fritas, fome 12 e saturação 0.8 cada
- **Etapas:**
  1. Corte filé de bacalhau — Tábua de corte
  2. Corte batata em palitos — Tábua de corte
  3. Moa farinha de trigo — Moedor manual
  4. Cozinhe sal — Fogão com panela
  5. Cozinhe peixe com fritas — Fogão com frigideira

### 33. Moqueca de peixe

- **ID:** `culinaria:moqueca` · **Categoria:** Peixes · **Rende:** 1
- **Ingredientes:** 2× Filé de bacalhau (P), 1× Tomate fatiado (P), 1× Cebola picada (P), 1× Pimentão em tiras (P), 1× Salsinha
- **Origem:** Filé de bacalhau: Preparo: Tábua de corte; Tomate fatiado: Preparo: Tábua de corte; Cebola picada: Preparo: Tábua de corte; Pimentão em tiras: Preparo: Tábua de corte; Salsinha: Lavoura de salsinha (sementes de salsinha da grama)
- **Intermediários:** Filé de bacalhau, Tomate fatiado, Cebola picada, Pimentão em tiras
- **Equipamento:** Fogão + Panela · **Tempo:** 15.0 s (300 ticks)
- **Servir em:** Tigela (volta) × 1
- **Resultado:** 1× Moqueca de peixe, fome 13 e saturação 0.9 cada
- **Etapas:**
  1. Corte filé de bacalhau — Tábua de corte
  2. Corte tomate fatiado — Tábua de corte
  3. Corte cebola picada — Tábua de corte
  4. Corte pimentão em tiras — Tábua de corte
  5. Cozinhe moqueca de peixe — Fogão com panela

### 34. Sushi de salmão

- **ID:** `culinaria:sushi` · **Categoria:** Peixes · **Rende:** 4
- **Ingredientes:** 1× Arroz cozido (P), 1× Filé de salmão (P), 1× Alga seca
- **Origem:** Arroz cozido: Preparo: Fogão; Filé de salmão: Preparo: Tábua de corte; Alga seca: Alga do oceano seca na fornalha
- **Intermediários:** Arroz cozido, Filé de salmão
- **Equipamento:** Bancada de cozinha · **Tempo:** 6.0 s (120 ticks)
- **Servir em:** nada (comida de mão)
- **Resultado:** 4× Sushi de salmão, fome 3 e saturação 0.7 cada
- **Etapas:**
  1. Lave arroz lavado — Pia
  2. Cozinhe sal — Fogão com panela
  3. Cozinhe arroz cozido — Fogão com panela
  4. Corte filé de salmão — Tábua de corte
  5. Prepare sushi de salmão — Bancada de cozinha

### 35. Bacalhoada

- **ID:** `culinaria:bacalhoada` · **Categoria:** Peixes · **Rende:** 2
- **Ingredientes:** 2× Filé de bacalhau (P), 2× Batata, 1× Cebola picada (P), 1× Alho picado (P)
- **Origem:** Filé de bacalhau: Preparo: Tábua de corte; Batata: Lavoura de batata; aldeias e zumbis; Cebola picada: Preparo: Tábua de corte; Alho picado: Preparo: Tábua de corte
- **Intermediários:** Filé de bacalhau, Cebola picada, Alho picado
- **Equipamento:** Forno + Forma de assar · **Tempo:** 16.0 s (320 ticks)
- **Servir em:** Prato (volta sujo) × 2
- **Resultado:** 2× Bacalhoada, fome 13 e saturação 0.9 cada
- **Etapas:**
  1. Corte filé de bacalhau — Tábua de corte
  2. Corte cebola picada — Tábua de corte
  3. Corte alho picado — Tábua de corte
  4. Asse bacalhoada — Forno com forma de assar

### 36. Poke de salmão

- **ID:** `culinaria:poke_bowl` · **Categoria:** Peixes · **Rende:** 1
- **Ingredientes:** 1× Arroz cozido (P), 1× Filé de salmão (P), 1× Alface picada (P), 1× Milho
- **Origem:** Arroz cozido: Preparo: Fogão; Filé de salmão: Preparo: Tábua de corte; Alface picada: Preparo: Tábua de corte; Milho: Lavoura de milho (sementes de milho da grama)
- **Intermediários:** Arroz cozido, Filé de salmão, Alface picada
- **Equipamento:** Bancada de cozinha · **Tempo:** 5.0 s (100 ticks)
- **Servir em:** Tigela (volta) × 1
- **Resultado:** 1× Poke de salmão, fome 11 e saturação 0.8 cada
- **Etapas:**
  1. Lave arroz lavado — Pia
  2. Cozinhe sal — Fogão com panela
  3. Cozinhe arroz cozido — Fogão com panela
  4. Corte filé de salmão — Tábua de corte
  5. Corte alface picada — Tábua de corte
  6. Prepare poke de salmão — Bancada de cozinha

## Refeições principais

### 37. Arroz com feijão

- **ID:** `culinaria:rice_and_beans` · **Categoria:** Refeições principais · **Rende:** 1
- **Ingredientes:** 1× Arroz cozido (P), 1× Feijão cozido (P)
- **Origem:** Arroz cozido: Preparo: Fogão; Feijão cozido: Preparo: Fogão
- **Intermediários:** Arroz cozido, Feijão cozido
- **Equipamento:** Bancada de cozinha · **Tempo:** 4.0 s (80 ticks)
- **Servir em:** Prato (volta sujo) × 1
- **Resultado:** 1× Arroz com feijão, fome 10 e saturação 0.8 cada
- **Etapas:**
  1. Lave arroz lavado — Pia
  2. Cozinhe sal — Fogão com panela
  3. Cozinhe arroz cozido — Fogão com panela
  4. Lave feijão de molho — Pia
  5. Corte alho picado — Tábua de corte
  6. Corte cebola picada — Tábua de corte
  7. Cozinhe feijão cozido — Fogão com panela
  8. Prepare arroz com feijão — Bancada de cozinha

### 38. Feijoada

- **ID:** `culinaria:feijoada` · **Categoria:** Refeições principais · **Rende:** 2
- **Ingredientes:** 2× Feijão de molho (P), 1× Linguiça crua (P), 1× Bacon cru (P), 1× Costeleta de porco crua, 1× Alho picado (P)
- **Origem:** Feijão de molho: Preparo: Pia; Linguiça crua: Preparo: Moedor manual; Bacon cru: Preparo: Tábua de corte; Costeleta de porco crua: Porcos; Alho picado: Preparo: Tábua de corte
- **Intermediários:** Feijão de molho, Linguiça crua, Bacon cru, Alho picado
- **Equipamento:** Fogão + Panela · **Tempo:** 18.0 s (360 ticks)
- **Servir em:** Tigela (volta) × 2
- **Resultado:** 2× Feijoada, fome 16 e saturação 1 cada
- **Etapas:**
  1. Lave feijão de molho — Pia
  2. Moa linguiça crua — Moedor manual
  3. Corte bacon cru — Tábua de corte
  4. Corte alho picado — Tábua de corte
  5. Cozinhe feijoada — Fogão com panela

### 39. Prato feito

- **ID:** `culinaria:prato_feito` · **Categoria:** Refeições principais · **Rende:** 1
- **Ingredientes:** 1× Arroz cozido (P), 1× Feijão cozido (P), 1× Ovo frito (P), 1× Batata frita (P), 1× Alface picada (P)
- **Origem:** Arroz cozido: Preparo: Fogão; Feijão cozido: Preparo: Fogão; Ovo frito: Preparo: Fogão; Batata frita: Preparo: Fogão; Alface picada: Preparo: Tábua de corte
- **Intermediários:** Arroz cozido, Feijão cozido, Ovo frito, Batata frita, Alface picada
- **Equipamento:** Bancada de cozinha · **Tempo:** 6.0 s (120 ticks)
- **Servir em:** Prato (volta sujo) × 1
- **Resultado:** 1× Prato feito, fome 20 e saturação 1 cada
- **Etapas:**
  1. Lave arroz lavado — Pia
  2. Cozinhe sal — Fogão com panela
  3. Cozinhe arroz cozido — Fogão com panela
  4. Lave feijão de molho — Pia
  5. Corte alho picado — Tábua de corte
  6. Corte cebola picada — Tábua de corte
  7. Cozinhe feijão cozido — Fogão com panela
  8. Prepare creme de leite — Bancada de cozinha com batedor de arame
  9. Prepare manteiga — Bancada de cozinha com batedor de arame
  10. Cozinhe ovo frito — Fogão com frigideira
  11. Corte batata em palitos — Tábua de corte
  12. Cozinhe batata frita — Fogão com frigideira
  13. Corte alface picada — Tábua de corte
  14. Prepare prato feito — Bancada de cozinha

### 40. Estrogonofe de frango

- **ID:** `culinaria:chicken_stroganoff` · **Categoria:** Refeições principais · **Rende:** 2
- **Ingredientes:** 2× Frango em pedaços (P), 1× Creme de leite (P), 1× Cogumelo marrom, 1× Molho de tomate (P), 1× Cebola picada (P)
- **Origem:** Frango em pedaços: Preparo: Tábua de corte; Creme de leite: Preparo: Bancada de cozinha; Cogumelo marrom: Lugares escuros, pântanos e florestas escuras; Molho de tomate: Preparo: Fogão; Cebola picada: Preparo: Tábua de corte
- **Intermediários:** Frango em pedaços, Creme de leite, Molho de tomate, Cebola picada
- **Equipamento:** Fogão + Frigideira · **Tempo:** 13.0 s (260 ticks)
- **Servir em:** Prato (volta sujo) × 2
- **Resultado:** 2× Estrogonofe de frango, fome 13 e saturação 0.9 cada
- **Etapas:**
  1. Corte frango em pedaços — Tábua de corte
  2. Prepare creme de leite — Bancada de cozinha com batedor de arame
  3. Corte cebola picada — Tábua de corte
  4. Corte alho picado — Tábua de corte
  5. Cozinhe sal — Fogão com panela
  6. Cozinhe molho de tomate — Fogão com panela
  7. Cozinhe estrogonofe de frango — Fogão com frigideira

### 41. Risoto de cogumelos

- **ID:** `culinaria:mushroom_risotto` · **Categoria:** Refeições principais · **Rende:** 2
- **Ingredientes:** 2× Arroz lavado (P), 2× Cogumelo marrom, 1× Queijo (P), 1× Manteiga (P), 1× Cebola picada (P)
- **Origem:** Arroz lavado: Preparo: Pia; Cogumelo marrom: Lugares escuros, pântanos e florestas escuras; Queijo: Preparo: Fogão; Manteiga: Preparo: Bancada de cozinha; Cebola picada: Preparo: Tábua de corte
- **Intermediários:** Arroz lavado, Queijo, Manteiga, Cebola picada
- **Equipamento:** Fogão + Panela · **Tempo:** 15.0 s (300 ticks)
- **Servir em:** Prato (volta sujo) × 2
- **Resultado:** 2× Risoto de cogumelos, fome 12 e saturação 0.8 cada
- **Etapas:**
  1. Lave arroz lavado — Pia
  2. Cozinhe queijo — Fogão com panela
  3. Prepare creme de leite — Bancada de cozinha com batedor de arame
  4. Prepare manteiga — Bancada de cozinha com batedor de arame
  5. Corte cebola picada — Tábua de corte
  6. Cozinhe risoto de cogumelos — Fogão com panela

### 42. Galinhada

- **ID:** `culinaria:galinhada` · **Categoria:** Refeições principais · **Rende:** 2
- **Ingredientes:** 2× Frango em pedaços (P), 2× Arroz lavado (P), 1× Milho, 1× Pimentão em tiras (P), 1× Balde de água
- **Origem:** Frango em pedaços: Preparo: Tábua de corte; Arroz lavado: Preparo: Pia; Milho: Lavoura de milho (sementes de milho da grama); Pimentão em tiras: Preparo: Tábua de corte; Balde de água: Encha um balde em qualquer água (o balde volta vazio)
- **Intermediários:** Frango em pedaços, Arroz lavado, Pimentão em tiras
- **Equipamento:** Fogão + Panela · **Tempo:** 16.0 s (320 ticks)
- **Servir em:** Prato (volta sujo) × 2
- **Resultado:** 2× Galinhada, fome 14 e saturação 0.9 cada
- **Etapas:**
  1. Corte frango em pedaços — Tábua de corte
  2. Lave arroz lavado — Pia
  3. Corte pimentão em tiras — Tábua de corte
  4. Cozinhe galinhada — Fogão com panela

### 43. Escondidinho de carne

- **ID:** `culinaria:escondidinho` · **Categoria:** Refeições principais · **Rende:** 2
- **Ingredientes:** 1× Carne moída (P), 2× Mandioca descascada (P), 1× Queijo (P), 1× Manteiga (P)
- **Origem:** Carne moída: Preparo: Moedor manual; Mandioca descascada: Preparo: Tábua de corte; Queijo: Preparo: Fogão; Manteiga: Preparo: Bancada de cozinha
- **Intermediários:** Carne moída, Mandioca descascada, Queijo, Manteiga
- **Equipamento:** Forno + Forma de assar · **Tempo:** 15.0 s (300 ticks)
- **Servir em:** Prato (volta sujo) × 2
- **Resultado:** 2× Escondidinho de carne, fome 14 e saturação 0.9 cada
- **Etapas:**
  1. Moa carne moída — Moedor manual
  2. Corte mandioca descascada — Tábua de corte
  3. Cozinhe queijo — Fogão com panela
  4. Prepare creme de leite — Bancada de cozinha com batedor de arame
  5. Prepare manteiga — Bancada de cozinha com batedor de arame
  6. Asse escondidinho de carne — Forno com forma de assar

### 44. Pimentão recheado

- **ID:** `culinaria:stuffed_peppers` · **Categoria:** Refeições principais · **Rende:** 2
- **Ingredientes:** 2× Pimentão, 1× Carne moída (P), 1× Arroz cozido (P), 1× Molho de tomate (P)
- **Origem:** Pimentão: Lavoura de pimentão (sementes de pimentão da grama); Carne moída: Preparo: Moedor manual; Arroz cozido: Preparo: Fogão; Molho de tomate: Preparo: Fogão
- **Intermediários:** Carne moída, Arroz cozido, Molho de tomate
- **Equipamento:** Forno + Forma de assar · **Tempo:** 14.0 s (280 ticks)
- **Servir em:** Prato (volta sujo) × 2
- **Resultado:** 2× Pimentão recheado, fome 12 e saturação 0.8 cada
- **Etapas:**
  1. Moa carne moída — Moedor manual
  2. Lave arroz lavado — Pia
  3. Cozinhe sal — Fogão com panela
  4. Cozinhe arroz cozido — Fogão com panela
  5. Corte cebola picada — Tábua de corte
  6. Corte alho picado — Tábua de corte
  7. Cozinhe molho de tomate — Fogão com panela
  8. Asse pimentão recheado — Forno com forma de assar

### 45. Baião de dois

- **ID:** `culinaria:baiao_de_dois` · **Categoria:** Refeições principais · **Rende:** 2
- **Ingredientes:** 1× Arroz cozido (P), 1× Feijão cozido (P), 1× Queijo (P), 1× Bacon cru (P), 1× Salsinha
- **Origem:** Arroz cozido: Preparo: Fogão; Feijão cozido: Preparo: Fogão; Queijo: Preparo: Fogão; Bacon cru: Preparo: Tábua de corte; Salsinha: Lavoura de salsinha (sementes de salsinha da grama)
- **Intermediários:** Arroz cozido, Feijão cozido, Queijo, Bacon cru
- **Equipamento:** Fogão + Panela · **Tempo:** 12.0 s (240 ticks)
- **Servir em:** Prato (volta sujo) × 2
- **Resultado:** 2× Baião de dois, fome 14 e saturação 0.9 cada
- **Etapas:**
  1. Lave arroz lavado — Pia
  2. Cozinhe sal — Fogão com panela
  3. Cozinhe arroz cozido — Fogão com panela
  4. Lave feijão de molho — Pia
  5. Corte alho picado — Tábua de corte
  6. Corte cebola picada — Tábua de corte
  7. Cozinhe feijão cozido — Fogão com panela
  8. Cozinhe queijo — Fogão com panela
  9. Corte bacon cru — Tábua de corte
  10. Cozinhe baião de dois — Fogão com panela

### 46. Carne assada com batatas

- **ID:** `culinaria:roast_beef_potatoes` · **Categoria:** Refeições principais · **Rende:** 2
- **Ingredientes:** 2× Carne bovina crua, 2× Batata, 1× Cebola picada (P), 1× Alho picado (P)
- **Origem:** Carne bovina crua: Vacas; Batata: Lavoura de batata; aldeias e zumbis; Cebola picada: Preparo: Tábua de corte; Alho picado: Preparo: Tábua de corte
- **Intermediários:** Cebola picada, Alho picado
- **Equipamento:** Forno + Forma de assar · **Tempo:** 17.0 s (340 ticks)
- **Servir em:** Prato (volta sujo) × 2
- **Resultado:** 2× Carne assada com batatas, fome 14 e saturação 1 cada
- **Etapas:**
  1. Corte cebola picada — Tábua de corte
  2. Corte alho picado — Tábua de corte
  3. Asse carne assada com batatas — Forno com forma de assar

## Sopas e caldos

### 47. Sopa de tomate

- **ID:** `culinaria:tomato_soup` · **Categoria:** Sopas e caldos · **Rende:** 2
- **Ingredientes:** 3× Tomate, 1× Cebola picada (P), 1× Manjericão, 1× Balde de água
- **Origem:** Tomate: Lavoura de tomate (sementes de tomate da grama); Cebola picada: Preparo: Tábua de corte; Manjericão: Lavoura de manjericão (sementes de manjericão da grama); Balde de água: Encha um balde em qualquer água (o balde volta vazio)
- **Intermediários:** Cebola picada
- **Equipamento:** Fogão + Panela · **Tempo:** 11.0 s (220 ticks)
- **Servir em:** Tigela (volta) × 2
- **Resultado:** 2× Sopa de tomate, fome 7 e saturação 0.6 cada
- **Etapas:**
  1. Corte cebola picada — Tábua de corte
  2. Cozinhe sopa de tomate — Fogão com panela

### 48. Canja de galinha

- **ID:** `culinaria:chicken_soup` · **Categoria:** Sopas e caldos · **Rende:** 2
- **Ingredientes:** 1× Frango em pedaços (P), 1× Arroz lavado (P), 1× Cenoura, 1× Cebola picada (P), 1× Balde de água
- **Origem:** Frango em pedaços: Preparo: Tábua de corte; Arroz lavado: Preparo: Pia; Cenoura: Lavoura de cenoura; aldeias e zumbis; Cebola picada: Preparo: Tábua de corte; Balde de água: Encha um balde em qualquer água (o balde volta vazio)
- **Intermediários:** Frango em pedaços, Arroz lavado, Cebola picada
- **Equipamento:** Fogão + Panela · **Tempo:** 13.0 s (260 ticks)
- **Servir em:** Tigela (volta) × 2
- **Resultado:** 2× Canja de galinha, fome 9 e saturação 0.7 cada
- **Etapas:**
  1. Corte frango em pedaços — Tábua de corte
  2. Lave arroz lavado — Pia
  3. Corte cebola picada — Tábua de corte
  4. Cozinhe canja de galinha — Fogão com panela

### 49. Sopa de legumes

- **ID:** `culinaria:vegetable_soup` · **Categoria:** Sopas e caldos · **Rende:** 2
- **Ingredientes:** 1× Batata, 1× Cenoura, 1× Cebola picada (P), 1× Beterraba, 1× Balde de água
- **Origem:** Batata: Lavoura de batata; aldeias e zumbis; Cenoura: Lavoura de cenoura; aldeias e zumbis; Cebola picada: Preparo: Tábua de corte; Beterraba: Lavoura de beterraba (sementes em aldeias e baús); Balde de água: Encha um balde em qualquer água (o balde volta vazio)
- **Intermediários:** Cebola picada
- **Equipamento:** Fogão + Panela · **Tempo:** 12.0 s (240 ticks)
- **Servir em:** Tigela (volta) × 2
- **Resultado:** 2× Sopa de legumes, fome 8 e saturação 0.6 cada
- **Etapas:**
  1. Corte cebola picada — Tábua de corte
  2. Cozinhe sopa de legumes — Fogão com panela

### 50. Creme de abóbora

- **ID:** `culinaria:pumpkin_soup` · **Categoria:** Sopas e caldos · **Rende:** 2
- **Ingredientes:** 1× Abóbora, 1× Cebola picada (P), 1× Creme de leite (P), 1× Sal (P)
- **Origem:** Abóbora: Abóboras nascem na grama; plante as sementes; Cebola picada: Preparo: Tábua de corte; Creme de leite: Preparo: Bancada de cozinha; Sal: Preparo: Fogão
- **Intermediários:** Cebola picada, Creme de leite, Sal
- **Equipamento:** Fogão + Panela · **Tempo:** 12.0 s (240 ticks)
- **Servir em:** Tigela (volta) × 2
- **Resultado:** 2× Creme de abóbora, fome 8 e saturação 0.7 cada
- **Etapas:**
  1. Corte cebola picada — Tábua de corte
  2. Prepare creme de leite — Bancada de cozinha com batedor de arame
  3. Cozinhe sal — Fogão com panela
  4. Cozinhe creme de abóbora — Fogão com panela

### 51. Sopa de cebola

- **ID:** `culinaria:onion_soup` · **Categoria:** Sopas e caldos · **Rende:** 2
- **Ingredientes:** 3× Cebola picada (P), 1× Manteiga (P), 1× Queijo (P), 1× Fatia de pão (P), 1× Balde de água
- **Origem:** Cebola picada: Preparo: Tábua de corte; Manteiga: Preparo: Bancada de cozinha; Queijo: Preparo: Fogão; Fatia de pão: Preparo: Tábua de corte; Balde de água: Encha um balde em qualquer água (o balde volta vazio)
- **Intermediários:** Cebola picada, Manteiga, Queijo, Fatia de pão
- **Equipamento:** Fogão + Panela · **Tempo:** 13.0 s (260 ticks)
- **Servir em:** Tigela (volta) × 2
- **Resultado:** 2× Sopa de cebola, fome 9 e saturação 0.7 cada
- **Etapas:**
  1. Corte cebola picada — Tábua de corte
  2. Prepare creme de leite — Bancada de cozinha com batedor de arame
  3. Prepare manteiga — Bancada de cozinha com batedor de arame
  4. Cozinhe queijo — Fogão com panela
  5. Corte fatia de pão — Tábua de corte
  6. Cozinhe sopa de cebola — Fogão com panela

### 52. Caldo verde

- **ID:** `culinaria:caldo_verde` · **Categoria:** Sopas e caldos · **Rende:** 2
- **Ingredientes:** 2× Batata, 1× Couve, 1× Linguiça crua (P), 1× Alho picado (P), 1× Balde de água
- **Origem:** Batata: Lavoura de batata; aldeias e zumbis; Couve: Lavoura de couve (sementes de couve da grama); Linguiça crua: Preparo: Moedor manual; Alho picado: Preparo: Tábua de corte; Balde de água: Encha um balde em qualquer água (o balde volta vazio)
- **Intermediários:** Linguiça crua, Alho picado
- **Equipamento:** Fogão + Panela · **Tempo:** 13.0 s (260 ticks)
- **Servir em:** Tigela (volta) × 2
- **Resultado:** 2× Caldo verde, fome 10 e saturação 0.7 cada
- **Etapas:**
  1. Moa linguiça crua — Moedor manual
  2. Corte alho picado — Tábua de corte
  3. Cozinhe caldo verde — Fogão com panela

### 53. Caldinho de feijão

- **ID:** `culinaria:bean_broth` · **Categoria:** Sopas e caldos · **Rende:** 2
- **Ingredientes:** 2× Feijão cozido (P), 1× Bacon cru (P), 1× Alho picado (P), 1× Salsinha
- **Origem:** Feijão cozido: Preparo: Fogão; Bacon cru: Preparo: Tábua de corte; Alho picado: Preparo: Tábua de corte; Salsinha: Lavoura de salsinha (sementes de salsinha da grama)
- **Intermediários:** Feijão cozido, Bacon cru, Alho picado
- **Equipamento:** Fogão + Panela · **Tempo:** 10.0 s (200 ticks)
- **Servir em:** Tigela (volta) × 2
- **Resultado:** 2× Caldinho de feijão, fome 9 e saturação 0.7 cada
- **Etapas:**
  1. Lave feijão de molho — Pia
  2. Corte alho picado — Tábua de corte
  3. Corte cebola picada — Tábua de corte
  4. Cozinhe sal — Fogão com panela
  5. Cozinhe feijão cozido — Fogão com panela
  6. Corte bacon cru — Tábua de corte
  7. Cozinhe caldinho de feijão — Fogão com panela

### 54. Creme de milho

- **ID:** `culinaria:corn_cream` · **Categoria:** Sopas e caldos · **Rende:** 2
- **Ingredientes:** 3× Milho, 1× Balde de leite, 1× Manteiga (P), 1× Cebola picada (P)
- **Origem:** Milho: Lavoura de milho (sementes de milho da grama); Balde de leite: Ordenhe uma vaca ou cabra com um balde (o balde volta vazio); Manteiga: Preparo: Bancada de cozinha; Cebola picada: Preparo: Tábua de corte
- **Intermediários:** Manteiga, Cebola picada
- **Equipamento:** Fogão + Panela · **Tempo:** 11.0 s (220 ticks)
- **Servir em:** Tigela (volta) × 2
- **Resultado:** 2× Creme de milho, fome 8 e saturação 0.6 cada
- **Etapas:**
  1. Prepare creme de leite — Bancada de cozinha com batedor de arame
  2. Prepare manteiga — Bancada de cozinha com batedor de arame
  3. Corte cebola picada — Tábua de corte
  4. Cozinhe creme de milho — Fogão com panela

### 55. Sopa de peixe

- **ID:** `culinaria:fish_soup` · **Categoria:** Sopas e caldos · **Rende:** 2
- **Ingredientes:** 1× Filé de bacalhau (P), 1× Batata, 1× Tomate fatiado (P), 1× Cebola picada (P), 1× Salsinha, 1× Balde de água
- **Origem:** Filé de bacalhau: Preparo: Tábua de corte; Batata: Lavoura de batata; aldeias e zumbis; Tomate fatiado: Preparo: Tábua de corte; Cebola picada: Preparo: Tábua de corte; Salsinha: Lavoura de salsinha (sementes de salsinha da grama); Balde de água: Encha um balde em qualquer água (o balde volta vazio)
- **Intermediários:** Filé de bacalhau, Tomate fatiado, Cebola picada
- **Equipamento:** Fogão + Panela · **Tempo:** 13.0 s (260 ticks)
- **Servir em:** Tigela (volta) × 2
- **Resultado:** 2× Sopa de peixe, fome 9 e saturação 0.7 cada
- **Etapas:**
  1. Corte filé de bacalhau — Tábua de corte
  2. Corte tomate fatiado — Tábua de corte
  3. Corte cebola picada — Tábua de corte
  4. Cozinhe sopa de peixe — Fogão com panela

## Acompanhamentos

### 56. Batata frita

- **ID:** `culinaria:fries` · **Categoria:** Acompanhamentos · **Rende:** 1
- **Ingredientes:** 2× Batata em palitos (P), 1× Manteiga (P), 1× Sal (P)
- **Origem:** Batata em palitos: Preparo: Tábua de corte; Manteiga: Preparo: Bancada de cozinha; Sal: Preparo: Fogão
- **Intermediários:** Batata em palitos, Manteiga, Sal
- **Equipamento:** Fogão + Frigideira · **Tempo:** 7.0 s (140 ticks)
- **Servir em:** nada (comida de mão)
- **Resultado:** 1× Batata frita, fome 6 e saturação 0.5 cada
- **Etapas:**
  1. Corte batata em palitos — Tábua de corte
  2. Prepare creme de leite — Bancada de cozinha com batedor de arame
  3. Prepare manteiga — Bancada de cozinha com batedor de arame
  4. Cozinhe sal — Fogão com panela
  5. Cozinhe batata frita — Fogão com frigideira

### 57. Purê de batata

- **ID:** `culinaria:mashed_potatoes` · **Categoria:** Acompanhamentos · **Rende:** 2
- **Ingredientes:** 2× Batata, 1× Manteiga (P), 1× Balde de leite, 1× Sal (P)
- **Origem:** Batata: Lavoura de batata; aldeias e zumbis; Manteiga: Preparo: Bancada de cozinha; Balde de leite: Ordenhe uma vaca ou cabra com um balde (o balde volta vazio); Sal: Preparo: Fogão
- **Intermediários:** Manteiga, Sal
- **Equipamento:** Fogão + Panela · **Tempo:** 10.0 s (200 ticks)
- **Servir em:** Tigela (volta) × 2
- **Resultado:** 2× Purê de batata, fome 7 e saturação 0.6 cada
- **Etapas:**
  1. Prepare creme de leite — Bancada de cozinha com batedor de arame
  2. Prepare manteiga — Bancada de cozinha com batedor de arame
  3. Cozinhe sal — Fogão com panela
  4. Cozinhe purê de batata — Fogão com panela

### 58. Farofa com bacon

- **ID:** `culinaria:farofa` · **Categoria:** Acompanhamentos · **Rende:** 2
- **Ingredientes:** 2× Farinha de mandioca (P), 1× Manteiga (P), 1× Bacon cru (P), 1× Cebola picada (P)
- **Origem:** Farinha de mandioca: Preparo: Moedor manual; Manteiga: Preparo: Bancada de cozinha; Bacon cru: Preparo: Tábua de corte; Cebola picada: Preparo: Tábua de corte
- **Intermediários:** Farinha de mandioca, Manteiga, Bacon cru, Cebola picada
- **Equipamento:** Fogão + Frigideira · **Tempo:** 8.0 s (160 ticks)
- **Servir em:** Tigela (volta) × 2
- **Resultado:** 2× Farofa com bacon, fome 6 e saturação 0.6 cada
- **Etapas:**
  1. Corte mandioca descascada — Tábua de corte
  2. Moa farinha de mandioca — Moedor manual
  3. Prepare creme de leite — Bancada de cozinha com batedor de arame
  4. Prepare manteiga — Bancada de cozinha com batedor de arame
  5. Corte bacon cru — Tábua de corte
  6. Corte cebola picada — Tábua de corte
  7. Cozinhe farofa com bacon — Fogão com frigideira

### 59. Couve refogada

- **ID:** `culinaria:couve_refogada` · **Categoria:** Acompanhamentos · **Rende:** 1
- **Ingredientes:** 2× Couve, 1× Alho picado (P), 1× Manteiga (P)
- **Origem:** Couve: Lavoura de couve (sementes de couve da grama); Alho picado: Preparo: Tábua de corte; Manteiga: Preparo: Bancada de cozinha
- **Intermediários:** Alho picado, Manteiga
- **Equipamento:** Fogão + Frigideira · **Tempo:** 6.0 s (120 ticks)
- **Servir em:** Prato (volta sujo) × 1
- **Resultado:** 1× Couve refogada, fome 5 e saturação 0.6 cada
- **Etapas:**
  1. Corte alho picado — Tábua de corte
  2. Prepare creme de leite — Bancada de cozinha com batedor de arame
  3. Prepare manteiga — Bancada de cozinha com batedor de arame
  4. Cozinhe couve refogada — Fogão com frigideira

### 60. Vinagrete

- **ID:** `culinaria:vinaigrette` · **Categoria:** Acompanhamentos · **Rende:** 2
- **Ingredientes:** 1× Tomate fatiado (P), 1× Cebola picada (P), 1× Pimentão em tiras (P), 1× Salsinha, 1× Limão
- **Origem:** Tomate fatiado: Preparo: Tábua de corte; Cebola picada: Preparo: Tábua de corte; Pimentão em tiras: Preparo: Tábua de corte; Salsinha: Lavoura de salsinha (sementes de salsinha da grama); Limão: Colheita da limoeiro (clique direito na folha madura)
- **Intermediários:** Tomate fatiado, Cebola picada, Pimentão em tiras
- **Equipamento:** Bancada de cozinha · **Tempo:** 5.0 s (100 ticks)
- **Servir em:** Tigela (volta) × 2
- **Resultado:** 2× Vinagrete, fome 4 e saturação 0.5 cada
- **Etapas:**
  1. Corte tomate fatiado — Tábua de corte
  2. Corte cebola picada — Tábua de corte
  3. Corte pimentão em tiras — Tábua de corte
  4. Prepare vinagrete — Bancada de cozinha

### 61. Cuscuz nordestino

- **ID:** `culinaria:cuscuz` · **Categoria:** Acompanhamentos · **Rende:** 2
- **Ingredientes:** 2× Fubá (P), 1× Balde de água, 1× Sal (P), 1× Manteiga (P)
- **Origem:** Fubá: Preparo: Moedor manual; Balde de água: Encha um balde em qualquer água (o balde volta vazio); Sal: Preparo: Fogão; Manteiga: Preparo: Bancada de cozinha
- **Intermediários:** Fubá, Sal, Manteiga
- **Equipamento:** Fogão + Panela · **Tempo:** 10.0 s (200 ticks)
- **Servir em:** Prato (volta sujo) × 2
- **Resultado:** 2× Cuscuz nordestino, fome 7 e saturação 0.6 cada
- **Etapas:**
  1. Moa fubá — Moedor manual
  2. Cozinhe sal — Fogão com panela
  3. Prepare creme de leite — Bancada de cozinha com batedor de arame
  4. Prepare manteiga — Bancada de cozinha com batedor de arame
  5. Cozinhe cuscuz nordestino — Fogão com panela

### 62. Polenta cremosa

- **ID:** `culinaria:polenta` · **Categoria:** Acompanhamentos · **Rende:** 2
- **Ingredientes:** 2× Fubá (P), 1× Balde de leite, 1× Queijo (P), 1× Manteiga (P)
- **Origem:** Fubá: Preparo: Moedor manual; Balde de leite: Ordenhe uma vaca ou cabra com um balde (o balde volta vazio); Queijo: Preparo: Fogão; Manteiga: Preparo: Bancada de cozinha
- **Intermediários:** Fubá, Queijo, Manteiga
- **Equipamento:** Fogão + Panela · **Tempo:** 11.0 s (220 ticks)
- **Servir em:** Tigela (volta) × 2
- **Resultado:** 2× Polenta cremosa, fome 7 e saturação 0.6 cada
- **Etapas:**
  1. Moa fubá — Moedor manual
  2. Cozinhe queijo — Fogão com panela
  3. Prepare creme de leite — Bancada de cozinha com batedor de arame
  4. Prepare manteiga — Bancada de cozinha com batedor de arame
  5. Cozinhe polenta cremosa — Fogão com panela

### 63. Milho cozido na manteiga

- **ID:** `culinaria:corn_on_the_cob` · **Categoria:** Acompanhamentos · **Rende:** 2
- **Ingredientes:** 2× Milho, 1× Balde de água, 1× Manteiga (P), 1× Sal (P)
- **Origem:** Milho: Lavoura de milho (sementes de milho da grama); Balde de água: Encha um balde em qualquer água (o balde volta vazio); Manteiga: Preparo: Bancada de cozinha; Sal: Preparo: Fogão
- **Intermediários:** Manteiga, Sal
- **Equipamento:** Fogão + Panela · **Tempo:** 10.0 s (200 ticks)
- **Servir em:** nada (comida de mão)
- **Resultado:** 2× Milho cozido na manteiga, fome 5 e saturação 0.6 cada
- **Etapas:**
  1. Prepare creme de leite — Bancada de cozinha com batedor de arame
  2. Prepare manteiga — Bancada de cozinha com batedor de arame
  3. Cozinhe sal — Fogão com panela
  4. Cozinhe milho cozido na manteiga — Fogão com panela

### 64. Batata recheada

- **ID:** `culinaria:stuffed_baked_potato` · **Categoria:** Acompanhamentos · **Rende:** 1
- **Ingredientes:** 1× Batata, 1× Queijo (P), 1× Bacon cru (P), 1× Manteiga (P)
- **Origem:** Batata: Lavoura de batata; aldeias e zumbis; Queijo: Preparo: Fogão; Bacon cru: Preparo: Tábua de corte; Manteiga: Preparo: Bancada de cozinha
- **Intermediários:** Queijo, Bacon cru, Manteiga
- **Equipamento:** Forno + Forma de assar · **Tempo:** 12.0 s (240 ticks)
- **Servir em:** Prato (volta sujo) × 1
- **Resultado:** 1× Batata recheada, fome 9 e saturação 0.7 cada
- **Etapas:**
  1. Cozinhe queijo — Fogão com panela
  2. Corte bacon cru — Tábua de corte
  3. Prepare creme de leite — Bancada de cozinha com batedor de arame
  4. Prepare manteiga — Bancada de cozinha com batedor de arame
  5. Asse batata recheada — Forno com forma de assar

### 65. Arroz à grega

- **ID:** `culinaria:arroz_a_grega` · **Categoria:** Acompanhamentos · **Rende:** 2
- **Ingredientes:** 2× Arroz lavado (P), 1× Cenoura, 1× Pimentão em tiras (P), 1× Milho, 1× Balde de água
- **Origem:** Arroz lavado: Preparo: Pia; Cenoura: Lavoura de cenoura; aldeias e zumbis; Pimentão em tiras: Preparo: Tábua de corte; Milho: Lavoura de milho (sementes de milho da grama); Balde de água: Encha um balde em qualquer água (o balde volta vazio)
- **Intermediários:** Arroz lavado, Pimentão em tiras
- **Equipamento:** Fogão + Panela · **Tempo:** 12.0 s (240 ticks)
- **Servir em:** Prato (volta sujo) × 2
- **Resultado:** 2× Arroz à grega, fome 8 e saturação 0.6 cada
- **Etapas:**
  1. Lave arroz lavado — Pia
  2. Corte pimentão em tiras — Tábua de corte
  3. Cozinhe arroz à grega — Fogão com panela

## Pratos vegetais

### 66. Salada verde

- **ID:** `culinaria:garden_salad` · **Categoria:** Pratos vegetais · **Rende:** 1
- **Ingredientes:** 1× Alface picada (P), 1× Tomate fatiado (P), 1× Cebola picada (P), 1× Limão, 1× Sal (P)
- **Origem:** Alface picada: Preparo: Tábua de corte; Tomate fatiado: Preparo: Tábua de corte; Cebola picada: Preparo: Tábua de corte; Limão: Colheita da limoeiro (clique direito na folha madura); Sal: Preparo: Fogão
- **Intermediários:** Alface picada, Tomate fatiado, Cebola picada, Sal
- **Equipamento:** Bancada de cozinha · **Tempo:** 4.0 s (80 ticks)
- **Servir em:** Tigela (volta) × 1
- **Resultado:** 1× Salada verde, fome 5 e saturação 0.6 cada
- **Etapas:**
  1. Corte alface picada — Tábua de corte
  2. Corte tomate fatiado — Tábua de corte
  3. Corte cebola picada — Tábua de corte
  4. Cozinhe sal — Fogão com panela
  5. Prepare salada verde — Bancada de cozinha

### 67. Salada caprese

- **ID:** `culinaria:caprese` · **Categoria:** Pratos vegetais · **Rende:** 1
- **Ingredientes:** 2× Tomate fatiado (P), 1× Queijo (P), 1× Manjericão
- **Origem:** Tomate fatiado: Preparo: Tábua de corte; Queijo: Preparo: Fogão; Manjericão: Lavoura de manjericão (sementes de manjericão da grama)
- **Intermediários:** Tomate fatiado, Queijo
- **Equipamento:** Bancada de cozinha · **Tempo:** 4.0 s (80 ticks)
- **Servir em:** Prato (volta sujo) × 1
- **Resultado:** 1× Salada caprese, fome 6 e saturação 0.6 cada
- **Etapas:**
  1. Corte tomate fatiado — Tábua de corte
  2. Cozinhe queijo — Fogão com panela
  3. Prepare salada caprese — Bancada de cozinha

### 68. Ratatouille

- **ID:** `culinaria:ratatouille` · **Categoria:** Pratos vegetais · **Rende:** 2
- **Ingredientes:** 2× Tomate fatiado (P), 1× Pimentão em tiras (P), 1× Cebola picada (P), 1× Alho picado (P), 1× Cenoura em rodelas (P)
- **Origem:** Tomate fatiado: Preparo: Tábua de corte; Pimentão em tiras: Preparo: Tábua de corte; Cebola picada: Preparo: Tábua de corte; Alho picado: Preparo: Tábua de corte; Cenoura em rodelas: Preparo: Tábua de corte
- **Intermediários:** Tomate fatiado, Pimentão em tiras, Cebola picada, Alho picado, Cenoura em rodelas
- **Equipamento:** Fogão + Panela · **Tempo:** 13.0 s (260 ticks)
- **Servir em:** Prato (volta sujo) × 2
- **Resultado:** 2× Ratatouille, fome 8 e saturação 0.7 cada
- **Etapas:**
  1. Corte tomate fatiado — Tábua de corte
  2. Corte pimentão em tiras — Tábua de corte
  3. Corte cebola picada — Tábua de corte
  4. Corte alho picado — Tábua de corte
  5. Corte cenoura em rodelas — Tábua de corte
  6. Cozinhe ratatouille — Fogão com panela

### 69. Legumes grelhados

- **ID:** `culinaria:grilled_vegetables` · **Categoria:** Pratos vegetais · **Rende:** 1
- **Ingredientes:** 1× Pimentão em tiras (P), 1× Cenoura em rodelas (P), 1× Cogumelo marrom, 1× Manteiga (P), 1× Sal (P)
- **Origem:** Pimentão em tiras: Preparo: Tábua de corte; Cenoura em rodelas: Preparo: Tábua de corte; Cogumelo marrom: Lugares escuros, pântanos e florestas escuras; Manteiga: Preparo: Bancada de cozinha; Sal: Preparo: Fogão
- **Intermediários:** Pimentão em tiras, Cenoura em rodelas, Manteiga, Sal
- **Equipamento:** Fogão + Frigideira · **Tempo:** 8.0 s (160 ticks)
- **Servir em:** Prato (volta sujo) × 1
- **Resultado:** 1× Legumes grelhados, fome 7 e saturação 0.6 cada
- **Etapas:**
  1. Corte pimentão em tiras — Tábua de corte
  2. Corte cenoura em rodelas — Tábua de corte
  3. Prepare creme de leite — Bancada de cozinha com batedor de arame
  4. Prepare manteiga — Bancada de cozinha com batedor de arame
  5. Cozinhe sal — Fogão com panela
  6. Cozinhe legumes grelhados — Fogão com frigideira

### 70. Cogumelos recheados

- **ID:** `culinaria:stuffed_mushrooms` · **Categoria:** Pratos vegetais · **Rende:** 1
- **Ingredientes:** 3× Cogumelo marrom, 1× Queijo (P), 1× Alho picado (P), 1× Farinha de rosca (P)
- **Origem:** Cogumelo marrom: Lugares escuros, pântanos e florestas escuras; Queijo: Preparo: Fogão; Alho picado: Preparo: Tábua de corte; Farinha de rosca: Preparo: Moedor manual
- **Intermediários:** Queijo, Alho picado, Farinha de rosca
- **Equipamento:** Forno + Forma de assar · **Tempo:** 11.0 s (220 ticks)
- **Servir em:** Prato (volta sujo) × 1
- **Resultado:** 1× Cogumelos recheados, fome 8 e saturação 0.7 cada
- **Etapas:**
  1. Cozinhe queijo — Fogão com panela
  2. Corte alho picado — Tábua de corte
  3. Moa farinha de rosca — Moedor manual
  4. Asse cogumelos recheados — Forno com forma de assar

### 71. Salada de maionese

- **ID:** `culinaria:potato_salad` · **Categoria:** Pratos vegetais · **Rende:** 2
- **Ingredientes:** 2× Batata, 1× Cenoura, 1× Maionese (P), 1× Salsinha, 1× Balde de água
- **Origem:** Batata: Lavoura de batata; aldeias e zumbis; Cenoura: Lavoura de cenoura; aldeias e zumbis; Maionese: Preparo: Bancada de cozinha; Salsinha: Lavoura de salsinha (sementes de salsinha da grama); Balde de água: Encha um balde em qualquer água (o balde volta vazio)
- **Intermediários:** Maionese
- **Equipamento:** Fogão + Panela · **Tempo:** 11.0 s (220 ticks)
- **Servir em:** Tigela (volta) × 2
- **Resultado:** 2× Salada de maionese, fome 8 e saturação 0.7 cada
- **Etapas:**
  1. Cozinhe sal — Fogão com panela
  2. Prepare maionese — Bancada de cozinha com batedor de arame
  3. Cozinhe salada de maionese — Fogão com panela

### 72. Abóbora assada com ervas

- **ID:** `culinaria:roasted_pumpkin` · **Categoria:** Pratos vegetais · **Rende:** 2
- **Ingredientes:** 1× Abóbora, 1× Manteiga (P), 1× Alho picado (P), 1× Salsinha
- **Origem:** Abóbora: Abóboras nascem na grama; plante as sementes; Manteiga: Preparo: Bancada de cozinha; Alho picado: Preparo: Tábua de corte; Salsinha: Lavoura de salsinha (sementes de salsinha da grama)
- **Intermediários:** Manteiga, Alho picado
- **Equipamento:** Forno + Forma de assar · **Tempo:** 13.0 s (260 ticks)
- **Servir em:** Prato (volta sujo) × 2
- **Resultado:** 2× Abóbora assada com ervas, fome 7 e saturação 0.6 cada
- **Etapas:**
  1. Prepare creme de leite — Bancada de cozinha com batedor de arame
  2. Prepare manteiga — Bancada de cozinha com batedor de arame
  3. Corte alho picado — Tábua de corte
  4. Asse abóbora assada com ervas — Forno com forma de assar

### 73. Bruschetta

- **ID:** `culinaria:bruschetta` · **Categoria:** Pratos vegetais · **Rende:** 2
- **Ingredientes:** 2× Fatia de pão (P), 1× Tomate fatiado (P), 1× Alho picado (P), 1× Manjericão
- **Origem:** Fatia de pão: Preparo: Tábua de corte; Tomate fatiado: Preparo: Tábua de corte; Alho picado: Preparo: Tábua de corte; Manjericão: Lavoura de manjericão (sementes de manjericão da grama)
- **Intermediários:** Fatia de pão, Tomate fatiado, Alho picado
- **Equipamento:** Forno + Forma de assar · **Tempo:** 7.0 s (140 ticks)
- **Servir em:** nada (comida de mão)
- **Resultado:** 2× Bruschetta, fome 5 e saturação 0.6 cada
- **Etapas:**
  1. Corte fatia de pão — Tábua de corte
  2. Corte tomate fatiado — Tábua de corte
  3. Corte alho picado — Tábua de corte
  4. Asse bruschetta — Forno com forma de assar

## Bolos e sobremesas

### 74. Bolo de chocolate

- **ID:** `culinaria:chocolate_cake` · **Categoria:** Bolos e sobremesas · **Rende:** 4
- **Ingredientes:** 1× Massa de bolo (P), 1× Cacau em pó (P)
- **Origem:** Massa de bolo: Preparo: Bancada de cozinha; Cacau em pó: Preparo: Moedor manual
- **Intermediários:** Massa de bolo, Cacau em pó
- **Equipamento:** Forno + Forma de assar · **Tempo:** 15.0 s (300 ticks)
- **Servir em:** nada (comida de mão)
- **Resultado:** 4× Bolo de chocolate, fome 4 e saturação 0.4 cada
- **Etapas:**
  1. Moa farinha de trigo — Moedor manual
  2. Prepare massa de bolo — Bancada de cozinha com batedor de arame
  3. Moa cacau em pó — Moedor manual
  4. Asse bolo de chocolate — Forno com forma de assar

### 75. Bolo de cenoura com chocolate

- **ID:** `culinaria:carrot_cake` · **Categoria:** Bolos e sobremesas · **Rende:** 4
- **Ingredientes:** 1× Massa de bolo (P), 2× Cenoura, 1× Barra de chocolate (P)
- **Origem:** Massa de bolo: Preparo: Bancada de cozinha; Cenoura: Lavoura de cenoura; aldeias e zumbis; Barra de chocolate: Preparo: Fogão
- **Intermediários:** Massa de bolo, Barra de chocolate
- **Equipamento:** Forno + Forma de assar · **Tempo:** 15.0 s (300 ticks)
- **Servir em:** nada (comida de mão)
- **Resultado:** 4× Bolo de cenoura com chocolate, fome 5 e saturação 0.4 cada
- **Etapas:**
  1. Moa farinha de trigo — Moedor manual
  2. Prepare massa de bolo — Bancada de cozinha com batedor de arame
  3. Moa cacau em pó — Moedor manual
  4. Prepare creme de leite — Bancada de cozinha com batedor de arame
  5. Prepare manteiga — Bancada de cozinha com batedor de arame
  6. Cozinhe barra de chocolate — Fogão com panela
  7. Asse bolo de cenoura com chocolate — Forno com forma de assar

### 76. Bolo de fubá

- **ID:** `culinaria:corn_cake` · **Categoria:** Bolos e sobremesas · **Rende:** 4
- **Ingredientes:** 2× Fubá (P), 1× Ovo, 1× Açúcar, 1× Balde de leite, 1× Manteiga (P)
- **Origem:** Fubá: Preparo: Moedor manual; Ovo: Galinhas botam ovos sozinhas; Açúcar: Moa cana-de-açúcar no moedor (ou faça na bancada de trabalho); Balde de leite: Ordenhe uma vaca ou cabra com um balde (o balde volta vazio); Manteiga: Preparo: Bancada de cozinha
- **Intermediários:** Fubá, Manteiga
- **Equipamento:** Forno + Forma de assar · **Tempo:** 15.0 s (300 ticks)
- **Servir em:** nada (comida de mão)
- **Resultado:** 4× Bolo de fubá, fome 4 e saturação 0.4 cada
- **Etapas:**
  1. Moa fubá — Moedor manual
  2. Prepare creme de leite — Bancada de cozinha com batedor de arame
  3. Prepare manteiga — Bancada de cozinha com batedor de arame
  4. Asse bolo de fubá — Forno com forma de assar

### 77. Bolo de morango

- **ID:** `culinaria:strawberry_cake` · **Categoria:** Bolos e sobremesas · **Rende:** 4
- **Ingredientes:** 1× Massa de bolo (P), 2× Morango, 1× Creme de leite (P)
- **Origem:** Massa de bolo: Preparo: Bancada de cozinha; Morango: Lavoura de morango (sementes de morango da grama); Creme de leite: Preparo: Bancada de cozinha
- **Intermediários:** Massa de bolo, Creme de leite
- **Equipamento:** Forno + Forma de assar · **Tempo:** 15.0 s (300 ticks)
- **Servir em:** nada (comida de mão)
- **Resultado:** 4× Bolo de morango, fome 5 e saturação 0.4 cada
- **Etapas:**
  1. Moa farinha de trigo — Moedor manual
  2. Prepare massa de bolo — Bancada de cozinha com batedor de arame
  3. Prepare creme de leite — Bancada de cozinha com batedor de arame
  4. Asse bolo de morango — Forno com forma de assar

### 78. Pudim de leite

- **ID:** `culinaria:pudim` · **Categoria:** Bolos e sobremesas · **Rende:** 2
- **Ingredientes:** 1× Leite condensado (P), 2× Ovo, 1× Balde de leite, 1× Açúcar
- **Origem:** Leite condensado: Preparo: Fogão; Ovo: Galinhas botam ovos sozinhas; Balde de leite: Ordenhe uma vaca ou cabra com um balde (o balde volta vazio); Açúcar: Moa cana-de-açúcar no moedor (ou faça na bancada de trabalho)
- **Intermediários:** Leite condensado
- **Equipamento:** Forno + Forma de assar · **Tempo:** 16.0 s (320 ticks)
- **Servir em:** Prato (volta sujo) × 2
- **Resultado:** 2× Pudim de leite, fome 6 e saturação 0.5 cada
- **Etapas:**
  1. Cozinhe leite condensado — Fogão com panela
  2. Asse pudim de leite — Forno com forma de assar

### 79. Mousse de limão

- **ID:** `culinaria:lemon_mousse` · **Categoria:** Bolos e sobremesas · **Rende:** 1
- **Ingredientes:** 1× Creme de mousse de limão (P)
- **Origem:** Creme de mousse de limão: Preparo: Bancada de cozinha
- **Intermediários:** Creme de mousse de limão
- **Equipamento:** Geladeira · **Tempo:** 60.0 s (1200 ticks)
- **Servir em:** nada (comida de mão)
- **Resultado:** 1× Mousse de limão, fome 5 e saturação 0.4 cada
- **Etapas:**
  1. Cozinhe leite condensado — Fogão com panela
  2. Prepare creme de leite — Bancada de cozinha com batedor de arame
  3. Prepare creme de mousse de limão — Bancada de cozinha com batedor de arame
  4. Gele mousse de limão — Geladeira

### 80. Sorvete de morango

- **ID:** `culinaria:strawberry_ice_cream` · **Categoria:** Bolos e sobremesas · **Rende:** 1
- **Ingredientes:** 1× Creme de sorvete de morango (P)
- **Origem:** Creme de sorvete de morango: Preparo: Bancada de cozinha
- **Intermediários:** Creme de sorvete de morango
- **Equipamento:** Geladeira · **Tempo:** 80.0 s (1600 ticks)
- **Servir em:** nada (comida de mão)
- **Resultado:** 1× Sorvete de morango, fome 4 e saturação 0.3 cada
- **Etapas:**
  1. Prepare creme de leite — Bancada de cozinha com batedor de arame
  2. Prepare creme de sorvete de morango — Bancada de cozinha com batedor de arame
  3. Gele sorvete de morango — Geladeira

### 81. Salada de frutas

- **ID:** `culinaria:fruit_salad` · **Categoria:** Bolos e sobremesas · **Rende:** 2
- **Ingredientes:** 1× Maçã fatiada (P), 1× Laranja, 1× Pêssego, 1× Morango, 1× Cereja
- **Origem:** Maçã fatiada: Preparo: Tábua de corte; Laranja: Colheita da laranjeira (clique direito na folha madura); Pêssego: Colheita da pessegueiro (clique direito na folha madura); Morango: Lavoura de morango (sementes de morango da grama); Cereja: Colheita da cerejeira frutífera (clique direito na folha madura)
- **Intermediários:** Maçã fatiada
- **Equipamento:** Bancada de cozinha · **Tempo:** 5.0 s (100 ticks)
- **Servir em:** Tigela (volta) × 2
- **Resultado:** 2× Salada de frutas, fome 7 e saturação 0.5 cada
- **Etapas:**
  1. Corte maçã fatiada — Tábua de corte
  2. Prepare salada de frutas — Bancada de cozinha

### 82. Arroz-doce

- **ID:** `culinaria:rice_pudding` · **Categoria:** Bolos e sobremesas · **Rende:** 2
- **Ingredientes:** 1× Arroz lavado (P), 1× Balde de leite, 1× Açúcar, 1× Leite condensado (P)
- **Origem:** Arroz lavado: Preparo: Pia; Balde de leite: Ordenhe uma vaca ou cabra com um balde (o balde volta vazio); Açúcar: Moa cana-de-açúcar no moedor (ou faça na bancada de trabalho); Leite condensado: Preparo: Fogão
- **Intermediários:** Arroz lavado, Leite condensado
- **Equipamento:** Fogão + Panela · **Tempo:** 13.0 s (260 ticks)
- **Servir em:** Tigela (volta) × 2
- **Resultado:** 2× Arroz-doce, fome 7 e saturação 0.5 cada
- **Etapas:**
  1. Lave arroz lavado — Pia
  2. Cozinhe leite condensado — Fogão com panela
  3. Cozinhe arroz-doce — Fogão com panela

## Tortas e doces

### 83. Torta de maçã

- **ID:** `culinaria:apple_pie` · **Categoria:** Tortas e doces · **Rende:** 1
- **Ingredientes:** 1× Massa de torta (P), 2× Maçã, 1× Açúcar, 1× Manteiga (P)
- **Origem:** Massa de torta: Preparo: Bancada de cozinha; Maçã: Colha a macieira do mod (clique direito) ou ache em folhas de carvalho; Açúcar: Moa cana-de-açúcar no moedor (ou faça na bancada de trabalho); Manteiga: Preparo: Bancada de cozinha
- **Intermediários:** Massa de torta, Manteiga
- **Equipamento:** Forno + Forma de assar · **Tempo:** 16.0 s (320 ticks)
- **Servir em:** nada (comida de mão)
- **Resultado:** 1× Torta de maçã, fome 10 e saturação 0.5 cada
- **Etapas:**
  1. Moa farinha de trigo — Moedor manual
  2. Prepare creme de leite — Bancada de cozinha com batedor de arame
  3. Prepare manteiga — Bancada de cozinha com batedor de arame
  4. Prepare massa de torta — Bancada de cozinha com rolo de massa
  5. Asse torta de maçã — Forno com forma de assar

### 84. Torta de limão

- **ID:** `culinaria:lemon_pie` · **Categoria:** Tortas e doces · **Rende:** 1
- **Ingredientes:** 1× Massa de torta (P), 2× Limão, 1× Leite condensado (P), 1× Ovo
- **Origem:** Massa de torta: Preparo: Bancada de cozinha; Limão: Colheita da limoeiro (clique direito na folha madura); Leite condensado: Preparo: Fogão; Ovo: Galinhas botam ovos sozinhas
- **Intermediários:** Massa de torta, Leite condensado
- **Equipamento:** Forno + Forma de assar · **Tempo:** 16.0 s (320 ticks)
- **Servir em:** nada (comida de mão)
- **Resultado:** 1× Torta de limão, fome 9 e saturação 0.5 cada
- **Etapas:**
  1. Moa farinha de trigo — Moedor manual
  2. Prepare creme de leite — Bancada de cozinha com batedor de arame
  3. Prepare manteiga — Bancada de cozinha com batedor de arame
  4. Prepare massa de torta — Bancada de cozinha com rolo de massa
  5. Cozinhe leite condensado — Fogão com panela
  6. Asse torta de limão — Forno com forma de assar

### 85. Empadão de frango

- **ID:** `culinaria:chicken_pot_pie` · **Categoria:** Tortas e doces · **Rende:** 1
- **Ingredientes:** 2× Massa de torta (P), 1× Frango em pedaços (P), 1× Milho, 1× Creme de leite (P)
- **Origem:** Massa de torta: Preparo: Bancada de cozinha; Frango em pedaços: Preparo: Tábua de corte; Milho: Lavoura de milho (sementes de milho da grama); Creme de leite: Preparo: Bancada de cozinha
- **Intermediários:** Massa de torta, Frango em pedaços, Creme de leite
- **Equipamento:** Forno + Forma de assar · **Tempo:** 17.0 s (340 ticks)
- **Servir em:** nada (comida de mão)
- **Resultado:** 1× Empadão de frango, fome 13 e saturação 0.8 cada
- **Etapas:**
  1. Moa farinha de trigo — Moedor manual
  2. Prepare creme de leite — Bancada de cozinha com batedor de arame
  3. Prepare manteiga — Bancada de cozinha com batedor de arame
  4. Prepare massa de torta — Bancada de cozinha com rolo de massa
  5. Corte frango em pedaços — Tábua de corte
  6. Asse empadão de frango — Forno com forma de assar

### 86. Torta de cereja

- **ID:** `culinaria:cherry_pie` · **Categoria:** Tortas e doces · **Rende:** 1
- **Ingredientes:** 1× Massa de torta (P), 3× Cereja, 1× Açúcar
- **Origem:** Massa de torta: Preparo: Bancada de cozinha; Cereja: Colheita da cerejeira frutífera (clique direito na folha madura); Açúcar: Moa cana-de-açúcar no moedor (ou faça na bancada de trabalho)
- **Intermediários:** Massa de torta
- **Equipamento:** Forno + Forma de assar · **Tempo:** 16.0 s (320 ticks)
- **Servir em:** nada (comida de mão)
- **Resultado:** 1× Torta de cereja, fome 9 e saturação 0.5 cada
- **Etapas:**
  1. Moa farinha de trigo — Moedor manual
  2. Prepare creme de leite — Bancada de cozinha com batedor de arame
  3. Prepare manteiga — Bancada de cozinha com batedor de arame
  4. Prepare massa de torta — Bancada de cozinha com rolo de massa
  5. Asse torta de cereja — Forno com forma de assar

### 87. Brigadeiro

- **ID:** `culinaria:brigadeiro` · **Categoria:** Tortas e doces · **Rende:** 6
- **Ingredientes:** 1× Leite condensado (P), 1× Cacau em pó (P), 1× Manteiga (P)
- **Origem:** Leite condensado: Preparo: Fogão; Cacau em pó: Preparo: Moedor manual; Manteiga: Preparo: Bancada de cozinha
- **Intermediários:** Leite condensado, Cacau em pó, Manteiga
- **Equipamento:** Fogão + Panela · **Tempo:** 10.0 s (200 ticks)
- **Servir em:** nada (comida de mão)
- **Resultado:** 6× Brigadeiro, fome 2 e saturação 0.3 cada
- **Etapas:**
  1. Cozinhe leite condensado — Fogão com panela
  2. Moa cacau em pó — Moedor manual
  3. Prepare creme de leite — Bancada de cozinha com batedor de arame
  4. Prepare manteiga — Bancada de cozinha com batedor de arame
  5. Cozinhe brigadeiro — Fogão com panela

### 88. Cookies com gotas de chocolate

- **ID:** `culinaria:chocolate_chip_cookies` · **Categoria:** Tortas e doces · **Rende:** 8
- **Ingredientes:** 1× Farinha de trigo (P), 1× Manteiga (P), 1× Açúcar, 1× Ovo, 1× Barra de chocolate (P)
- **Origem:** Farinha de trigo: Preparo: Moedor manual; Manteiga: Preparo: Bancada de cozinha; Açúcar: Moa cana-de-açúcar no moedor (ou faça na bancada de trabalho); Ovo: Galinhas botam ovos sozinhas; Barra de chocolate: Preparo: Fogão
- **Intermediários:** Farinha de trigo, Manteiga, Barra de chocolate
- **Equipamento:** Forno + Forma de assar · **Tempo:** 10.0 s (200 ticks)
- **Servir em:** nada (comida de mão)
- **Resultado:** 8× Cookies com gotas de chocolate, fome 2 e saturação 0.2 cada
- **Etapas:**
  1. Moa farinha de trigo — Moedor manual
  2. Prepare creme de leite — Bancada de cozinha com batedor de arame
  3. Prepare manteiga — Bancada de cozinha com batedor de arame
  4. Moa cacau em pó — Moedor manual
  5. Cozinhe barra de chocolate — Fogão com panela
  6. Asse cookies com gotas de chocolate — Forno com forma de assar

### 89. Churros de doce de leite

- **ID:** `culinaria:churros` · **Categoria:** Tortas e doces · **Rende:** 4
- **Ingredientes:** 2× Farinha de trigo (P), 1× Balde de água, 1× Manteiga (P), 1× Açúcar, 1× Doce de leite (P)
- **Origem:** Farinha de trigo: Preparo: Moedor manual; Balde de água: Encha um balde em qualquer água (o balde volta vazio); Manteiga: Preparo: Bancada de cozinha; Açúcar: Moa cana-de-açúcar no moedor (ou faça na bancada de trabalho); Doce de leite: Preparo: Fogão
- **Intermediários:** Farinha de trigo, Manteiga, Doce de leite
- **Equipamento:** Fogão + Frigideira · **Tempo:** 10.0 s (200 ticks)
- **Servir em:** nada (comida de mão)
- **Resultado:** 4× Churros de doce de leite, fome 4 e saturação 0.4 cada
- **Etapas:**
  1. Moa farinha de trigo — Moedor manual
  2. Prepare creme de leite — Bancada de cozinha com batedor de arame
  3. Prepare manteiga — Bancada de cozinha com batedor de arame
  4. Cozinhe leite condensado — Fogão com panela
  5. Cozinhe doce de leite — Fogão com panela
  6. Cozinhe churros de doce de leite — Fogão com frigideira

### 90. Rabanada

- **ID:** `culinaria:rabanada` · **Categoria:** Tortas e doces · **Rende:** 2
- **Ingredientes:** 2× Fatia de pão (P), 1× Ovo, 1× Balde de leite, 1× Açúcar
- **Origem:** Fatia de pão: Preparo: Tábua de corte; Ovo: Galinhas botam ovos sozinhas; Balde de leite: Ordenhe uma vaca ou cabra com um balde (o balde volta vazio); Açúcar: Moa cana-de-açúcar no moedor (ou faça na bancada de trabalho)
- **Intermediários:** Fatia de pão
- **Equipamento:** Fogão + Frigideira · **Tempo:** 8.0 s (160 ticks)
- **Servir em:** nada (comida de mão)
- **Resultado:** 2× Rabanada, fome 5 e saturação 0.5 cada
- **Etapas:**
  1. Corte fatia de pão — Tábua de corte
  2. Cozinhe rabanada — Fogão com frigideira

### 91. Pêssego em calda

- **ID:** `culinaria:peach_in_syrup` · **Categoria:** Tortas e doces · **Rende:** 2
- **Ingredientes:** 3× Pêssego, 2× Açúcar, 1× Balde de água
- **Origem:** Pêssego: Colheita da pessegueiro (clique direito na folha madura); Açúcar: Moa cana-de-açúcar no moedor (ou faça na bancada de trabalho); Balde de água: Encha um balde em qualquer água (o balde volta vazio)
- **Intermediários:** nenhum
- **Equipamento:** Fogão + Panela · **Tempo:** 12.0 s (240 ticks)
- **Servir em:** Tigela (volta) × 2
- **Resultado:** 2× Pêssego em calda, fome 6 e saturação 0.4 cada
- **Etapas:**
  1. Cozinhe pêssego em calda — Fogão com panela

## Bebidas

### 92. Suco de laranja

- **ID:** `culinaria:orange_juice` · **Categoria:** Bebidas · **Rende:** 1
- **Ingredientes:** 3× Laranja
- **Origem:** Laranja: Colheita da laranjeira (clique direito na folha madura)
- **Intermediários:** nenhum
- **Equipamento:** Liquidificador · **Tempo:** 5.0 s (100 ticks)
- **Servir em:** Frasco de vidro (volta) × 1
- **Resultado:** 1× Suco de laranja, fome 4 e saturação 0.4 cada (bebida)
- **Etapas:**
  1. Bata suco de laranja — Liquidificador

### 93. Limonada

- **ID:** `culinaria:lemonade` · **Categoria:** Bebidas · **Rende:** 2
- **Ingredientes:** 2× Limão, 1× Açúcar, 1× Balde de água
- **Origem:** Limão: Colheita da limoeiro (clique direito na folha madura); Açúcar: Moa cana-de-açúcar no moedor (ou faça na bancada de trabalho); Balde de água: Encha um balde em qualquer água (o balde volta vazio)
- **Intermediários:** nenhum
- **Equipamento:** Liquidificador · **Tempo:** 5.0 s (100 ticks)
- **Servir em:** Frasco de vidro (volta) × 2
- **Resultado:** 2× Limonada, fome 3 e saturação 0.3 cada (bebida)
- **Etapas:**
  1. Bata limonada — Liquidificador

### 94. Milk-shake de morango

- **ID:** `culinaria:strawberry_milkshake` · **Categoria:** Bebidas · **Rende:** 2
- **Ingredientes:** 2× Morango, 1× Balde de leite, 1× Açúcar
- **Origem:** Morango: Lavoura de morango (sementes de morango da grama); Balde de leite: Ordenhe uma vaca ou cabra com um balde (o balde volta vazio); Açúcar: Moa cana-de-açúcar no moedor (ou faça na bancada de trabalho)
- **Intermediários:** nenhum
- **Equipamento:** Liquidificador · **Tempo:** 6.0 s (120 ticks)
- **Servir em:** Frasco de vidro (volta) × 2
- **Resultado:** 2× Milk-shake de morango, fome 5 e saturação 0.4 cada (bebida)
- **Etapas:**
  1. Bata milk-shake de morango — Liquidificador

### 95. Vitamina de frutas

- **ID:** `culinaria:fruit_smoothie` · **Categoria:** Bebidas · **Rende:** 2
- **Ingredientes:** 1× Maçã, 1× Pêssego, 1× Balde de leite, 1× Frasco de mel
- **Origem:** Maçã: Colha a macieira do mod (clique direito) ou ache em folhas de carvalho; Pêssego: Colheita da pessegueiro (clique direito na folha madura); Balde de leite: Ordenhe uma vaca ou cabra com um balde (o balde volta vazio); Frasco de mel: Use um frasco de vidro numa colmeia cheia (o frasco volta)
- **Intermediários:** nenhum
- **Equipamento:** Liquidificador · **Tempo:** 6.0 s (120 ticks)
- **Servir em:** Frasco de vidro (volta) × 2
- **Resultado:** 2× Vitamina de frutas, fome 6 e saturação 0.5 cada (bebida)
- **Etapas:**
  1. Bata vitamina de frutas — Liquidificador

### 96. Caldo de cana com limão

- **ID:** `culinaria:sugarcane_juice` · **Categoria:** Bebidas · **Rende:** 1
- **Ingredientes:** 3× Cana-de-açúcar, 1× Limão
- **Origem:** Cana-de-açúcar: Cresce na beira da água; plante para multiplicar; Limão: Colheita da limoeiro (clique direito na folha madura)
- **Intermediários:** nenhum
- **Equipamento:** Liquidificador · **Tempo:** 5.0 s (100 ticks)
- **Servir em:** Frasco de vidro (volta) × 1
- **Resultado:** 1× Caldo de cana com limão, fome 4 e saturação 0.3 cada (bebida)
- **Etapas:**
  1. Bata caldo de cana com limão — Liquidificador

### 97. Café coado

- **ID:** `culinaria:coffee` · **Categoria:** Bebidas · **Rende:** 3
- **Ingredientes:** 1× Café moído (P), 1× Balde de água, 1× Açúcar
- **Origem:** Café moído: Preparo: Moedor manual; Balde de água: Encha um balde em qualquer água (o balde volta vazio); Açúcar: Moa cana-de-açúcar no moedor (ou faça na bancada de trabalho)
- **Intermediários:** Café moído
- **Equipamento:** Fogão + Panela · **Tempo:** 8.0 s (160 ticks)
- **Servir em:** Xícara (volta suja) × 3
- **Resultado:** 3× Café coado, fome 2 e saturação 0.4 cada (bebida)
- **Etapas:**
  1. Asse café torrado — Forno com forma de assar
  2. Moa café moído — Moedor manual
  3. Cozinhe café coado — Fogão com panela

### 98. Café com leite

- **ID:** `culinaria:cafe_com_leite` · **Categoria:** Bebidas · **Rende:** 3
- **Ingredientes:** 1× Café moído (P), 1× Balde de leite, 1× Açúcar
- **Origem:** Café moído: Preparo: Moedor manual; Balde de leite: Ordenhe uma vaca ou cabra com um balde (o balde volta vazio); Açúcar: Moa cana-de-açúcar no moedor (ou faça na bancada de trabalho)
- **Intermediários:** Café moído
- **Equipamento:** Fogão + Panela · **Tempo:** 8.0 s (160 ticks)
- **Servir em:** Xícara (volta suja) × 3
- **Resultado:** 3× Café com leite, fome 4 e saturação 0.5 cada (bebida)
- **Etapas:**
  1. Asse café torrado — Forno com forma de assar
  2. Moa café moído — Moedor manual
  3. Cozinhe café com leite — Fogão com panela

### 99. Chocolate quente

- **ID:** `culinaria:hot_chocolate` · **Categoria:** Bebidas · **Rende:** 3
- **Ingredientes:** 1× Barra de chocolate (P), 1× Balde de leite, 1× Açúcar
- **Origem:** Barra de chocolate: Preparo: Fogão; Balde de leite: Ordenhe uma vaca ou cabra com um balde (o balde volta vazio); Açúcar: Moa cana-de-açúcar no moedor (ou faça na bancada de trabalho)
- **Intermediários:** Barra de chocolate
- **Equipamento:** Fogão + Panela · **Tempo:** 9.0 s (180 ticks)
- **Servir em:** Xícara (volta suja) × 3
- **Resultado:** 3× Chocolate quente, fome 5 e saturação 0.5 cada (bebida)
- **Etapas:**
  1. Moa cacau em pó — Moedor manual
  2. Prepare creme de leite — Bancada de cozinha com batedor de arame
  3. Prepare manteiga — Bancada de cozinha com batedor de arame
  4. Cozinhe barra de chocolate — Fogão com panela
  5. Cozinhe chocolate quente — Fogão com panela

### 100. Chá de limão com mel

- **ID:** `culinaria:lemon_honey_tea` · **Categoria:** Bebidas · **Rende:** 3
- **Ingredientes:** 1× Limão, 1× Frasco de mel, 1× Balde de água
- **Origem:** Limão: Colheita da limoeiro (clique direito na folha madura); Frasco de mel: Use um frasco de vidro numa colmeia cheia (o frasco volta); Balde de água: Encha um balde em qualquer água (o balde volta vazio)
- **Intermediários:** nenhum
- **Equipamento:** Fogão + Panela · **Tempo:** 8.0 s (160 ticks)
- **Servir em:** Xícara (volta suja) × 3
- **Resultado:** 3× Chá de limão com mel, fome 3 e saturação 0.4 cada (bebida)
- **Etapas:**
  1. Cozinhe chá de limão com mel — Fogão com panela

## Preparos de ingredientes

| Resultado | Estação e utensílio | Ingredientes | Rende | Tempo |
|---|---|---|---|---|
| Tomate fatiado (`culinaria:sliced_tomato`) | Tábua de corte | 1× Tomate | 2 | 2 cortes |
| Cebola picada (`culinaria:chopped_onion`) | Tábua de corte | 1× Cebola | 2 | 3 cortes |
| Alho picado (`culinaria:minced_garlic`) | Tábua de corte | 1× Alho | 3 | 3 cortes |
| Alface picada (`culinaria:chopped_lettuce`) | Tábua de corte | 1× Alface | 2 | 2 cortes |
| Pimentão em tiras (`culinaria:pepper_strips`) | Tábua de corte | 1× Pimentão | 2 | 2 cortes |
| Batata em palitos (`culinaria:potato_sticks`) | Tábua de corte | 1× Batata | 2 | 3 cortes |
| Cenoura em rodelas (`culinaria:carrot_slices`) | Tábua de corte | 1× Cenoura | 2 | 2 cortes |
| Carne em cubos (`culinaria:beef_cubes`) | Tábua de corte | 1× Carne bovina crua | 2 | 3 cortes |
| Frango em pedaços (`culinaria:chicken_pieces`) | Tábua de corte | 1× Frango cru | 2 | 3 cortes |
| Filé de bacalhau (`culinaria:cod_fillet`) | Tábua de corte | 1× Bacalhau cru | 2 | 2 cortes |
| Filé de salmão (`culinaria:salmon_fillet`) | Tábua de corte | 1× Salmão cru | 2 | 2 cortes |
| Bacon cru (`culinaria:raw_bacon`) | Tábua de corte | 1× Costeleta de porco crua | 2 | 3 cortes |
| Presunto (`culinaria:ham`) | Tábua de corte | 1× Costeleta de porco assada | 3 | 3 cortes |
| Fatia de pão (`culinaria:bread_slice`) | Tábua de corte | 1× Pão | 4 | 2 cortes |
| Mandioca descascada (`culinaria:peeled_cassava`) | Tábua de corte | 1× Mandioca | 1 | 3 cortes |
| Maçã fatiada (`culinaria:apple_slices`) | Tábua de corte | 1× Maçã | 2 | 2 cortes |
| Arroz lavado (`culinaria:washed_rice`) | Pia | 1× Arroz | 1 | 1 lavagem |
| Feijão de molho (`culinaria:soaked_beans`) | Pia | 1× Feijão | 1 | 1 lavagem |
| Polvilho (`culinaria:tapioca_starch`) | Pia | 1× Farinha de mandioca | 1 | 1 lavagem |
| Prato (`culinaria:plate`) | Pia | 1× Prato sujo | 1 | 1 lavagem |
| Xícara (`culinaria:cup`) | Pia | 1× Xícara suja | 1 | 1 lavagem |
| Farinha de trigo (`culinaria:flour`) | Moedor manual | 1× Trigo | 1 | 4 voltas |
| Fubá (`culinaria:cornmeal`) | Moedor manual | 1× Milho | 1 | 4 voltas |
| Farinha de mandioca (`culinaria:cassava_flour`) | Moedor manual | 1× Mandioca descascada | 2 | 5 voltas |
| Cacau em pó (`culinaria:cocoa_powder`) | Moedor manual | 1× Sementes de cacau | 1 | 4 voltas |
| Café moído (`culinaria:ground_coffee`) | Moedor manual | 1× Café torrado | 1 | 4 voltas |
| Farinha de rosca (`culinaria:breadcrumbs`) | Moedor manual | 1× Pão | 2 | 3 voltas |
| Carne moída (`culinaria:minced_beef`) | Moedor manual | 1× Carne bovina crua | 2 | 5 voltas |
| Linguiça crua (`culinaria:raw_sausage`) | Moedor manual | 1× Costeleta de porco crua | 2 | 5 voltas |
| Açúcar (`minecraft:sugar`) | Moedor manual | 1× Cana-de-açúcar | 2 | 3 voltas |
| Massa de pão (`culinaria:bread_dough`) | Bancada de cozinha | 3× Farinha de trigo, 1× Sal, 1× Balde de água | 3 | 8.0 s (160 ticks) |
| Massa de macarrão (`culinaria:pasta_dough`) | Bancada de cozinha | 2× Farinha de trigo, 1× Ovo | 2 | 7.0 s (140 ticks) |
| Macarrão cru (`culinaria:raw_pasta`) | Bancada de cozinha + Rolo de massa | 1× Massa de macarrão | 2 | 5.0 s (100 ticks) |
| Disco de pizza (`culinaria:pizza_base`) | Bancada de cozinha + Rolo de massa | 1× Massa de pão | 1 | 5.0 s (100 ticks) |
| Massa de torta (`culinaria:pie_crust`) | Bancada de cozinha + Rolo de massa | 2× Farinha de trigo, 1× Manteiga, 1× Ovo | 2 | 7.0 s (140 ticks) |
| Massa de bolo (`culinaria:cake_batter`) | Bancada de cozinha + Batedor de arame | 2× Farinha de trigo, 2× Açúcar, 2× Ovo, 1× Balde de leite | 1 | 8.0 s (160 ticks) |
| Massa de panqueca (`culinaria:pancake_batter`) | Bancada de cozinha + Batedor de arame | 1× Farinha de trigo, 1× Ovo, 1× Balde de leite | 2 | 6.0 s (120 ticks) |
| Creme de leite (`culinaria:cream`) | Bancada de cozinha + Batedor de arame | 1× Balde de leite | 2 | 6.0 s (120 ticks) |
| Manteiga (`culinaria:butter`) | Bancada de cozinha + Batedor de arame | 2× Creme de leite | 1 | 8.0 s (160 ticks) |
| Maionese (`culinaria:mayonnaise`) | Bancada de cozinha + Batedor de arame | 2× Ovo, 1× Limão, 1× Sal | 2 | 6.0 s (120 ticks) |
| Creme de mousse de limão (`culinaria:lemon_mousse_mix`) | Bancada de cozinha + Batedor de arame | 1× Leite condensado, 2× Limão, 1× Creme de leite | 2 | 7.0 s (140 ticks) |
| Creme de sorvete de morango (`culinaria:ice_cream_mix`) | Bancada de cozinha + Batedor de arame | 1× Creme de leite, 2× Morango, 1× Açúcar | 2 | 7.0 s (140 ticks) |
| Sal (`culinaria:salt`) | Fogão + Panela | 1× Balde de água | 3 | 10.0 s (200 ticks) |
| Queijo (`culinaria:cheese`) | Fogão + Panela | 1× Balde de leite, 1× Limão | 2 | 12.0 s (240 ticks) |
| Molho de tomate (`culinaria:tomato_sauce`) | Fogão + Panela | 3× Tomate, 1× Cebola picada, 1× Alho picado, 1× Sal | 2 | 10.0 s (200 ticks) |
| Molho à bolonhesa (`culinaria:bolognese_sauce`) | Fogão + Panela | 1× Carne moída, 1× Molho de tomate, 1× Cebola picada | 2 | 10.0 s (200 ticks) |
| Macarrão cozido (`culinaria:cooked_pasta`) | Fogão + Panela | 2× Macarrão cru, 1× Balde de água, 1× Sal | 2 | 8.0 s (160 ticks) |
| Arroz cozido (`culinaria:cooked_rice`) | Fogão + Panela | 2× Arroz lavado, 1× Balde de água, 1× Sal | 3 | 10.0 s (200 ticks) |
| Feijão cozido (`culinaria:cooked_beans`) | Fogão + Panela | 2× Feijão de molho, 1× Alho picado, 1× Cebola picada, 1× Sal, 1× Balde de água | 3 | 13.0 s (260 ticks) |
| Leite condensado (`culinaria:condensed_milk`) | Fogão + Panela | 1× Balde de leite, 2× Açúcar | 2 | 12.0 s (240 ticks) |
| Doce de leite (`culinaria:doce_de_leite`) | Fogão + Panela | 2× Leite condensado | 1 | 15.0 s (300 ticks) |
| Barra de chocolate (`culinaria:chocolate`) | Fogão + Panela | 2× Cacau em pó, 1× Açúcar, 1× Manteiga | 3 | 10.0 s (200 ticks) |
| Hambúrguer de carne (`culinaria:beef_patty`) | Fogão + Frigideira | 1× Carne moída, 1× Sal | 1 | 7.0 s (140 ticks) |
| Café torrado (`culinaria:roasted_coffee`) | Forno + Forma de assar | 1× Grãos de café | 1 | 8.0 s (160 ticks) |
