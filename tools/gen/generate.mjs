// Gerador do Culinária: lê tools/gen/content.mjs e escreve src/generated/{resources,java}.
// Uso: node tools/gen/generate.mjs        (falha com mensagem clara se o catálogo tiver erro)
import { mkdirSync, rmSync, writeFileSync } from 'node:fs';
import { dirname, join, resolve } from 'node:path';
import { fileURLToPath } from 'node:url';
import * as C from './content.mjs';
import { blockTextures, cropStage, itemTexture, leavesTexture, saplingTexture, seedTexture } from '../art/shapes.mjs';

const ROOT = resolve(dirname(fileURLToPath(import.meta.url)), '..', '..');
const OUT = join(ROOT, 'src', 'generated');
const RES = join(OUT, 'resources');
const NS = 'culinaria';
const ASSETS = join(RES, 'assets', NS);
const DATA = join(RES, 'data', NS);

const errors = [];
const fail = (msg) => errors.push(msg);

// ================================================================ montagem do catálogo
const full = (id) => (id.includes(':') ? id : `${NS}:${id}`);
const path = (id) => full(id).split(':')[1];

/** Itens simples do mod: [id, {kind, pt, en, tex, food, container, drink, stack, durability, hint}] */
const items = new Map();
for (const t of C.TOOLS) items.set(t.id, { kind: 'tool', pt: t.pt, en: t.en, tex: t.tex, stack: t.stack ?? 1, durability: t.durability ?? 0, hint: t.hint });
for (const c of C.CROPS) items.set(c.id, { kind: 'ingredient', pt: c.pt, en: c.en, tex: [c.shape, c.color], food: c.food, raw: 'crop', crop: c });
for (const t of C.TREES) {
  if (t.fruit.includes(':')) continue;
  items.set(t.fruit, { kind: 'ingredient', pt: t.fruitPt, en: t.fruitEn, tex: [t.fruit, t.fruitColor], food: t.food, raw: 'tree', tree: t });
}
for (const p of C.PREPARED) items.set(p.id, { kind: 'ingredient', pt: p.pt, en: p.en, tex: p.tex, food: p.edible });

const CONTAINER_RETURNS = { plate: 'culinaria:dirty_plate', cup: 'culinaria:dirty_cup', 'minecraft:bowl': 'minecraft:bowl', 'minecraft:glass_bottle': 'minecraft:glass_bottle' };
const recipes = [];
for (const [id, station, utensil, ing, container, count, time] of C.PREP_RECIPES) {
  const name = id === 'plate' ? 'wash_plate' : id === 'cup' ? 'wash_cup' : id === 'minecraft:sugar' ? 'sugar_from_cane' : id;
  recipes.push({ name, result: full(id), station, utensil, ingredients: ing, container, count, time, category: C.PREP_CATEGORY.id, dish: false });
}
for (const [id, pt, en, cat, station, utensil, ing, container, count, time, food, tex] of C.DISHES) {
  if (items.has(id)) fail(`prato com ID repetido: ${id}`);
  const drink = container === 'cup' || container === 'minecraft:glass_bottle';
  items.set(id, { kind: 'dish', pt, en, tex, food, container: container ? CONTAINER_RETURNS[container] : null, drink, stack: 16, category: cat });
  recipes.push({ name: id, result: full(id), station, utensil, ingredients: ing, container, count, time, category: cat, dish: true });
}
const stationById = new Map(C.STATIONS.map((s) => [s.id, s]));
const cropBlocks = C.CROPS.map((c) => ({ ...c, block: `${c.crop ?? c.id}_crop`, seeds: `${c.id}_seeds` }));
const trees = C.TREES.map((t) => ({ ...t, leaves: `${t.id}_leaves`, sapling: `${t.id}_sapling`, fruitFull: full(t.fruit) }));
const furniture = C.FURNITURE;

// todos os IDs de item do mod, para conferir referências
const allModItems = new Set([...items.keys(), ...cropBlocks.map((c) => c.seeds), ...trees.map((t) => t.sapling), ...trees.map((t) => t.leaves), ...furniture.map((f) => f.id)]);
const knownItem = (id) => (id.startsWith('minecraft:') ? true : allModItems.has(id));

