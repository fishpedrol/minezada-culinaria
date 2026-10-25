// Gera docs/CATALOGO_RECEITAS.md e docs/INGREDIENTES.md a partir de tools/gen/content.mjs (a mesma fonte do jogo).
// Uso: node tools/gen/docs.mjs
import { writeFileSync } from 'node:fs';
import { dirname, join, resolve } from 'node:path';
import { fileURLToPath } from 'node:url';
import * as C from './content.mjs';

const ROOT = resolve(dirname(fileURLToPath(import.meta.url)), '..', '..');
const VANILLA_NAMES = {
  'minecraft:milk_bucket': 'Balde de leite', 'minecraft:water_bucket': 'Balde de água', 'minecraft:egg': 'Ovo', 'minecraft:beef': 'Carne bovina crua',
  'minecraft:porkchop': 'Costeleta de porco crua', 'minecraft:cooked_porkchop': 'Costeleta de porco assada', 'minecraft:chicken': 'Frango cru',
  'minecraft:mutton': 'Carne de carneiro crua', 'minecraft:rabbit': 'Coelho cru', 'minecraft:cod': 'Bacalhau cru', 'minecraft:salmon': 'Salmão cru',
  'minecraft:honey_bottle': 'Frasco de mel', 'minecraft:sugar': 'Açúcar', 'minecraft:sugar_cane': 'Cana-de-açúcar', 'minecraft:cocoa_beans': 'Sementes de cacau',
  'minecraft:brown_mushroom': 'Cogumelo marrom', 'minecraft:wheat': 'Trigo', 'minecraft:bread': 'Pão', 'minecraft:carrot': 'Cenoura', 'minecraft:potato': 'Batata',
  'minecraft:beetroot': 'Beterraba', 'minecraft:pumpkin': 'Abóbora', 'minecraft:apple': 'Maçã', 'minecraft:dried_kelp': 'Alga seca', 'minecraft:stick': 'Graveto',
  'minecraft:bowl': 'Tigela', 'minecraft:glass_bottle': 'Frasco de vidro',
};
const STATIONS = Object.fromEntries(C.STATIONS.map((s) => [s.id, s]));
const CATS = Object.fromEntries([...C.CATEGORIES, C.PREP_CATEGORY].map((c) => [c.id, c]));
const names = {};
for (const c of C.CROPS) {
  names[c.id] = c.pt;
  names[`${c.id}_seeds`] = c.seedPt;
}
for (const t of C.TREES) if (!t.fruit.includes(':')) names[t.fruit] = t.fruitPt;
for (const t of C.TOOLS) names[t.id] = t.pt;
for (const p of C.PREPARED) names[p.id] = p.pt;
for (const d of C.DISHES) names[d[0]] = d[1];
const nameOf = (id) => VANILLA_NAMES[id] ?? names[id] ?? id;
const full = (id) => (id.includes(':') ? id : `culinaria:${id}`);
const containerName = { plate: 'Prato (volta sujo)', cup: 'Xícara (volta suja)', 'minecraft:bowl': 'Tigela (volta)', 'minecraft:glass_bottle': 'Frasco de vidro (volta)' };

const recipes = [];
for (const [id, station, utensil, ing, container, count, time] of C.PREP_RECIPES) recipes.push({ id, station, utensil, ing, container, count, time, cat: 'prep' });
for (const [id, pt, en, cat, station, utensil, ing, container, count, time, food] of C.DISHES) recipes.push({ id, pt, en, station, utensil, ing, container, count, time, cat, food });
const producer = {};
for (const r of recipes) if (!producer[r.id]) producer[r.id] = r;

function source(id) {
  if (C.VANILLA_SOURCES[id]) return C.VANILLA_SOURCES[id][0];
  const crop = C.CROPS.find((c) => c.id === id);
  if (crop) return `Lavoura de ${crop.pt.toLowerCase()} (${crop.seedPt.toLowerCase()} da grama${crop.rare ? ', mais rara' : ''})`;
  const tree = C.TREES.find((t) => t.fruit === id);
  if (tree) return `Colheita da ${tree.pt.toLowerCase()} (clique direito na folha madura)`;
  const r = producer[id];
  if (r) return `Preparo: ${STATIONS[r.station].pt}`;
  return 'Bancada de trabalho';
}
function timeText(r) {
  const st = STATIONS[r.station];
  if (st.kind === 'board') return `${r.time} cortes`;
  if (st.kind === 'mill') return `${r.time} voltas`;
  if (st.kind === 'sink') return '1 lavagem';
  return `${(r.time / 20).toFixed(1)} s (${r.time} ticks)`;
}
function steps(r) {
  const list = [];
  const seen = new Set();
  const visit = (rec, depth) => {
    if (depth > 8) return;
    for (const k of Object.keys(rec.ing)) {
      if (seen.has(k)) continue;
      seen.add(k);
      const p = producer[k];
      if (p && !C.VANILLA_SOURCES[k]) {
        visit(p, depth + 1);
        list.push(p);
      }
    }
  };
  visit(r, 0);
  list.push(r);
  return list.map((s, i) => `${i + 1}. ${STATIONS[s.station].verbPt} ${nameOf(s.id).toLowerCase()} — ${STATIONS[s.station].pt}${s.utensil ? ` com ${nameOf(s.utensil).toLowerCase()}` : ''}`);
}

