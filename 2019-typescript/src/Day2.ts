const fs = require('fs');

let f = fs.readFileSync('inputs/2', 'utf8')
let values = f.trim().split(',').map((x: string) => parseInt(x, 10));

function runProgram(values: number[]) {
  let i = 0;
  while (values[i] != 99) {
    let v = values[i];
    let a = values[values[i + 1]];
    let b = values[values[i + 2]];
    let output = values[i + 3];
    //console.log(i, values); console.log(v, a, b, output);
    if (v === 1) {
      values[output] = a + b;
    } else if (v === 2) {
      values[output] = a * b;
    } else {
      throw new Error("Kaputt at " + i);
    }
    i += 4;
  }
  return values[0];
}
values[1] = 12;
values[2] = 2;
console.log(runProgram(values.slice()));

for (let a = 0; a <= 99; a++) {
  for (let b = 0; b <= 99; b++) {
    values[1] = a;
    values[2] = b;
    if (runProgram(values.slice()) === 19690720) {
      console.log(100 * a + b);
    }
  }
}