// ================================================================ validação
const dishCount = recipes.filter((r) => r.dish).length;
if (dishCount !== 100) fail(`o catálogo precisa de exatamente 100 pratos; tem ${dishCount}`);
for (const cat of C.CATEGORIES) if (!recipes.some((r) => r.dish && r.category === cat.id)) fail(`categoria vazia: ${cat.id}`);
const signatures = new Map();
for (const r of recipes) {
  const st = stationById.get(r.station);
  if (!st) fail(`${r.name}: estação desconhecida ${r.station}`);
  if (!knownItem(r.result.replace(`${NS}:`, '')) && !r.result.startsWith('minecraft:')) fail(`${r.name}: resultado desconhecido ${r.result}`);
  if (!C.CATEGORIES.some((c) => c.id === r.category) && r.category !== C.PREP_CATEGORY.id) fail(`${r.name}: categoria ${r.category}`);
  const keys = Object.keys(r.ingredients);
  for (const k of keys) {
    if (!knownItem(k)) fail(`${r.name}: ingrediente desconhecido ${k}`);
    if (k.startsWith('minecraft:') && !C.VANILLA_SOURCES[k]) fail(`${r.name}: item vanilla sem origem documentada ${k}`);
    if (!(r.ingredients[k] >= 1)) fail(`${r.name}: quantidade inválida de ${k}`);
  }
  if (st && st.kind !== 'menu') {
    if (keys.length !== 1 || r.ingredients[keys[0]] !== 1) fail(`${r.name}: ${r.station} aceita um único item por vez`);
    if (r.container) fail(`${r.name}: ${r.station} não usa recipiente`);
  }
  if (st && st.kind === 'menu' && keys.length > 6) fail(`${r.name}: mais de 6 ingredientes diferentes`);
  if (r.utensil && !(st && (st.utensils || []).includes(r.utensil))) fail(`${r.name}: utensílio ${r.utensil} não serve em ${r.station}`);
  if (st && st.id === 'stove' && !r.utensil) fail(`${r.name}: o fogão precisa de panela ou frigideira`);
  if (st && st.id === 'oven' && r.utensil !== 'baking_tray') fail(`${r.name}: o forno precisa da forma`);
  if (r.container && !CONTAINER_RETURNS[r.container]) fail(`${r.name}: recipiente desconhecido ${r.container}`);
  if (r.count < 1 || r.count > 64) fail(`${r.name}: quantidade produzida inválida`);
  const sig = `${r.station}|${keys.map(full).sort().join(',')}`;
  if (signatures.has(sig)) fail(`${r.name} e ${signatures.get(sig)} têm os mesmos ingredientes na mesma estação`);
  signatures.set(sig, r.name);
}
// alcançabilidade: tudo sai de fontes cruas por alguma cadeia de receitas
const obtainable = new Set();
for (const k of Object.keys(C.VANILLA_SOURCES)) obtainable.add(k);
for (const [id, it] of items) if (it.raw || it.kind === 'tool') obtainable.add(full(id));
let changed = true;
while (changed) {
  changed = false;
  for (const r of recipes) {
    if (obtainable.has(r.result)) continue;
    const ok = Object.keys(r.ingredients).every((k) => obtainable.has(full(k))) && (!r.container || r.container === 'plate' || r.container === 'cup' || obtainable.has(r.container) || r.container.startsWith('minecraft:'));
    if (ok) {
      obtainable.add(r.result);
      changed = true;
    }
  }
}
for (const r of recipes) if (!obtainable.has(r.result)) fail(`${r.name}: sem cadeia viável a partir das fontes do jogo`);
// todo ingrediente do mod é usado por alguma receita
const used = new Set(recipes.flatMap((r) => Object.keys(r.ingredients).map(full)));
for (const [id, it] of items) if (it.kind === 'ingredient' && !used.has(full(id))) fail(`ingrediente sem uso: ${id}`);
if (errors.length) {
  console.error('Erros no catálogo:\n - ' + errors.join('\n - '));
  process.exit(1);
}

// ================================================================ escrita
rmSync(OUT, { recursive: true, force: true });
let jsonCount = 0;
let pngCount = 0;
function json(file, obj) {
  mkdirSync(dirname(file), { recursive: true });
  writeFileSync(file, JSON.stringify(obj, null, 2) + '\n');
  jsonCount++;
}
function png(file, canvas) {
  mkdirSync(dirname(file), { recursive: true });
  writeFileSync(file, canvas.png());
  pngCount++;
}
const itemModel = (id, texture) => {
  json(join(ASSETS, 'models', 'item', `${id}.json`), { parent: 'minecraft:item/generated', textures: { layer0: texture } });
  json(join(ASSETS, 'items', `${id}.json`), { model: { type: 'minecraft:model', model: `${NS}:item/${id}` } });
};
const blockItem = (id, model = `${NS}:block/${id}`) => json(join(ASSETS, 'items', `${id}.json`), { model: { type: 'minecraft:model', model } });
const lang = { pt: {}, en: {} };
const tr = (key, pt, en) => {
  lang.pt[key] = pt;
  lang.en[key] = en ?? pt;
};

// ---------------------------------------------------------------- itens simples
for (const [id, it] of items) {
  const [shape, c1, c2, c3] = it.tex;
  png(join(ASSETS, 'textures', 'item', `${id}.png`), itemTexture(shape, c1, c2, c3));
  itemModel(id, `${NS}:item/${id}`);
  tr(`item.${NS}.${id}`, it.pt, it.en);
}

// ---------------------------------------------------------------- lavouras
const STAGE_OF_AGE = [0, 0, 1, 1, 2, 2, 2, 3];
for (const c of cropBlocks) {
  for (let s = 0; s < 4; s++) {
    png(join(ASSETS, 'textures', 'block', `${c.block}_stage${s}.png`), cropStage(c, s));
    json(join(ASSETS, 'models', 'block', `${c.block}_stage${s}.json`), { parent: 'minecraft:block/crop', textures: { crop: `${NS}:block/${c.block}_stage${s}` } });
  }
  const variants = {};
  STAGE_OF_AGE.forEach((s, age) => (variants[`age=${age}`] = { model: `${NS}:block/${c.block}_stage${s}` }));
  json(join(ASSETS, 'blockstates', `${c.block}.json`), { variants });
  png(join(ASSETS, 'textures', 'item', `${c.seeds}.png`), seedTexture(c.color));
  itemModel(c.seeds, `${NS}:item/${c.seeds}`);
  tr(`item.${NS}.${c.seeds}`, c.seedPt, c.seedEn);
  tr(`block.${NS}.${c.block}`, `Lavoura de ${c.pt.toLowerCase()}`, `${c.en} Crop`);
  // loot como o trigo: produto na idade 7 (1–3) + sementes extras; antes disso só a semente de volta
  const mature = { type: 'minecraft:match_block', blocks: full(c.block), state: { age: '7' } };
  json(join(DATA, 'loot_table', 'blocks', `${c.block}.json`), {
    type: 'minecraft:block',
    modifier: { type: 'minecraft:explosion_decay' },
    pools: [
      {
        rolls: 1,
        entries: [{ type: 'minecraft:alternatives', children: [
          { type: 'minecraft:item', name: full(c.id), condition: mature, modifier: [{ type: 'minecraft:set_count', count: { type: 'minecraft:uniform', min: 1, max: 3 } }] },
          { type: 'minecraft:item', name: full(c.seeds) },
        ] }],
      },
      {
        rolls: 1,
        condition: mature,
        entries: [{ type: 'minecraft:item', name: full(c.seeds), modifier: { type: 'minecraft:apply_bonus', enchantment: 'minecraft:fortune', formula: 'minecraft:binomial_with_bonus_count', parameters: { extra: 1, probability: 0.5714286 } } }],
      },
    ],
    random_sequence: `${NS}:blocks/${c.block}`,
  });
}

