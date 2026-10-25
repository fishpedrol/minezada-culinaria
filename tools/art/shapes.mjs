// Formas de pixel art 16x16 dos itens e blocos do Culinária. Cada forma recebe até três cores (c1 principal,
// c2 secundária, c3 detalhe) e devolve uma Canvas já sombreada e contornada.
import { Canvas, darken, lighten, mix, rng } from './pixel.mjs';

const WHITE = '#f4f4f0';
const PLATE = '#eeeeea';
const WOOD = '#9a6a3a';
const IRON = '#b8bcc4';
const GLASS = '#cfe6f2';
const GREEN = '#3c8a2a';

function finish(c, { shade = 0.18, outline = 0.55 } = {}) {
  return c.shade(shade).outline(outline);
}
function dots(c, n, color, area, seed) {
  const r = rng(seed);
  for (let i = 0; i < n; i++) {
    const x = area[0] + Math.floor(r() * (area[2] - area[0] + 1));
    const y = area[1] + Math.floor(r() * (area[3] - area[1] + 1));
    if (c.get(x, y)) c.set(x, y, color);
  }
}

// ================================================================ ingredientes crus
const ingredientShapes = {
  round: (c1) => {
    const c = new Canvas().ellipse(8, 9, 5, 4.5, c1);
    c.paint((x, y) => (x - 6) ** 2 + (y - 7) ** 2 < 2, lighten(c1, 0.35));
    finish(c);
    c.rect(7, 3, 8, 4, GREEN).set(9, 3, lighten(GREEN, 0.2)).set(6, 4, GREEN);
    return c;
  },
  bulb: (c1) => {
    const c = new Canvas().ellipse(8, 10, 4.5, 4, c1).poly([[6, 7], [10, 7], [8, 2]], c1);
    c.paint((x) => x === 7 || x === 9, darken(c1, 0.12));
    finish(c);
    c.set(7, 15, '#e8dcc0').set(9, 15, '#e8dcc0');
    return c;
  },
  garlic: (c1) => {
    const c = new Canvas().ellipse(8, 10, 5, 4, c1).poly([[7, 6], [9, 6], [8, 2]], c1);
    c.paint((x, y) => (x === 6 || x === 10) && y > 7, darken(c1, 0.15));
    c.paint((x, y) => x === 8 && y > 8, darken(c1, 0.1));
    return finish(c);
  },
  leafy: (c1) => {
    const c = new Canvas().ellipse(8, 9, 6, 5, c1).ellipse(5, 7, 3.5, 3, lighten(c1, 0.1)).ellipse(11, 7, 3.5, 3, lighten(c1, 0.1));
    c.ellipse(8, 10, 3, 2.5, lighten(c1, 0.3));
    c.line(8, 13, 8, 6, lighten(c1, 0.45)).line(8, 10, 5, 7, lighten(c1, 0.4)).line(8, 10, 11, 7, lighten(c1, 0.4));
    return finish(c);
  },
  herb: (c1) => {
    const c = new Canvas().line(8, 14, 8, 3, darken(c1, 0.2));
    for (const [y, s] of [[4, 1], [6, -1], [8, 1], [10, -1], [12, 1]]) c.ellipse(8 + s * 2.5, y, 2, 1.3, s > 0 ? c1 : lighten(c1, 0.12));
    c.ellipse(8, 3, 1.5, 1.5, lighten(c1, 0.2));
    return finish(c);
  },
  cob: (c1) => {
    const c = new Canvas().ellipse(8, 8, 3, 6.5, c1);
    c.paint((x, y) => (x + y) % 2 === 0, darken(c1, 0.12));
    c.poly([[4, 15], [6, 6], [8, 15]], GREEN).poly([[12, 15], [10, 7], [8, 15]], lighten(GREEN, 0.15));
    return finish(c);
  },
  grains: (c1) => {
    const c = new Canvas();
    const r = rng(c1);
    for (let row = 0; row < 4; row++) {
      for (let i = 0; i <= row; i++) {
        const x = 8 - row * 1.5 + i * 3 + (r() - 0.5);
        const y = 5 + row * 2.5;
        c.ellipse(x, y, 1.2, 0.9, i % 2 ? c1 : darken(c1, 0.12));
      }
    }
    for (let i = 0; i < 6; i++) c.ellipse(2.5 + i * 2.2, 14, 1.2, 0.9, i % 2 ? darken(c1, 0.1) : c1);
    return finish(c, { shade: 0.22 });
  },
  pepper: (c1) => {
    const c = new Canvas().ellipse(8, 10, 5, 4.5, c1).ellipse(5.5, 8, 2.2, 3, c1).ellipse(10.5, 8, 2.2, 3, c1);
    c.paint((x) => x === 8, darken(c1, 0.15));
    c.paint((x, y) => x === 6 && y < 10, lighten(c1, 0.3));
    finish(c);
    c.rect(7, 3, 8, 5, '#3c6a20').set(9, 3, '#3c6a20');
    return c;
  },
  berry: (c1) => {
    const c = new Canvas().poly([[3, 6], [13, 6], [8, 15]], c1).ellipse(8, 7, 5, 2.5, c1);
    dots(c, 10, '#f7e07a', [4, 7, 12, 13], 'berry');
    finish(c);
    c.poly([[4, 5], [12, 5], [8, 3]], GREEN).set(8, 2, GREEN);
    return c;
  },
  root: (c1) => {
    const c = new Canvas().poly([[2, 12], [4, 10], [13, 2], [14, 4], [5, 14], [3, 14]], c1);
    c.paint((x, y) => (x + y) % 5 === 0, darken(c1, 0.15));
    return finish(c);
  },
  // frutas das árvores
  orange: (c1) => ingredientShapes.round(c1),
  lemon: (c1) => {
    const c = new Canvas().ellipse(8, 9, 6, 4, c1).set(1, 9, c1).set(15, 9, c1);
    c.paint((x, y) => (x - 6) ** 2 + (y - 7) ** 2 < 3, lighten(c1, 0.35));
    finish(c);
    c.rect(9, 4, 10, 4, GREEN).set(11, 3, GREEN);
    return c;
  },
  peach: (c1) => {
    const c = new Canvas().ellipse(8, 9, 5.5, 5, c1);
    c.paint((x, y) => x === 9 && y > 5, darken(c1, 0.15));
    c.paint((x, y) => (x - 5) ** 2 + (y - 7) ** 2 < 4, mix(c1, '#e0405a', 0.35));
    finish(c);
    c.ellipse(11, 3.5, 2.2, 1, GREEN).set(9, 4, '#5a3a1a');
    return c;
  },
  cherry: (c1) => {
    const c = new Canvas().ellipse(5, 11, 3, 3, c1).ellipse(11, 12, 3, 3, darken(c1, 0.08));
    c.set(4, 10, lighten(c1, 0.45)).set(10, 11, lighten(c1, 0.45));
    finish(c);
    c.line(5, 8, 9, 2, '#5a7a2a').line(11, 9, 9, 2, '#5a7a2a').ellipse(11, 2.5, 2, 1, GREEN);
    return c;
  },
};

