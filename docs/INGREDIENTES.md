# Catálogo de ingredientes e fontes

Gerado por `node tools/gen/docs.mjs` a partir de `tools/gen/content.mjs`. O livro de receitas mostra o mesmo
"onde conseguir" (chaves `culinaria.source.*` das traduções). Todo ingrediente do mod é usado por pelo menos uma receita (o gerador e o teste
`CatalogDataTest` falham se não for), e toda receita tem cadeia a partir destas fontes.

## Lavouras do mod

Sementes: quebrar grama baixa, grama alta ou samambaia (10% por padrão, configurável); café e mandioca pesam 1/3 das outras. A lavoura madura (idade 7) dá 1–3 do produto e devolve sementes, como o trigo.

| Produto | Semente | Comestível cru | Usado em |
|---|---|---|---|
| Tomate (`culinaria:tomato`) | Sementes de tomate | sim (2) | 3 receitas |
| Cebola (`culinaria:onion`) | Sementes de cebola | não | 1 receitas |
| Alho (`culinaria:garlic`) | Dente de alho para plantio | não | 1 receitas |
| Alface (`culinaria:lettuce`) | Sementes de alface | sim (1) | 1 receitas |
| Milho (`culinaria:corn`) | Sementes de milho | não | 7 receitas |
| Arroz (`culinaria:rice`) | Sementes de arroz | não | 1 receitas |
| Feijão (`culinaria:beans`) | Sementes de feijão | não | 1 receitas |
| Pimentão (`culinaria:bell_pepper`) | Sementes de pimentão | sim (2) | 2 receitas |
| Manjericão (`culinaria:basil`) | Sementes de manjericão | não | 5 receitas |
| Salsinha (`culinaria:parsley`) | Sementes de salsinha | não | 9 receitas |
| Morango (`culinaria:strawberry`) | Sementes de morango | sim (2) | 4 receitas |
| Grãos de café (`culinaria:coffee_beans`) | Muda de café (rara) | não | 1 receitas |
| Mandioca (`culinaria:cassava`) | Rama de mandioca (rara) | não | 1 receitas |
| Couve (`culinaria:kale`) | Sementes de couve | sim (1) | 2 receitas |

## Árvores frutíferas

Nascem no mundo (biomas abaixo, cerca de 1 a cada 14 chunks em cada bioma) e também pela muda: a muda cai das folhas (5%) ou sai da bancada de trabalho com muda de carvalho + a fruta. Uma em cada 4 folhas da árvore dá fruto: flor → fruto verde → maduro; clique direito na madura colhe 1–2 sem quebrar e ela recomeça.

| Árvore | Fruta | Biomas | Usado em |
|---|---|---|---|
| Macieira | Maçã (`minecraft:apple`) | #minecraft:is_forest, minecraft:plains, minecraft:meadow | 3 receitas |
| Laranjeira | Laranja (`culinaria:orange`) | #minecraft:is_savanna, minecraft:plains, minecraft:sunflower_plains | 3 receitas |
| Limoeiro | Limão (`culinaria:lemon`) | #minecraft:is_jungle, #minecraft:is_savanna, minecraft:plains | 10 receitas |
| Pessegueiro | Pêssego (`culinaria:peach`) | minecraft:flower_forest, minecraft:meadow, minecraft:plains | 3 receitas |
| Cerejeira frutífera | Cereja (`culinaria:cherry`) | #minecraft:is_forest, minecraft:cherry_grove | 2 receitas |

## Itens do Minecraft reaproveitados

