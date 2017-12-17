class Day17: Day<Int> {
    override fun parse(text: String): Int {
        return text.toInt()
    }

    override fun part1(input: Int): Any {
        val spin = mutableListOf(0)
        var index = 0
        for (i in 1..2017) {
            index = (index + input) % spin.size
            spin.add(index + 1, i)
            index++
        }
        return spin[(index + 1) % spin.size]
    }

    override fun part2(input: Int): Any {
        return 0
    }

}