function seedPacket(color) {
  const c = new Canvas().rect(3, 3, 12, 14, '#e8d8b0');
  c.rect(3, 3, 12, 4, '#d2bf8e');
  c.ellipse(8, 9.5, 3, 3, color);
  c.set(7, 8, lighten(color, 0.4));
  finish(c, { shade: 0.1 });
  c.set(5, 13, '#8a6a3a').set(10, 13, '#8a6a3a').set(7, 13, '#8a6a3a');
  return c;
}

// ================================================================ ingredientes preparados
const preparedShapes = {
  slices: (c1, c2, c3) => {
    const c = new Canvas();
    for (const [x, y] of [[5, 10], [10, 9], [8, 5]]) {
      c.ellipse(x, y, 3.5, 3.5, c1).ellipse(x, y, 2.3, 2.3, c2);
      c.set(x - 1, y, c3).set(x + 1, y + 1, c3).set(x, y - 1, c3);
    }
    return finish(c, { shade: 0.1 });
  },
  bits: (c1, c2, c3) => {
    const c = new Canvas();
    const r = rng(c1 + c2);
    for (let i = 0; i < 14; i++) {
      const x = 3 + Math.floor(r() * 10);
      const y = 5 + Math.floor(r() * 8);
      c.rect(x, y, x + 1, y + (r() < 0.5 ? 0 : 1), [c1, c2, c3][i % 3]);
    }
    return finish(c, { shade: 0.1 });
  },
  strips: (c1, c2, c3) => {
    const c = new Canvas();
    [c1, c2, c3].forEach((col, i) => {
      c.line(3 + i * 3, 13, 7 + i * 3, 3, col).line(4 + i * 3, 13, 8 + i * 3, 3, col);
    });
    return finish(c, { shade: 0.12 });
  },
  sticks: (c1, c2, c3) => {
    const c = new Canvas();
    for (let i = 0; i < 5; i++) c.rect(3 + i * 2, 3 + (i % 2) * 2, 3 + i * 2, 13, i % 2 ? c1 : c2);
    c.rect(2, 9, 13, 10, c3);
    return finish(c, { shade: 0.1 });
  },
  cubes: (c1, c2, c3) => {
    const c = new Canvas();
    for (const [x, y] of [[3, 9], [9, 10], [6, 4]]) {
      c.rect(x, y, x + 4, y + 3, c1).rect(x, y, x + 4, y, c2).set(x + 2, y + 2, c3);
    }
    return finish(c);
  },
  fillet: (c1, c2, c3) => {
    const c = new Canvas().poly([[2, 11], [5, 6], [11, 4], [14, 6], [12, 11], [6, 13]], c1);
    c.paint((x, y) => (x + y) % 4 === 0, c2);
    c.paint((x, y) => y > 11, c3);
    return finish(c);
  },
  bacon: (c1, c2, c3) => {
    const c = new Canvas();
    for (let x = 2; x < 14; x++) {
      const w = Math.round(Math.sin(x / 2) * 1.2);
      for (let k = 0; k < 6; k++) c.set(x, 5 + k + w, k % 3 === 1 ? c2 : k === 5 ? c3 : c1);
    }
    return finish(c, { shade: 0.1 });
  },
  slab: (c1, c2) => {
    const c = new Canvas().rect(3, 6, 12, 14, c1).ellipse(5, 6, 2.5, 2.5, c1).ellipse(10, 6, 2.5, 2.5, c1);
    c.rect(4, 7, 11, 13, c2).ellipse(5.5, 6.5, 1.5, 1.5, c2).ellipse(9.5, 6.5, 1.5, 1.5, c2);
    return finish(c);
  },
  sack: (c1, c2, c3) => {
    const c = new Canvas().poly([[4, 6], [12, 6], [13, 14], [3, 14]], c1).rect(6, 3, 10, 6, c1);
    c.rect(5, 5, 11, 5, c2);
    c.ellipse(8, 10.5, 2.5, 2, c3);
    return finish(c, { shade: 0.12 });
  },
  pile: (c1, c2) => {
    const c = new Canvas().poly([[1, 14], [8, 5], [15, 14]], c1);
    dots(c, 16, c2, [3, 7, 13, 13], c1);
    return finish(c, { shade: 0.15 });
  },
  mince: (c1, c2, c3) => {
    const c = new Canvas().ellipse(8, 10, 6, 4, c1);
    dots(c, 20, c2, [3, 6, 13, 13], c1);
    dots(c, 8, c3, [3, 6, 13, 13], c2);
    return finish(c);
  },
  sausage: (c1, c2, c3) => {
    const c = new Canvas();
    for (const [x, y] of [[4, 11], [8, 8], [12, 5]]) c.ellipse(x, y, 2.8, 2.2, c1).set(x - 1, y - 1, c2);
    c.set(6, 10, c3).set(10, 7, c3);
    return finish(c);
  },
  dough: (c1, c2, c3) => {
    const c = new Canvas().ellipse(8, 9.5, 6, 4.5, c1);
    c.ellipse(6, 8, 2, 1.5, c3);
    c.paint((x, y) => y > 12, c2);
    return finish(c, { shade: 0.1 });
  },
  noodles: (c1, c2, c3) => {
    const c = new Canvas();
    for (let i = 0; i < 4; i++) for (let y = 3; y < 14; y++) c.set(4 + i * 2 + Math.round(Math.sin(y / 1.5 + i) * 0.8), y, i % 2 ? c1 : c2);
    c.rect(3, 8, 12, 9, c3);
    return finish(c, { shade: 0.08 });
  },
  disc: (c1, c2, c3) => {
    const c = new Canvas().ellipse(8, 10, 7, 4, c3).ellipse(8, 9.5, 6, 3.2, c1);
    dots(c, 6, c2, [4, 8, 12, 11], c1);
    return finish(c, { shade: 0.1 });
  },
  mixbowl: (c1, c2, c3) => {
    const c = new Canvas().ellipse(8, 8, 6.5, 2.5, c1).poly([[1, 8], [15, 8], [12, 14], [4, 14]], c2);
    c.ellipse(8, 8, 6.5, 2.5, c1);
    finish(c, { shade: 0.1 });
    c.line(10, 8, 13, 2, c3).ellipse(10, 8, 1, 0.8, '#c0c0c0');
    return c;
  },
  jar: (c1, c2, c3) => {
    const c = new Canvas().rect(4, 5, 11, 14, GLASS).rect(5, 7, 10, 14, c1).rect(5, 7, 10, 7, c2);
    c.rect(4, 3, 11, 4, c3);
    c.set(5, 9, lighten(c1, 0.5)).set(5, 10, lighten(c1, 0.5));
    return finish(c, { shade: 0.08 });
  },
  block: (c1, c2, c3) => {
    const c = new Canvas().poly([[2, 9], [9, 6], [14, 8], [7, 12]], c2).poly([[2, 9], [7, 12], [7, 14], [2, 11]], c1).poly([[7, 12], [14, 8], [14, 10], [7, 14]], c3);
    return c.outline(0.5);
  },
  wedge: (c1, c2, c3) => {
    const c = new Canvas().poly([[2, 13], [14, 13], [14, 6]], c1).poly([[2, 13], [14, 6], [14, 5], [3, 11]], c2);
    for (const [x, y] of [[10, 10], [12, 8], [8, 12], [12, 11]]) c.set(x, y, c3);
    return c.shade(0.1).outline(0.5);
  },
  heap: (c1, c2, c3) => {
    const c = new Canvas().ellipse(8, 10, 6.5, 4, c1);
    dots(c, 18, c2, [3, 7, 13, 13], c1);
    dots(c, 6, c3, [3, 7, 13, 13], c2);
    return finish(c);
  },
  can: (c1, c2, c3) => {
    const c = new Canvas().rect(4, 4, 11, 14, c3).rect(4, 6, 11, 11, c2).ellipse(7.5, 4, 3.5, 1, c1);
    c.rect(5, 8, 10, 9, c1);
    return finish(c, { shade: 0.15 });
  },
  bar: (c1, c2, c3) => {
    const c = new Canvas().rect(3, 3, 12, 14, c1);
    c.paint((x, y) => x === 7 || x === 8 || y === 7 || y === 11, c2);
    c.rect(3, 12, 12, 14, c3);
    return finish(c, { shade: 0.15 });
  },
  patty: (c1, c2, c3) => {
    const c = new Canvas().ellipse(8, 10, 6.5, 3.5, c1).ellipse(8, 9, 5, 2.2, c2);
    dots(c, 6, c3, [3, 8, 13, 12], c1);
    return finish(c);
  },
};

