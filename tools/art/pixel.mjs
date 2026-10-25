// Motor de pixel art sem dependências: tela RGBA, formas básicas, sombreamento e contorno automáticos e encoder PNG.
import { deflateSync } from 'node:zlib';

// ---------------------------------------------------------------- cores
export function rgba(hex) {
  const h = hex.replace('#', '');
  const n = (i) => parseInt(h.slice(i, i + 2), 16);
  return [n(0), n(2), n(4), h.length === 8 ? n(6) : 255];
}
export function hex([r, g, b, a = 255]) {
  const c = (v) => Math.max(0, Math.min(255, Math.round(v))).toString(16).padStart(2, '0');
  return '#' + c(r) + c(g) + c(b) + (a === 255 ? '' : c(a));
}
export function mix(a, b, t) {
  const x = rgba(a);
  const y = rgba(b);
  return hex(x.map((v, i) => v + (y[i] - v) * t));
}
export const lighten = (c, t = 0.25) => mix(c, '#ffffff', t);
export const darken = (c, t = 0.25) => mix(c, '#000000', t);

// ---------------------------------------------------------------- tela
export class Canvas {
  constructor(w = 16, h = 16) {
    this.w = w;
    this.h = h;
    this.px = new Array(w * h).fill(null);
  }
  inside(x, y) {
    return x >= 0 && y >= 0 && x < this.w && y < this.h;
  }
  get(x, y) {
    return this.inside(x, y) ? this.px[y * this.w + x] : null;
  }
  set(x, y, c) {
    x = Math.round(x);
    y = Math.round(y);
    if (!this.inside(x, y)) return this;
    if (c === null) this.px[y * this.w + x] = null;
    else if (c) this.px[y * this.w + x] = c;
    return this;
  }
  clear(x, y) {
    if (this.inside(x, y)) this.px[y * this.w + x] = null;
  }
  rect(x0, y0, x1, y1, c) {
    for (let y = y0; y <= y1; y++) for (let x = x0; x <= x1; x++) this.set(x, y, c);
    return this;
  }
  /** Elipse preenchida (centro e raios em pixels, aceita meio pixel). */
  ellipse(cx, cy, rx, ry, c) {
    for (let y = Math.floor(cy - ry); y <= Math.ceil(cy + ry); y++) {
      for (let x = Math.floor(cx - rx); x <= Math.ceil(cx + rx); x++) {
        const dx = (x - cx) / (rx + 0.35);
        const dy = (y - cy) / (ry + 0.35);
        if (dx * dx + dy * dy <= 1) this.set(x, y, c);
      }
    }
    return this;
  }
  line(x0, y0, x1, y1, c) {
    const steps = Math.max(Math.abs(x1 - x0), Math.abs(y1 - y0), 1);
    for (let i = 0; i <= steps; i++) this.set(x0 + ((x1 - x0) * i) / steps, y0 + ((y1 - y0) * i) / steps, c);
    return this;
  }
  /** Polígono preenchido (lista de [x, y]). */
  poly(points, c) {
    const ys = points.map((p) => p[1]);
    for (let y = Math.floor(Math.min(...ys)); y <= Math.ceil(Math.max(...ys)); y++) {
      for (let x = 0; x < this.w; x++) {
        let inside = false;
        for (let i = 0, j = points.length - 1; i < points.length; j = i++) {
          const [xi, yi] = points[i];
          const [xj, yj] = points[j];
          if (yi > y + 0.5 !== yj > y + 0.5 && x + 0.5 < ((xj - xi) * (y + 0.5 - yi)) / (yj - yi) + xi) inside = !inside;
        }
        if (inside) this.set(x, y, c);
      }
    }
    return this;
  }
  /** Pinta pixels já preenchidos que satisfazem o filtro (para listras, brilhos etc.). */
  paint(filter, c) {
    for (let y = 0; y < this.h; y++) for (let x = 0; x < this.w; x++) if (this.get(x, y) && filter(x, y)) this.set(x, y, c);
    return this;
  }
  /** Copia outra tela por cima desta. */
  draw(other, ox = 0, oy = 0) {
    for (let y = 0; y < other.h; y++) for (let x = 0; x < other.w; x++) if (other.get(x, y)) this.set(x + ox, y + oy, other.get(x, y));
    return this;
  }
  /** Luz de cima à esquerda: clareia bordas expostas acima/esquerda e escurece abaixo/direita. */
  shade(amount = 0.18) {
    const out = this.px.slice();
    for (let y = 0; y < this.h; y++) {
      for (let x = 0; x < this.w; x++) {
        const c = this.get(x, y);
        if (!c) continue;
        const up = !this.get(x, y - 1) || !this.get(x - 1, y);
        const down = !this.get(x, y + 1) || !this.get(x + 1, y);
        if (up && !down) out[y * this.w + x] = lighten(c, amount);
        else if (down && !up) out[y * this.w + x] = darken(c, amount);
      }
    }
    this.px = out;
    return this;
  }
  /** Contorno escuro em volta da forma (no pixel vazio vizinho), na cor do vizinho escurecida. */
  outline(t = 0.55) {
    const out = this.px.slice();
    for (let y = 0; y < this.h; y++) {
      for (let x = 0; x < this.w; x++) {
        if (this.get(x, y)) continue;
        const n = this.get(x, y - 1) || this.get(x - 1, y) || this.get(x + 1, y) || this.get(x, y + 1);
        if (n) out[y * this.w + x] = darken(n, t);
      }
    }
    this.px = out;
    return this;
  }
  /** Ruído determinístico de textura (para madeira, pedra, folhas). */
  noise(seed, amount = 0.08) {
    let s = seed >>> 0 || 1;
    const rnd = () => ((s = (s * 1664525 + 1013904223) >>> 0) / 4294967296);
    this.px = this.px.map((c) => (c ? (rnd() < 0.5 ? lighten(c, rnd() * amount) : darken(c, rnd() * amount)) : c));
    return this;
  }
  png() {
    const data = new Uint8Array(this.w * this.h * 4);
    this.px.forEach((c, i) => {
      if (c) data.set(rgba(c), i * 4);
    });
    return encodePng(this.w, this.h, data);
  }
}