| Item | Onde conseguir | Usado em |
|---|---|---|
| Balde de leite (`minecraft:milk_bucket`) | Ordenhe uma vaca ou cabra com um balde (o balde volta vazio) | 18 receitas |
| Balde de água (`minecraft:water_bucket`) | Encha um balde em qualquer água (o balde volta vazio) | 22 receitas |
| Ovo (`minecraft:egg`) | Galinhas botam ovos sozinhas | 19 receitas |
| Carne bovina crua (`minecraft:beef`) | Vacas | 4 receitas |
| Costeleta de porco crua (`minecraft:porkchop`) | Porcos | 4 receitas |
| Costeleta de porco assada (`minecraft:cooked_porkchop`) | Asse uma costeleta de porco na fornalha comum | 1 receitas |
| Frango cru (`minecraft:chicken`) | Galinhas | 2 receitas |
| Carne de carneiro crua (`minecraft:mutton`) | Ovelhas | 1 receitas |
| Coelho cru (`minecraft:rabbit`) | Coelhos | 1 receitas |
| Bacalhau cru (`minecraft:cod`) | Pesque com vara ou mate bacalhaus no oceano | 1 receitas |
| Salmão cru (`minecraft:salmon`) | Pesque com vara ou pegue salmões em rios e oceanos frios | 1 receitas |
| Frasco de mel (`minecraft:honey_bottle`) | Use um frasco de vidro numa colmeia cheia (o frasco volta) | 4 receitas |
| Açúcar (`minecraft:sugar`) | Moa cana-de-açúcar no moedor (ou faça na bancada de trabalho) | 18 receitas |
| Cana-de-açúcar (`minecraft:sugar_cane`) | Cresce na beira da água; plante para multiplicar | 2 receitas |
| Sementes de cacau (`minecraft:cocoa_beans`) | Cacau nos troncos de árvore da selva | 1 receitas |
| Cogumelo marrom (`minecraft:brown_mushroom`) | Lugares escuros, pântanos e florestas escuras | 4 receitas |
| Trigo (`minecraft:wheat`) | Lavoura de trigo (sementes da grama) | 1 receitas |
| Pão (`minecraft:bread`) | Bancada de trabalho: 3 trigos | 2 receitas |
| Cenoura (`minecraft:carrot`) | Lavoura de cenoura; aldeias e zumbis | 8 receitas |
| Batata (`minecraft:potato`) | Lavoura de batata; aldeias e zumbis | 11 receitas |
| Beterraba (`minecraft:beetroot`) | Lavoura de beterraba (sementes em aldeias e baús) | 1 receitas |
| Abóbora (`minecraft:pumpkin`) | Abóboras nascem na grama; plante as sementes | 2 receitas |
| Maçã (`minecraft:apple`) | Colha a macieira do mod (clique direito) ou ache em folhas de carvalho | 3 receitas |
| Alga seca (`minecraft:dried_kelp`) | Alga do oceano seca na fornalha | 1 receitas |
| Graveto (`minecraft:stick`) | Bancada de trabalho: 2 tábuas | 1 receitas |

## Ingredientes preparados