// ================================================================ pratos
function plate() {
  return new Canvas().ellipse(8, 10.5, 7.5, 4.5, PLATE).ellipse(8, 10.5, 5.5, 3, '#ffffff');
}
function bowlBase(c) {
  c.poly([[1, 8], [15, 8], [13, 13], [10, 15], [6, 15], [3, 13]], '#8a5a2a');
  c.paint((x, y) => y === 9, '#a8723a');
}
const dishShapes = {
  plate: (c1, c2, c3) => {
    const c = plate();
    c.ellipse(7, 9, 3.5, 2.2, c1).ellipse(10, 10, 2.5, 1.8, c2);
    dots(c, 5, c3, [5, 7, 11, 11], c1 + c3);
    c.set(6, 8, lighten(c1, 0.4));
    return finish(c, { shade: 0.08, outline: 0.45 });
  },
  bowl: (c1, c2, c3) => {
    const c = new Canvas();
    bowlBase(c);
    c.ellipse(8, 7.5, 6.5, 2.2, c1);
    dots(c, 6, c2, [3, 6, 13, 9], c1 + c2);
    dots(c, 4, c3, [3, 6, 13, 9], c3 + c1);
    return finish(c, { shade: 0.1 });
  },
  bottle: (c1, c2, c3) => {
    const c = new Canvas().rect(6, 2, 9, 5, GLASS).ellipse(8, 10, 4.5, 4.5, GLASS);
    c.ellipse(8, 10.5, 3.5, 3.5, c1).rect(5, 8, 11, 8, c2);
    c.set(6, 9, lighten(c1, 0.5)).set(6, 10, lighten(c1, 0.5));
    finish(c, { shade: 0.06, outline: 0.45 });
    c.rect(6, 1, 9, 2, '#a8723a');
    if (c3) c.set(10, 4, c3).set(11, 3, c3);
    return c;
  },
  cup: (c1, c2, c3) => {
    const c = new Canvas().poly([[3, 7], [12, 7], [11, 13], [4, 13]], c3 || WHITE).ellipse(7.5, 7, 4.5, 1.6, c1);
    c.set(6, 7, c2);
    c.rect(12, 8, 13, 11, c3 || WHITE).clear(12, 9);
    c.ellipse(8, 14, 6.5, 1, '#dcdcd8');
    finish(c, { shade: 0.08, outline: 0.45 });
    c.set(6, 4, '#e8e8e8').set(8, 3, '#e8e8e8').set(7, 5, '#e8e8e8');
    return c;
  },
  loaf: (c1, c2, c3) => {
    const c = new Canvas().ellipse(8, 9, 6.5, 4, c1);
    c.line(5, 7, 7, 11, c2).line(8, 6, 10, 10, c2).line(11, 7, 12, 9, c2);
    c.set(4, 7, c3);
    return finish(c);
  },
  balls: (c1, c2, c3) => {
    const c = new Canvas();
    for (const [x, y] of [[5, 11], [11, 11], [8, 7], [8, 12]]) c.ellipse(x, y, 2.6, 2.4, c1).set(x - 1, y - 1, c2);
    dots(c, 5, c3, [3, 5, 13, 14], c1 + 'b');
    return finish(c);
  },
  layers: (c1, c2, c3) => {
    const c = plate();
    c.rect(4, 5, 12, 11, c3).rect(4, 5, 12, 6, c1).rect(4, 8, 12, 8, c2).rect(4, 10, 12, 10, c2);
    return finish(c, { shade: 0.08, outline: 0.45 });
  },
  pizza: (c1, c2, c3) => {
    const c = new Canvas().ellipse(8, 8, 7, 7, '#d9973a').ellipse(8, 8, 5.5, 5.5, c1);
    dots(c, 14, c2, [3, 3, 13, 13], 'pz' + c2);
    for (const [x, y] of [[6, 6], [10, 7], [7, 10], [10, 10], [8, 4]]) c.set(x, y, c3);
    c.line(8, 2, 8, 14, darken('#d9973a', 0.1));
    return finish(c, { shade: 0.08 });
  },
  egg: (c1, c2) => {
    const c = plate();
    c.ellipse(8, 9.5, 4.5, 2.8, c1).ellipse(8, 9, 1.6, 1.4, c2).set(7, 8, lighten(c2, 0.4));
    return finish(c, { shade: 0.06, outline: 0.45 });
  },
  omelette: (c1, c2, c3) => {
    const c = plate();
    c.poly([[3, 10], [13, 10], [11, 7], [5, 7]], c1);
    c.set(6, 8, c2).set(9, 9, c2).set(11, 8, c3).set(7, 9, c3);
    return finish(c, { shade: 0.08, outline: 0.45 });
  },
  stack: (c1, c2, c3) => {
    const c = plate();
    for (let i = 0; i < 3; i++) c.ellipse(8, 10 - i * 1.6, 4.5, 1.5, i % 2 ? c2 : c1);
    c.ellipse(8, 6.4, 3, 0.8, c3).set(10, 8, c3).set(10, 9, c3);
    return finish(c, { shade: 0.06, outline: 0.45 });
  },
  tapioca: (c1, c2, c3) => {
    const c = new Canvas().ellipse(8, 10, 7, 4, c1);
    c.paint((x, y) => y < 9, null);
    c.ellipse(8, 9, 5, 1.5, c2).ellipse(8, 9, 7, 1, c3);
    c.ellipse(8, 10, 7, 4, c1).paint((x, y) => y < 10 && y > 8 && x > 2 && x < 14, c2);
    return finish(c, { shade: 0.1 });
  },
  sandwich: (c1, c2, c3) => {
    const c = new Canvas().poly([[2, 13], [14, 13], [8, 3]], c1);
    c.line(4, 10, 12, 10, c2).line(5, 9, 11, 9, c3);
    c.paint((x, y) => y === 13, darken(c1, 0.2));
    return finish(c);
  },
  skewer: (c1, c2, c3) => {
    const c = new Canvas().line(2, 14, 14, 2, '#c8a060');
    [[4, 12, c1], [6, 10, c2], [8, 8, c1], [10, 6, c3], [12, 4, c1]].forEach(([x, y, col]) => c.rect(x - 1, y - 1, x + 1, y + 1, col));
    return finish(c);
  },
  roast: (c1, c2, c3) => {
    const c = plate();
    c.ellipse(8, 8.5, 4.5, 3, c1).ellipse(4.5, 9, 1.5, 1.2, c1).ellipse(11.5, 9, 1.5, 1.2, c1);
    c.set(7, 7, c2).set(8, 7, c2).set(6, 8, c2);
    c.set(3, 11, c3).set(13, 11, c3).set(12, 12, c3);
    return finish(c, { shade: 0.1, outline: 0.45 });
  },
  burger: (c1, c2, c3) => {
    const c = new Canvas().ellipse(8, 5.5, 6, 3, c1).rect(2, 8, 14, 8, c3).rect(2, 9, 14, 10, c2).rect(2, 11, 14, 11, '#f6d04a').rect(2, 12, 14, 13, c1);
    c.set(5, 4, '#fff1c4').set(9, 3, '#fff1c4').set(11, 5, '#fff1c4');
    return finish(c, { shade: 0.1 });
  },
  sushi: (c1, c2, c3) => {
    const c = new Canvas();
    for (const [x, y] of [[4, 10], [11, 10], [7.5, 5]]) c.ellipse(x, y, 3.2, 3.2, c3).ellipse(x, y, 2.4, 2.4, c1).ellipse(x, y, 1, 1, c2);
    return finish(c, { shade: 0.05 });
  },
  fries: (c1, c2, c3) => {
    const c = new Canvas();
    for (let i = 0; i < 6; i++) c.rect(4 + i, 2 + (i % 3), 4 + i, 9, i % 2 ? c1 : c2);
    c.poly([[3, 8], [13, 8], [12, 15], [4, 15]], c3);
    c.paint((x, y) => y > 8 && x === 8, lighten(c3, 0.3));
    return finish(c, { shade: 0.1 });
  },
  mound: (c1, c2, c3) => {
    const c = plate();
    c.ellipse(8, 8, 4.5, 3.5, c1).ellipse(8, 6.5, 3, 1.5, c2);
    dots(c, 5, c3, [4, 6, 12, 11], 'md' + c1);
    return finish(c, { shade: 0.1, outline: 0.45 });
  },
  cob_cooked: (c1, c2, c3) => {
    const c = new Canvas().poly([[3, 13], [11, 3], [14, 6], [6, 15]], c1);
    c.paint((x, y) => (x + y) % 2 === 0, darken(c1, 0.12));
    c.paint((x, y) => x + y < 12 && x + y > 9, c2);
    c.rect(1, 14, 3, 15, c3);
    return finish(c);
  },
  toast: (c1, c2, c3) => {
    const c = new Canvas().rect(3, 6, 13, 13, c1).ellipse(5, 6, 2.5, 2, c1).ellipse(11, 6, 2.5, 2, c1);
    c.rect(4, 7, 12, 12, lighten(c1, 0.15));
    dots(c, 8, c2, [4, 6, 12, 12], 'ts' + c2);
    dots(c, 4, c3, [4, 6, 12, 12], 'ts' + c3);
    return finish(c);
  },
  cake: (c1, c2, c3) => {
    const c = new Canvas().poly([[2, 13], [14, 13], [14, 6], [2, 9]], c1).poly([[2, 9], [14, 6], [14, 4], [2, 7]], c2);
    c.line(2, 11, 14, 9, c2);
    c.set(6, 6, c3).set(10, 5, c3).set(13, 4, c3);
    return finish(c, { shade: 0.1 });
  },
  flan: (c1, c2, c3) => {
    const c = plate();
    c.poly([[4, 11], [12, 11], [11, 6], [5, 6]], c1).ellipse(8, 6, 3, 1, c2).rect(5, 7, 11, 7, c2);
    c.ellipse(8, 12, 5, 1, c2).set(7, 9, c3);
    return finish(c, { shade: 0.08, outline: 0.45 });
  },
  glass: (c1, c2, c3) => {
    const c = new Canvas().poly([[3, 3], [13, 3], [11, 10], [5, 10]], GLASS).rect(7, 10, 8, 13, GLASS).rect(4, 14, 11, 14, GLASS);
    c.poly([[4, 5], [12, 5], [11, 9], [5, 9]], c1).rect(4, 4, 12, 4, c2);
    c.set(8, 3, c3).set(9, 2, c3);
    return finish(c, { shade: 0.06, outline: 0.45 });
  },
  icecream: (c1, c2, c3) => {
    const c = new Canvas().poly([[5, 8], [11, 8], [8, 15]], c2).ellipse(8, 6, 4, 3.5, c1);
    c.paint((x, y) => y > 8 && (x + y) % 3 === 0, darken(c2, 0.2));
    c.set(7, 4, c3).set(9, 6, c3).set(6, 6, lighten(c1, 0.4));
    return finish(c, { shade: 0.1 });
  },
  pie: (c1, c2, c3) => {
    const c = new Canvas().ellipse(8, 9.5, 7, 5, c3).ellipse(8, 9, 6, 4, c2);
    c.paint((x, y) => (x - 8) ** 2 / 36 + (y - 9) ** 2 / 16 < 1 && ((x + y) % 4 === 0 || (x - y + 16) % 4 === 0), c1);
    return finish(c, { shade: 0.1 });
  },
  cookie: (c1, c2, c3) => {
    const c = new Canvas().ellipse(8, 8.5, 6, 5.5, c1);
    c.paint((x, y) => (x - 6) ** 2 + (y - 6) ** 2 < 5, c2);
    for (const [x, y] of [[6, 7], [10, 6], [9, 10], [5, 10], [11, 9]]) c.set(x, y, c3);
    return finish(c, { shade: 0.08 });
  },
  churro: (c1, c2, c3) => {
    const c = new Canvas();
    for (let i = 0; i < 3; i++) c.line(3 + i * 3, 14, 7 + i * 3, 2, c1).line(4 + i * 3, 14, 8 + i * 3, 2, c2);
    c.ellipse(9, 3, 1.5, 1.2, c3);
    return finish(c, { shade: 0.1 });
  },
};

