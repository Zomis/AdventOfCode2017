import Day from "./Day";
import IntCodeComputer from "./IntCodeComputer"
import Map2D from "./Map2D";
import Point from "./Point";

class Day17 implements Day<number[]> {

  parse(text: string): number[] {
    return text.split(',').map((x: string) => parseInt(x, 10));
  }

  part1(program: number[]): any {
    let computer = new IntCodeComputer()
    computer.runProgram(program.slice())
    let map = this.readMap(computer.outputs)
    let crossings = map.findPositions((v, x, y) => {
      if (v !== 1) {
        return false
      }
      if (!map.inRange(x, y, 1)) {
        return false
      }
      let left = map.get(x - 1, y)
      let top = map.get(x + 1, y)
      let right = map.get(x, y - 1)
      let bottom = map.get(x, y + 1)
      return left === 1 && top === 1 && right === 1 && bottom === 1
    })
    return crossings.map((p: Point) => p.x * p.y).reduce((a, b) => a + b, 0)
  }

  private readMap(outputs: number[]) {
    let s = ""
    for (let i of outputs) {
      s += String.fromCharCode(i)
    }
    let rows = s.trim().split("\n").map((line) => line.trim())
    let map = new Map2D<number>(rows[0].length, rows.length, (x, y) => {
      let v = rows[y][x]
      if (v === '.') return 0
      if (v === '#') return 1
      if (v === '<') return 2
      if (v === '>') return 3
      if (v === '^') return 4
      if (v === 'v') return 5
      throw new Error("Invalid value: " + v + " at " + x + ", " + y)
    })
    map.print((v: number) => {
      return v.toString()
    })
    return map
  }

  part2(program: number[]): any {
    program[0] = 2
    
    return 0
  }

}

export default Day17;
