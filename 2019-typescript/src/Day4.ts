import Day from "./Day";

class Day4 implements Day<number[]> {
  parse(input: string): number[] {
    return input.split('-').map(s => parseInt(s, 10));
  }

  part1(input: number[]): any {
    return this.countMatching(input[0], input[1], (e: number[]) => true)
  }

  countMatching(lowest: number, highest: number, extraCondition: Function) {
    var count = 0;
    for (var a = 0; a <= 9; a++) {
      for (var b = 0; b <= 9; b++) {
        for (var c = 0; c <= 9; c++) {
          for (var d = 0; d <= 9; d++) {
            for (var e = 0; e <= 9; e++) {
              for (var f = 0; f <= 9; f++) {
                var digits = [a, b, c, d, e, f];
                let n = a*100000 + b*10000 + c*1000 + d*100 + e*10 + f;
                if (n >= lowest && n <= highest) {
                  let match = false, decreasing = false;
                  for (var i = 0; i < digits.length - 1; i++) {
                    if (digits[i] === digits[i + 1]) {
                      match = true;
                    }
                    if (digits[i + 1] < digits[i]) {
                      decreasing = true;
                      break;
                    }
                  }
                  if (match && !decreasing && extraCondition(digits)) {
                    count++;
                  }
                }
              }
            }
          }
        }
      }
    }
    return count;
  }

  part2(input: number[]): any {
    let isOK = (digits: number[]) => {
      var consecutiveNumber = digits[0];
      var consecutiveCount = 1;
      for (var i = 1; i < digits.length; i++) {
        if (digits[i] === consecutiveNumber) {
          consecutiveCount++;
        } else {
          if (consecutiveCount === 2) {
            return true;
          }
          consecutiveNumber = digits[i];
          consecutiveCount = 1;
        }
      }
      return consecutiveCount === 2;
    }
    return this.countMatching(input[0], input[1], isOK)
  }
}

export default Day4;
