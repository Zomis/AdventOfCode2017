import Day from "./Day";

class Item {
  constructor(public count: number, public name: string) {}

  copy(): Item {
    return new Item(this.count, this.name)
  }

  toString(): string {
    return `${this.count} ${this.name}`
  }
}

function modify(inventory: Array<Item>, itemName: string, count: number) {
  let existing: Item | undefined = inventory.find((i: Item) => i.name == itemName)
  if (existing) {
    existing.count += count
  } else {
    inventory.push(new Item(count, itemName))
  }
}

class Recipe {
  constructor(public inputs: Array<Item>, public result: Item) {}

  apply(have: Array<Item>, want: Item): Array<Item> {
    if (this.result.name != want.name) {
      throw new Error("Trying to apply the incorrect recipe to get " + want)
    }
    let results = have.slice()
    let times = Math.ceil(Math.abs(want.count) / this.result.count)
    this.inputs.forEach((i: Item) => {
      modify(results, i.name, -i.count * times)
    })
    modify(results, this.result.name, this.result.count * times)
    return results
  }

  toString(): string {
    return `${this.inputs} => ${this.result}`
  }
}

function parseItem(item: string): Item {
  let split = item.split(" ")
  return new Item(parseInt(split[0], 10), split[1])
}

function parseRecipe(line: string): Recipe {
  let split = line.split(" => ")
  let ingredients = split[0].split(", ")
  let result = split[1]
  return new Recipe(ingredients.map(parseItem), parseItem(result))
}

class Recipes {
  public recipes: Array<Recipe> = new Array<Recipe>()

  add(line: string) {
    this.recipes.push(parseRecipe(line.trim()))
  }

  findRecipeFor(name: string): Recipe | undefined {
    return this.recipes.find((r: Recipe) => r.result.name == name)
  }

}

function itemIsMissing(item: Item): boolean {
  return item.count < 0 && item.name != "ORE"
}

class Day14 implements Day<Recipes> {

  parse(text: string): Recipes {
    let map = new Recipes()
    text.split('\n').forEach((s: string) => map.add(s));
    return map
  }

  resolveRequirements(recipes: Recipes, inventory: Array<Item>): Array<Item> {
    let have = inventory.slice()
    while (have.some(itemIsMissing)) {
      let product = have.find(itemIsMissing)!!
      let recipe: Recipe | undefined = recipes.findRecipeFor(product.name)
      if (recipe) {
        have = recipe.apply(have, product)
      } else {
        throw new Error("Cannot find recipe for " + product.name)
      }
    }
    return have
  }

  resourceCount(inventory: Array<Item>, name: string): number {
    return inventory.find((i: Item) => i.name == name)!!.count
  }

  part1(input: Recipes): any {
    let have = new Array<Item>()
    have.push(new Item(-1, "FUEL"))
    have = this.resolveRequirements(input, have)
    return Math.abs(this.resourceCount(have, "ORE"))
  }

  part2(input: Recipes): any {
    let have = new Array<Item>()
    let requiredForOne = this.part1(input) as number
    have.push(new Item(1000000000000, "ORE"))
    let totalFuelAdded = 0
    while (this.resourceCount(have, "ORE") > 0) {
      let moreFuel = Math.floor(this.resourceCount(have, "ORE") / requiredForOne)
      if (moreFuel == 0) {
        moreFuel = 1
      }
      totalFuelAdded += moreFuel

      modify(have, "FUEL", -moreFuel)
      have = this.resolveRequirements(input, have)
    }
    return totalFuelAdded - 1;// this.resourceCount(have, "FUEL");
  }

}

export default Day14;
