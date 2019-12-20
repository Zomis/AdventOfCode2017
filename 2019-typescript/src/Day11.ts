import Day from "./Day";
import IntCodeComputer from "./IntCodeComputer"
import Map2D from "./Map2D";
import Point from "./Point";
import { Direction, Dir4 } from "./Direction";

enum Paint {
  UNPAINTED, BLACK, WHITE
}

class Day11 implements Day<number[]> {

  parse(text: string): number[] {
    return text.split(',').map((x: string) => parseInt(x, 10));
  }

  part1(program: number[]): any {
    let computer = new IntCodeComputer()
    let map = new Map2D<Paint>(1000, 1000, () => Paint.UNPAINTED)
    computer.runProgram(program.slice())
    this.paint(computer, map)
    return map.findPositions((v: Paint) => v !== Paint.UNPAINTED).length
  }

  private paint(computer: IntCodeComputer, map: Map2D<Paint>) {
    let direction = new Dir4(Direction.NORTH)
    let position = map.center()
    while (computer.waitingForInput) {
      let curr = map.get(position.x, position.y)
      if (curr === Paint.UNPAINTED) {
        curr = Paint.BLACK
      }
      let input = curr === Paint.BLACK ? 0 : 1
      computer.giveInput(input)
      let paintColor = computer.readOutput()
      let paint = paintColor === 1 ? Paint.WHITE : Paint.BLACK
      map.set(position, paint)

      let output = computer.readOutput()
      if (output === 1) {
        direction = direction.rotateRight()
      } else if (output === 0) {
        direction = direction.rotateLeft()
      }
      position = position.plus(direction.delta())
    }
  }

  part2(program: number[]): any {
    let computer = new IntCodeComputer()
    let map = new Map2D<Paint>(1000, 1000, () => Paint.UNPAINTED)
    map.set(map.center(), Paint.WHITE)
    computer.runProgram(program.slice())
    this.paint(computer, map)
    this.cleanPrint(map)
    return -1
  }

  cleanPrint(map: Map2D<Paint>) {
    let painted = map.findPositions((v: Paint) => v !== Paint.UNPAINTED)
    let topLeft = painted.reduce((a, b) => new Point(Math.min(a.x, b.x), Math.min(a.y, b.y)))
    let bottomRight = painted.reduce((a, b) => new Point(Math.max(a.x, b.x), Math.max(a.y, b.y)))
    let map2 = new Map2D<Paint>(bottomRight.x - topLeft.x + 1, bottomRight.y - topLeft.y + 1, (x, y) => map.get(topLeft.x + x, topLeft.y + y))
    map2.print((v) => {
      if (v === Paint.UNPAINTED) return ' '
      if (v === Paint.BLACK) return '.'
      if (v === Paint.WHITE) return '#'
      throw new Error()
    })
  }

}

export default Day11;
