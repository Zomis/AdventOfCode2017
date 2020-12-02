package net.zomis.aoc.y2020

class Day1 {

    fun sfd(input: Sequence<String>) {
        val numbers = input.map { it.toInt() }.toSet()
        val match = numbers.filter { numbers.contains(2020 - it) }
        println(match)
        println(match.reduce { acc, i -> acc * i })

        val m2 = numbers.map {
            findSum(numbers.minus(it), 2020 - it)
        }.filter { it.isNotEmpty() }.flatMap { it }.distinct()
        println(m2)
        println(m2.reduce { acc, i -> acc * i })
    }

    fun findSum(numbers: Iterable<Int>, target: Int): List<Int> =
        numbers.filter { numbers.contains(target - it) }

}

fun main() {
    val input = Day1::class.java.classLoader.getResourceAsStream("day1.txt").bufferedReader().lineSequence()
    Day1().sfd(input)
}