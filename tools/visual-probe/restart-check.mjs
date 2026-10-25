// Confere a persistência num reinício de verdade do servidor dedicado de desenvolvimento (run/world):
// 1ª subida: coloca geladeira com itens, fogão com ingredientes e combustível, folha de laranjeira na idade 2, pia com água 5
// e lavoura na idade 4; salva e desliga. 2ª subida: lê tudo de volta pelo console e imprime as linhas "CHECK".
// Uso: node tools/visual-probe/restart-check.mjs (JAVA_HOME no JDK 25; run/world criado por make-world.mjs).
import { spawn } from 'node:child_process';
import { join, resolve, dirname } from 'node:path';
import { fileURLToPath } from 'node:url';

const root = resolve(dirname(fileURLToPath(import.meta.url)), '..', '..');
const gradle = process.platform === 'win32' ? join(root, 'gradlew.bat') : './gradlew';

function server(commands, label) {
  return new Promise((done) => {
    const child = spawn(gradle, ['runServer', '--console=plain'], { cwd: root, shell: process.platform === 'win32' });
    let out = '';
    let sent = false;
    child.stdout.on('data', (chunk) => {
      out += chunk.toString();
      if (!sent && /Done \(/.test(out)) {
        sent = true;
        let i = 0;
        const next = () => {
          if (i < commands.length) {
            child.stdin.write(commands[i++] + '\n');
            setTimeout(next, 400);
          } else {
            setTimeout(() => child.stdin.write('stop\n'), 1500);
          }
        };
        next();
      }
    });
    child.stderr.on('data', (chunk) => (out += chunk.toString()));
    child.on('exit', (code) => {
      console.log(`== ${label}: servidor saiu (${code})`);
      done(out);
    });
  });
}

const P = '100 120 100';
const setup = [
  'forceload add 96 96 111 111',
  'setblock 100 119 100 minecraft:stone', 'setblock 101 119 100 minecraft:stone', 'setblock 102 119 100 minecraft:stone',
  'setblock 103 119 100 minecraft:farmland[moisture=7]', 'setblock 104 119 100 minecraft:stone',
  `setblock ${P} culinaria:fridge[facing=south]`,
  'item replace block 100 120 100 container.0 with culinaria:cheese 7',
  'item replace block 100 120 100 container.26 with culinaria:feijoada 2',
  'setblock 101 120 100 culinaria:stove[facing=south]',
  'item replace block 101 120 100 container.0 with culinaria:tomato 3',
  'item replace block 101 120 100 container.6 with culinaria:cooking_pot 1',
  'item replace block 101 120 100 container.8 with minecraft:coal 5',
  'setblock 102 120 100 culinaria:orange_leaves[age=2,fruiting=true,persistent=true]',
  'setblock 103 120 100 culinaria:tomato_crop[age=4]',
  'setblock 104 120 100 culinaria:sink[facing=south,water=5]',
  'save-all flush',
];
const check = [
  'forceload add 96 96 111 111',
  'data get block 100 120 100 Items',
  'data get block 101 120 100 Items',
  'execute if block 102 120 100 culinaria:orange_leaves[age=2,fruiting=true] run say CHECK folha idade 2 ok',
  'execute if block 103 120 100 culinaria:tomato_crop[age=4] run say CHECK lavoura idade 4 ok',
  'execute if block 104 120 100 culinaria:sink[water=5] run say CHECK pia agua 5 ok',
  'forceload remove all',
];
await server(setup, 'gravação');
const out = await server(check, 'leitura');
for (const line of out.split(/\r?\n/)) {
  if (/CHECK|has the following block data|culinaria:(cheese|feijoada|tomato|cooking_pot)|minecraft:coal/.test(line)) console.log(line.replace(/^.*?\]: /, ''));
}
