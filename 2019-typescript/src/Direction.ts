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

export {
  Direction,
  charToDir,
  dirTurnRight
}
