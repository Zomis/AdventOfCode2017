import Day from "./Day";
import IntCodeComputer from "./IntCodeComputer"
import Point from "./Point";

class Day19 implements Day<number[]> {
  program: number[] = new Array<number>();

  parse(text: string): number[] {
    return text.split(',').map((x: string) => parseInt(x, 10));
  }

  part1(program: number[]): any {
    this.program = program
    let count = 0
    for (let y = 0; y < 50; y++) {
      for (let x = 0; x < 50; x++) {
        if (this.scanXY(x, y)) {
          count++
        }
      }
    }
    return count
  }

  private scanXY(x: number, y: number): boolean {
    let computer = new IntCodeComputer()
    computer.runProgram(this.program.slice())
    computer.giveInput(x)
    computer.giveInput(y)
    let output = computer.readOutput() === 1
    return output
  }

  part2(program: number[]): any {
    this.program = program
    let count = 0
    let y = 10
    let x = 0
    let consecutiveRows = 0
    while (true) {
      while (!this.scanXY(x, y)) {
        x++
      }

      // If x + 99, y
      // If x, y + 99
      // If x + 99, y + 99
      let startX = x
      let topRight
      do {
        topRight = this.scanXY(x + 99, y)
        let downLeft = this.scanXY(x, y + 99)
        let downRight = this.scanXY(x + 99, y + 99)
        if (topRight && downLeft && downRight) {
          return x*10000 + y
        }
        x++
      } while (topRight)
      x = startX
      y++
    }
  }

  findConsecutive(x: number, y: number): Point {
    let xx = x
    while (this.scanXY(xx, y)) {
      xx++
    }
    return new Point(xx, y)
  }

}

export default Day19;
