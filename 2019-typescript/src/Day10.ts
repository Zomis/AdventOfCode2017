import Day from "./Day";
import Map2D from "./Map2D";
import Point from "./Point";

class Day10 implements Day<Map2D<boolean>> {

  parse(text: string): Map2D<boolean> {
    let splitted = text.split('\n')
    let f = function(x: number, y: number): boolean {
      return splitted[y].substring(x, x + 1) === '#'
    }
    let map = new Map2D<boolean>(splitted[0].length, splitted.length, f)
    return map
  }

  findObservations(map: Map2D<boolean>, asteroids: Array<Point>, a: Point): number {
    return asteroids.filter((b: Point) => a !== b).map((b: Point) => {
      let delta = b.minus(a).normalize()
      return map.walk(a, delta, b, (x: boolean) => x) === b ? 1 : 0
    }).reduce((a: number, b: number) => a + b, 0)
  }

  part1(input: Map2D<boolean>): any {
    /*
for each asteroid - a
for each other asteroid - b
dx, dy = distance between a and b
shorten dx, dy
walk in increments of dx, dy from a towards b, if there is any asteroid in the way, return false. otherwise return true.
    */
    let asteroids = input.findPositions((b: boolean) => b)
    let values = asteroids.map((a: Point) => this.findObservations(input, asteroids, a))
    return Math.max(...values)
  }

  part2(input: Map2D<boolean>): any {
    return 0
  }

}

export default Day10;