// ---------------------------------------------------------------- árvores frutíferas
const SHEAR_OR_SILK = { type: 'minecraft:any_of', terms: ['minecraft:tool/can_shear', 'minecraft:tool/can_silk_touch'] };
for (const t of trees) {
  for (let age = 0; age < 4; age++) {
    png(join(ASSETS, 'textures', 'block', `${t.leaves}_${age}.png`), leavesTexture(t, age));
    json(join(ASSETS, 'models', 'block', `${t.leaves}_${age}.json`), { parent: 'minecraft:block/cube_all', textures: { all: `${NS}:block/${t.leaves}_${age}` } });
  }
  const variants = { 'fruiting=false': { model: `${NS}:block/${t.leaves}_0` } };
  for (let age = 0; age < 4; age++) variants[`age=${age},fruiting=true`] = { model: `${NS}:block/${t.leaves}_${age}` };
  json(join(ASSETS, 'blockstates', `${t.leaves}.json`), { variants });
  blockItem(t.leaves, `${NS}:block/${t.leaves}_3`);
  png(join(ASSETS, 'textures', 'block', `${t.sapling}.png`), saplingTexture(t));
  json(join(ASSETS, 'models', 'block', `${t.sapling}.json`), { parent: 'minecraft:block/cross', textures: { cross: `${NS}:block/${t.sapling}` } });
  json(join(ASSETS, 'blockstates', `${t.sapling}.json`), { variants: { '': { model: `${NS}:block/${t.sapling}` } } });
  itemModel(t.sapling, `${NS}:block/${t.sapling}`);
  tr(`block.${NS}.${t.leaves}`, `Folhas de ${t.pt.toLowerCase()}`, `${t.en} Leaves`);
  tr(`block.${NS}.${t.sapling}`, `Muda de ${t.pt.toLowerCase()}`, `${t.en} Sapling`);
  // folhas: com tesoura/toque de seda a própria folha; senão muda (5%), gravetos e, se madura, a fruta
  const ripe = { type: 'minecraft:match_block', blocks: full(t.leaves), state: { age: '3', fruiting: 'true' } };
  json(join(DATA, 'loot_table', 'blocks', `${t.leaves}.json`), {
    type: 'minecraft:block',
    pools: [
      { rolls: 1, entries: [{ type: 'minecraft:alternatives', children: [
        { type: 'minecraft:item', name: full(t.leaves), condition: SHEAR_OR_SILK },
        { type: 'minecraft:item', name: full(t.sapling), condition: { type: 'minecraft:all_of', terms: [{ type: 'minecraft:survives_explosion' }, { type: 'minecraft:table_bonus', enchantment: 'minecraft:fortune', chances: [0.05, 0.0625, 0.083333336, 0.1] }] } },
      ] }] },
      { rolls: 1, condition: { type: 'minecraft:inverted', term: SHEAR_OR_SILK }, entries: [{ type: 'minecraft:item', name: 'minecraft:stick', condition: { type: 'minecraft:table_bonus', enchantment: 'minecraft:fortune', chances: [0.02, 0.022222223, 0.025, 0.033333335, 0.1] }, modifier: [{ type: 'minecraft:set_count', count: { type: 'minecraft:uniform', min: 1, max: 2 } }, { type: 'minecraft:explosion_decay' }] }] },
      { rolls: 1, condition: ripe, entries: [{ type: 'minecraft:item', name: t.fruitFull, modifier: { type: 'minecraft:explosion_decay' } }] },
    ],
    random_sequence: `${NS}:blocks/${t.leaves}`,
  });
  json(join(DATA, 'loot_table', 'blocks', `${t.sapling}.json`), selfDrop(t.sapling));
  // árvore: tronco de carvalho, folhas do mod (1 em 4 dá fruto)
  json(join(DATA, 'worldgen', 'feature', `${t.id}_tree.json`), {
    type: 'minecraft:tree',
    below_trunk_provider: 'minecraft:soil_beneath_tree',
    decorators: [],
    foliage_placer: { type: 'minecraft:blob_foliage_placer', height: 3, offset: 0, radius: 2 },
    foliage_provider: { type: 'minecraft:weighted', entries: [
      { data: { id: full(t.leaves), properties: { age: '0', distance: '7', fruiting: 'false', persistent: 'false', waterlogged: 'false' } }, weight: 3 },
      { data: { id: full(t.leaves), properties: { age: '3', distance: '7', fruiting: 'true', persistent: 'false', waterlogged: 'false' } }, weight: 1 },
    ] },
    ignore_vines: true,
    minimum_size: { type: 'minecraft:two_layers_feature_size' },
    trunk_placer: { type: 'minecraft:straight_trunk_placer', base_height: 4, height_rand_a: 1, height_rand_b: 0 },
    trunk_provider: { id: 'minecraft:oak_log', properties: { axis: 'y' } },
  });
  json(join(DATA, 'worldgen', 'placed_feature', `${t.id}_tree_wild.json`), {
    feature: `${NS}:${t.id}_tree`,
    placement: [
      { type: 'minecraft:rarity_filter', chance: 14 },
      { type: 'minecraft:in_square' },
      { type: 'minecraft:surface_water_depth_filter', max_water_depth: 0 },
      { type: 'minecraft:heightmap', heightmap: 'OCEAN_FLOOR' },
      { type: 'minecraft:block_predicate_filter', predicate: { type: 'minecraft:would_survive', state: 'minecraft:oak_sapling' } },
      { type: 'minecraft:biome' },
    ],
  });
  const tagBiomes = t.biomes.filter((b) => b.startsWith('#'));
  const plainBiomes = t.biomes.filter((b) => !b.startsWith('#'));
  const biomeSets = [...tagBiomes, ...(plainBiomes.length ? [plainBiomes] : [])];
  biomeSets.forEach((biomes, i) => {
    json(join(DATA, 'forge', 'biome_modifier', `${t.id}_trees_${i}.json`), { type: 'forge:add_features', biomes, features: `${NS}:${t.id}_tree_wild`, step: 'vegetal_decoration' });
  });
}

