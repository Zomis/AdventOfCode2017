import Day from "./Day";
import IntCodeComputer from "./IntCodeComputer"
import Combinatorics from "./Combinatorics"

function createArray(): number[] { return [0, 1, 2, 3, 4] }

class Day7 implements Day<number[]> {

  parse(text: string): number[] {
    return text.split(',').map((x: string) => parseInt(x, 10));
  }

  part1(input: number[]): any {
    let max = 0
    for (let i = 0; i < 120; i++) {
      let inputs = Combinatorics.permutation(createArray(), i)
      let nextInput = 0

      for (let amplifier = 0; amplifier < 5; amplifier++) {
        let computer = new IntCodeComputer()
        computer.inputs = [inputs[amplifier], nextInput]
        computer.runProgram(input.slice())
        nextInput = computer.outputs[computer.outputs.length - 1]
      }
      max = Math.max(max, nextInput)
    }
    return max
  }

  part2(input: number[]): any {
    let max = 0
    for (let i = 0; i < 120; i++) {
      let phaseSettings = Combinatorics.permutation(createArray(), i)
      let computers = phaseSettings.map((i: number) => {
        let computer = new IntCodeComputer()
        computer.giveInput(5 + i)
        computer.runProgram(input.slice())
        return computer
      })

      let nextInput = 0
      while (computers[4].waitingForInput) {
        for (let amplifier = 0; amplifier < 5; amplifier++) {
          let computer = computers[amplifier]
          computer.giveInput(nextInput)
          nextInput = computer.outputs[computer.outputs.length - 1]
        }
      }
      max = Math.max(max, nextInput)
    }
    return max
  }

}

export default Day7;