/** Gerador pseudoaleatório determinístico (mesma saída a cada geração). */
export function rng(seed) {
  let s = 0;
  for (const ch of String(seed)) s = (s * 31 + ch.charCodeAt(0)) >>> 0;
  s = s || 7;
  return () => ((s = (s * 1664525 + 1013904223) >>> 0) / 4294967296);
}

// ---------------------------------------------------------------- PNG
const CRC_TABLE = (() => {
  const table = new Uint32Array(256);
  for (let n = 0; n < 256; n++) {
    let c = n;
    for (let k = 0; k < 8; k++) c = c & 1 ? 0xedb88320 ^ (c >>> 1) : c >>> 1;
    table[n] = c >>> 0;
  }
  return table;
})();
function crc32(buf) {
  let c = 0xffffffff;
  for (let i = 0; i < buf.length; i++) c = CRC_TABLE[(c ^ buf[i]) & 0xff] ^ (c >>> 8);
  return (c ^ 0xffffffff) >>> 0;
}
function chunk(type, data) {
  const len = Buffer.alloc(4);
  len.writeUInt32BE(data.length);
  const body = Buffer.concat([Buffer.from(type, 'ascii'), data]);
  const crc = Buffer.alloc(4);
  crc.writeUInt32BE(crc32(body));
  return Buffer.concat([len, body, crc]);
}
export function encodePng(width, height, rgbaData) {
  const header = Buffer.from([0x89, 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a]);
  const ihdr = Buffer.alloc(13);
  ihdr.writeUInt32BE(width, 0);
  ihdr.writeUInt32BE(height, 4);
  ihdr[8] = 8;
  ihdr[9] = 6;
  const raw = Buffer.alloc((width * 4 + 1) * height);
  for (let y = 0; y < height; y++) {
    raw[y * (width * 4 + 1)] = 0;
    Buffer.from(rgbaData.buffer, rgbaData.byteOffset + y * width * 4, width * 4).copy(raw, y * (width * 4 + 1) + 1);
  }
  return Buffer.concat([header, chunk('IHDR', ihdr), chunk('IDAT', deflateSync(raw, { level: 9 })), chunk('IEND', Buffer.alloc(0))]);
}
