import Day from "./Day";
import TestCase from "./TestCase";
import IntCodeComputer from "./IntCodeComputer"

class Day5 implements Day<number[]> {

  parse(text: string): number[] {
    return text.split(',').map((x: string) => parseInt(x, 10));
  }

  part1(input: number[]): any {
    let computer = new IntCodeComputer()
    computer.runProgram(input);
    return computer.outputs[computer.outputs.length - 1];
  }

  part2(input: number[]): any {
    return 0;
  }

  testCases: TestCase[] = [
      {
        text: "1002,4,3,4,33",
        expectInput: [ 1002, 4, 3, 4, 99 ],
        result: ""
      },
      {
        text: "1001,1,-106,1,99",
        expectInput: [ 1001, -105, -106, 1, 99 ],
        result: ""
      }
    ]
}

export default Day5;