| Item | Como fazer | Usado em |
|---|---|---|
| Tomate fatiado (`culinaria:sliced_tomato`) | Tábua de corte: 1× Tomate → 2 | 9 receitas |
| Cebola picada (`culinaria:chopped_onion`) | Tábua de corte: 1× Cebola → 2 | 23 receitas |
| Alho picado (`culinaria:minced_garlic`) | Tábua de corte: 1× Alho → 3 | 18 receitas |
| Alface picada (`culinaria:chopped_lettuce`) | Tábua de corte: 1× Alface → 2 | 4 receitas |
| Pimentão em tiras (`culinaria:pepper_strips`) | Tábua de corte: 1× Pimentão → 2 | 7 receitas |
| Batata em palitos (`culinaria:potato_sticks`) | Tábua de corte: 1× Batata → 2 | 2 receitas |
| Cenoura em rodelas (`culinaria:carrot_slices`) | Tábua de corte: 1× Cenoura → 2 | 2 receitas |
| Carne em cubos (`culinaria:beef_cubes`) | Tábua de corte: 1× Carne bovina crua → 2 | 2 receitas |
| Frango em pedaços (`culinaria:chicken_pieces`) | Tábua de corte: 1× Frango cru → 2 | 5 receitas |
| Filé de bacalhau (`culinaria:cod_fillet`) | Tábua de corte: 1× Bacalhau cru → 2 | 5 receitas |
| Filé de salmão (`culinaria:salmon_fillet`) | Tábua de corte: 1× Salmão cru → 2 | 4 receitas |
| Bacon cru (`culinaria:raw_bacon`) | Tábua de corte: 1× Costeleta de porco crua → 2 | 6 receitas |
| Presunto (`culinaria:ham`) | Tábua de corte: 1× Costeleta de porco assada → 3 | 1 receitas |
| Fatia de pão (`culinaria:bread_slice`) | Tábua de corte: 1× Pão → 4 | 4 receitas |
| Mandioca descascada (`culinaria:peeled_cassava`) | Tábua de corte: 1× Mandioca → 1 | 2 receitas |
| Maçã fatiada (`culinaria:apple_slices`) | Tábua de corte: 1× Maçã → 2 | 1 receitas |
| Arroz lavado (`culinaria:washed_rice`) | Pia: 1× Arroz → 1 | 6 receitas |
| Feijão de molho (`culinaria:soaked_beans`) | Pia: 1× Feijão → 1 | 2 receitas |
| Polvilho (`culinaria:tapioca_starch`) | Pia: 1× Farinha de mandioca → 1 | 2 receitas |
| Farinha de trigo (`culinaria:flour`) | Moedor manual: 1× Trigo → 1 | 11 receitas |
| Fubá (`culinaria:cornmeal`) | Moedor manual: 1× Milho → 1 | 3 receitas |
| Farinha de mandioca (`culinaria:cassava_flour`) | Moedor manual: 1× Mandioca descascada → 2 | 2 receitas |
| Cacau em pó (`culinaria:cocoa_powder`) | Moedor manual: 1× Sementes de cacau → 1 | 3 receitas |
| Café moído (`culinaria:ground_coffee`) | Moedor manual: 1× Café torrado → 1 | 2 receitas |
| Farinha de rosca (`culinaria:breadcrumbs`) | Moedor manual: 1× Pão → 2 | 2 receitas |
| Carne moída (`culinaria:minced_beef`) | Moedor manual: 1× Carne bovina crua → 2 | 5 receitas |
| Linguiça crua (`culinaria:raw_sausage`) | Moedor manual: 1× Costeleta de porco crua → 2 | 3 receitas |
| Massa de pão (`culinaria:bread_dough`) | Bancada de cozinha: 3× Farinha de trigo, 1× Sal, 1× Balde de água → 3 | 2 receitas |
| Massa de macarrão (`culinaria:pasta_dough`) | Bancada de cozinha: 2× Farinha de trigo, 1× Ovo → 2 | 1 receitas |
| Macarrão cru (`culinaria:raw_pasta`) | Bancada de cozinha + Rolo de massa: 1× Massa de macarrão → 2 | 2 receitas |
| Disco de pizza (`culinaria:pizza_base`) | Bancada de cozinha + Rolo de massa: 1× Massa de pão → 1 | 2 receitas |
| Massa de torta (`culinaria:pie_crust`) | Bancada de cozinha + Rolo de massa: 2× Farinha de trigo, 1× Manteiga, 1× Ovo → 2 | 4 receitas |
| Massa de bolo (`culinaria:cake_batter`) | Bancada de cozinha + Batedor de arame: 2× Farinha de trigo, 2× Açúcar, 2× Ovo, 1× Balde de leite → 1 | 3 receitas |
| Massa de panqueca (`culinaria:pancake_batter`) | Bancada de cozinha + Batedor de arame: 1× Farinha de trigo, 1× Ovo, 1× Balde de leite → 2 | 1 receitas |
| Creme de leite (`culinaria:cream`) | Bancada de cozinha + Batedor de arame: 1× Balde de leite → 2 | 7 receitas |
| Manteiga (`culinaria:butter`) | Bancada de cozinha + Batedor de arame: 2× Creme de leite → 1 | 32 receitas |
| Maionese (`culinaria:mayonnaise`) | Bancada de cozinha + Batedor de arame: 2× Ovo, 1× Limão, 1× Sal → 2 | 1 receitas |
| Creme de mousse de limão (`culinaria:lemon_mousse_mix`) | Bancada de cozinha + Batedor de arame: 1× Leite condensado, 2× Limão, 1× Creme de leite → 2 | 1 receitas |
| Creme de sorvete de morango (`culinaria:ice_cream_mix`) | Bancada de cozinha + Batedor de arame: 1× Creme de leite, 2× Morango, 1× Açúcar → 2 | 1 receitas |
| Sal (`culinaria:salt`) | Fogão + Panela: 1× Balde de água → 3 | 21 receitas |
| Queijo (`culinaria:cheese`) | Fogão + Panela: 1× Balde de leite, 1× Limão → 2 | 17 receitas |
| Molho de tomate (`culinaria:tomato_sauce`) | Fogão + Panela: 3× Tomate, 1× Cebola picada, 1× Alho picado, 1× Sal → 2 | 8 receitas |
| Molho à bolonhesa (`culinaria:bolognese_sauce`) | Fogão + Panela: 1× Carne moída, 1× Molho de tomate, 1× Cebola picada → 2 | 2 receitas |
| Macarrão cozido (`culinaria:cooked_pasta`) | Fogão + Panela: 2× Macarrão cru, 1× Balde de água, 1× Sal → 2 | 3 receitas |
| Arroz cozido (`culinaria:cooked_rice`) | Fogão + Panela: 2× Arroz lavado, 1× Balde de água, 1× Sal → 3 | 6 receitas |
| Feijão cozido (`culinaria:cooked_beans`) | Fogão + Panela: 2× Feijão de molho, 1× Alho picado, 1× Cebola picada, 1× Sal, 1× Balde de água → 3 | 4 receitas |
| Leite condensado (`culinaria:condensed_milk`) | Fogão + Panela: 1× Balde de leite, 2× Açúcar → 2 | 6 receitas |
| Doce de leite (`culinaria:doce_de_leite`) | Fogão + Panela: 2× Leite condensado → 1 | 1 receitas |
| Barra de chocolate (`culinaria:chocolate`) | Fogão + Panela: 2× Cacau em pó, 1× Açúcar, 1× Manteiga → 3 | 3 receitas |
| Hambúrguer de carne (`culinaria:beef_patty`) | Fogão + Frigideira: 1× Carne moída, 1× Sal → 1 | 1 receitas |
| Café torrado (`culinaria:roasted_coffee`) | Forno + Forma de assar: 1× Grãos de café → 1 | 1 receitas |

## Utensílios e louça

| Item | Para que serve |
|---|---|
| Faca de cozinha (`culinaria:kitchen_knife`) | Cortar na tábua (gasta 1 de durabilidade por corte) |
| Panela (`culinaria:cooking_pot`) | Slot de utensílio do fogão: ferver, cozidos, caldos, doces de panela, café |
| Frigideira (`culinaria:frying_pan`) | Slot de utensílio do fogão: fritar e grelhar |
| Forma de assar (`culinaria:baking_tray`) | Slot de utensílio do forno |
| Rolo de massa (`culinaria:rolling_pin`) | Slot de utensílio da bancada: abrir massas |
| Batedor de arame (`culinaria:whisk`) | Slot de utensílio da bancada: bater cremes e massas |
| Prato (`culinaria:plate`) | Recipiente dos pratos; volta sujo depois de comer |
| Prato sujo (`culinaria:dirty_plate`) | Lave na pia para virar prato limpo |
| Xícara (`culinaria:cup`) | Recipiente do café, chocolate e chá; volta suja |
| Xícara suja (`culinaria:dirty_cup`) | Lave na pia para virar xícara limpa |
| Livro de receitas (`culinaria:recipe_book`) | Abre o livro de receitas |
