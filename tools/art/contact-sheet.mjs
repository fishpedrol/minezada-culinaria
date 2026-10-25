// Monta uma folha de contato (PNG ampliado) das texturas geradas, para revisar a arte sem abrir o jogo.
// Uso: node tools/art/contact-sheet.mjs <pasta de texturas> <saída.png> [escala]
import { readdirSync, readFileSync, writeFileSync } from 'node:fs';
import { join } from 'node:path';
import { inflateSync } from 'node:zlib';
import { encodePng } from './pixel.mjs';

function decode(buf) {
  const w = buf.readUInt32BE(16);
  const h = buf.readUInt32BE(20);
  let off = 8;
  const idat = [];
  while (off < buf.length) {
    const len = buf.readUInt32BE(off);
    const type = buf.toString('ascii', off + 4, off + 8);
    if (type === 'IDAT') idat.push(buf.subarray(off + 8, off + 8 + len));
    off += 12 + len;
  }
  const raw = inflateSync(Buffer.concat(idat));
  const out = new Uint8Array(w * h * 4);
  for (let y = 0; y < h; y++) raw.copy(Buffer.from(out.buffer), y * w * 4, y * (w * 4 + 1) + 1, (y + 1) * (w * 4 + 1));
  return { w, h, data: out };
}
const [dir, outFile, scaleArg] = process.argv.slice(2);
const scale = Number(scaleArg || 4);
const files = readdirSync(dir).filter((f) => f.endsWith('.png')).sort();
const cols = 16;
const cell = 16 * scale + 4;
const rows = Math.ceil(files.length / cols);
const W = cols * cell;
const H = rows * cell;
const sheet = new Uint8Array(W * H * 4);
for (let i = 0; i < W * H; i++) sheet.set((Math.floor(i / W / 8) + Math.floor((i % W) / 8)) % 2 ? [200, 200, 200, 255] : [160, 160, 160, 255], i * 4);
files.forEach((f, i) => {
  const img = decode(readFileSync(join(dir, f)));
  const ox = (i % cols) * cell + 2;
  const oy = Math.floor(i / cols) * cell + 2;
  for (let y = 0; y < 16 * scale; y++) for (let x = 0; x < 16 * scale; x++) {
    const sx = Math.floor(x / scale);
    const sy = Math.floor(y / scale) % img.h;
    const p = (sy * img.w + sx) * 4;
    if (img.data[p + 3] > 0) sheet.set(img.data.subarray(p, p + 4), ((oy + y) * W + ox + x) * 4);
  }
});
writeFileSync(outFile, encodePng(W, H, sheet));
console.log(files.map((f, i) => `${i}:${f.replace('.png', '')}`).join(' '));
