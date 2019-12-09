import Day from "./Day";
import IntCodeComputer from "./IntCodeComputer"

class Day9 implements Day<number[]> {

  parse(text: string): number[] {
    return text.split(',').map((x: string) => parseInt(x, 10));
  }

  part1(program: number[]): any {
    return this.runProgramWithInput(program, 1)
  }

  part2(program: number[]): any {
    return this.runProgramWithInput(program, 2)
  }

  runProgramWithInput(program: number[], value: number): number {
    let computer = new IntCodeComputer()
    computer.inputs = [value]
    computer.runProgram(program.slice())
    if (computer.outputs.length > 1) {
      throw new Error("Something is not working")
    }
    return computer.outputs[0]
  }

}

export default Day9;
