import Day from "./Day";

class Orbit {
  constructor(public a: string, orbiting: string) {}
}

class OrbitMap {
  public orbits: any = {}

  add(a: string, orbiting: string) {
    this.orbits[a.trim()] = orbiting.trim()
  }

  orbitCount(key: string): number {
    var orbiter: string = key;
    let count = 0;
    while (this.orbits[orbiter]) {
      orbiter = this.orbits[orbiter]
      count++
    }
    return count;
  }

  findParents(key: string): string[] {
    let orbiter: string = key;
    let result = new Array<string>()
    while (this.orbits[orbiter]) {
      orbiter = this.orbits[orbiter]
      result.splice(0, 0, orbiter)
    }
    return result
  }
}

class Day6 implements Day<OrbitMap> {

  parse(text: string): OrbitMap {
    let map = new OrbitMap()
    text.split('\n').map((x: string) => x.split(')')).forEach((x: string[]) => map.add(x[1], x[0]));
    return map
  }

  part1(input: OrbitMap): any {
    let keys = Object.keys(input.orbits)
    let orbitCount = keys.map((key: string) => input.orbitCount(key))
    return orbitCount.reduce((a: number, b: number) => a + b, 0)
  }

  part2(input: OrbitMap): any {
    let santa = input.findParents("SAN")
    let you = input.findParents("YOU")
    for (let i = Math.min(santa.length, you.length); i >= 0; i--) {
      if (santa[i] === you[i]) {
        return santa.length - 1 - i + you.length - 1 - i;
      }
    }
    return -1;
  }

}

export default Day6;
