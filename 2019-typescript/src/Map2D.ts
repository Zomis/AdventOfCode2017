import Point from "./Point"

class Map2D<T> {
  public map: Array<Array<T>>

  constructor(public width: number, public height: number, init: Function) {
    this.map = new Array<Array<T>>()
    for (let y = 0; y < height; y++) {
      let row = new Array<T>()
      for (let x = 0; x < width; x++) {
        row.push(init(x, y))
      }
      this.map.push(row)
    }
  }

  get(x: number, y: number): T {
    return this.map[y][x]
  }

  findPositions(condition: Function): Array<Point> {
    let results = new Array<Point>()
    for (let y = 0; y < this.height; y++) {
      for (let x = 0; x < this.width; x++) {
        if (condition(this.map[y][x], x, y)) {
          results.push(new Point(x, y))
        }
      }
    }
    return results
  }

  walk(start: Point, delta: Point, end: Point, until: Function): Point {
    let current = start.clone()
    while (true) {
      current.x += delta.x
      current.y += delta.y
      if (current.x === end.x && current.y === end.y) {
        return end
      }
      if (until(this.get(current.x, current.y), current.x, current.y)) {
        return current
      }
    }
  }
}

export default Map2D
