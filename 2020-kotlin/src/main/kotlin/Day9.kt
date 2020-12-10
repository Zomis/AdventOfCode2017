package net.zomis.aoc.y2020

object Day9 {

    val day = Days.day<List<Long>>(this) {
        input {
            map { it.toLong() }.toList()
        }
        part1 {
            partOne(input)
        }
        part2 {
            val search = partOne(input)
            for (startIndex in input.indices) {
                val startNumber = input[startIndex]
                var sum = startNumber
                for (endIndex in input.indices.filter { it > startIndex }) {
                    val endNumber = input[endIndex]
                    sum += endNumber
                    if (sum == search) {
                        return@part2 startNumber + endNumber
                    }
                    if (sum > search) {
                        break
                    }
                }
            }
            throw IllegalStateException()
        }
    }

    fun partOne(input: List<Long>): Long {
        val preamble = 25
        for (index in preamble until input.size) {
            val set = input.subList(index - preamble, index).toSet()
            val target = input[index]
            val found = Day1.findSum(set, target)
            if (found.isEmpty()) return target
        }
        throw IllegalStateException()
    }


}