// ---------------------------------------------------------------- móveis e equipamentos
function selfDrop(id) {
  return { type: 'minecraft:block', pools: [{ rolls: 1, condition: { type: 'minecraft:survives_explosion' }, entries: [{ type: 'minecraft:item', name: full(id) }] }], random_sequence: `${NS}:blocks/${id}` };
}
const FURNITURE_TEX = ['counter_top', 'counter_front', 'counter_side', 'stove_top', 'stove_front', 'stove_front_lit', 'stove_side', 'oven_front', 'oven_front_lit', 'oven_side', 'oven_top',
  'fridge_front', 'fridge_side', 'fridge_top', 'cabinet_front', 'cabinet_side', 'pantry_front', 'pantry_side', 'wood_top', 'cutting_board', 'sink_basin', 'sink_side', 'sink_front', 'sink_water',
  'mill_stone', 'mill_wood', 'blender_base', 'blender_glass'];
for (const name of FURNITURE_TEX) png(join(ASSETS, 'textures', 'block', `${name}.png`), blockTextures[name]());
const tex = (n) => `${NS}:block/${n}`;
const orientable = (top, front, side) => ({ parent: 'minecraft:block/orientable', textures: { top: tex(top), front: tex(front), side: tex(side), particle: tex(side) } });
const box = (from, to, texture, extra = {}) => {
  const faces = {};
  for (const f of ['north', 'south', 'east', 'west', 'up', 'down']) faces[f] = { texture: extra[f] ?? texture };
  return { from, to, faces };
};
const elements = (particle, els, textures) => ({ parent: 'minecraft:block/block', textures: { particle: tex(particle), ...textures }, elements: els });
const FACING_Y = { north: 0, east: 90, south: 180, west: 270 };
function facingStates(model, extraKey = '') {
  const variants = {};
  for (const [f, y] of Object.entries(FACING_Y)) variants[`facing=${f}${extraKey}`] = { model, ...(y ? { y } : {}) };
  return variants;
}
const models = {
  counter: orientable('counter_top', 'counter_front', 'counter_side'),
  stove: orientable('stove_top', 'stove_front', 'stove_side'),
  stove_lit: orientable('stove_top', 'stove_front_lit', 'stove_side'),
  oven: orientable('oven_top', 'oven_front', 'oven_side'),
  oven_lit: orientable('oven_top', 'oven_front_lit', 'oven_side'),
  fridge: orientable('fridge_top', 'fridge_front', 'fridge_side'),
  cabinet: orientable('wood_top', 'cabinet_front', 'cabinet_side'),
  pantry: orientable('wood_top', 'pantry_front', 'pantry_side'),
  cutting_board: elements('cutting_board', [box([2, 0, 4], [14, 1.5, 12], '#board')], { board: tex('cutting_board') }),
  mill: elements('mill_stone', [
    box([2, 0, 2], [14, 6, 14], '#stone'),
    box([3, 6, 3], [13, 10, 13], '#stone'),
    box([7, 10, 7], [9, 13, 9], '#wood'),
    box([7, 12, 7], [14, 13.5, 9], '#wood'),
    box([12.5, 13.5, 7.5], [14, 16, 9], '#wood'),
  ], { stone: tex('mill_stone'), wood: tex('mill_wood') }),
  blender: elements('blender_base', [
    box([4, 0, 4], [12, 4, 12], '#base'),
    box([5, 4, 5], [11, 13, 11], '#glass'),
    box([4.5, 13, 4.5], [11.5, 14.5, 11.5], '#lid'),
  ], { base: tex('blender_base'), glass: tex('blender_glass'), lid: tex('stove_top') }),
};
const sinkEls = (water) => [
  box([0, 0, 0], [16, 10, 16], '#side', { north: '#front', up: '#basin' }),
  box([0, 10, 0], [16, 16, 2], '#side', { up: '#basin' }),
  box([0, 10, 14], [16, 16, 16], '#side', { up: '#basin' }),
  box([0, 10, 2], [2, 16, 14], '#side', { up: '#basin' }),
  box([14, 10, 2], [16, 16, 14], '#side', { up: '#basin' }),
  ...(water ? [box([2, 10, 2], [14, water, 14], '#water')] : []),
];
const sinkTextures = { side: tex('sink_side'), front: tex('sink_front'), basin: tex('sink_basin'), water: tex('sink_water') };
models.sink = elements('sink_side', sinkEls(0), sinkTextures);
models.sink_low = elements('sink_side', sinkEls(12), sinkTextures);
models.sink_full = elements('sink_side', sinkEls(15), sinkTextures);
for (const [name, m] of Object.entries(models)) json(join(ASSETS, 'models', 'block', `${name}.json`), m);

