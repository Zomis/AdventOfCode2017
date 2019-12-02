import Day from "./Day";

function fuel(mass: number): number {
  let f = Math.floor(mass / 3) - 2;
  return f > 0 ? f : 0;
}

class Day1 implements Day<number[]> {
  constructor() {}
  parse(input: string): number[] {
    return input.split('\n').map((s: string) => parseInt(s));
  }

  part1(input: number[]): any {
    return input.map(fuel).reduce((a: number, b: number) => a + b, 0);
  }

  part2(input: number[]): any {
    let total = 0;

    input.forEach((m: number) => {
      let f = m;
      do {
        f = fuel(f);
        total += f;
      } while (f > 0);
    });
    return total;
  }
}

export default Day1;