// ================================================================ utensílios
const toolShapes = {
  knife: () => {
    const c = new Canvas().poly([[5, 11], [13, 3], [14, 4], [7, 12]], '#d8dce2').line(5, 11, 13, 3, '#f4f6f8');
    c.line(2, 14, 5, 11, '#6a4020').line(3, 14, 6, 11, '#8a5a30');
    return c.outline(0.5);
  },
  pot: () => {
    const c = new Canvas().rect(3, 6, 12, 13, '#7a7e86').rect(3, 6, 12, 6, IRON).ellipse(7.5, 5, 4.5, 1, '#5a5e66');
    c.rect(1, 7, 2, 8, '#3a3a3a').rect(13, 7, 14, 8, '#3a3a3a');
    return finish(c, { shade: 0.12 });
  },
  pan: () => {
    const c = new Canvas().ellipse(6, 9, 5, 3.5, '#4a4e56').ellipse(6, 8.5, 3.5, 2.2, '#6a6e76');
    c.line(10, 8, 15, 4, '#3a2a1a').line(10, 9, 15, 5, '#5a4028');
    return c.outline(0.5);
  },
  tray: () => {
    const c = new Canvas().poly([[1, 9], [9, 5], [15, 8], [7, 12]], '#9aa0a8').poly([[3, 9], [9, 6], [13, 8], [7, 11]], '#c8ccd2');
    c.poly([[1, 9], [7, 12], [7, 13], [1, 10]], '#6a6e76').poly([[7, 12], [15, 8], [15, 9], [7, 13]], '#5a5e66');
    return c.outline(0.5);
  },
  rolling_pin: () => {
    const c = new Canvas().poly([[3, 11], [11, 3], [13, 5], [5, 13]], '#d8a868');
    c.line(1, 14, 3, 12, '#9a6a3a').line(13, 4, 15, 2, '#9a6a3a').line(4, 11, 11, 4, '#e8c088');
    return c.outline(0.5);
  },
  whisk: () => {
    const c = new Canvas().line(3, 14, 7, 9, '#8a5a30').line(4, 14, 8, 9, '#6a4020');
    c.ellipse(10, 6, 3.5, 4, '#d8dce2').ellipse(10, 6, 2.5, 3, null).line(8, 8, 12, 4, '#c0c4ca').line(10, 9, 10, 2, '#c0c4ca');
    return c.outline(0.5);
  },
  plate_empty: () => finish(plate(), { shade: 0.06, outline: 0.4 }),
  plate_dirty: () => {
    const c = plate();
    dots(c, 14, '#a8946a', [3, 7, 13, 14], 'dirty');
    dots(c, 6, '#8a5a3a', [3, 7, 13, 14], 'dirty2');
    return finish(c, { shade: 0.06, outline: 0.4 });
  },
  cup_empty: () => dishShapes.cup('#d8d8d4', '#d8d8d4', WHITE),
  cup_dirty: () => dishShapes.cup('#6a4a30', '#8a6a4a', '#e0d8c8'),
  book: () => {
    const c = new Canvas().rect(3, 2, 12, 14, '#b8452a').rect(4, 3, 11, 13, '#d0603a').rect(12, 3, 13, 14, '#f2ead8');
    c.ellipse(7.5, 7, 2.5, 2.5, '#f6d04a').rect(6, 7, 9, 9, '#f4f4f0').rect(5, 11, 10, 11, '#f6d04a');
    return c.outline(0.5);
  },
};

