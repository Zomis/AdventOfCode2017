import Day from "./Day";

class Day16 implements Day<number[]> {

  parse(text: string): number[] {
    return text.split("").map((s: string) => parseInt(s, 10))
  }

  private nums = [0, 1, 0, -1]
  private patternNumber(index: number, i: number): number {
    let eachPatternNumberRepeats = (index + 1)
    let cycleLength = eachPatternNumberRepeats * 4
    let indexWithinCycle = i % cycleLength
    let num = Math.floor((i + 1) % cycleLength / eachPatternNumberRepeats)
    let result = this.nums[num]
    return result
  }

  private multiplyWithPattern(v: number, index: number, values: number[]): number {
    let result = 0
    for (let i = 0; i < values.length; i++) {
      result += values[i] * this.patternNumber(index, i)
    }
    return Math.abs(result) % 10
  }

  private phase(values: number[]): number[] {
    let result = values.slice()
    for (let i = 0; i < values.length; i++) {
      let v = values[i]
      let r = this.multiplyWithPattern(v, i, values)
      result[i] = r
    }
    return result
  }

  private arrayToNumber(array: number[]): number {
    return array.reduce((a, b) => a*10+b, 0)
  }

  part1(input: number[]): any {
    let values = input.slice()
    for (let phaseNumber = 0; phaseNumber < 100; phaseNumber++) {
      values = this.phase(values)
    }
    return this.arrayToNumber(values.slice(0, 8))
  }

  part2(input: number[]): any {
    return 0
  }

}

export default Day16;
