// Catálogo do Culinária: a fonte única de itens, lavouras, árvores, estações, receitas e textos.
// O gerador (generate.mjs) transforma isto em JSON de dados/recursos, texturas e na tabela Java gerada.
// IDs sem namespace são do mod (culinaria:); "minecraft:x" são itens vanilla reaproveitados.

// ---------------------------------------------------------------- categorias
export const CATEGORIES = [
  { id: 'breads_pasta', pt: 'Pães e massas', en: 'Breads & Pasta', icon: 'pizza_margherita' },
  { id: 'breakfast', pt: 'Café da manhã', en: 'Breakfast', icon: 'fried_egg' },
  { id: 'meat', pt: 'Carnes e aves', en: 'Meat & Poultry', icon: 'roast_chicken' },
  { id: 'fish', pt: 'Peixes', en: 'Fish', icon: 'grilled_salmon' },
  { id: 'mains', pt: 'Refeições principais', en: 'Main Meals', icon: 'feijoada' },
  { id: 'soups', pt: 'Sopas e caldos', en: 'Soups & Broths', icon: 'tomato_soup' },
  { id: 'sides', pt: 'Acompanhamentos', en: 'Side Dishes', icon: 'fries' },
  { id: 'vegetables', pt: 'Pratos vegetais', en: 'Vegetable Dishes', icon: 'garden_salad' },
  { id: 'desserts', pt: 'Bolos e sobremesas', en: 'Cakes & Desserts', icon: 'chocolate_cake' },
  { id: 'sweets', pt: 'Tortas e doces', en: 'Pies & Sweets', icon: 'apple_pie' },
  { id: 'drinks', pt: 'Bebidas', en: 'Drinks', icon: 'orange_juice' },
];
/** Categoria das receitas de preparo (ingredientes intermediários): não conta nas 100. */
export const PREP_CATEGORY = { id: 'prep', pt: 'Preparos', en: 'Preparations', icon: 'flour' };

// ---------------------------------------------------------------- estações
// kind: menu = interface com slots; board/sink/mill = clique no bloco; fridge = guarda e gela com o tempo.
export const STATIONS = [
  { id: 'cutting_board', kind: 'board', pt: 'Tábua de corte', en: 'Cutting Board', verbPt: 'Corte', verbEn: 'Cut', unitPt: 'cortes', unitEn: 'cuts' },
  { id: 'sink', kind: 'sink', pt: 'Pia', en: 'Sink', verbPt: 'Lave', verbEn: 'Wash', unitPt: 'lavagem', unitEn: 'wash' },
  { id: 'mill', kind: 'mill', pt: 'Moedor manual', en: 'Hand Mill', verbPt: 'Moa', verbEn: 'Grind', unitPt: 'voltas', unitEn: 'turns' },
  { id: 'counter', kind: 'menu', pt: 'Bancada de cozinha', en: 'Kitchen Counter', verbPt: 'Prepare', verbEn: 'Prepare', fuel: false, utensils: ['rolling_pin', 'whisk'] },
  { id: 'stove', kind: 'menu', pt: 'Fogão', en: 'Stove', verbPt: 'Cozinhe', verbEn: 'Cook', fuel: true, utensils: ['cooking_pot', 'frying_pan'] },
  { id: 'oven', kind: 'menu', pt: 'Forno', en: 'Oven', verbPt: 'Asse', verbEn: 'Bake', fuel: true, utensils: ['baking_tray'] },
  { id: 'blender', kind: 'menu', pt: 'Liquidificador', en: 'Blender', verbPt: 'Bata', verbEn: 'Blend', fuel: false, utensils: [] },
  { id: 'fridge', kind: 'fridge', pt: 'Geladeira', en: 'Fridge', verbPt: 'Gele', verbEn: 'Chill', unitPt: 'segundos', unitEn: 'seconds' },
];

// ---------------------------------------------------------------- lavouras
// product: o que a lavoura madura dá; food: [fome, saturação] se dá para comer cru.
export const CROPS = [
  { id: 'tomato', pt: 'Tomate', en: 'Tomato', seedPt: 'Sementes de tomate', seedEn: 'Tomato Seeds', color: '#d8372b', shape: 'round', food: [2, 0.3] },
  { id: 'onion', pt: 'Cebola', en: 'Onion', seedPt: 'Sementes de cebola', seedEn: 'Onion Seeds', color: '#c98a4a', shape: 'bulb' },
  { id: 'garlic', pt: 'Alho', en: 'Garlic', seedPt: 'Dente de alho para plantio', seedEn: 'Garlic Clove', color: '#ece3cf', shape: 'garlic' },
  { id: 'lettuce', pt: 'Alface', en: 'Lettuce', seedPt: 'Sementes de alface', seedEn: 'Lettuce Seeds', color: '#7cc94a', shape: 'leafy', food: [1, 0.3] },
  { id: 'corn', pt: 'Milho', en: 'Corn', seedPt: 'Sementes de milho', seedEn: 'Corn Seeds', color: '#f2c933', shape: 'cob' },
  { id: 'rice', pt: 'Arroz', en: 'Rice', seedPt: 'Sementes de arroz', seedEn: 'Rice Seeds', color: '#efe8d2', shape: 'grains' },
  { id: 'beans', pt: 'Feijão', en: 'Beans', seedPt: 'Sementes de feijão', seedEn: 'Bean Seeds', color: '#5a2a1c', shape: 'grains' },
  { id: 'bell_pepper', pt: 'Pimentão', en: 'Bell Pepper', seedPt: 'Sementes de pimentão', seedEn: 'Bell Pepper Seeds', color: '#3fa63a', shape: 'pepper', food: [2, 0.3] },
  { id: 'basil', pt: 'Manjericão', en: 'Basil', seedPt: 'Sementes de manjericão', seedEn: 'Basil Seeds', color: '#2f8f3a', shape: 'herb' },
  { id: 'parsley', pt: 'Salsinha', en: 'Parsley', seedPt: 'Sementes de salsinha', seedEn: 'Parsley Seeds', color: '#4caf3c', shape: 'herb' },
  { id: 'strawberry', pt: 'Morango', en: 'Strawberry', seedPt: 'Sementes de morango', seedEn: 'Strawberry Seeds', color: '#e0313f', shape: 'berry', food: [2, 0.2] },
  { id: 'coffee_beans', pt: 'Grãos de café', en: 'Coffee Beans', seedPt: 'Muda de café', seedEn: 'Coffee Seedling', color: '#b8322a', shape: 'grains', crop: 'coffee', rare: true },
  { id: 'cassava', pt: 'Mandioca', en: 'Cassava', seedPt: 'Rama de mandioca', seedEn: 'Cassava Cutting', color: '#8a5a33', shape: 'root', rare: true },
  { id: 'kale', pt: 'Couve', en: 'Kale', seedPt: 'Sementes de couve', seedEn: 'Kale Seeds', color: '#2c7a3a', shape: 'leafy', food: [1, 0.3] },
];

// ---------------------------------------------------------------- árvores frutíferas
// fruit: item colhido (apple é o vanilla). biomes: onde nascem no mundo.
export const TREES = [
  { id: 'apple', pt: 'Macieira', en: 'Apple Tree', fruit: 'minecraft:apple', fruitColor: '#d8262a', biomes: ['#minecraft:is_forest', 'minecraft:plains', 'minecraft:meadow'] },
  { id: 'orange', pt: 'Laranjeira', en: 'Orange Tree', fruit: 'orange', fruitPt: 'Laranja', fruitEn: 'Orange', fruitColor: '#f28a1c', food: [4, 0.3], biomes: ['#minecraft:is_savanna', 'minecraft:plains', 'minecraft:sunflower_plains'] },
  { id: 'lemon', pt: 'Limoeiro', en: 'Lemon Tree', fruit: 'lemon', fruitPt: 'Limão', fruitEn: 'Lemon', fruitColor: '#e7e23a', biomes: ['#minecraft:is_jungle', '#minecraft:is_savanna', 'minecraft:plains'] },
  { id: 'peach', pt: 'Pessegueiro', en: 'Peach Tree', fruit: 'peach', fruitPt: 'Pêssego', fruitEn: 'Peach', fruitColor: '#f5a36a', food: [4, 0.3], biomes: ['minecraft:flower_forest', 'minecraft:meadow', 'minecraft:plains'] },
  { id: 'cherry', pt: 'Cerejeira frutífera', en: 'Fruiting Cherry Tree', fruit: 'cherry', fruitPt: 'Cereja', fruitEn: 'Cherry', fruitColor: '#a3122a', food: [2, 0.2], biomes: ['#minecraft:is_forest', 'minecraft:cherry_grove'] },
];

