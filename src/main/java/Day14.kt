class Day14: Day<String> {
    override fun parse(text: String): String {
        return text
    }

    override fun part1(input: String): Any {
        val range = 0..127
        val operation = Day10::knotHash
        val day = Day10()
        val hashes = range.map { "$input-$it" }.map { operation.invoke(day, it) }
        return hashes.sumBy { it.toCharArray().sumBy { Integer.bitCount(Integer.parseInt(it.toString(), 16)) }}
    }

    override fun part2(input: String): Any {
        return 0
    }

}