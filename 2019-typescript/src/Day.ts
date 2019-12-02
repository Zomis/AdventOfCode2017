interface Day<T> {
  parse(text: string): T
  part1(input: T): any
  part2(input: T): any
}

export default Day