// ---------------------------------------------------------------- utensílios, louça e outros itens
// tex: [forma, cor principal, cor secundária, cor de detalhe]
export const TOOLS = [
  { id: 'kitchen_knife', pt: 'Faca de cozinha', en: 'Kitchen Knife', tex: ['knife'], durability: 256, hint: true },
  { id: 'cooking_pot', pt: 'Panela', en: 'Cooking Pot', tex: ['pot'], hint: true },
  { id: 'frying_pan', pt: 'Frigideira', en: 'Frying Pan', tex: ['pan'], hint: true },
  { id: 'baking_tray', pt: 'Forma de assar', en: 'Baking Tray', tex: ['tray'], hint: true },
  { id: 'rolling_pin', pt: 'Rolo de massa', en: 'Rolling Pin', tex: ['rolling_pin'], hint: true },
  { id: 'whisk', pt: 'Batedor de arame', en: 'Whisk', tex: ['whisk'], hint: true },
  { id: 'plate', pt: 'Prato', en: 'Plate', tex: ['plate_empty'], stack: 64, hint: true },
  { id: 'dirty_plate', pt: 'Prato sujo', en: 'Dirty Plate', tex: ['plate_dirty'], stack: 64, hint: true },
  { id: 'cup', pt: 'Xícara', en: 'Cup', tex: ['cup_empty'], stack: 64, hint: true },
  { id: 'dirty_cup', pt: 'Xícara suja', en: 'Dirty Cup', tex: ['cup_dirty'], stack: 64, hint: true },
  { id: 'recipe_book', pt: 'Livro de receitas', en: 'Recipe Book', tex: ['book'], stack: 1, hint: true },
];

// Receitas de bancada de trabalho vanilla (utensílios, estações, mudas). key: letra → item ou #tag.
export const CRAFTING = [
  { out: 'kitchen_knife', pattern: [' I', 'S '], key: { I: 'minecraft:iron_ingot', S: 'minecraft:stick' } },
  { out: 'cooking_pot', pattern: ['I I', 'III'], key: { I: 'minecraft:iron_ingot' } },
  { out: 'frying_pan', pattern: ['II ', 'II ', '  S'], key: { I: 'minecraft:iron_ingot', S: 'minecraft:stick' } },
  { out: 'baking_tray', pattern: ['IBI'], key: { I: 'minecraft:iron_ingot', B: 'minecraft:brick' } },
  { out: 'rolling_pin', pattern: ['SPS'], key: { S: 'minecraft:stick', P: '#minecraft:planks' } },
  { out: 'whisk', pattern: ['N N', ' N ', ' S '], key: { N: 'minecraft:iron_nugget', S: 'minecraft:stick' } },
  { out: 'plate', count: 2, pattern: ['C C', ' C '], key: { C: 'minecraft:clay_ball' } },
  { out: 'cup', count: 2, pattern: ['C C', 'C C'], key: { C: 'minecraft:clay_ball' } },
  { out: 'recipe_book', shapeless: ['minecraft:book', 'minecraft:wheat'] },
  { out: 'cutting_board', pattern: ['##'], key: { '#': '#minecraft:wooden_slabs' } },
  { out: 'sink', pattern: ['I I', 'IBI', 'SSS'], key: { I: 'minecraft:iron_ingot', B: 'minecraft:bucket', S: 'minecraft:stone' } },
  { out: 'mill', pattern: [' S ', 'CIC', 'CCC'], key: { S: 'minecraft:stick', C: 'minecraft:cobblestone', I: 'minecraft:iron_ingot' } },
  { out: 'counter', pattern: ['QQQ', 'PBP', 'PPP'], key: { Q: 'minecraft:smooth_stone_slab', P: '#minecraft:planks', B: 'minecraft:crafting_table' } },
  { out: 'stove', pattern: ['I I', 'IFI', 'III'], key: { I: 'minecraft:iron_ingot', F: 'minecraft:furnace' } },
  { out: 'oven', pattern: ['BBB', 'BFB', 'BBB'], key: { B: 'minecraft:bricks', F: 'minecraft:furnace' } },
  { out: 'blender', pattern: ['GGG', 'G G', 'IRI'], key: { G: 'minecraft:glass', I: 'minecraft:iron_ingot', R: 'minecraft:redstone' } },
  { out: 'fridge', pattern: ['III', 'IcI', 'IRI'], key: { I: 'minecraft:iron_ingot', c: 'minecraft:ice', R: 'minecraft:redstone' } },
  { out: 'cabinet', pattern: ['DCD'], key: { D: '#minecraft:wooden_trapdoors', C: 'minecraft:chest' } },
  { out: 'pantry', pattern: ['BCB'], key: { B: 'minecraft:barrel', C: 'minecraft:chest' } },
];

// ---------------------------------------------------------------- móveis e equipamentos (blocos)
export const FURNITURE = [
  { id: 'cutting_board', station: 'cutting_board', pt: 'Tábua de corte', en: 'Cutting Board' },
  { id: 'sink', station: 'sink', pt: 'Pia', en: 'Sink' },
  { id: 'mill', station: 'mill', pt: 'Moedor manual', en: 'Hand Mill' },
  { id: 'counter', station: 'counter', pt: 'Bancada de cozinha', en: 'Kitchen Counter' },
  { id: 'stove', station: 'stove', pt: 'Fogão', en: 'Stove' },
  { id: 'oven', station: 'oven', pt: 'Forno', en: 'Oven' },
  { id: 'blender', station: 'blender', pt: 'Liquidificador', en: 'Blender' },
  { id: 'fridge', station: 'fridge', pt: 'Geladeira', en: 'Fridge', rows: 3 },
  { id: 'cabinet', pt: 'Armário', en: 'Cabinet', rows: 2 },
  { id: 'pantry', pt: 'Despensa', en: 'Pantry', rows: 4 },
];

