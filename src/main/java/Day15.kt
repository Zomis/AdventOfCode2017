class Day15: Day<Pair<Int, Int>> {
    override fun parse(text: String): Pair<Int, Int> {
        val arr = text.lines().map { it.substringAfter("starts with ").toInt() }.toIntArray()
        return Pair(arr[0], arr[1])
    }

    fun next(input: Pair<Long, Long>): Pair<Long, Long> {
        return Pair(input.first * 16807 % 2147483647, input.second * 48271 % 2147483647)
    }

    private val matchAND = (1.shl(16) - 1).toLong()
    private val zero = 0.toLong()
    fun match(input: Pair<Long, Long>): Boolean {
        val a = input.first.and(matchAND)
        val b = input.second.and(matchAND)
        return a.xor(b) == zero
    }

    override fun part1(input: Pair<Int, Int>): Any {
        val range = 0 until 40_000_000
        var count = 0
        var values = Pair(input.first.toLong(), input.second.toLong())
        for (i in range) {
            values = next(values)
            if (match(values)) {
                count++
            }
        }
        return count
    }

    override fun part2(input: Pair<Int, Int>): Any {
        return 0
    }

}