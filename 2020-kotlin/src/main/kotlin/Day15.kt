package net.zomis.aoc.y2020

object Day15 {

    class Memory(val startingNumbers: List<Int>) {
        val lastAppearance: MutableMap<Int, Int> = mutableMapOf()
        var turn = 1
        var nextNumber = 0
        init {
            startingNumbers.forEachIndexed { index, i ->
                addNumber(i)
            }
        }

        private fun addNumber(i: Int) {
            lastAppearance[nextNumber] = turn
            nextNumber = i
            turn++
        }

        fun process() {
            val lastTurn = lastAppearance[nextNumber]
            if (lastTurn != null) {
                addNumber(turn - lastTurn)
            } else addNumber(0)
        }
    }
    val day = Days.day<List<Int>>(this) {
        input {
            joinToString("").split(",").map { it.toInt() }
        }
        part1 {
            val memory = Memory(input.toMutableList())
            while (memory.turn <= 2020) memory.process()
            memory.nextNumber
        }
        part2 {
            val memory = Memory(input.toMutableList())
            while (memory.turn <= 30000000) {
                memory.process()
            }
            memory.nextNumber
        }
    }
}