// ---------------------------------------------------------------- ingredientes preparados (intermediários)
// edible: [fome, saturação] quando dá para comer assim mesmo.
export const PREPARED = [
  // tábua de corte
  { id: 'sliced_tomato', pt: 'Tomate fatiado', en: 'Sliced Tomato', tex: ['slices', '#d8372b', '#f06a4a', '#f7d65a'] },
  { id: 'chopped_onion', pt: 'Cebola picada', en: 'Chopped Onion', tex: ['bits', '#f1e2c2', '#c98a4a', '#e9d2a8'] },
  { id: 'minced_garlic', pt: 'Alho picado', en: 'Minced Garlic', tex: ['bits', '#f5efdc', '#d8cfb2', '#fffaf0'] },
  { id: 'chopped_lettuce', pt: 'Alface picada', en: 'Chopped Lettuce', tex: ['bits', '#7cc94a', '#4e9a30', '#b6e57a'] },
  { id: 'pepper_strips', pt: 'Pimentão em tiras', en: 'Pepper Strips', tex: ['strips', '#3fa63a', '#d8372b', '#f2c933'] },
  { id: 'potato_sticks', pt: 'Batata em palitos', en: 'Potato Sticks', tex: ['sticks', '#efd9a0', '#d6b56a', '#fff1c4'] },
  { id: 'carrot_slices', pt: 'Cenoura em rodelas', en: 'Carrot Slices', tex: ['slices', '#ef8a1f', '#f6b04a', '#c86410'] },
  { id: 'beef_cubes', pt: 'Carne em cubos', en: 'Beef Cubes', tex: ['cubes', '#b8323a', '#e8a0a0', '#7a1f25'] },
  { id: 'chicken_pieces', pt: 'Frango em pedaços', en: 'Chicken Pieces', tex: ['cubes', '#f0c2a8', '#fbe2d0', '#c98a6a'] },
  { id: 'cod_fillet', pt: 'Filé de bacalhau', en: 'Cod Fillet', tex: ['fillet', '#efe6d8', '#d9c7aa', '#b9a78a'] },
  { id: 'salmon_fillet', pt: 'Filé de salmão', en: 'Salmon Fillet', tex: ['fillet', '#f2855a', '#ffd0b0', '#c95a3a'] },
  { id: 'raw_bacon', pt: 'Bacon cru', en: 'Raw Bacon', tex: ['bacon', '#e07a7a', '#fbe2d8', '#b84a4a'] },
  { id: 'ham', pt: 'Presunto', en: 'Ham', tex: ['slab', '#f0a0a8', '#f8cfd2', '#c8707a'], edible: [3, 0.4] },
  { id: 'bread_slice', pt: 'Fatia de pão', en: 'Bread Slice', tex: ['slab', '#e8c27a', '#f7e6b8', '#b07a32'], edible: [2, 0.3] },
  { id: 'peeled_cassava', pt: 'Mandioca descascada', en: 'Peeled Cassava', tex: ['root', '#f5efdc', '#e3d6b0', '#c8b78a'] },
  { id: 'apple_slices', pt: 'Maçã fatiada', en: 'Apple Slices', tex: ['slices', '#f4ecc8', '#d8262a', '#e9dfa8'], edible: [2, 0.3] },
  // pia
  { id: 'washed_rice', pt: 'Arroz lavado', en: 'Washed Rice', tex: ['grains', '#f7f3e6', '#dcd3b8', '#9ec9e8'] },
  { id: 'soaked_beans', pt: 'Feijão de molho', en: 'Soaked Beans', tex: ['grains', '#6a3424', '#9a5a40', '#9ec9e8'] },
  { id: 'tapioca_starch', pt: 'Polvilho', en: 'Tapioca Starch', tex: ['sack', '#fbfbf4', '#e5e2d2', '#7fb3d9'] },
  // moedor
  { id: 'flour', pt: 'Farinha de trigo', en: 'Wheat Flour', tex: ['sack', '#f6f0e0', '#dcc9a0', '#d9a13a'] },
  { id: 'cornmeal', pt: 'Fubá', en: 'Cornmeal', tex: ['sack', '#f2d04a', '#dcc9a0', '#e08a1a'] },
  { id: 'cassava_flour', pt: 'Farinha de mandioca', en: 'Cassava Flour', tex: ['sack', '#efe2b8', '#d6c08a', '#8a5a33'] },
  { id: 'cocoa_powder', pt: 'Cacau em pó', en: 'Cocoa Powder', tex: ['pile', '#6a3a22', '#8a5434', '#4a2614'] },
  { id: 'ground_coffee', pt: 'Café moído', en: 'Ground Coffee', tex: ['pile', '#4a2a1a', '#6a4028', '#2c180e'] },
  { id: 'breadcrumbs', pt: 'Farinha de rosca', en: 'Breadcrumbs', tex: ['pile', '#d9a85a', '#efcf8a', '#a8742e'] },
  { id: 'minced_beef', pt: 'Carne moída', en: 'Minced Beef', tex: ['mince', '#b8323a', '#e07070', '#7a1f25'] },
  { id: 'raw_sausage', pt: 'Linguiça crua', en: 'Raw Sausage', tex: ['sausage', '#c85a5a', '#e89090', '#8a3030'] },
  // bancada
  { id: 'bread_dough', pt: 'Massa de pão', en: 'Bread Dough', tex: ['dough', '#f2e3c0', '#d9c49a', '#fff6e0'] },
  { id: 'pasta_dough', pt: 'Massa de macarrão', en: 'Pasta Dough', tex: ['dough', '#f6dc8a', '#d9b85a', '#fff0b8'] },
  { id: 'raw_pasta', pt: 'Macarrão cru', en: 'Raw Pasta', tex: ['noodles', '#f6dc8a', '#d9b85a', '#fff0b8'] },
  { id: 'pizza_base', pt: 'Disco de pizza', en: 'Pizza Base', tex: ['disc', '#f2e3c0', '#d9c49a', '#c8a870'] },
  { id: 'pie_crust', pt: 'Massa de torta', en: 'Pie Crust', tex: ['disc', '#efc884', '#d6a858', '#b8843a'] },
  { id: 'cake_batter', pt: 'Massa de bolo', en: 'Cake Batter', tex: ['mixbowl', '#f6e2a8', '#ffffff', '#8a5a33'] },
  { id: 'pancake_batter', pt: 'Massa de panqueca', en: 'Pancake Batter', tex: ['mixbowl', '#f2d89a', '#ffffff', '#8a5a33'] },
  { id: 'cream', pt: 'Creme de leite', en: 'Cream', tex: ['jar', '#fbf8ee', '#e8e2cf', '#7fb3d9'] },
  { id: 'butter', pt: 'Manteiga', en: 'Butter', tex: ['block', '#f7e07a', '#fff2b0', '#d6b83a'] },
  { id: 'mayonnaise', pt: 'Maionese', en: 'Mayonnaise', tex: ['jar', '#f7f0c8', '#e9dfa8', '#e7e23a'] },
  { id: 'lemon_mousse_mix', pt: 'Creme de mousse de limão', en: 'Lemon Mousse Mix', tex: ['mixbowl', '#eef5a8', '#ffffff', '#8a5a33'] },
  { id: 'ice_cream_mix', pt: 'Creme de sorvete de morango', en: 'Strawberry Ice Cream Mix', tex: ['mixbowl', '#f7a8b8', '#ffffff', '#8a5a33'] },
  // fogão
  { id: 'salt', pt: 'Sal', en: 'Salt', tex: ['pile', '#fbfbfb', '#dfe6ea', '#b8c6d0'] },
  { id: 'cheese', pt: 'Queijo', en: 'Cheese', tex: ['wedge', '#f6d04a', '#fbe68a', '#d6a82a'], edible: [3, 0.4] },
  { id: 'tomato_sauce', pt: 'Molho de tomate', en: 'Tomato Sauce', tex: ['jar', '#c8281f', '#e85a3a', '#f2f2f2'] },
  { id: 'bolognese_sauce', pt: 'Molho à bolonhesa', en: 'Bolognese Sauce', tex: ['jar', '#9a2a1f', '#c8503a', '#6a2414'] },
  { id: 'cooked_pasta', pt: 'Macarrão cozido', en: 'Cooked Pasta', tex: ['noodles', '#f8e6a0', '#e6c870', '#fff4c8'] },
  { id: 'cooked_rice', pt: 'Arroz cozido', en: 'Cooked Rice', tex: ['heap', '#fbf8ee', '#e8e2cf', '#ffffff'] },
  { id: 'cooked_beans', pt: 'Feijão cozido', en: 'Cooked Beans', tex: ['heap', '#5a2a1c', '#7a4028', '#3a1a10'] },
  { id: 'condensed_milk', pt: 'Leite condensado', en: 'Condensed Milk', tex: ['can', '#f7f0d8', '#3a6ab8', '#e8d8a8'] },
  { id: 'doce_de_leite', pt: 'Doce de leite', en: 'Dulce de Leche', tex: ['jar', '#b8743a', '#d8945a', '#f2f2f2'], edible: [2, 0.2] },
  { id: 'chocolate', pt: 'Barra de chocolate', en: 'Chocolate Bar', tex: ['bar', '#5a2e1a', '#7a4428', '#d8d8d8'], edible: [3, 0.3] },
  { id: 'beef_patty', pt: 'Hambúrguer de carne', en: 'Beef Patty', tex: ['patty', '#7a4028', '#9a5a3a', '#4a2414'] },
  // forno
  { id: 'roasted_coffee', pt: 'Café torrado', en: 'Roasted Coffee', tex: ['grains', '#4a2a1a', '#6a4028', '#2c180e'] },
];

