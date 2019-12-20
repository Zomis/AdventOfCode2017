import Point from "./Point";

enum Direction {
  WEST,
  EAST,
  NORTH,
  SOUTH
}

function charToDir(ch: string) {
  if (ch === "R") {
    return Direction.EAST;
  }
  if (ch === "L") {
    return Direction.WEST;
  }
  if (ch === "U") {
    return Direction.NORTH;
  }
  if (ch === "D") {
    return Direction.SOUTH;
  }
  throw new Error("Invalid direction value: " + ch);
}

function dirTurnRight(dir: Direction) {
  if (dir == Direction.WEST) {
    return Direction.NORTH;
  }
  if (dir == Direction.NORTH) {
    return Direction.EAST;
  }
  if (dir == Direction.EAST) {
    return Direction.SOUTH;
  }
  if (dir == Direction.SOUTH) {
    return Direction.WEST;
  }
  throw Error('Bad dir');
}

class Dir4 {
  constructor(public direction: Direction) {}

  delta(): Point {
    let x = 0
    let y = 0
    if (this.direction === Direction.WEST) {
      x--
    }
    if (this.direction === Direction.EAST) {
      x++
    }
    if (this.direction === Direction.NORTH) {
      y--
    }
    if (this.direction === Direction.SOUTH) {
      y++
    }
    return new Point(x, y)
  }

  rotateRight(): Dir4 {
    return new Dir4(dirTurnRight(this.direction))
  }

  rotateLeft(): Dir4 {
    return new Dir4(dirTurnRight(dirTurnRight(dirTurnRight(this.direction))))
  }
}

export {
  Dir4,
  Direction,
  charToDir,
  dirTurnRight
}