// ================================================================ API
export function itemTexture(shape, c1, c2, c3) {
  const fn = ingredientShapes[shape] || preparedShapes[shape] || dishShapes[shape] || toolShapes[shape];
  if (!fn) throw new Error(`forma desconhecida: ${shape}`);
  return c1 ? fn(c1, c2 || lighten(c1, 0.3), c3 || darken(c1, 0.3)) : fn();
}
export function seedTexture(color) {
  return seedPacket(color);
}
export function knownShapes() {
  return [...Object.keys(ingredientShapes), ...Object.keys(preparedShapes), ...Object.keys(dishShapes), ...Object.keys(toolShapes)];
}

// ---------------------------------------------------------------- lavouras (4 estágios)
export function cropStage(crop, stage) {
  const c = new Canvas();
  const leaf = crop.shape === 'leafy' ? crop.color : '#4f9a32';
  const stem = '#3f7a28';
  const heights = [4, 7, 10, 12];
  const h = heights[stage];
  for (const x of [3, 8, 12]) {
    c.line(x, 15, x + (x === 8 ? 0 : x < 8 ? -1 : 1), 15 - h, stem);
    for (let y = 15 - h + 1; y < 15; y += 3) {
      c.set(x - 1, y, leaf).set(x + 1, y - 1, lighten(leaf, 0.15));
      if (stage >= 2) c.set(x - 2, y + 1, leaf).set(x + 2, y, darken(leaf, 0.1));
    }
  }
  if (stage === 3) {
    const col = crop.color;
    const top = 15 - h;
    switch (crop.shape) {
      case 'leafy':
        for (const x of [3, 8, 12]) c.ellipse(x, 11, 2.5, 2.5, col).set(x, 10, lighten(col, 0.3));
        break;
      case 'cob':
        for (const x of [3, 12]) c.rect(x + 1, top + 3, x + 2, top + 7, col);
        c.rect(9, top + 2, 10, top + 6, col);
        break;
      case 'grains':
        for (const x of [3, 8, 12]) for (let k = 0; k < 4; k++) c.set(x + (k % 2 ? 1 : -1), top + k, col);
        break;
      case 'root':
      case 'bulb':
      case 'garlic':
        for (const x of [3, 8, 12]) c.ellipse(x, 14, 1.6, 1.2, col);
        break;
      case 'herb':
        for (const x of [3, 8, 12]) c.ellipse(x, top + 2, 2, 2, col).set(x, top + 1, lighten(col, 0.3));
        break;
      default:
        for (const [x, y] of [[2, top + 4], [9, top + 3], [13, top + 6], [5, top + 8], [11, top + 9]]) c.ellipse(x, y, 1.2, 1.2, col).set(x, y - 1, lighten(col, 0.35));
    }
  }
  return c;
}

