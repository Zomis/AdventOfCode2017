import Day from "./Day";
import { setPriority } from "os";

class Position3 {
  constructor(public x: number, public y: number, public z: number) {}

  signs(): Position3 {
    return new Position3(Math.sign(this.x), Math.sign(this.y), Math.sign(this.z))
  }

  plus(other: Position3): Position3 {
    return new Position3(this.x + other.x, this.y + other.y, this.z + other.z)
  }

  minus(other: Position3): Position3 {
    return new Position3(this.x - other.x, this.y - other.y, this.z - other.z)
  }

  absSum(): number {
    return Math.abs(this.x) + Math.abs(this.y) + Math.abs(this.z)
  }

  toString(): string {
    return `(${this.x}, ${this.y}, ${this.z})`
  }
}

class CelestialBody {

  velocity = new Position3(0, 0, 0)

  constructor(public position: Position3) {}

  applyGravity(other: CelestialBody) {
    let gravity = other.position.minus(this.position).signs()
    this.velocity = this.velocity.plus(gravity)
  }

  applyVelocity() {
    this.position = this.position.plus(this.velocity)
  }

  toString(): string {
    return `pos=${this.position} vel=${this.velocity}`
  }

  totalEnergy(): number {
    return this.potentialEnergy() * this.kineticEnergy()
  }

  potentialEnergy(): number {
    return this.position.absSum()
  }

  kineticEnergy(): number {
    return this.velocity.absSum()
  }

}

class Day12 implements Day<CelestialBody[]> {

  parse(text: string): CelestialBody[] {
    // <x=14, y=15, z=-2>
    return text.split('\n').map((x: string) => {
      let results = x.match(/<x=(-?\d+), y=(-?\d+), z=(-?\d+)/)!!
      let pos = new Position3(parseInt(results[1]), parseInt(results[2]), parseInt(results[3]))
      return new CelestialBody(pos)
    });
  }

  private step(bodies: Array<CelestialBody>) {
    for (let a of bodies) {
      for (let b of bodies) {
        if (a !== b) {
          a.applyGravity(b)
        }
      }
    }
    for (let a of bodies) {
      a.applyVelocity()
    }
  }

  part1(input: CelestialBody[]): any {
    for (let i = 0; i < 1000; i++) {
      this.step(input)
    }
    return input.map((b) => b.totalEnergy()).reduce((a, b) => a + b)
  }

  part2(input: CelestialBody[]): any {
    return 0
  }

}

export default Day12;