for (const f of furniture) {
  tr(`block.${NS}.${f.id}`, f.pt, f.en);
  tr(`container.${NS}.${f.id}`, f.pt, f.en);
  blockItem(f.id);
  json(join(DATA, 'loot_table', 'blocks', `${f.id}.json`), selfDrop(f.id));
  let variants;
  if (f.id === 'stove' || f.id === 'oven') variants = { ...facingStates(`${NS}:block/${f.id}`, ',lit=false'), ...facingStates(`${NS}:block/${f.id}_lit`, ',lit=true') };
  else if (f.id === 'sink') {
    variants = {};
    for (let w = 0; w <= 8; w++) Object.assign(variants, facingStates(`${NS}:block/${w === 0 ? 'sink' : w <= 4 ? 'sink_low' : 'sink_full'}`, `,water=${w}`));
  } else variants = facingStates(`${NS}:block/${f.id}`);
  json(join(ASSETS, 'blockstates', `${f.id}.json`), { variants });
}

// ---------------------------------------------------------------- receitas
for (const r of recipes) {
  const body = {
    type: `${NS}:cooking`,
    station: r.station,
    ...(r.utensil ? { utensil: full(r.utensil) } : {}),
    ingredients: Object.entries(r.ingredients).map(([k, n]) => ({ item: full(k), count: n })),
    ...(r.container ? { container: full(r.container) } : {}),
    result: { id: r.result, count: r.count },
    time: r.time,
    category: r.category,
  };
  json(join(DATA, 'recipe', 'cooking', `${r.name}.json`), body);
}
const ingredientJson = (k) => (k.startsWith('#') ? k : full(k));
for (const r of C.CRAFTING) {
  const result = { id: full(r.out), ...(r.count ? { count: r.count } : {}) };
  if (r.shapeless) json(join(DATA, 'recipe', `${r.out}.json`), { type: 'minecraft:crafting_shapeless', category: 'misc', ingredients: r.shapeless.map(ingredientJson), result });
  else json(join(DATA, 'recipe', `${r.out}.json`), { type: 'minecraft:crafting_shaped', category: 'misc', key: Object.fromEntries(Object.entries(r.key).map(([k, v]) => [k, ingredientJson(v)])), pattern: r.pattern, result });
}
for (const t of trees) {
  json(join(DATA, 'recipe', `${t.sapling}.json`), { type: 'minecraft:crafting_shapeless', category: 'misc', ingredients: ['minecraft:oak_sapling', t.fruitFull], result: { id: full(t.sapling) } });
}

// ---------------------------------------------------------------- tags
const tag = (dir, name, values, ns = NS) => json(join(RES, 'data', ns, 'tags', dir, `${name}.json`), { replace: false, values });
tag('block', 'leaves', trees.map((t) => full(t.leaves)), 'minecraft');
tag('item', 'leaves', trees.map((t) => full(t.leaves)), 'minecraft');
tag('block', 'saplings', trees.map((t) => full(t.sapling)), 'minecraft');
tag('item', 'saplings', trees.map((t) => full(t.sapling)), 'minecraft');
tag('block', 'crops', cropBlocks.map((c) => full(c.block)), 'minecraft');
tag('block', 'mineable/hoe', trees.map((t) => full(t.leaves)), 'minecraft');
tag('block', 'mineable/axe', furniture.filter((f) => ['cutting_board', 'counter', 'cabinet', 'pantry'].includes(f.id)).map((f) => full(f.id)), 'minecraft');
tag('block', 'mineable/pickaxe', furniture.filter((f) => !['cutting_board', 'counter', 'cabinet', 'pantry'].includes(f.id)).map((f) => full(f.id)), 'minecraft');
// o que a geladeira aceita: ingredientes, pratos e matérias-primas de cozinha
const fridgeItems = new Set([...items.entries()].filter(([, it]) => it.kind !== 'tool').map(([id]) => full(id)));
for (const k of Object.keys(C.VANILLA_SOURCES)) if (!['minecraft:stick', 'minecraft:water_bucket', 'minecraft:sugar_cane'].includes(k)) fridgeItems.add(k);
tag('item', 'fridge_storable', [...fridgeItems].sort());
// entradas das estações de clique (o cliente prevê o clique por elas)
for (const st of ['cutting_board', 'sink', 'mill']) tag('item', `station/${st}`, [...new Set(recipes.filter((r) => r.station === st).flatMap((r) => Object.keys(r.ingredients).map(full)))].sort());

