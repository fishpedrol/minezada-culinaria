// Gera o datapack de cena do teste visual (tools/visual-probe/datapack). Ferramenta de desenvolvimento: o datapack só
// vai para o mundo de teste, nunca para o .jar. Uso: node tools/visual-probe/gen-datapack.mjs
import { mkdirSync, rmSync, writeFileSync } from 'node:fs';
import { dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';
import { CROPS, TREES } from '../gen/content.mjs';

const out = join(dirname(fileURLToPath(import.meta.url)), 'datapack');
rmSync(out, { recursive: true, force: true });
const write = (file, text) => {
  const path = join(out, file);
  mkdirSync(dirname(path), { recursive: true });
  writeFileSync(path, text.trimStart().replace(/\r/g, '') + '\n');
};
const fn = (name, lines) => write(`data/probe/function/${name}.mcfunction`, lines.join('\n'));

write('pack.mcmeta', JSON.stringify({ pack: { description: 'Culinaria visual probe (dev only, not shipped in the jar)', min_format: 121, max_format: 121 } }, null, 2));
write('data/minecraft/tags/function/load.json', JSON.stringify({ values: ['probe:load'] }));
write('data/minecraft/tags/function/tick.json', JSON.stringify({ values: ['probe:tick'] }));

// Linha do tempo (ticks desde que o jogador entrou). O run-probe.ps1 espera cada "say probe:<nome>".
const timeline = [
  [20, 'prepare'], [60, 'build'], [120, 'cam_kitchen'], [280, 'cam_crops'], [440, 'cam_trees'], [600, 'open_stove'],
  [900, 'open_counter'], [1100, 'open_fridge'], [1300, 'board_cut'], [1500, 'sink_wash'], [1700, 'book'], [2300, 'inventory'],
  [2700, 'harvest_tree'], [2900, 'end'],
];
fn('tick', ['execute if entity @a run scoreboard players add #t probe 1',
  ...timeline.map(([t, name]) => `execute if score #t probe matches ${t} run function probe:${name}`)]);
fn('load', ['scoreboard objectives add probe dummy', 'scoreboard players set #t probe 0', 'gamerule random_tick_speed 0',
  'gamerule advance_time false', 'gamerule advance_weather false', 'gamerule spawn_mobs false', 'time set noon', 'weather clear']);
fn('prepare', ['gamemode creative @a', 'forceload add 0 0 31 31', 'tp @a 8 160 12', 'effect clear @a']);

const kitchen = ['counter', 'stove', 'oven', 'sink', 'cutting_board', 'mill', 'blender', 'fridge', 'cabinet', 'pantry'];
const extra = { stove: ',lit=true', oven: ',lit=false', sink: ',water=8' };
fn('build', [
  'fill 0 150 0 31 150 31 minecraft:smooth_stone',
  'fill 0 151 0 31 165 31 minecraft:air',
  ...kitchen.map((id, i) => `setblock ${2 + i} 151 2 culinaria:${id}[facing=south${extra[id] ?? ''}]`),
  'data merge block 6 151 2 {item:{id:"culinaria:tomato",count:1}}',
  'data merge block 7 151 2 {item:{id:"minecraft:wheat",count:8}}',
  'fill 2 150 8 15 150 8 minecraft:farmland[moisture=7]',
  'fill 2 150 9 15 150 9 minecraft:water',
  'fill 2 150 10 9 150 10 minecraft:farmland[moisture=7]',
  ...CROPS.map((c, i) => `setblock ${2 + i} 151 8 culinaria:${c.crop ?? c.id}_crop[age=7]`),
  ...[0, 1, 2, 3, 4, 5, 6, 7].map((age) => `setblock ${2 + age} 151 10 culinaria:tomato_crop[age=${age}]`),
  'fill 0 150 15 31 150 22 minecraft:grass_block',
  ...TREES.map((t, i) => `place feature culinaria:${t.id}_tree ${3 + i * 6} 151 18`),
  'setblock 9 152 21 culinaria:orange_leaves[age=3,fruiting=true,persistent=true]',
  'setblock 10 152 21 culinaria:orange_leaves[age=1,fruiting=true,persistent=true]',
  'setblock 8 152 21 culinaria:orange_leaves[age=2,fruiting=true,persistent=true]',
]);
fn('cam_kitchen', ['tp @a 6.5 152.6 6.5 180 18', 'say probe:cam_kitchen']);
fn('cam_crops', ['tp @a 8.5 153.5 13.5 180 35', 'say probe:cam_crops']);
fn('cam_trees', ['tp @a 15.5 155 31.5 180 12', 'say probe:cam_trees']);
const fill = (x, slots) => Object.entries(slots).map(([slot, item]) => `item replace block ${x} 151 2 container.${slot} with ${item}`);
fn('open_stove', [...fill(3, { 0: 'culinaria:tomato 3', 1: 'culinaria:chopped_onion 1', 2: 'culinaria:basil 1', 3: 'minecraft:water_bucket 1',
  6: 'culinaria:cooking_pot 1', 7: 'minecraft:bowl 2', 8: 'minecraft:coal 4' }), 'clear @a', 'tp @a 3.5 151 4.2 180 25', 'say probe:open_stove']);
fn('open_counter', [...fill(2, { 0: 'culinaria:cooked_rice 1', 1: 'culinaria:cooked_beans 1' }), 'tp @a 2.5 151 4.2 180 25', 'say probe:open_counter']);
fn('open_fridge', [...fill(9, { 0: 'culinaria:lemon_mousse_mix 2', 1: 'culinaria:cheese 4', 2: 'minecraft:milk_bucket 1', 3: 'culinaria:strawberry 12',
  4: 'culinaria:feijoada 2' }), 'tp @a 9.5 151 4.2 180 25', 'say probe:open_fridge']);
fn('board_cut', ['gamemode survival @a', 'clear @a', 'item replace entity @a hotbar.0 with culinaria:kitchen_knife', 'tp @a 6.5 151 3.6 180 60',
  'say probe:board_cut']);
fn('sink_wash', ['clear @a', 'item replace entity @a hotbar.0 with culinaria:rice 4', 'tp @a 5.5 151 3.8 180 55', 'say probe:sink_wash']);
fn('book', ['gamemode creative @a', 'clear @a', 'item replace entity @a hotbar.0 with culinaria:recipe_book',
  'item replace entity @a hotbar.1 with culinaria:tomato 8', 'item replace entity @a hotbar.2 with culinaria:basil 2',
  'item replace entity @a hotbar.3 with culinaria:chopped_onion 1', 'tp @a 6.5 151 12.5 0 0', 'say probe:book']);
fn('inventory', ['gamemode survival @a', 'clear @a', 'item replace entity @a hotbar.0 with culinaria:feijoada 2',
  'item replace entity @a hotbar.1 with culinaria:coffee 3', 'item replace entity @a hotbar.2 with culinaria:kitchen_knife',
  'item replace entity @a hotbar.3 with culinaria:stove', 'item replace entity @a hotbar.4 with culinaria:orange 5',
  'item replace entity @a hotbar.5 with culinaria:tomato_seeds 8', 'tp @a 6.5 151 12.5 0 0', 'say probe:inventory']);
fn('harvest_tree', ['clear @a', 'tp @a 9.5 151 23.2 180 -12', 'say probe:harvest_tree']);
fn('end', ['say probe:end']);
console.log(`datapack em ${out}`);