// ---------------------------------------------------------------- receitas
const dishes = recipes.filter((r) => r.cat !== 'prep');
let md = `# Catálogo de receitas\n\nGerado por \`node tools/gen/docs.mjs\` a partir de \`tools/gen/content.mjs\`, a mesma fonte dos JSON que o jogo carrega
(\`data/culinaria/recipe/cooking/\`). **${dishes.length} pratos** em ${C.CATEGORIES.length} categorias, mais ${recipes.length - dishes.length} receitas de preparo
de ingredientes (que não contam nas 100).\n\nComo ler: *ID* é o identificador técnico (\`culinaria:<id>\`); *Equipamento* é a estação e o utensílio que
precisa estar no slot de utensílio; *Servir em* é o recipiente gasto (um por unidade); *Etapas* parte das matérias-primas. Ingredientes marcados com
(P) são preparados em outra receita (veja a seção de preparos ou \`INGREDIENTES.md\`).\n\n`;
md += '| Categoria | Pratos |\n|---|---|\n';
for (const cat of C.CATEGORIES) md += `| ${cat.pt} | ${dishes.filter((d) => d.cat === cat.id).length} |\n`;
md += `| **Total** | **${dishes.length}** |\n\n`;
let n = 0;
for (const cat of C.CATEGORIES) {
  md += `## ${cat.pt}\n\n`;
  for (const r of dishes.filter((d) => d.cat === cat.id)) {
    n++;
    const ings = Object.entries(r.ing).map(([k, q]) => `${q}× ${nameOf(k)}${producer[k] && !C.VANILLA_SOURCES[k] ? ' (P)' : ''}`).join(', ');
    const inter = Object.keys(r.ing).filter((k) => producer[k] && !C.VANILLA_SOURCES[k]).map(nameOf);
    md += `### ${n}. ${r.pt}\n\n`;
    md += `- **ID:** \`culinaria:${r.id}\` · **Categoria:** ${cat.pt} · **Rende:** ${r.count}\n`;
    md += `- **Ingredientes:** ${ings}\n`;
    md += `- **Origem:** ${Object.keys(r.ing).map((k) => `${nameOf(k)}: ${source(k)}`).join('; ')}\n`;
    md += `- **Intermediários:** ${inter.length ? inter.join(', ') : 'nenhum'}\n`;
    md += `- **Equipamento:** ${STATIONS[r.station].pt}${r.utensil ? ` + ${nameOf(r.utensil)}` : ''} · **Tempo:** ${timeText(r)}\n`;
    md += `- **Servir em:** ${r.container ? `${containerName[r.container]} × ${r.count}` : 'nada (comida de mão)'}\n`;
    md += `- **Resultado:** ${r.count}× ${r.pt}, fome ${r.food[0]} e saturação ${r.food[1]} cada${['cup', 'minecraft:glass_bottle'].includes(r.container) ? ' (bebida)' : ''}\n`;
    md += `- **Etapas:**\n${steps(r).map((s) => `  ${s}`).join('\n')}\n\n`;
  }
}
md += `## Preparos de ingredientes\n\n| Resultado | Estação e utensílio | Ingredientes | Rende | Tempo |\n|---|---|---|---|---|\n`;
for (const r of recipes.filter((x) => x.cat === 'prep')) {
  md += `| ${nameOf(r.id)} (\`${full(r.id)}\`) | ${STATIONS[r.station].pt}${r.utensil ? ` + ${nameOf(r.utensil)}` : ''} | ${Object.entries(r.ing).map(([k, q]) => `${q}× ${nameOf(k)}`).join(', ')} | ${r.count} | ${timeText(r)} |\n`;
}
writeFileSync(join(ROOT, 'docs', 'CATALOGO_RECEITAS.md'), md);

