const fs = require('fs');

let f = fs.readFileSync('inputs/1', 'utf8')
let modules = f.trim().split('\n');

function fuel(mass: number): number {
  let f = Math.floor(mass / 3) - 2;
  return f > 0 ? f : 0;
}

let r = modules.map((m: string) => parseInt(m, 10)).map(fuel).reduce((a: number, b: number) => a + b, 0);
console.log(r);

let p2 = modules.map((m: string) => parseInt(m, 10));
let total = 0;

p2.forEach((m: number) => {
  let f = m;
  do {
    f = fuel(f);
    total += f;
  } while (f > 0);
});

console.log(total);
