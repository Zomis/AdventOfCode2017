import Day from "./Day";
import IntCodeComputer from "./IntCodeComputer"

class Day2 implements Day<number[]> {

  parse(text: string): number[] {
    return text.split(',').map((x: string) => parseInt(x, 10));
  }

  part1(input: number[]) {
    input[1] = 12;
    input[2] = 2;
    return this.runProgram(input.slice());
  }

  part2(input: number[]) {
    for (let a = 0; a <= 99; a++) {
      for (let b = 0; b <= 99; b++) {
        let copy = input.slice()
        copy[1] = a;
        copy[2] = b;
        if (this.runProgram(copy) === 19690720) {
          return 100 * a + b;
        }
      }
    }
    throw new Error("Not found");
  }

  runProgram(values: number[]) {
    let computer = new IntCodeComputer()
    computer.runProgram(values);
    return computer.tape[0];
  }
}

export default Day2;