// ---------------------------------------------------------------- receitas de preparo
// [id da receita/resultado, estação, utensílio|null, ingredientes {item: qtd}, recipiente|null, qtd produzida, tempo]
// tempo: ticks nas estações com interface e na geladeira; cortes na tábua; voltas no moedor; 1 na pia.
export const PREP_RECIPES = [
  ['sliced_tomato', 'cutting_board', null, { tomato: 1 }, null, 2, 2],
  ['chopped_onion', 'cutting_board', null, { onion: 1 }, null, 2, 3],
  ['minced_garlic', 'cutting_board', null, { garlic: 1 }, null, 3, 3],
  ['chopped_lettuce', 'cutting_board', null, { lettuce: 1 }, null, 2, 2],
  ['pepper_strips', 'cutting_board', null, { bell_pepper: 1 }, null, 2, 2],
  ['potato_sticks', 'cutting_board', null, { 'minecraft:potato': 1 }, null, 2, 3],
  ['carrot_slices', 'cutting_board', null, { 'minecraft:carrot': 1 }, null, 2, 2],
  ['beef_cubes', 'cutting_board', null, { 'minecraft:beef': 1 }, null, 2, 3],
  ['chicken_pieces', 'cutting_board', null, { 'minecraft:chicken': 1 }, null, 2, 3],
  ['cod_fillet', 'cutting_board', null, { 'minecraft:cod': 1 }, null, 2, 2],
  ['salmon_fillet', 'cutting_board', null, { 'minecraft:salmon': 1 }, null, 2, 2],
  ['raw_bacon', 'cutting_board', null, { 'minecraft:porkchop': 1 }, null, 2, 3],
  ['ham', 'cutting_board', null, { 'minecraft:cooked_porkchop': 1 }, null, 3, 3],
  ['bread_slice', 'cutting_board', null, { 'minecraft:bread': 1 }, null, 4, 2],
  ['peeled_cassava', 'cutting_board', null, { cassava: 1 }, null, 1, 3],
  ['apple_slices', 'cutting_board', null, { 'minecraft:apple': 1 }, null, 2, 2],

  ['washed_rice', 'sink', null, { rice: 1 }, null, 1, 1],
  ['soaked_beans', 'sink', null, { beans: 1 }, null, 1, 1],
  ['tapioca_starch', 'sink', null, { cassava_flour: 1 }, null, 1, 1],
  ['plate', 'sink', null, { dirty_plate: 1 }, null, 1, 1],
  ['cup', 'sink', null, { dirty_cup: 1 }, null, 1, 1],

  ['flour', 'mill', null, { 'minecraft:wheat': 1 }, null, 1, 4],
  ['cornmeal', 'mill', null, { corn: 1 }, null, 1, 4],
  ['cassava_flour', 'mill', null, { peeled_cassava: 1 }, null, 2, 5],
  ['cocoa_powder', 'mill', null, { 'minecraft:cocoa_beans': 1 }, null, 1, 4],
  ['ground_coffee', 'mill', null, { roasted_coffee: 1 }, null, 1, 4],
  ['breadcrumbs', 'mill', null, { 'minecraft:bread': 1 }, null, 2, 3],
  ['minced_beef', 'mill', null, { 'minecraft:beef': 1 }, null, 2, 5],
  ['raw_sausage', 'mill', null, { 'minecraft:porkchop': 1 }, null, 2, 5],
  ['minecraft:sugar', 'mill', null, { 'minecraft:sugar_cane': 1 }, null, 2, 3],

  ['bread_dough', 'counter', null, { flour: 3, salt: 1, 'minecraft:water_bucket': 1 }, null, 3, 160],
  ['pasta_dough', 'counter', null, { flour: 2, 'minecraft:egg': 1 }, null, 2, 140],
  ['raw_pasta', 'counter', 'rolling_pin', { pasta_dough: 1 }, null, 2, 100],
  ['pizza_base', 'counter', 'rolling_pin', { bread_dough: 1 }, null, 1, 100],
  ['pie_crust', 'counter', 'rolling_pin', { flour: 2, butter: 1, 'minecraft:egg': 1 }, null, 2, 140],
  ['cake_batter', 'counter', 'whisk', { flour: 2, 'minecraft:sugar': 2, 'minecraft:egg': 2, 'minecraft:milk_bucket': 1 }, null, 1, 160],
  ['pancake_batter', 'counter', 'whisk', { flour: 1, 'minecraft:egg': 1, 'minecraft:milk_bucket': 1 }, null, 2, 120],
  ['cream', 'counter', 'whisk', { 'minecraft:milk_bucket': 1 }, null, 2, 120],
  ['butter', 'counter', 'whisk', { cream: 2 }, null, 1, 160],
  ['mayonnaise', 'counter', 'whisk', { 'minecraft:egg': 2, lemon: 1, salt: 1 }, null, 2, 120],
  ['lemon_mousse_mix', 'counter', 'whisk', { condensed_milk: 1, lemon: 2, cream: 1 }, null, 2, 140],
  ['ice_cream_mix', 'counter', 'whisk', { cream: 1, strawberry: 2, 'minecraft:sugar': 1 }, null, 2, 140],

  ['salt', 'stove', 'cooking_pot', { 'minecraft:water_bucket': 1 }, null, 3, 200],
  ['cheese', 'stove', 'cooking_pot', { 'minecraft:milk_bucket': 1, lemon: 1 }, null, 2, 240],
  ['tomato_sauce', 'stove', 'cooking_pot', { tomato: 3, chopped_onion: 1, minced_garlic: 1, salt: 1 }, null, 2, 200],
  ['bolognese_sauce', 'stove', 'cooking_pot', { minced_beef: 1, tomato_sauce: 1, chopped_onion: 1 }, null, 2, 200],
  ['cooked_pasta', 'stove', 'cooking_pot', { raw_pasta: 2, 'minecraft:water_bucket': 1, salt: 1 }, null, 2, 160],
  ['cooked_rice', 'stove', 'cooking_pot', { washed_rice: 2, 'minecraft:water_bucket': 1, salt: 1 }, null, 3, 200],
  ['cooked_beans', 'stove', 'cooking_pot', { soaked_beans: 2, minced_garlic: 1, chopped_onion: 1, salt: 1, 'minecraft:water_bucket': 1 }, null, 3, 260],
  ['condensed_milk', 'stove', 'cooking_pot', { 'minecraft:milk_bucket': 1, 'minecraft:sugar': 2 }, null, 2, 240],
  ['doce_de_leite', 'stove', 'cooking_pot', { condensed_milk: 2 }, null, 1, 300],
  ['chocolate', 'stove', 'cooking_pot', { cocoa_powder: 2, 'minecraft:sugar': 1, butter: 1 }, null, 3, 200],
  ['beef_patty', 'stove', 'frying_pan', { minced_beef: 1, salt: 1 }, null, 1, 140],

  ['roasted_coffee', 'oven', 'baking_tray', { coffee_beans: 1 }, null, 1, 160],
];

