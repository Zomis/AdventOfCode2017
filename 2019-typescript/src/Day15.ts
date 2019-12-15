import Day from "./Day";
import IntCodeComputer from "./IntCodeComputer";
import Map2D from "./Map2D";
import Point from "./Point";

class Day15 implements Day<number[]> {

  private map: Map2D<number> = new Map2D<number>(1000, 1000, () => -1)
  private position = new Point(500, 500)
  private moveOptions: Array<Point> = [new Point(0, -1), new Point(0, 1), new Point(-1, 0), new Point(1, 0)]

  parse(text: string): number[] {
    return text.split(',').map((x: string) => parseInt(x, 10));
  }

  part1(input: number[]): any {
    let computer = new IntCodeComputer()
    computer.runProgram(input)
    let output = 0
    this.map.set(this.position, 1)
    do {
      let moveOrders: Array<Point> = this.inputToNearestUnexplored(this.map, this.position)
      for (let i = 0; i < moveOrders.length; i++) {
        output = this.makeMove(computer, this.moveOptions.indexOf(moveOrders[i]))
        if (output === 0 && i !== moveOrders.length - 1) {
          throw new Error("Move order error")
        }
      }
    } while (output !== 2)

    return this.map.depthFirstSearch(new Point(500, 500), (v: number) => v === 2, (v: number) => v === 1 ? this.moveOptions : []).length
  }

  makeMove(computer: IntCodeComputer, moveOrder: number): number {
    computer.giveInput(moveOrder + 1)
    let output = computer.readOutput()
    let move = this.moveOptions[moveOrder]
    this.position = this.position.plus(move)
    this.map.set(this.position, output)
    if (output === 0) {
      this.position = this.position.minus(move)
    }
    return output
  }

  inputToNearestUnexplored(map: Map2D<number>, position: Point): Array<Point> {
    return map.depthFirstSearch(position, (v: number) => v === -1, (v: number) => v === 1 ? this.moveOptions : [])
  }

  part2(input: number[]): any {
    return 0
  }

}

export default Day15;
