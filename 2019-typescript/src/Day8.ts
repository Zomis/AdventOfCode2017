import Day from "./Day";

class Day8 implements Day<number[]> {

  sizeX = 25
  sizeY = 6

  parse(text: string): number[] {
    return text.split('').map((x: string) => parseInt(x, 10));
  }

  part1(input: number[]): any {
    let w = this.sizeX, h = this.sizeY
    let layerCount = input.length / (w * h)
    let zeros = new Array<number>()
    for (let layer = 0; layer < layerCount; layer++) {
      let layerArray = input.slice(layer * w*h, layer*w*h+w*h)
      let zeroCount = layerArray.filter((i: number) => i === 0).length
      zeros.push(zeroCount)
    }
    let layerValue = Math.min(...zeros)
    let layerIndex = zeros.indexOf(layerValue)
    let realLayer = input.slice(layerIndex * w*h, layerIndex*w*h+w*h)

    return realLayer.filter((i: number) => i === 1).length * realLayer.filter((i: number) => i === 2).length
  }

  createLayers(input: number[]): Array<Array<Array<number>>> {
    let w = this.sizeX, h = this.sizeY
    let layerCount = input.length / (w * h)
    let layers = new Array<Array<Array<number>>>()

    for (let layerIndex = 0; layerIndex < layerCount; layerIndex++) {
      let layerInput = input.slice(layerIndex * w*h, layerIndex*w*h+w*h)
      let layer = new Array<Array<number>>()
      for (let y = 0; y < h; y++) {
        let row = layerInput.slice(y*w, y*w+w)
        layer.push(row)
      }
      layers.push(layer)
    }
    return layers
  }

  part2(input: number[]): any {
    let layers = this.createLayers(input)
    for (let y = 0; y < this.sizeY; y++) {
      let p = new Array<number>()
      for (let x = 0; x < this.sizeX; x++) {
        for (let layer = 0; layer < layers.length; layer++) {
          let value = layers[layer][y][x]
          if (value !== 2) {
            p.push(value)
            break
          }
        }
      }
      console.log(p.map((i: number) => i.toString()).map((s: string) => s === '0' ? ' ' : s).join(''))
    }
    return 0
  }

}

export default Day8;
