import Day from "./Day";
import IntCodeComputer from "./IntCodeComputer"
import Map2D from "./Map2D";
import Point from "./Point";

class Day13 implements Day<number[]> {

  parse(text: string): number[] {
    return text.split(',').map((x: string) => parseInt(x, 10));
  }

  part1(program: number[]): any {
    let computer = new IntCodeComputer()
    computer.runProgram(program.slice())
    let map = new Map2D(100, 100, () => 0)
    let count = 0
    while (computer.outputs.length > 0) {
      let x = computer.readOutput()
      let y = computer.readOutput()
      let object = computer.readOutput()
      if (object === 2) {
        count++
      }
      map.set(new Point(x, y), object)
    }
    return count
  }

  private makeMove(map: Map2D<number>): number {
    let paddle = map.findPositions((v: number) => v === 3)[0]
    let ball = map.findPositions((v: number) => v === 4)[0]
    if (paddle.x < ball.x) {
      return 1
    }
    if (paddle.x > ball.x) {
      return -1
    }
    return 0
  }

  part2(program: number[]): any {
    let computer = new IntCodeComputer()
    program[0] = 2
    computer.runProgram(program.slice())

    let map = new Map2D(100, 100, () => 0)
    let score = 0
    do {
      let count = 0
      while (computer.outputs.length > 0) {
        let x = computer.readOutput()
        let y = computer.readOutput()
        let object = computer.readOutput()
        if (x === -1 && y === 0) {
          score = object
        } else {
          map.set(new Point(x, y), object)
        }
      }
      let move = this.makeMove(map)
      computer.giveInput(move)
    } while (map.findPositions((v: number) => v === 2).length > 0)
    return score
  }

}

export default Day13;
