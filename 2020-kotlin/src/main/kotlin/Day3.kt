package net.zomis.aoc.y2020

data class Pos(val x: Int, val y: Int) {
    operator fun plus(other: Pos): Pos = Pos(this.x + other.x, this.y + other.y)
}
object Day3 {

    data class Input(val lines: List<List<Boolean>>) {
        fun at(x: Int, y: Int): Boolean? {
            if (lines.size <= y) return null
            val line = lines[y]
            return line[x % line.size]
        }

        fun countTrees(slope: Pos): Long {
            var pos = Pos(0, 0)
            var count = 0
            while (true) {
                val v = this.at(pos.x, pos.y) ?: return count.toLong()
                count += if (v) 1 else 0
                pos += slope
            }
        }
    }
    val day = Days.day<Input>(3) {
        input {
            Input(map { it.map { c -> c == '#' } }.toList())
        }
        part1 {
            input.countTrees(Pos(3, 1))
        }
        part2 {
            val slopes = listOf(Pos(1, 1), Pos(3, 1), Pos(5, 1), Pos(7, 1), Pos(1, 2))
            val results = slopes.map { input.countTrees(it) }
            results.reduce { acc, i -> acc * i }
        }
    }

}
