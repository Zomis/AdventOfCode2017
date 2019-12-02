const fs = require('fs');

let f = fs.readFileSync('inputs/1', 'utf8')
console.log(f);
let moves = f.split(', ');
let x = 0, y = 0;
import { Direction, dirTurnRight } from './Direction';
let direction = Direction.NORTH;




moves.forEach((m: string) => {
  let turn = m.substring(0, 1);
  let steps = parseInt(m.substring(1), 10);
  if (turn == 'R') {
    direction = dirTurnRight(direction);
  } else {
    direction = dirTurnRight(dirTurnRight(dirTurnRight(direction)));
  }

  if (direction == Direction.WEST) x -= steps;
  if (direction == Direction.EAST) x += steps;
  if (direction == Direction.SOUTH) y += steps;
  if (direction == Direction.NORTH) y -= steps;
  console.log(`Direction is ${direction} and steps ${steps} for ${m} ending up at ${x}, ${y}}`);
})
console.log(x, y)
