typealias BridgePiece = Pair<Int, Int>

class Day24: Day<List<BridgePiece>> {
    override fun parse(text: String): List<BridgePiece> {
        return text.lines().map { it.split("/") }.map {
            val a = it[0].toInt()
            val b = it[1].toInt()
            Pair(a, b)
            // Pair(Math.min(a, b), Math.max(a, b))
        }
    }

    override fun part1(input: List<BridgePiece>): Any {
        return solve(emptyList(), 0, 0, input)
    }

    private fun solve(used: List<BridgePiece>, current: Int, sum: Int, remaining: List<BridgePiece>): Int {
        val options = remaining.filter { it.first == current || it.second == current }
        if (options.isEmpty()) {
            return sum
        }
//        println("Used $used has options $options current $current sum $sum")

        return options.map {
            val next = if (it.first == current) it.second else it.first
            val nextList = remaining.toMutableList()
            nextList.remove(it)
            val usedNext = used.toMutableList()
            usedNext.add(it)
            solve(usedNext, next, sum + it.first + it.second, nextList)
        }.max()!!
    }

    override fun part2(input: List<BridgePiece>): Any {
        return 0
    }

}