// ---------------------------------------------------------------- os 100 pratos
// [id, pt, en, categoria, estação, utensílio|null, ingredientes, recipiente|null, qtd, tempo, [fome, saturação], textura]
// recipiente: plate (devolve prato sujo), cup (devolve xícara suja), minecraft:bowl e minecraft:glass_bottle (devolvem
// o vanilla); null = comida de mão. Cada prato precisa de 1 recipiente por unidade produzida.
const W = 'minecraft:water_bucket';
const M = 'minecraft:milk_bucket';
const EGG = 'minecraft:egg';
const SUGAR = 'minecraft:sugar';
const BOWL = 'minecraft:bowl';
const BOTTLE = 'minecraft:glass_bottle';
const MUSH = 'minecraft:brown_mushroom';
export const DISHES = [
  // Pães e massas
  ['bread_roll', 'Pão francês', 'Bread Roll', 'breads_pasta', 'oven', 'baking_tray', { bread_dough: 1 }, null, 2, 200, [5, 0.6], ['loaf', '#d9973a', '#f2c97a', '#a86420']],
  ['cheese_bread', 'Pão de queijo', 'Cheese Bread', 'breads_pasta', 'oven', 'baking_tray', { tapioca_starch: 2, cheese: 1, [EGG]: 1, [M]: 1 }, null, 4, 220, [4, 0.6], ['balls', '#f2c65a', '#fbe29a', '#c8902a']],
  ['garlic_bread', 'Pão de alho', 'Garlic Bread', 'breads_pasta', 'oven', 'baking_tray', { bread_roll: 1, minced_garlic: 1, butter: 1 }, null, 2, 160, [6, 0.6], ['loaf', '#d9973a', '#f7e07a', '#3c8a2a']],
  ['spaghetti_sugo', 'Espaguete ao sugo', 'Spaghetti Pomodoro', 'breads_pasta', 'counter', null, { cooked_pasta: 1, tomato_sauce: 1, basil: 1 }, 'plate', 1, 100, [10, 0.7], ['plate', '#f6dc8a', '#c8281f', '#2f8f3a']],
  ['spaghetti_bolognese', 'Espaguete à bolonhesa', 'Spaghetti Bolognese', 'breads_pasta', 'counter', null, { cooked_pasta: 1, bolognese_sauce: 1 }, 'plate', 1, 100, [12, 0.8], ['plate', '#f6dc8a', '#9a2a1f', '#6a2414']],
  ['lasagna', 'Lasanha', 'Lasagna', 'breads_pasta', 'oven', 'baking_tray', { raw_pasta: 2, bolognese_sauce: 1, cheese: 2 }, 'plate', 2, 300, [13, 0.8], ['layers', '#f6d04a', '#9a2a1f', '#f6dc8a']],
  ['pizza_margherita', 'Pizza marguerita', 'Margherita Pizza', 'breads_pasta', 'oven', 'baking_tray', { pizza_base: 1, tomato_sauce: 1, cheese: 1, basil: 1 }, null, 1, 240, [12, 0.7], ['pizza', '#c8281f', '#f6e08a', '#2f8f3a']],
  ['pizza_calabresa', 'Pizza de calabresa', 'Sausage Pizza', 'breads_pasta', 'oven', 'baking_tray', { pizza_base: 1, tomato_sauce: 1, cheese: 1, raw_sausage: 1 }, null, 1, 240, [13, 0.7], ['pizza', '#c8281f', '#f6e08a', '#8a3030']],
  ['mac_and_cheese', 'Macarrão com queijo', 'Mac and Cheese', 'breads_pasta', 'stove', 'cooking_pot', { cooked_pasta: 1, cheese: 2, [M]: 1, butter: 1 }, BOWL, 1, 200, [11, 0.7], ['bowl', '#f6c83a', '#f8e6a0', '#e0a020']],
  ['gnocchi', 'Nhoque ao sugo', 'Gnocchi Pomodoro', 'breads_pasta', 'stove', 'cooking_pot', { 'minecraft:potato': 2, flour: 1, [EGG]: 1, tomato_sauce: 1 }, 'plate', 1, 240, [11, 0.7], ['plate', '#f2e3c0', '#c8281f', '#f6dc8a']],
  // Café da manhã
  ['fried_egg', 'Ovo frito', 'Fried Egg', 'breakfast', 'stove', 'frying_pan', { [EGG]: 1, butter: 1 }, 'plate', 1, 100, [5, 0.6], ['egg', '#ffffff', '#f6b81a', '#f2f2f2']],
  ['scrambled_eggs', 'Ovos mexidos', 'Scrambled Eggs', 'breakfast', 'stove', 'frying_pan', { [EGG]: 2, butter: 1, salt: 1 }, 'plate', 1, 120, [7, 0.6], ['plate', '#f7d84a', '#fbe88a', '#e0b020']],
  ['omelette', 'Omelete', 'Omelette', 'breakfast', 'stove', 'frying_pan', { [EGG]: 2, cheese: 1, sliced_tomato: 1, chopped_onion: 1 }, 'plate', 1, 160, [9, 0.7], ['omelette', '#f6d04a', '#d8372b', '#f1e2c2']],
  ['pancakes', 'Panquecas com mel', 'Honey Pancakes', 'breakfast', 'stove', 'frying_pan', { pancake_batter: 1, butter: 1, 'minecraft:honey_bottle': 1 }, 'plate', 1, 160, [9, 0.6], ['stack', '#e0a850', '#f2c97a', '#f2a81a']],
  ['tapioca', 'Tapioca com queijo', 'Cheese Tapioca', 'breakfast', 'stove', 'frying_pan', { tapioca_starch: 2, cheese: 1 }, null, 1, 120, [6, 0.5], ['tapioca', '#fbfbf4', '#f6d04a', '#e5e2d2']],
  ['bacon_and_eggs', 'Ovos com bacon', 'Bacon and Eggs', 'breakfast', 'stove', 'frying_pan', { raw_bacon: 2, [EGG]: 2 }, 'plate', 1, 160, [10, 0.8], ['plate', '#ffffff', '#c8504a', '#f6b81a']],
  ['pao_na_chapa', 'Pão na chapa', 'Buttered Griddle Roll', 'breakfast', 'stove', 'frying_pan', { bread_roll: 1, butter: 1 }, null, 1, 100, [6, 0.6], ['loaf', '#c8842a', '#f7e07a', '#8a5418']],
  ['misto_quente', 'Misto-quente', 'Ham and Cheese Toastie', 'breakfast', 'stove', 'frying_pan', { bread_slice: 2, ham: 1, cheese: 1 }, null, 1, 120, [9, 0.7], ['sandwich', '#d9a85a', '#f0a0a8', '#f6d04a']],
  // Carnes e aves
  ['grilled_steak', 'Bife grelhado', 'Grilled Steak', 'meat', 'stove', 'frying_pan', { 'minecraft:beef': 1, salt: 1, minced_garlic: 1 }, 'plate', 1, 160, [10, 0.9], ['plate', '#7a3a22', '#9a5a3a', '#f5efdc']],
  ['beef_skewer', 'Espetinho de carne', 'Beef Skewer', 'meat', 'stove', 'frying_pan', { beef_cubes: 2, chopped_onion: 1, pepper_strips: 1, 'minecraft:stick': 1 }, null, 2, 160, [7, 0.8], ['skewer', '#7a3a22', '#3fa63a', '#f1e2c2']],
  ['roast_chicken', 'Frango assado', 'Roast Chicken', 'meat', 'oven', 'baking_tray', { 'minecraft:chicken': 1, minced_garlic: 1, butter: 1, salt: 1, parsley: 1 }, 'plate', 1, 300, [12, 0.9], ['roast', '#c8843a', '#e8b06a', '#4caf3c']],
  ['fried_chicken', 'Frango frito', 'Fried Chicken', 'meat', 'stove', 'frying_pan', { chicken_pieces: 2, flour: 1, [EGG]: 1, butter: 1 }, 'plate', 1, 200, [11, 0.8], ['plate', '#d9973a', '#f2c97a', '#a86420']],
  ['meatballs', 'Almôndegas ao molho', 'Meatballs in Sauce', 'meat', 'stove', 'cooking_pot', { minced_beef: 2, [EGG]: 1, breadcrumbs: 1, tomato_sauce: 1 }, BOWL, 1, 240, [12, 0.8], ['bowl', '#c8281f', '#7a4028', '#e85a3a']],
  ['roast_pork', 'Lombo assado com laranja', 'Orange Roast Pork', 'meat', 'oven', 'baking_tray', { 'minecraft:porkchop': 2, minced_garlic: 1, orange: 1, salt: 1 }, 'plate', 2, 300, [12, 0.9], ['roast', '#d9905a', '#f2b88a', '#f28a1c']],
  ['hamburger', 'Hambúrguer', 'Hamburger', 'meat', 'counter', null, { bread_roll: 1, beef_patty: 1, cheese: 1, chopped_lettuce: 1, sliced_tomato: 1 }, null, 1, 100, [13, 0.8], ['burger', '#d9973a', '#7a4028', '#7cc94a']],
  ['pot_roast', 'Carne de panela', 'Pot Roast', 'meat', 'stove', 'cooking_pot', { beef_cubes: 2, 'minecraft:potato': 1, 'minecraft:carrot': 1, chopped_onion: 1, [W]: 1 }, BOWL, 1, 300, [13, 0.9], ['bowl', '#6a3018', '#ef8a1f', '#efd9a0']],
  ['herb_lamb_chops', 'Costeletas de cordeiro com ervas', 'Herb Lamb Chops', 'meat', 'stove', 'frying_pan', { 'minecraft:mutton': 1, minced_garlic: 1, parsley: 1, butter: 1 }, 'plate', 1, 180, [10, 0.9], ['plate', '#9a4a3a', '#c87a6a', '#4caf3c']],
  ['roast_rabbit', 'Coelho assado', 'Roast Rabbit', 'meat', 'oven', 'baking_tray', { 'minecraft:rabbit': 1, 'minecraft:carrot': 1, chopped_onion: 1, salt: 1 }, 'plate', 1, 280, [11, 0.9], ['roast', '#b8743a', '#d8a06a', '#ef8a1f']],
  // Peixes
  ['grilled_salmon', 'Salmão grelhado com limão', 'Lemon Grilled Salmon', 'fish', 'stove', 'frying_pan', { salmon_fillet: 1, lemon: 1, butter: 1, salt: 1 }, 'plate', 1, 160, [10, 0.9], ['plate', '#f2855a', '#e7e23a', '#ffd0b0']],
  ['honey_salmon', 'Salmão ao mel', 'Honey Glazed Salmon', 'fish', 'stove', 'frying_pan', { salmon_fillet: 1, 'minecraft:honey_bottle': 1, minced_garlic: 1 }, 'plate', 1, 160, [10, 0.8], ['plate', '#d8653a', '#f2a81a', '#ffd0b0']],
  ['fried_fish', 'Peixe frito', 'Fried Fish', 'fish', 'stove', 'frying_pan', { cod_fillet: 2, flour: 1, [EGG]: 1, butter: 1 }, 'plate', 1, 180, [10, 0.8], ['plate', '#e0a850', '#f2c97a', '#e7e23a']],
  ['fish_and_chips', 'Peixe com fritas', 'Fish and Chips', 'fish', 'stove', 'frying_pan', { cod_fillet: 1, potato_sticks: 2, flour: 1, salt: 1 }, 'plate', 1, 200, [12, 0.8], ['plate', '#e0a850', '#f6dc6a', '#d8372b']],
  ['moqueca', 'Moqueca de peixe', 'Fish Moqueca', 'fish', 'stove', 'cooking_pot', { cod_fillet: 2, sliced_tomato: 1, chopped_onion: 1, pepper_strips: 1, parsley: 1 }, BOWL, 1, 300, [13, 0.9], ['bowl', '#e8742a', '#f2c933', '#3fa63a']],
  ['sushi', 'Sushi de salmão', 'Salmon Sushi', 'fish', 'counter', null, { cooked_rice: 1, salmon_fillet: 1, 'minecraft:dried_kelp': 1 }, null, 4, 120, [3, 0.7], ['sushi', '#fbf8ee', '#f2855a', '#2a3a2a']],
  ['bacalhoada', 'Bacalhoada', 'Baked Cod with Potatoes', 'fish', 'oven', 'baking_tray', { cod_fillet: 2, 'minecraft:potato': 2, chopped_onion: 1, minced_garlic: 1 }, 'plate', 2, 320, [13, 0.9], ['plate', '#efe6d8', '#efd9a0', '#3c8a2a']],
  ['poke_bowl', 'Poke de salmão', 'Salmon Poke Bowl', 'fish', 'counter', null, { cooked_rice: 1, salmon_fillet: 1, chopped_lettuce: 1, corn: 1 }, BOWL, 1, 100, [11, 0.8], ['bowl', '#fbf8ee', '#f2855a', '#7cc94a']],
  // Refeições principais
  ['rice_and_beans', 'Arroz com feijão', 'Rice and Beans', 'mains', 'counter', null, { cooked_rice: 1, cooked_beans: 1 }, 'plate', 1, 80, [10, 0.8], ['plate', '#fbf8ee', '#5a2a1c', '#e8e2cf']],
  ['feijoada', 'Feijoada', 'Feijoada', 'mains', 'stove', 'cooking_pot', { soaked_beans: 2, raw_sausage: 1, raw_bacon: 1, 'minecraft:porkchop': 1, minced_garlic: 1 }, BOWL, 2, 360, [16, 1.0], ['bowl', '#3a1a10', '#8a3030', '#5a2a1c']],
  ['prato_feito', 'Prato feito', 'Brazilian Lunch Plate', 'mains', 'counter', null, { cooked_rice: 1, cooked_beans: 1, fried_egg: 1, fries: 1, chopped_lettuce: 1 }, 'plate', 1, 120, [20, 1.0], ['plate', '#fbf8ee', '#5a2a1c', '#f6dc6a']],
  ['chicken_stroganoff', 'Estrogonofe de frango', 'Chicken Stroganoff', 'mains', 'stove', 'frying_pan', { chicken_pieces: 2, cream: 1, [MUSH]: 1, tomato_sauce: 1, chopped_onion: 1 }, 'plate', 2, 260, [13, 0.9], ['plate', '#e8a070', '#f2c8a0', '#fbf8ee']],
  ['mushroom_risotto', 'Risoto de cogumelos', 'Mushroom Risotto', 'mains', 'stove', 'cooking_pot', { washed_rice: 2, [MUSH]: 2, cheese: 1, butter: 1, chopped_onion: 1 }, 'plate', 2, 300, [12, 0.8], ['plate', '#efe2c0', '#8a6a4a', '#f6d04a']],
  ['galinhada', 'Galinhada', 'Chicken and Rice Pot', 'mains', 'stove', 'cooking_pot', { chicken_pieces: 2, washed_rice: 2, corn: 1, pepper_strips: 1, [W]: 1 }, 'plate', 2, 320, [14, 0.9], ['plate', '#f2c933', '#f0c2a8', '#3fa63a']],
  ['escondidinho', 'Escondidinho de carne', 'Cassava Cottage Pie', 'mains', 'oven', 'baking_tray', { minced_beef: 1, peeled_cassava: 2, cheese: 1, butter: 1 }, 'plate', 2, 300, [14, 0.9], ['layers', '#f6d04a', '#f5efdc', '#7a3a22']],
  ['stuffed_peppers', 'Pimentão recheado', 'Stuffed Peppers', 'mains', 'oven', 'baking_tray', { bell_pepper: 2, minced_beef: 1, cooked_rice: 1, tomato_sauce: 1 }, 'plate', 2, 280, [12, 0.8], ['plate', '#3fa63a', '#c8281f', '#fbf8ee']],
  ['baiao_de_dois', 'Baião de dois', 'Baiao de Dois', 'mains', 'stove', 'cooking_pot', { cooked_rice: 1, cooked_beans: 1, cheese: 1, raw_bacon: 1, parsley: 1 }, 'plate', 2, 240, [14, 0.9], ['plate', '#b8946a', '#f6d04a', '#4caf3c']],
  ['roast_beef_potatoes', 'Carne assada com batatas', 'Roast Beef with Potatoes', 'mains', 'oven', 'baking_tray', { 'minecraft:beef': 2, 'minecraft:potato': 2, chopped_onion: 1, minced_garlic: 1 }, 'plate', 2, 340, [14, 1.0], ['roast', '#7a3a22', '#efd9a0', '#9a5a3a']],
  // Sopas e caldos
  ['tomato_soup', 'Sopa de tomate', 'Tomato Soup', 'soups', 'stove', 'cooking_pot', { tomato: 3, chopped_onion: 1, basil: 1, [W]: 1 }, BOWL, 2, 220, [7, 0.6], ['bowl', '#d8372b', '#e85a3a', '#2f8f3a']],
  ['chicken_soup', 'Canja de galinha', 'Chicken and Rice Soup', 'soups', 'stove', 'cooking_pot', { chicken_pieces: 1, washed_rice: 1, 'minecraft:carrot': 1, chopped_onion: 1, [W]: 1 }, BOWL, 2, 260, [9, 0.7], ['bowl', '#f2dc9a', '#fbf8ee', '#ef8a1f']],
  ['vegetable_soup', 'Sopa de legumes', 'Vegetable Soup', 'soups', 'stove', 'cooking_pot', { 'minecraft:potato': 1, 'minecraft:carrot': 1, chopped_onion: 1, 'minecraft:beetroot': 1, [W]: 1 }, BOWL, 2, 240, [8, 0.6], ['bowl', '#c8843a', '#ef8a1f', '#a4223a']],
  ['pumpkin_soup', 'Creme de abóbora', 'Cream of Pumpkin', 'soups', 'stove', 'cooking_pot', { 'minecraft:pumpkin': 1, chopped_onion: 1, cream: 1, salt: 1 }, BOWL, 2, 240, [8, 0.7], ['bowl', '#ef9a2a', '#f6c06a', '#fbf8ee']],
  ['onion_soup', 'Sopa de cebola', 'French Onion Soup', 'soups', 'stove', 'cooking_pot', { chopped_onion: 3, butter: 1, cheese: 1, bread_slice: 1, [W]: 1 }, BOWL, 2, 260, [9, 0.7], ['bowl', '#b8743a', '#f6d04a', '#e8c27a']],
  ['caldo_verde', 'Caldo verde', 'Caldo Verde', 'soups', 'stove', 'cooking_pot', { 'minecraft:potato': 2, kale: 1, raw_sausage: 1, minced_garlic: 1, [W]: 1 }, BOWL, 2, 260, [10, 0.7], ['bowl', '#d8d0a0', '#2c7a3a', '#c85a5a']],
  ['bean_broth', 'Caldinho de feijão', 'Bean Broth', 'soups', 'stove', 'cooking_pot', { cooked_beans: 2, raw_bacon: 1, minced_garlic: 1, parsley: 1 }, BOWL, 2, 200, [9, 0.7], ['bowl', '#4a2216', '#6a3424', '#4caf3c']],
  ['corn_cream', 'Creme de milho', 'Creamed Corn Soup', 'soups', 'stove', 'cooking_pot', { corn: 3, [M]: 1, butter: 1, chopped_onion: 1 }, BOWL, 2, 220, [8, 0.6], ['bowl', '#f2d04a', '#fbe68a', '#f7e07a']],
  ['fish_soup', 'Sopa de peixe', 'Fish Soup', 'soups', 'stove', 'cooking_pot', { cod_fillet: 1, 'minecraft:potato': 1, sliced_tomato: 1, chopped_onion: 1, parsley: 1, [W]: 1 }, BOWL, 2, 260, [9, 0.7], ['bowl', '#e8c88a', '#efe6d8', '#d8372b']],
  // Acompanhamentos
  ['fries', 'Batata frita', 'French Fries', 'sides', 'stove', 'frying_pan', { potato_sticks: 2, butter: 1, salt: 1 }, null, 1, 140, [6, 0.5], ['fries', '#f6dc6a', '#e0b030', '#d8372b']],
  ['mashed_potatoes', 'Purê de batata', 'Mashed Potatoes', 'sides', 'stove', 'cooking_pot', { 'minecraft:potato': 2, butter: 1, [M]: 1, salt: 1 }, BOWL, 2, 200, [7, 0.6], ['bowl', '#f7ecc0', '#fbf4d8', '#f7e07a']],
  ['farofa', 'Farofa com bacon', 'Bacon Farofa', 'sides', 'stove', 'frying_pan', { cassava_flour: 2, butter: 1, raw_bacon: 1, chopped_onion: 1 }, BOWL, 2, 160, [6, 0.6], ['bowl', '#e0bc7a', '#c8504a', '#efe2b8']],
  ['couve_refogada', 'Couve refogada', 'Sauteed Kale', 'sides', 'stove', 'frying_pan', { kale: 2, minced_garlic: 1, butter: 1 }, 'plate', 1, 120, [5, 0.6], ['plate', '#2c7a3a', '#4e9a30', '#f5efdc']],
  ['vinaigrette', 'Vinagrete', 'Brazilian Salsa', 'sides', 'counter', null, { sliced_tomato: 1, chopped_onion: 1, pepper_strips: 1, parsley: 1, lemon: 1 }, BOWL, 2, 100, [4, 0.5], ['bowl', '#d8372b', '#3fa63a', '#f1e2c2']],
  ['cuscuz', 'Cuscuz nordestino', 'Northeastern Couscous', 'sides', 'stove', 'cooking_pot', { cornmeal: 2, [W]: 1, salt: 1, butter: 1 }, 'plate', 2, 200, [7, 0.6], ['mound', '#f2c933', '#f7e07a', '#e0a020']],
  ['polenta', 'Polenta cremosa', 'Creamy Polenta', 'sides', 'stove', 'cooking_pot', { cornmeal: 2, [M]: 1, cheese: 1, butter: 1 }, BOWL, 2, 220, [7, 0.6], ['bowl', '#f2d04a', '#fbe68a', '#f6d04a']],
  ['corn_on_the_cob', 'Milho cozido na manteiga', 'Buttered Corn on the Cob', 'sides', 'stove', 'cooking_pot', { corn: 2, [W]: 1, butter: 1, salt: 1 }, null, 2, 200, [5, 0.6], ['cob_cooked', '#f6d84a', '#fff2a0', '#8ab84a']],
  ['stuffed_baked_potato', 'Batata recheada', 'Loaded Baked Potato', 'sides', 'oven', 'baking_tray', { 'minecraft:potato': 1, cheese: 1, raw_bacon: 1, butter: 1 }, 'plate', 1, 240, [9, 0.7], ['plate', '#c8944a', '#f6d04a', '#c8504a']],
  ['arroz_a_grega', 'Arroz à grega', 'Greek-Style Rice', 'sides', 'stove', 'cooking_pot', { washed_rice: 2, 'minecraft:carrot': 1, pepper_strips: 1, corn: 1, [W]: 1 }, 'plate', 2, 240, [8, 0.6], ['plate', '#fbf8ee', '#ef8a1f', '#3fa63a']],
  // Pratos vegetais
  ['garden_salad', 'Salada verde', 'Garden Salad', 'vegetables', 'counter', null, { chopped_lettuce: 1, sliced_tomato: 1, chopped_onion: 1, lemon: 1, salt: 1 }, BOWL, 1, 80, [5, 0.6], ['bowl', '#7cc94a', '#d8372b', '#f1e2c2']],
  ['caprese', 'Salada caprese', 'Caprese Salad', 'vegetables', 'counter', null, { sliced_tomato: 2, cheese: 1, basil: 1 }, 'plate', 1, 80, [6, 0.6], ['plate', '#d8372b', '#fbf8ee', '#2f8f3a']],
  ['ratatouille', 'Ratatouille', 'Ratatouille', 'vegetables', 'stove', 'cooking_pot', { sliced_tomato: 2, pepper_strips: 1, chopped_onion: 1, minced_garlic: 1, carrot_slices: 1 }, 'plate', 2, 260, [8, 0.7], ['plate', '#c8281f', '#3fa63a', '#ef8a1f']],
  ['grilled_vegetables', 'Legumes grelhados', 'Grilled Vegetables', 'vegetables', 'stove', 'frying_pan', { pepper_strips: 1, carrot_slices: 1, [MUSH]: 1, butter: 1, salt: 1 }, 'plate', 1, 160, [7, 0.6], ['plate', '#3fa63a', '#ef8a1f', '#8a6a4a']],
  ['stuffed_mushrooms', 'Cogumelos recheados', 'Stuffed Mushrooms', 'vegetables', 'oven', 'baking_tray', { [MUSH]: 3, cheese: 1, minced_garlic: 1, breadcrumbs: 1 }, 'plate', 1, 220, [8, 0.7], ['plate', '#8a6a4a', '#f6d04a', '#d9a85a']],
  ['potato_salad', 'Salada de maionese', 'Potato Salad', 'vegetables', 'stove', 'cooking_pot', { 'minecraft:potato': 2, 'minecraft:carrot': 1, mayonnaise: 1, parsley: 1, [W]: 1 }, BOWL, 2, 220, [8, 0.7], ['bowl', '#f7f0c8', '#ef8a1f', '#4caf3c']],
  ['roasted_pumpkin', 'Abóbora assada com ervas', 'Herb Roasted Pumpkin', 'vegetables', 'oven', 'baking_tray', { 'minecraft:pumpkin': 1, butter: 1, minced_garlic: 1, parsley: 1 }, 'plate', 2, 260, [7, 0.6], ['plate', '#ef9a2a', '#f6c06a', '#4caf3c']],
  ['bruschetta', 'Bruschetta', 'Bruschetta', 'vegetables', 'oven', 'baking_tray', { bread_slice: 2, sliced_tomato: 1, minced_garlic: 1, basil: 1 }, null, 2, 140, [5, 0.6], ['toast', '#d9a85a', '#d8372b', '#2f8f3a']],
  // Bolos e sobremesas
  ['chocolate_cake', 'Bolo de chocolate', 'Chocolate Cake', 'desserts', 'oven', 'baking_tray', { cake_batter: 1, cocoa_powder: 1 }, null, 4, 300, [4, 0.4], ['cake', '#5a2e1a', '#7a4428', '#3a1a0e']],
  ['carrot_cake', 'Bolo de cenoura com chocolate', 'Carrot Cake with Chocolate', 'desserts', 'oven', 'baking_tray', { cake_batter: 1, 'minecraft:carrot': 2, chocolate: 1 }, null, 4, 300, [5, 0.4], ['cake', '#ef9a2a', '#f6c06a', '#5a2e1a']],
  ['corn_cake', 'Bolo de fubá', 'Cornmeal Cake', 'desserts', 'oven', 'baking_tray', { cornmeal: 2, [EGG]: 1, [SUGAR]: 1, [M]: 1, butter: 1 }, null, 4, 300, [4, 0.4], ['cake', '#f2c933', '#f7e07a', '#e0a020']],
  ['strawberry_cake', 'Bolo de morango', 'Strawberry Cake', 'desserts', 'oven', 'baking_tray', { cake_batter: 1, strawberry: 2, cream: 1 }, null, 4, 300, [5, 0.4], ['cake', '#f6e2a8', '#fbf8ee', '#e0313f']],
  ['pudim', 'Pudim de leite', 'Milk Flan', 'desserts', 'oven', 'baking_tray', { condensed_milk: 1, [EGG]: 2, [M]: 1, [SUGAR]: 1 }, 'plate', 2, 320, [6, 0.5], ['flan', '#f2d48a', '#b8641a', '#fbf4d8']],
  ['lemon_mousse', 'Mousse de limão', 'Lemon Mousse', 'desserts', 'fridge', null, { lemon_mousse_mix: 1 }, null, 1, 1200, [5, 0.4], ['glass', '#eef5a8', '#ffffff', '#7cc94a']],
  ['strawberry_ice_cream', 'Sorvete de morango', 'Strawberry Ice Cream', 'desserts', 'fridge', null, { ice_cream_mix: 1 }, null, 1, 1600, [4, 0.3], ['icecream', '#f7a8b8', '#f2c97a', '#e0313f']],
  ['fruit_salad', 'Salada de frutas', 'Fruit Salad', 'desserts', 'counter', null, { apple_slices: 1, orange: 1, peach: 1, strawberry: 1, cherry: 1 }, BOWL, 2, 100, [7, 0.5], ['bowl', '#f28a1c', '#e0313f', '#f5a36a']],
  ['rice_pudding', 'Arroz-doce', 'Rice Pudding', 'desserts', 'stove', 'cooking_pot', { washed_rice: 1, [M]: 1, [SUGAR]: 1, condensed_milk: 1 }, BOWL, 2, 260, [7, 0.5], ['bowl', '#f7f0d8', '#fbf8ee', '#c8843a']],
  // Tortas e doces
  ['apple_pie', 'Torta de maçã', 'Apple Pie', 'sweets', 'oven', 'baking_tray', { pie_crust: 1, 'minecraft:apple': 2, [SUGAR]: 1, butter: 1 }, null, 1, 320, [10, 0.5], ['pie', '#e0a850', '#d8262a', '#b8843a']],
  ['lemon_pie', 'Torta de limão', 'Lemon Pie', 'sweets', 'oven', 'baking_tray', { pie_crust: 1, lemon: 2, condensed_milk: 1, [EGG]: 1 }, null, 1, 320, [9, 0.5], ['pie', '#e0a850', '#eef5a8', '#fbf8ee']],
  ['chicken_pot_pie', 'Empadão de frango', 'Chicken Pot Pie', 'sweets', 'oven', 'baking_tray', { pie_crust: 2, chicken_pieces: 1, corn: 1, cream: 1 }, null, 1, 340, [13, 0.8], ['pie', '#e0a850', '#f2c933', '#b8843a']],
  ['cherry_pie', 'Torta de cereja', 'Cherry Pie', 'sweets', 'oven', 'baking_tray', { pie_crust: 1, cherry: 3, [SUGAR]: 1 }, null, 1, 320, [9, 0.5], ['pie', '#e0a850', '#a3122a', '#b8843a']],
  ['brigadeiro', 'Brigadeiro', 'Brigadeiro', 'sweets', 'stove', 'cooking_pot', { condensed_milk: 1, cocoa_powder: 1, butter: 1 }, null, 6, 200, [2, 0.3], ['balls', '#4a2414', '#6a3a22', '#2c140a']],
  ['chocolate_chip_cookies', 'Cookies com gotas de chocolate', 'Chocolate Chip Cookies', 'sweets', 'oven', 'baking_tray', { flour: 1, butter: 1, [SUGAR]: 1, [EGG]: 1, chocolate: 1 }, null, 8, 200, [2, 0.2], ['cookie', '#d9a85a', '#efcf8a', '#4a2414']],
  ['churros', 'Churros de doce de leite', 'Dulce de Leche Churros', 'sweets', 'stove', 'frying_pan', { flour: 2, [W]: 1, butter: 1, [SUGAR]: 1, doce_de_leite: 1 }, null, 4, 200, [4, 0.4], ['churro', '#c8842a', '#e8b06a', '#b8743a']],
  ['rabanada', 'Rabanada', 'Brazilian French Toast', 'sweets', 'stove', 'frying_pan', { bread_slice: 2, [EGG]: 1, [M]: 1, [SUGAR]: 1 }, null, 2, 160, [5, 0.5], ['toast', '#d9973a', '#fbf4d8', '#b8743a']],
  ['peach_in_syrup', 'Pêssego em calda', 'Peaches in Syrup', 'sweets', 'stove', 'cooking_pot', { peach: 3, [SUGAR]: 2, [W]: 1 }, BOWL, 2, 240, [6, 0.4], ['bowl', '#f5a36a', '#f7d0a0', '#f2c97a']],
  // Bebidas
  ['orange_juice', 'Suco de laranja', 'Orange Juice', 'drinks', 'blender', null, { orange: 3 }, BOTTLE, 1, 100, [4, 0.4], ['bottle', '#f29a1c', '#f7c060', '#ffffff']],
  ['lemonade', 'Limonada', 'Lemonade', 'drinks', 'blender', null, { lemon: 2, [SUGAR]: 1, [W]: 1 }, BOTTLE, 2, 100, [3, 0.3], ['bottle', '#eef5a8', '#f7f9d0', '#7cc94a']],
  ['strawberry_milkshake', 'Milk-shake de morango', 'Strawberry Milkshake', 'drinks', 'blender', null, { strawberry: 2, [M]: 1, [SUGAR]: 1 }, BOTTLE, 2, 120, [5, 0.4], ['bottle', '#f7a8b8', '#fbd0da', '#e0313f']],
  ['fruit_smoothie', 'Vitamina de frutas', 'Fruit Smoothie', 'drinks', 'blender', null, { 'minecraft:apple': 1, peach: 1, [M]: 1, 'minecraft:honey_bottle': 1 }, BOTTLE, 2, 120, [6, 0.5], ['bottle', '#f5c08a', '#fbe0c0', '#f28a1c']],
  ['sugarcane_juice', 'Caldo de cana com limão', 'Sugarcane Juice with Lemon', 'drinks', 'blender', null, { 'minecraft:sugar_cane': 3, lemon: 1 }, BOTTLE, 1, 100, [4, 0.3], ['bottle', '#c8d870', '#e8f0a8', '#e7e23a']],
  ['coffee', 'Café coado', 'Brewed Coffee', 'drinks', 'stove', 'cooking_pot', { ground_coffee: 1, [W]: 1, [SUGAR]: 1 }, 'cup', 3, 160, [2, 0.4], ['cup', '#3a1e10', '#5a3420', '#ffffff']],
  ['cafe_com_leite', 'Café com leite', 'Coffee with Milk', 'drinks', 'stove', 'cooking_pot', { ground_coffee: 1, [M]: 1, [SUGAR]: 1 }, 'cup', 3, 160, [4, 0.5], ['cup', '#a8784a', '#c89a6a', '#ffffff']],
  ['hot_chocolate', 'Chocolate quente', 'Hot Chocolate', 'drinks', 'stove', 'cooking_pot', { chocolate: 1, [M]: 1, [SUGAR]: 1 }, 'cup', 3, 180, [5, 0.5], ['cup', '#5a2e1a', '#7a4428', '#fbf8ee']],
  ['lemon_honey_tea', 'Chá de limão com mel', 'Lemon Honey Tea', 'drinks', 'stove', 'cooking_pot', { lemon: 1, 'minecraft:honey_bottle': 1, [W]: 1 }, 'cup', 3, 160, [3, 0.4], ['cup', '#e8c85a', '#f2dc8a', '#e7e23a']],
];