// ---------------------------------------------------------------- folhas frutíferas (idade 0–3) e mudas
export function leavesTexture(tree, age) {
  const c = new Canvas();
  const r = rng('leaves' + tree.id);
  const base = '#4a8a2e';
  for (let y = 0; y < 16; y++) {
    for (let x = 0; x < 16; x++) {
      const v = r();
      if (v < 0.1) continue; // furos, como as folhas vanilla (cutout)
      c.set(x, y, v < 0.4 ? darken(base, 0.15) : v < 0.8 ? base : lighten(base, 0.15));
    }
  }
  const spots = [[3, 3], [11, 4], [6, 9], [13, 11], [2, 12], [9, 13]];
  if (age === 1) for (const [x, y] of spots) c.set(x, y, '#fbf4f8').set(x + 1, y, '#f2c8d8');
  if (age === 2) for (const [x, y] of spots.slice(0, 5)) c.rect(x, y, x + 1, y + 1, '#9ac24a');
  if (age === 3) {
    for (const [x, y] of spots) {
      c.rect(x, y, x + 1, y + 1, tree.fruitColor).set(x, y, lighten(tree.fruitColor, 0.35)).set(x + 1, y + 1, darken(tree.fruitColor, 0.25));
    }
  }
  return c;
}
export function saplingTexture(tree) {
  const c = new Canvas().line(8, 15, 8, 8, '#6a4a2a').line(7, 15, 7, 11, '#5a3a1a');
  c.ellipse(8, 6, 4.5, 4, '#4a8a2e').ellipse(6, 5, 2, 2, '#5aa03a');
  c.set(6, 7, tree.fruitColor).set(10, 5, tree.fruitColor).set(9, 8, tree.fruitColor);
  return c.outline(0.45);
}

