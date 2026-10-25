// Cria run/world para o teste visual: sobe o servidor de desenvolvimento (gradlew runServer), espera o "Done" e manda
// "stop" para salvar e sair. Uso: node tools/visual-probe/make-world.mjs (precisa do JAVA_HOME no JDK 25 e de
// run/eula.txt com eula=true).
import { spawn } from 'node:child_process';
import { existsSync, writeFileSync } from 'node:fs';
import { join, resolve, dirname } from 'node:path';
import { fileURLToPath } from 'node:url';

const root = resolve(dirname(fileURLToPath(import.meta.url)), '..', '..');
const run = join(root, 'run');
if (!existsSync(join(run, 'eula.txt'))) throw new Error('Falta run/eula.txt (eula=true)');
// Mundo plano e sem mobs: o cenário do teste visual é montado pelo datapack
writeFileSync(join(run, 'server.properties'), ['level-type=minecraft\:flat', 'spawn-monsters=false', 'pause-when-empty-seconds=0',
  'online-mode=false', 'server-ip=127.0.0.1', 'spawn-protection=0', 'generate-structures=false'].join('\n') + '\n');
const gradle = process.platform === 'win32' ? join(root, 'gradlew.bat') : './gradlew';
const child = spawn(gradle, ['runServer', '--console=plain'], { cwd: root, shell: process.platform === 'win32' });
let stopped = false;
const timer = setTimeout(() => { console.error('tempo esgotado'); child.kill(); process.exit(1); }, 15 * 60 * 1000);
child.stdout.on('data', (chunk) => {
  const text = chunk.toString();
  process.stdout.write(text);
  if (!stopped && /Done \(/.test(text)) {
    stopped = true;
    child.stdin.write('stop\n');
  }
});
child.stderr.on('data', (chunk) => process.stderr.write(chunk));
child.on('exit', (code) => {
  clearTimeout(timer);
  console.log(`servidor saiu (${code}); mundo em ${join(run, 'world')}: ${existsSync(join(run, 'world', 'level.dat')) ? 'ok' : 'FALTANDO'}`);
});
