import Day from "./Day";
import { Direction, charToDir } from "./Direction";

class Point {
  constructor(public x: number, public y: number) {}

  manhattan(): number {
    return Math.abs(this.x) + Math.abs(this.y);
  }

  manhattanDistance(p: Point): number {
    return Math.abs(this.x - p.x) + Math.abs(this.y - p.y);
  }
}

class Range {
  public min: number;
  public max: number;
  constructor(a: number, b: number) {
    this.min = Math.min(a, b);
    this.max = Math.max(a, b);
  }

  inRange(value: number): boolean {
    return value >= this.min && value <= this.max;
  }
}

class Intersection {
  constructor(public point: Point, public a: DirectionLength, public b: DirectionLength, public stepA: number, public stepB: number) {}
}

class DirectionLength {
  constructor(public startingPoint: Point, public direction: Direction, public length: number) {}

  endPoint(): Point {
    let x = this.startingPoint.x;
    let y = this.startingPoint.y;
    if (this.direction === Direction.WEST) {
      x -= this.length;
    }
    if (this.direction === Direction.EAST) {
      x += this.length;
    }
    if (this.direction === Direction.NORTH) {
      y -= this.length;
    }
    if (this.direction === Direction.SOUTH) {
      y += this.length;
    }
    return new Point(x, y);
  }

  xRange(): Range {
    return new Range(this.startingPoint.x, this.endPoint().x);
  }
  yRange(): Range {
    return new Range(this.startingPoint.y, this.endPoint().y);
  }

  intersection(other: DirectionLength): Intersection | undefined {
    let endPoint = this.endPoint();
    let otherEnd = other.endPoint();
    if (this.direction === Direction.WEST || this.direction === Direction.EAST) {
      if (other.direction === Direction.NORTH || other.direction === Direction.SOUTH) {
        let y = other.yRange().inRange(this.startingPoint.y);
        let x = this.xRange().inRange(other.startingPoint.x);
        if (x && y) {
          let p = new Point(other.startingPoint.x, this.startingPoint.y);
          return new Intersection(p, this, other, this.startingPoint.manhattanDistance(p), other.startingPoint.manhattanDistance(p));
        }
      }
    }
    if (other.direction === Direction.WEST || other.direction === Direction.EAST) {
      if (this.direction === Direction.NORTH || this.direction === Direction.SOUTH) {
        let x = other.xRange().inRange(this.startingPoint.x);
        let y = this.yRange().inRange(other.startingPoint.y);
        if (x && y) {
          let p = new Point(this.startingPoint.x, other.startingPoint.y);
          return new Intersection(p, this, other, this.startingPoint.manhattanDistance(p), other.startingPoint.manhattanDistance(p));
        }
      }
    }
    // 200, 0 ---> 200, 6     SOUTH
    // 194, 2 ---> 206, 2     EAST
      // intersects at 200, 2
    // ax + by + m = 0. a or b is 1. The other 0.
  }
}

class Wire {
  constructor(public paths: DirectionLength[]) {}
}

class Day3 implements Day<Wire[]> {
  parse(input: string): Wire[] {
    let wireTexts = input.split('\n');
    let wires = new Array<Wire>();
    for (let i = 0; i < wireTexts.length; i++) {
      let pos = new Point(0, 0);
      let wirePaths = wireTexts[i].split(',');
      let paths = new Array<DirectionLength>();
      for (let p = 0; p < wirePaths.length; p++) {
        let startingPoint = new Point(pos.x, pos.y);
        let dirCh = wirePaths[p].substring(0, 1);
        let length = parseInt(wirePaths[p].substring(1), 10);
        let dir = charToDir(dirCh);
        let wirePath = new DirectionLength(startingPoint, dir, length);
        paths.push(wirePath);
        pos = wirePath.endPoint();
      }
      wires.push(new Wire(paths));
    }
    return wires;
  }

  part1(input: Wire[]): any {
    let w = input[0];
    let o = input[1];
    let mDist = 2147483647;
    for (let idx in w.paths) {
      let dl = w.paths[idx];
      for (let idx2 in o.paths) {
        let dl2 = o.paths[idx2];
        let isect = dl.intersection(dl2);
        if (isect) {
          let distance = isect.point.manhattan();
          if (distance < mDist) {
            mDist = distance;
          }
        }
      }
    }
    return mDist;
  }

  part2(input: Wire[]): any {
    let w = input[0];
    let o = input[1];
    let mDist = 2147483647;
    let travelledA = 0;
    for (let idx in w.paths) {
      let travelledB = 0;
      let dl = w.paths[idx];
      for (let idx2 in o.paths) {
        let dl2 = o.paths[idx2];
        let isect = dl.intersection(dl2);
        if (isect) {
          let distance = isect.stepA + isect.stepB + travelledA + travelledB;
          if (distance < mDist) {
            mDist = distance;
          }
        }
        travelledB += dl2.length;
      }
      travelledA += dl.length;
    }
    return mDist;
  }
}

export default Day3;
