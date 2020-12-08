package net.zomis.aoc.y2020

object Day7 {

    data class BagContent(val amount: Int, val bag: String)
    data class BagRule(val name: String, val contents: List<BagContent>)
    data class BagRules(val bags: Map<String, BagRule>) {
        fun findRulesThatContain(bag: String): Set<BagRule> {
            return bags.values.filter { it.contents.any { c -> c.bag == bag } }.toMutableSet()
        }
    }
    val day = Days.day<BagRules>(this) {
        input {
            BagRules(
                map {rule ->
                    val bagName = rule.substringBefore(" contain ").replace(" bags", " bag")
                    val bagContents = rule.substringAfter(" contain ").split(", ").mapNotNull {
                        if ("no other bags" in it) return@mapNotNull null
                        it.substringBefore(' ').toInt() to it.substringAfter(' ')
                            .replace("bags", "bag")
                            .replace(".", "")
                    }.map { BagContent(it.first, it.second) }
                    BagRule(bagName, bagContents)
                }.associateBy { it.name }
            )
        }
        part1 {
            val containsGolden = input.findRulesThatContain("shiny gold bag").toMutableSet()
            while (true) {
                val oldSize = containsGolden.size
                containsGolden.addAll(containsGolden.flatMap { input.findRulesThatContain(it.name) }.toMutableSet())
                if (oldSize == containsGolden.size) break
            }
            containsGolden.size
        }
        part2 {
            var closedBags = 0
            val openBags = mutableListOf(1 to "shiny gold bag")
            while (true) {
                val processing = openBags.toList()
                for (bag in processing) {
                    closedBags += bag.first
                    openBags.remove(bag)
                    val openedContents = input.bags.getValue(bag.second).contents.map { (bag.first * it.amount) to it.bag }
                    openBags.addAll(openedContents)
                }
                if (openBags.isEmpty()) return@part2 closedBags - 1 // Do not include the original shiny gold bag
            }
        }
    }

}
