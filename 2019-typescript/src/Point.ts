function gcd(a: number, b: number): number {
  if (b === 0) {
    return a
  }
  return gcd(b, Math.floor(a % b))
}

class Point {
  constructor(public x: number, public y: number) {}

  minus(other: Point): Point {
    return new Point(this.x - other.x, this.y - other.y)
  }

  plus(other: Point): Point {
    return new Point(this.x + other.x, this.y + other.y)
  }

  normalize(): Point {
    let d = Math.abs(gcd(this.x, this.y))
    let r = new Point(this.x / d, this.y / d)
    return r
  }

  clone(): Point {
    return new Point(this.x, this.y)
  }

  angle(to: Point): number {
    let dx = to.x - this.x
    let dy = this.x - to.y
    let radians = Math.atan2(dy, dx)
    let degrees = 90 - radians * 180 / Math.PI
    return degrees < 0 ? degrees + 360 : degrees
  }

  toString(): string {
    return `(${this.x}, ${this.y})`
  }
}

export default Point