// ================================================================ traduções
const UI = {
  [`itemGroup.${NS}`]: ['Culinária', 'Culinária'],
  [`itemGroup.${NS}.dishes`]: ['Culinária: pratos', 'Culinária: Dishes'],
  [`${NS}.gui.ingredients`]: ['Ingredientes', 'Ingredients'],
  [`${NS}.gui.utensil`]: ['Utensílio', 'Utensil'],
  [`${NS}.gui.container`]: ['Recipiente', 'Container'],
  [`${NS}.gui.fuel`]: ['Combustível', 'Fuel'],
  [`${NS}.gui.leftovers`]: ['Sobras', 'Leftovers'],
  [`${NS}.status.idle`]: ['Coloque os ingredientes', 'Add the ingredients'],
  [`${NS}.status.no_recipe`]: ['Nenhuma receita com estes ingredientes', 'No recipe uses exactly these ingredients'],
  [`${NS}.status.needs_utensil`]: ['Falta o utensílio: %s', 'Missing utensil: %s'],
  [`${NS}.status.needs_container`]: ['Falta o recipiente: %s (%s)', 'Missing container: %s (%s)'],
  [`${NS}.status.no_fuel`]: ['Sem combustível: coloque carvão ou lenha', 'No fuel: add coal or wood'],
  [`${NS}.status.output_full`]: ['Retire o que está pronto', 'Take out the finished food'],
  [`${NS}.status.leftovers_full`]: ['Retire as sobras (baldes e frascos)', 'Take out the leftovers (buckets and bottles)'],
  [`${NS}.status.working`]: ['Preparando: %s', 'Preparing: %s'],
  [`${NS}.status.more_needed`]: ['Faltam ingredientes para: %s', 'Not enough ingredients for: %s'],
  [`${NS}.board.placed`]: ['Use a faca para cortar (%s de %s)', 'Use the knife to cut (%s of %s)'],
  [`${NS}.board.needs_knife`]: ['Segure uma faca de cozinha para cortar', 'Hold a kitchen knife to cut'],
  [`${NS}.board.not_cuttable`]: ['Isso não se corta na tábua', 'That cannot be cut on the board'],
  [`${NS}.mill.progress`]: ['Moendo: volta %s de %s', 'Grinding: turn %s of %s'],
  [`${NS}.mill.not_millable`]: ['Isso não pode ser moído', 'That cannot be ground'],
  [`${NS}.board.empty`]: ['Ponha um ingrediente na tábua primeiro', 'Put an ingredient on the board first'],
  [`${NS}.sink.water`]: ['Água na pia: %s de 8', 'Water in the sink: %s of 8'],
  [`${NS}.sink.infinite`]: ['Pia ligada à água (água abaixo): não acaba', 'Sink connected to water (water below): never runs out'],
  [`${NS}.sink.empty`]: ['A pia está sem água: use um balde de água', 'The sink is empty: use a water bucket'],
  [`${NS}.sink.not_washable`]: ['Isso não precisa ser lavado', 'That does not need washing'],
  [`${NS}.fridge.chilling`]: ['Gelando na geladeira', 'Chilling in the fridge'],
  [`${NS}.book.title`]: ['Livro de receitas', 'Recipe Book'],
  [`${NS}.book.search`]: ['Pesquisar pelo nome…', 'Search by name…'],
  [`${NS}.book.all`]: ['Todas', 'All'],
  [`${NS}.book.count`]: ['%s receitas', '%s recipes'],
  [`${NS}.book.can_make`]: ['Só o que dá para fazer', 'Only what I can make'],
  [`${NS}.book.station`]: ['Equipamento: %s', 'Equipment: %s'],
  [`${NS}.book.utensil`]: ['Utensílio: %s', 'Utensil: %s'],
  [`${NS}.book.container`]: ['Servir em: %s (%s)', 'Serve in: %s (%s)'],
  [`${NS}.book.time`]: ['Tempo: %s', 'Time: %s'],
  [`${NS}.book.makes`]: ['Rende %s', 'Makes %s'],
  [`${NS}.book.ingredients`]: ['Ingredientes (tenho / preciso)', 'Ingredients (have / need)'],
  [`${NS}.book.steps`]: ['Etapas', 'Steps'],
  [`${NS}.book.step`]: ['%s. %s %s — %s', '%s. %s %s — %s'],
  [`${NS}.book.source`]: ['Onde conseguir: %s', 'Where to get: %s'],
  [`${NS}.book.made_by`]: ['Clique para ver como fazer', 'Click to see how to make it'],
  [`${NS}.book.missing`]: ['Falta: %s', 'Missing: %s'],
  [`${NS}.book.ready`]: ['Você tem tudo para a última etapa', 'You have everything for the last step'],
  [`${NS}.book.nearby`]: ['Contando o inventário e os móveis num raio de 8 blocos', 'Counting your inventory and furniture within 8 blocks'],
  [`${NS}.book.empty`]: ['Nenhuma receita encontrada', 'No recipes found'],
  [`${NS}.book.loading`]: ['O catálogo ainda não chegou do servidor', 'The catalog has not arrived from the server yet'],
  [`${NS}.book.back`]: ['Voltar', 'Back'],
  [`${NS}.book.dish`]: ['Prato', 'Dish'],
  [`${NS}.unit.ticks`]: ['%s s', '%s s'],
  [`${NS}.unit.cuts`]: ['%s cortes', '%s cuts'],
  [`${NS}.unit.turns`]: ['%s voltas', '%s turns'],
  [`${NS}.unit.wash`]: ['1 lavagem', '1 wash'],
  [`${NS}.config.title`]: ['Configuração do Culinária', 'Culinária Config'],
};
for (const [k, [pt, en]] of Object.entries(UI)) tr(k, pt, en);
for (const cat of [...C.CATEGORIES, C.PREP_CATEGORY]) tr(`${NS}.category.${cat.id}`, cat.pt, cat.en);
for (const s of C.STATIONS) {
  tr(`${NS}.station.${s.id}`, s.pt, s.en);
  tr(`${NS}.verb.${s.id}`, s.verbPt, s.verbEn);
}
// origem dos ingredientes "de fora"
const sourceKey = (id) => `${NS}.source.${full(id).replace(':', '.')}`;
for (const [k, [pt, en]] of Object.entries(C.VANILLA_SOURCES)) tr(sourceKey(k), pt, en);
for (const c of cropBlocks) {
  tr(sourceKey(c.id), `Colha a lavoura madura (plante ${c.seedPt.toLowerCase()} em terra arada)`, `Harvest the ripe crop (plant ${c.seedEn.toLowerCase()} on farmland)`);
  tr(sourceKey(c.seeds), `Quebre grama${c.rare ? ' (mais rara)' : ''}; a lavoura madura devolve mais`, `Break grass${c.rare ? ' (rarer)' : ''}; the ripe crop gives more back`);
}
for (const t of trees) {
  const where = t.biomes.map((b) => b.replace('#minecraft:is_', '').replace('minecraft:', '')).join(', ');
  tr(sourceKey(t.fruit), `Clique direito nas folhas maduras da ${t.pt.toLowerCase()} (o fruto volta a crescer). Árvores: ${where}`, `Right-click ripe ${t.en.toLowerCase()} leaves (fruit grows back). Trees: ${where}`);
  tr(sourceKey(t.sapling), `Cai das folhas da ${t.pt.toLowerCase()}; ou bancada de trabalho: muda de carvalho + fruta`, `Drops from ${t.en.toLowerCase()} leaves; or craft: oak sapling + fruit`);
}
for (const t of C.TOOLS) tr(sourceKey(t.id), 'Bancada de trabalho', 'Crafting table');
// dicas de uso nos tooltips
const HINTS = {
  kitchen_knife: ['Corta ingredientes na tábua de corte', 'Cuts ingredients on the cutting board'],
  cooking_pot: ['Utensílio do fogão: ferver, cozinhar e fazer caldos', 'Stove utensil: boil, stew and simmer'],
  frying_pan: ['Utensílio do fogão: fritar e grelhar', 'Stove utensil: fry and grill'],
  baking_tray: ['Utensílio do forno: assar', 'Oven utensil: bake and roast'],
  rolling_pin: ['Utensílio da bancada: abrir massas', 'Counter utensil: roll out dough'],
  whisk: ['Utensílio da bancada: bater cremes e massas', 'Counter utensil: whisk creams and batters'],
  plate: ['Recipiente para servir pratos', 'Container to serve dishes'],
  dirty_plate: ['Lave na pia para usar de novo', 'Wash it in the sink to reuse'],
  cup: ['Recipiente para café e chás', 'Container for coffee and teas'],
  dirty_cup: ['Lave na pia para usar de novo', 'Wash it in the sink to reuse'],
  recipe_book: ['Clique direito para abrir: receitas, ingredientes e etapas', 'Right-click to open: recipes, ingredients and steps'],
  cutting_board: ['Ponha um ingrediente e corte com a faca', 'Place an ingredient and cut it with the knife'],
  sink: ['Balde de água enche; lava arroz, feijão e louça. Água embaixo = infinita', 'Water bucket fills it; washes rice, beans and dishes. Water below = endless'],
  mill: ['Ponha o ingrediente e gire com a mão vazia; agachado devolve', 'Insert the ingredient and turn with an empty hand; sneak to take back'],
  counter: ['Misturar, amassar e montar pratos', 'Mix, knead and assemble dishes'],
  stove: ['Panelas e frigideiras; usa combustível', 'Pots and pans; burns fuel'],
  oven: ['Assar com a forma; usa combustível', 'Bake with the tray; burns fuel'],
  blender: ['Sucos, vitaminas e milk-shakes', 'Juices, smoothies and milkshakes'],
  fridge: ['Guarda comida e gela mousses e sorvetes com o tempo', 'Stores food and chills mousses and ice cream over time'],
  cabinet: ['Armário com 18 espaços', 'Cabinet with 18 slots'],
  pantry: ['Despensa com 36 espaços', 'Pantry with 36 slots'],
};
for (const [id, [pt, en]] of Object.entries(HINTS)) tr(`tooltip.${NS}.${id}`, pt, en);
tr(`tooltip.${NS}.dish_category`, 'Categoria: %s', 'Category: %s');
tr(`tooltip.${NS}.returns`, 'Depois de comer, devolve: %s', 'After eating, returns: %s');
json(join(ASSETS, 'lang', 'pt_br.json'), sortObj(lang.pt));
// Pedido do dono (25/10/2026): tudo do mod em português, mesmo com o jogo em inglês. Os textos em inglês continuam no
// catálogo; para voltar a ter en_us em inglês, ponha PORTUGUESE_EVERYWHERE = false.
const PORTUGUESE_EVERYWHERE = true;
json(join(ASSETS, 'lang', 'en_us.json'), sortObj(PORTUGUESE_EVERYWHERE ? lang.pt : lang.en));
function sortObj(o) {
  return Object.fromEntries(Object.entries(o).sort(([a], [b]) => a.localeCompare(b)));
}