// ---------------------------------------------------------------- móveis
function panel(base, seed, amount = 0.06) {
  const c = new Canvas().rect(0, 0, 15, 15, base);
  c.noise(seed, amount);
  return c;
}
function frame(c, col) {
  c.rect(0, 0, 15, 0, col).rect(0, 15, 15, 15, col).rect(0, 0, 0, 15, col).rect(15, 0, 15, 15, col);
  return c;
}
const WOOD_PANEL = '#a8763e';
const STEEL = '#c8ccd2';
const FRIDGE = '#e8ecee';
export const blockTextures = {
  counter_top: () => frame(panel('#9aa0a6', 11, 0.1), '#7a8086'),
  counter_front: () => {
    const c = frame(panel(WOOD_PANEL, 12), darken(WOOD_PANEL, 0.3));
    c.rect(0, 0, 15, 2, '#8a9096').rect(2, 5, 13, 6, darken(WOOD_PANEL, 0.2)).rect(6, 9, 9, 9, '#d8d0b8');
    return c;
  },
  counter_side: () => frame(panel(WOOD_PANEL, 13), darken(WOOD_PANEL, 0.3)).rect(0, 0, 15, 2, '#8a9096'),
  stove_top: () => {
    const c = frame(panel('#3a3c40', 21, 0.05), '#2a2c30');
    for (const [x, y] of [[4, 4], [11, 4], [4, 11], [11, 11]]) c.ellipse(x, y, 2.5, 2.5, '#1e1e20').ellipse(x, y, 1, 1, '#555860');
    return c;
  },
  stove_front: () => {
    const c = frame(panel(STEEL, 22, 0.04), '#9aa0a6');
    c.rect(2, 5, 13, 13, '#2a2c30').rect(3, 6, 12, 12, '#3a3c40').rect(4, 4, 11, 4, '#8a9096');
    for (const x of [3, 6, 9, 12]) c.set(x, 2, '#2a2c30');
    return c;
  },
  stove_front_lit: () => {
    const c = blockTextures.stove_front();
    c.rect(4, 10, 11, 12, '#f28a1c').rect(5, 11, 10, 11, '#f6d04a');
    return c;
  },
  stove_side: () => frame(panel(STEEL, 23, 0.04), '#9aa0a6'),
  oven_front: () => {
    const c = frame(panel('#a8462a', 31, 0.12), '#7a2a1a');
    c.paint((x, y) => y % 4 === 3 || (x + (Math.floor(y / 4) % 2) * 4) % 8 === 0, '#8a3a22');
    c.rect(3, 4, 12, 12, '#2a2220').rect(4, 5, 11, 11, '#4a3a32').rect(3, 2, 12, 2, '#c8ccd2');
    return c;
  },
  oven_front_lit: () => {
    const c = blockTextures.oven_front();
    c.rect(4, 8, 11, 11, '#e8641a').rect(5, 9, 10, 10, '#f6c04a');
    return c;
  },
  oven_side: () => {
    const c = panel('#a8462a', 32, 0.12);
    return c.paint((x, y) => y % 4 === 3 || (x + (Math.floor(y / 4) % 2) * 4) % 8 === 0, '#8a3a22');
  },
  oven_top: () => frame(panel('#8a3a22', 33, 0.1), '#6a2a1a'),
  fridge_front: () => {
    const c = frame(panel(FRIDGE, 41, 0.02), '#b8bcc0');
    c.rect(1, 5, 14, 5, '#a8acb0').rect(12, 1, 12, 3, '#8a9096').rect(12, 7, 12, 13, '#8a9096');
    return c;
  },
  fridge_side: () => frame(panel(FRIDGE, 42, 0.02), '#b8bcc0'),
  fridge_top: () => frame(panel('#d8dcde', 43, 0.02), '#b8bcc0'),
  cabinet_front: () => {
    const c = frame(panel('#c89a5a', 51, 0.08), darken('#c89a5a', 0.3));
    c.rect(7, 1, 8, 14, darken('#c89a5a', 0.3)).rect(2, 2, 6, 13, '#d8aa6a').rect(9, 2, 13, 13, '#d8aa6a');
    c.set(6, 8, '#3a3a3a').set(9, 8, '#3a3a3a');
    return c;
  },
  cabinet_side: () => frame(panel('#c89a5a', 52, 0.08), darken('#c89a5a', 0.3)),
  pantry_front: () => {
    const c = frame(panel('#8a5a2a', 61, 0.08), '#5a3a1a');
    for (const y of [5, 10]) c.rect(1, y, 14, y, '#5a3a1a');
    for (const [x, y, col] of [[2, 2, '#e8d8b0'], [6, 2, '#c8281f'], [10, 2, '#f6d04a'], [3, 7, '#7cc94a'], [8, 7, '#f2855a'], [4, 12, '#efe8d2'], [10, 12, '#8a5a33']]) {
      c.rect(x, y, x + 2, y + 2, col).set(x, y, lighten(col, 0.3));
    }
    return c;
  },
  pantry_side: () => frame(panel('#8a5a2a', 62, 0.08), '#5a3a1a'),
  wood_top: () => frame(panel('#9a6a3a', 63, 0.08), '#6a4520'),
  cutting_board: () => {
    const c = panel('#d8a868', 71, 0.06);
    c.paint((x, y) => y % 3 === 0, '#c8985a');
    return c;
  },
  sink_basin: () => frame(panel('#d8dce2', 81, 0.03), '#9aa0a8'),
  sink_side: () => {
    const c = frame(panel('#e8e8e4', 82, 0.03), '#b8b8b4');
    c.rect(0, 0, 15, 2, '#9aa0a8');
    return c;
  },
  sink_front: () => {
    const c = blockTextures.sink_side();
    c.rect(2, 5, 13, 13, '#d8d8d4').rect(7, 8, 8, 9, '#8a9096');
    return c;
  },
  sink_water: () => panel('#3a7ad8', 83, 0.06),
  mill_stone: () => {
    const c = panel('#8a8a8a', 91, 0.15);
    return c.paint((x, y) => (x * 7 + y * 3) % 11 === 0, '#6a6a6a');
  },
  mill_wood: () => panel('#9a6a3a', 92, 0.1).paint((x, y) => x % 4 === 0, '#7a4a22'),
  blender_base: () => frame(panel('#e8e8e4', 101, 0.03), '#9aa0a8').rect(5, 6, 10, 9, '#3a3c40').set(7, 7, '#d8372b'),
  blender_glass: () => {
    const c = new Canvas().rect(0, 0, 15, 15, '#cfe6f2c0');
    c.rect(1, 0, 1, 15, '#ffffffd0').rect(0, 0, 15, 0, '#a8c8d8e0');
    return c;
  },
};