// ---------------------------------------------------------------- de onde vêm os ingredientes "de fora"
// Itens vanilla e matérias-primas do mod que não são produzidos por receita de culinária.
export const VANILLA_SOURCES = {
  'minecraft:milk_bucket': ['Ordenhe uma vaca ou cabra com um balde (o balde volta vazio)', 'Milk a cow or goat with a bucket (the bucket comes back empty)'],
  'minecraft:water_bucket': ['Encha um balde em qualquer água (o balde volta vazio)', 'Fill a bucket from any water (the bucket comes back empty)'],
  'minecraft:egg': ['Galinhas botam ovos sozinhas', 'Chickens lay eggs on their own'],
  'minecraft:beef': ['Vacas', 'Cows'],
  'minecraft:porkchop': ['Porcos', 'Pigs'],
  'minecraft:cooked_porkchop': ['Asse uma costeleta de porco na fornalha comum', 'Smelt a raw porkchop in a furnace'],
  'minecraft:chicken': ['Galinhas', 'Chickens'],
  'minecraft:mutton': ['Ovelhas', 'Sheep'],
  'minecraft:rabbit': ['Coelhos', 'Rabbits'],
  'minecraft:cod': ['Pesque com vara ou mate bacalhaus no oceano', 'Fish with a rod or catch cod in the ocean'],
  'minecraft:salmon': ['Pesque com vara ou pegue salmões em rios e oceanos frios', 'Fish with a rod or catch salmon in rivers and cold oceans'],
  'minecraft:honey_bottle': ['Use um frasco de vidro numa colmeia cheia (o frasco volta)', 'Use a glass bottle on a full beehive (the bottle comes back)'],
  'minecraft:sugar': ['Moa cana-de-açúcar no moedor (ou faça na bancada de trabalho)', 'Grind sugar cane in the hand mill (or craft it)'],
  'minecraft:sugar_cane': ['Cresce na beira da água; plante para multiplicar', 'Grows next to water; plant it to multiply'],
  'minecraft:cocoa_beans': ['Cacau nos troncos de árvore da selva', 'Cocoa pods on jungle tree trunks'],
  'minecraft:brown_mushroom': ['Lugares escuros, pântanos e florestas escuras', 'Dark places, swamps and dark forests'],
  'minecraft:wheat': ['Lavoura de trigo (sementes da grama)', 'Wheat crop (seeds from grass)'],
  'minecraft:bread': ['Bancada de trabalho: 3 trigos', 'Crafting table: 3 wheat'],
  'minecraft:carrot': ['Lavoura de cenoura; aldeias e zumbis', 'Carrot crop; villages and zombies'],
  'minecraft:potato': ['Lavoura de batata; aldeias e zumbis', 'Potato crop; villages and zombies'],
  'minecraft:beetroot': ['Lavoura de beterraba (sementes em aldeias e baús)', 'Beetroot crop (seeds in villages and chests)'],
  'minecraft:pumpkin': ['Abóboras nascem na grama; plante as sementes', 'Pumpkins grow on grass; plant the seeds'],
  'minecraft:apple': ['Colha a macieira do mod (clique direito) ou ache em folhas de carvalho', 'Harvest the mod\'s apple tree (right-click) or find in oak leaves'],
  'minecraft:dried_kelp': ['Alga do oceano seca na fornalha', 'Ocean kelp smelted in a furnace'],
  'minecraft:stick': ['Bancada de trabalho: 2 tábuas', 'Crafting table: 2 planks'],
};