// ================================================================ GameTests (um test_instance por função)
export const GAME_TESTS = ['crop_grows_and_drops', 'fruit_harvest_regrows', 'fruit_leaves_persist', 'stove_cooks_recipe', 'station_status_messages', 'station_keeps_items_on_close',
  'station_persistence', 'oven_bakes_with_fuel', 'counter_assembles_dish', 'cutting_board_cuts', 'sink_washes_and_fills', 'mill_grinds', 'fridge_chills', 'storage_persists',
  'catalog_has_100_dishes', 'dish_returns_container', 'sapling_grows_fruit_tree', 'remainders_returned', 'grass_drops_seeds', 'station_hopper_faces'];
for (const name of GAME_TESTS) {
  json(join(DATA, 'test_instance', `${name}.json`), { type: 'minecraft:function', function: `${NS}:${name}`, environment: 'minecraft:default', structure: 'forge:empty9x9x9', max_ticks: 200, ...(name.startsWith('sapling') || name.startsWith('crop') ? { sky_access: true } : {}) });
}

// ================================================================ tabela Java
const jstr = (s) => (s == null ? 'null' : JSON.stringify(s));
const itemRows = [...items.entries()].map(([id, it]) => {
  const food = it.food ? `${it.food[0]}, ${it.food[1]}F` : '0, 0F';
  return `        new ItemDef(${jstr(id)}, Kind.${it.kind.toUpperCase()}, ${food}, ${jstr(it.container ?? null)}, ${it.drink ? 'true' : 'false'}, ${it.stack ?? 64}, ${it.durability ?? 0}, ${it.hint ? 'true' : 'false'}, ${jstr(it.category ?? null)})`;
});
const java = `// GERADO por tools/gen/generate.mjs a partir de tools/gen/content.mjs. Não edite à mão.
package io.github.peixeangola.culinaria.content;

import java.util.List;

/** Tabela de conteúdo gerada: itens simples, lavouras, árvores frutíferas e móveis do Culinária. */
public final class GeneratedContent {
    public enum Kind { TOOL, INGREDIENT, DISH }

    /**
     * Item simples. nutrition 0 = não é comida. container: o que sobra depois de comer (null = nada). drink: bebe em vez
     * de comer. category: categoria do prato (null fora dos pratos).
     */
    public record ItemDef(String id, Kind kind, int nutrition, float saturation, String container, boolean drink, int stackSize,
                          int durability, boolean hint, String category) {}

    /** Lavoura: bloco, produto colhido e semente (o BlockItem que planta). */
    public record CropDef(String block, String product, String seeds) {}

    /** Árvore frutífera: folhas, muda, fruta (ID completo) e feature da árvore. */
    public record TreeDef(String id, String leaves, String sapling, String fruit, String feature) {}

    /** Móvel: station é a estação de culinária (null no armário/despensa); rows de inventário nos guarda-volumes. */
    public record FurnitureDef(String id, String station, int rows, boolean hint) {}

    public static final List<ItemDef> ITEMS = List.of(
${itemRows.join(',\n')});

    public static final List<CropDef> CROPS = List.of(
${cropBlocks.map((c) => `        new CropDef(${jstr(c.block)}, ${jstr(c.id)}, ${jstr(c.seeds)})`).join(',\n')});

    public static final List<TreeDef> TREES = List.of(
${trees.map((t) => `        new TreeDef(${jstr(t.id)}, ${jstr(t.leaves)}, ${jstr(t.sapling)}, ${jstr(t.fruitFull)}, ${jstr(`${t.id}_tree`)})`).join(',\n')});

    public static final List<FurnitureDef> FURNITURE = List.of(
${furniture.map((f) => `        new FurnitureDef(${jstr(f.id)}, ${jstr(f.station ?? null)}, ${f.rows ?? 0}, true)`).join(',\n')});

    /** Sementes que caem da grama: as raras pesam 1, as demais 3. */
    public static final List<String> GRASS_SEEDS_COMMON = List.of(${cropBlocks.filter((c) => !c.rare).map((c) => jstr(c.seeds)).join(', ')});
    public static final List<String> GRASS_SEEDS_RARE = List.of(${cropBlocks.filter((c) => c.rare).map((c) => jstr(c.seeds)).join(', ')});

    /** Categorias dos pratos, na ordem do livro, e a dos preparos. */
    public static final List<String> CATEGORIES = List.of(${C.CATEGORIES.map((c) => jstr(c.id)).join(', ')});
    public static final String PREP_CATEGORY = ${jstr(C.PREP_CATEGORY.id)};
    public static final List<String> CATEGORY_ICONS = List.of(${C.CATEGORIES.map((c) => jstr(c.icon)).join(', ')});

    /** Nomes dos GameTests (um test_instance gerado para cada). */
    public static final List<String> GAME_TESTS = List.of(${GAME_TESTS.map(jstr).join(', ')});

    public static final int DISH_COUNT = ${dishCount};

    private GeneratedContent() {}
}
`;
const javaFile = join(OUT, 'java', 'io', 'github', 'peixeangola', 'culinaria', 'content', 'GeneratedContent.java');
mkdirSync(dirname(javaFile), { recursive: true });
writeFileSync(javaFile, java);

console.log(`${pngCount} texturas, ${jsonCount} arquivos JSON, ${recipes.length} receitas (${dishCount} pratos) gerados em ${OUT}`);
