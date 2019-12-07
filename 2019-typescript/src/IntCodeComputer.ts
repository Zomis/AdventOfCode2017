class IntCodeComputer {

  public inputs: number[] = [];
  public outputs = new Array<number>();
  public waitingForInput = false
  public instructionIndex = 0
  public tape: number[] = []

  readParameter(values: number[], index: number, mode: number) {
    let value = values[index];
    if (mode === 0) {
      return values[value];
    } else if (mode === 1) {
      return value;
    }
    throw new Error("mode " + mode);
  }

  readParameters(values: number[], instructionIndex: number, parameterCount: number, additional: number): Array<number> {
    let modes = values[instructionIndex] / 100;
    let params = new Array<number>();
    for (let i = 0; i < parameterCount; i++) {
      let param = this.readParameter(values, instructionIndex + i + 1, Math.floor(modes % 10));
      modes /= 10;
      params.push(param);
    }
    for (let i = 0; i < additional; i++) {
      let param = this.readParameter(values, instructionIndex + parameterCount + i + 1, 1);
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
      values[parameters[2]] = parameters[0] + parameters[1];
    } else if (v === 2) {
      parameters = this.readParameters(values, instructionIndex, 2, 1);
      values[parameters[2]] = parameters[0] * parameters[1];
    } else if (v === 3) {
      let param = this.readParameter(values, instructionIndex + 1, 1);
      this.waitingForInput = (this.inputs.length === 0)
      if (this.inputs.length === 0) {
        return instructionIndex
      }
      values[param] = this.inputs[0];
      this.inputs.splice(0, 1)
      return instructionIndex + 2;
    } else if (v === 4) {
      let param = this.readParameter(values, instructionIndex + 1, 1);
      this.outputs.push(values[param]);
      return instructionIndex + 2;
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
      values[parameters[2]] = parameters[0] < parameters[1] ? 1 : 0;
    } else if (v === 8) {
      parameters = this.readParameters(values, instructionIndex, 2, 1);
      values[parameters[2]] = parameters[0] === parameters[1] ? 1 : 0;
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

}

export default IntCodeComputer;
