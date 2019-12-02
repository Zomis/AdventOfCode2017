enum Direction {
  WEST,
  EAST,
  NORTH,
  SOUTH
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
  dirTurnRight
}
