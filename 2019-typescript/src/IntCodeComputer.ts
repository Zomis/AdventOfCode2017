class IntCodeComputer {

  public inputs: number[] = [];
  public outputs = new Array<number>();
  public waitingForInput = false
  public instructionIndex = 0
  public tape: number[] = []
  relativeBase = 0

  readParameter(values: number[], index: number, mode: number) {
    let value = values[index];
    if (mode === 0) {
      return this.readValue(values, value);
    } else if (mode === 1) {
      return value;
    } else if (mode === 2) {
      return this.readValue(values, value + this.relativeBase)
    }
    throw new Error("mode " + mode);
  }

  readWriteParameter(index: number, mode: number) {
    let value = this.tape[index];
    if (mode === 0) {
      return value;
    } else if (mode === 1) {
      return value;
    } else if (mode === 2) {
      return value + this.relativeBase
    }
    throw new Error("mode " + mode);
  }

  readValue(values: number[], index: number) {
    if (values.length <= index) {
      return 0
    }
    return values[index]
  }

  writeValue(values: number[], index: number, value: number) {
    if (index < 0) {
      throw new Error("Cannot write to a negative index: " + index)
    }
    while (values.length < index) {
      values.push(0)
    }
    values[index] = value
  }

  readParameters(values: number[], instructionIndex: number, reads: number, writes: number): Array<number> {
    let modes = values[instructionIndex] / 100;
    let params = new Array<number>();
    for (let i = 0; i < reads; i++) {
      let param = this.readParameter(values, instructionIndex + i + 1, Math.floor(modes % 10));
      modes = Math.floor(modes / 10);
      params.push(param);
    }
    for (let i = 0; i < writes; i++) {
      let mode = Math.floor(modes % 10)
      let param = this.readWriteParameter(instructionIndex + reads + i + 1, mode);
      modes = Math.floor(modes / 10);
      params.push(param);
    }
    return params;
  }

  giveInput(value: number) {
    this.inputs.push(value)
    if (this.waitingForInput) {
      this.run()
    }
  }

  performInstruction(values: number[], instructionIndex: number): number {
    let v = values[instructionIndex] % 100;
    let parameters: Array<number> = []
    if (v === 1) {
      parameters = this.readParameters(values, instructionIndex, 2, 1);
      this.writeValue(values, parameters[2], parameters[0] + parameters[1]);
    } else if (v === 2) {
      parameters = this.readParameters(values, instructionIndex, 2, 1);
      this.writeValue(values, parameters[2], parameters[0] * parameters[1]);
    } else if (v === 3) {
      parameters = this.readParameters(values, instructionIndex, 0, 1);

      this.waitingForInput = (this.inputs.length === 0)
      if (this.inputs.length === 0) {
        return instructionIndex
      }
      this.writeValue(values, parameters[0], this.inputs[0]);
      this.inputs.splice(0, 1)
      return instructionIndex + 2;
    } else if (v === 4) {
      parameters = this.readParameters(values, instructionIndex, 1, 0);
      this.outputs.push(parameters[0]);
    } else if (v === 5) {
      parameters = this.readParameters(values, instructionIndex, 2, 0);
      if (parameters[0] !== 0) {
        return parameters[1];
      }
    } else if (v === 6) {
      parameters = this.readParameters(values, instructionIndex, 2, 0);
      if (parameters[0] === 0) {
        return parameters[1];
      }
    } else if (v === 7) {
      parameters = this.readParameters(values, instructionIndex, 2, 1);
      this.writeValue(values, parameters[2], parameters[0] < parameters[1] ? 1 : 0);
    } else if (v === 8) {
      parameters = this.readParameters(values, instructionIndex, 2, 1);
      this.writeValue(values, parameters[2], parameters[0] === parameters[1] ? 1 : 0);
    } else if (v === 9) {
      parameters = this.readParameters(values, instructionIndex, 1, 0)
      this.relativeBase += parameters[0]
    } else if (v === 99) {
      return instructionIndex
    } else {
      throw new Error("Kaputt at " + instructionIndex + " with value " + v);
    }
    return instructionIndex + parameters.length + 1;
  }

  runProgram(values: number[]) {
    this.instructionIndex = 0
    this.tape = values.slice()
    this.run()
  }

  run() {
    while (true) {
      let old = this.instructionIndex
      this.instructionIndex = this.performInstruction(this.tape, this.instructionIndex)
      if (old === this.instructionIndex) {
        return
      }
    }
  }

  readOutput(): number {
    if (this.outputs.length === 0) {
      throw new Error("No outputs available");
    }
    let result = this.outputs[0]
    this.outputs.splice(0, 1)
    return result
  }

}

export default IntCodeComputer;
