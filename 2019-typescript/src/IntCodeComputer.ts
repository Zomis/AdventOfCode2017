class IntCodeComputer {

  public input: number = 0;
  public outputs = new Array<number>();

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
      values[param] = this.input;
      console.log("Writing input at " + param);
      return instructionIndex + 2;
    } else if (v === 4) {
      let param = this.readParameter(values, instructionIndex + 1, 1);
      console.log("Output at " + instructionIndex + ": " + param + " = " + values[param]);
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
    } else {
      throw new Error("Kaputt at " + instructionIndex + " with value " + v);
    }
    return instructionIndex + parameters.length + 1;
  }

  runProgram(values: number[]) {
    let i = 0;
    while (values[i] != 99) {
      i = this.performInstruction(values, i);
    }
    return values[0];
  }

}

export default IntCodeComputer;