// ---------------------------------------------------------------- ingredientes
const usedIn = {};
for (const r of recipes) for (const k of Object.keys(r.ing)) (usedIn[k] ??= []).push(r);
let ing = `# Catálogo de ingredientes e fontes\n\nGerado por \`node tools/gen/docs.mjs\` a partir de \`tools/gen/content.mjs\`. O livro de receitas mostra o mesmo
"onde conseguir" (chaves \`culinaria.source.*\` das traduções). Todo ingrediente do mod é usado por pelo menos uma receita (o gerador e o teste
\`CatalogDataTest\` falham se não for), e toda receita tem cadeia a partir destas fontes.\n\n`;
ing += '## Lavouras do mod\n\nSementes: quebrar grama baixa, grama alta ou samambaia (10% por padrão, configurável); café e mandioca pesam 1/3 das outras. A lavoura madura (idade 7) dá 1–3 do produto e devolve sementes, como o trigo.\n\n| Produto | Semente | Comestível cru | Usado em |\n|---|---|---|---|\n';
for (const c of C.CROPS) ing += `| ${c.pt} (\`culinaria:${c.id}\`) | ${c.seedPt}${c.rare ? ' (rara)' : ''} | ${c.food ? `sim (${c.food[0]})` : 'não'} | ${(usedIn[c.id] ?? []).length} receitas |\n`;
ing += '\n## Árvores frutíferas\n\nNascem no mundo (biomas abaixo, cerca de 1 a cada 14 chunks em cada bioma) e também pela muda: a muda cai das folhas (5%) ou sai da bancada de trabalho com muda de carvalho + a fruta. Uma em cada 4 folhas da árvore dá fruto: flor → fruto verde → maduro; clique direito na madura colhe 1–2 sem quebrar e ela recomeça.\n\n| Árvore | Fruta | Biomas | Usado em |\n|---|---|---|---|\n';
for (const t of C.TREES) ing += `| ${t.pt} | ${nameOf(full(t.fruit).replace('culinaria:', ''))} (\`${full(t.fruit)}\`) | ${t.biomes.join(', ')} | ${(usedIn[t.fruit] ?? []).length} receitas |\n`;
ing += '\n## Itens do Minecraft reaproveitados\n\n| Item | Onde conseguir | Usado em |\n|---|---|---|\n';
for (const [k, [pt]] of Object.entries(C.VANILLA_SOURCES)) ing += `| ${nameOf(k)} (\`${k}\`) | ${pt} | ${(usedIn[k] ?? []).length} receitas |\n`;
ing += '\n## Ingredientes preparados\n\n| Item | Como fazer | Usado em |\n|---|---|---|\n';
for (const p of C.PREPARED) {
  const r = producer[p.id];
  ing += `| ${p.pt} (\`culinaria:${p.id}\`) | ${STATIONS[r.station].pt}${r.utensil ? ` + ${nameOf(r.utensil)}` : ''}: ${Object.entries(r.ing).map(([k, q]) => `${q}× ${nameOf(k)}`).join(', ')} → ${r.count} | ${(usedIn[p.id] ?? []).length} receitas |\n`;
}
ing += '\n## Utensílios e louça\n\n| Item | Para que serve |\n|---|---|\n';
const uses = { kitchen_knife: 'Cortar na tábua (gasta 1 de durabilidade por corte)', cooking_pot: 'Slot de utensílio do fogão: ferver, cozidos, caldos, doces de panela, café', frying_pan: 'Slot de utensílio do fogão: fritar e grelhar', baking_tray: 'Slot de utensílio do forno', rolling_pin: 'Slot de utensílio da bancada: abrir massas', whisk: 'Slot de utensílio da bancada: bater cremes e massas', plate: 'Recipiente dos pratos; volta sujo depois de comer', dirty_plate: 'Lave na pia para virar prato limpo', cup: 'Recipiente do café, chocolate e chá; volta suja', dirty_cup: 'Lave na pia para virar xícara limpa', recipe_book: 'Abre o livro de receitas' };
for (const t of C.TOOLS) ing += `| ${t.pt} (\`culinaria:${t.id}\`) | ${uses[t.id] ?? ''} |\n`;
writeFileSync(join(ROOT, 'docs', 'INGREDIENTES.md'), ing);
console.log(`docs: ${dishes.length} pratos, ${recipes.length} receitas, ${Object.keys(usedIn).length} ingredientes distintos`);
