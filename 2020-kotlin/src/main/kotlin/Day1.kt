package net.zomis.aoc.y2020

object Day1 {

    val day = Days.day<Set<Long>>(this) {
        input {
            map { it.toLong() }.toSet()
        }
        part1 {
            findSum(input, 2020).reduce { acc, i -> acc * i }
        }
        part2 {
            input.asSequence().map {
                findSum(input.minus(it), 2020 - it)
            }.filter { it.isNotEmpty() }.flatten().distinct().reduce { acc, i -> acc * i }
        }
    }

    fun findSum(numbers: Iterable<Long>, target: Long): List<Long> =
        numbers.filter { numbers.contains(target - it) }

}
