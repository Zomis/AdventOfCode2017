function nCr(n: number, r: number): number {
    if (r > n || r < 0) {
        return 0;
    }
    if (r == 0 || r == n) {
        return 1;
    }
    if (r > n / 2) {
        // As Pascal's triangle is horizontally symmetric, use that property to reduce the for-loop below
        r = n - r;
    }

    let value = 1;

    for (let i = 0; i < r; i++) {
        value = value * (n - i) / (i + 1);
    }

    return value;
}

function specificCombination(elements: number, size: number, combinationNumber: number): number[] {
    if (combinationNumber <= 0) {
        throw new Error("Combination must be positive");
    }
    if (elements < 0 || size < 0) {
        throw new Error("Elements and size cannot be negative");
    }

    let result = new Array<number>();

    let resultIndex = 0;
    let nextNumber = 0;
    let combination = combinationNumber;
    let remainingSize = size;
    let remainingElements = elements;

    while (remainingSize > 0) {
        let ncr = nCr(remainingElements - 1, remainingSize - 1);
        if (ncr === 0) {
            throw new Error("Combination out of range: " + combinationNumber + " with " + elements + " elements and size " + size);
        }
        if (combination <= ncr) {
            result[resultIndex] = nextNumber;
            remainingSize--;
            resultIndex++;
        } else {
            combination -= ncr;
        }
        remainingElements--;
        nextNumber++;
    }

    return result;
}

function permutation(elements: number[], i: number): number[] {
  let result = new Array<number>()
  while (elements.length > 0) {
    let current = i % elements.length
    i = Math.floor(i / elements.length)
    result.push(elements[current])
    elements.splice(current, 1)
  }
  return result
}

export default {
  nCr, specificCombination, permutation
}
