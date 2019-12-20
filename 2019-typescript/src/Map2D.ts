import Point from "./Point"

class Map2D<T> {
  public map: Array<Array<T>>

  constructor(public width: number, public height: number, init: (x: number, y: number) => T) {
    this.map = new Array<Array<T>>()
    for (let y = 0; y < height; y++) {
      let row = new Array<T>()
      for (let x = 0; x < width; x++) {
        row.push(init(x, y))
      }
      this.map.push(row)
    }
  }

  inRange(x: number, y: number, range: number): boolean {
    return x >= range && y >= range && x < this.width - range && y < this.height - range
  }

  get(x: number, y: number): T {
    if (!this.inRange(x, y, 0)) {
      throw new Error(`Position ${x}, ${y} is not in range of map ${this.width}, ${this.height}`)
    }
    return this.map[y][x]
  }

  center(): Point {
    return new Point(this.width / 2, this.height / 2)
  }

  findPositions(condition: (v: T, x: number, y: number) => boolean): Array<Point> {
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

  set(position: Point, value: T) {
    if (this.height <= position.y || this.width <= position.x || position.x < 0 || position.y < 0) {
      throw new Error("Out of bounds: " + position.toString())
    }
    this.map[position.y][position.x] = value
  }

  print(printFunction: (v: T, x: number, y: number) => string) {
    for (let y = 0; y < this.height; y++) {
      let line = ""
      for (let x = 0; x < this.width; x++) {
        line += printFunction(this.get(x, y), x, y)
      }
      console.log(line)
    }
  }

  walk(start: Point, delta: Point, end: Point, until: (v: T, x: number, y: number) => boolean): Point {
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

  depthFirstSearch(position: Point, target: (v: T) => boolean, expandable: (v: T) => Array<Point>): Array<Point> {
    return this.internalDepthFirstSearch(position, target, expandable, new Array<Point>(), new Array<Point>())!!
  }

  private internalDepthFirstSearch(position: Point, target: (v: T) => boolean, expandable: (v: T) => Array<Point>, visited: Array<Point>, path: Array<Point>): Array<Point> | undefined {
    let options: Array<Point> = expandable(this.get(position.x, position.y))
    visited.push(position)

    for (let i = 0; i < options.length; i++) {
      let opt: Point = options[i]
      let next = position.plus(opt)
      if (visited.find((p: Point) => p.x === next.x && p.y === next.y)) {
        continue
      }
      let newPath = path.slice()
      visited.push(next)
      newPath.push(opt)
      if (target(this.get(next.x, next.y))) {
        return newPath
      }
      let subsearch = this.internalDepthFirstSearch(next, target, expandable, visited, newPath)
      if (subsearch !== undefined) {
        return subsearch
      }
    }
    return undefined
  }
}

export default Map2D
