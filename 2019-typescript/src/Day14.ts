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
    let results = have.slice()
    this.inputs.forEach((i: Item) => {
      modify(results, i.name, -i.count)
    })
    modify(results, this.result.name, this.result.count)
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

  part1(input: Recipes): any {
    let have = new Array<Item>()
    have.push(new Item(-1, "FUEL"))
    let i = 0
    while (have.some(itemIsMissing)) {
      // console.log(`Have: ${have}`)
      let product = have.find(itemIsMissing)!!
      let recipe: Recipe | undefined = input.findRecipeFor(product.name)
      // console.log(`Using recipe: ${recipe} to get ${product}`)
      if (recipe) {
        have = recipe.apply(have, product)
      } else {
        throw new Error("Cannot find recipe for " + product.name)
      }
    }
    return have.find((i: Item) => i.name == "ORE")!!.count
  }

  part2(input: Recipes): any {
    return 0;
  }

}

export default Day14;
