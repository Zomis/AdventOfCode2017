class Day5: Day<IntArray> {

    override fun parse(text: String): IntArray {
        return text.split("\n").map { it.trim() }.filter { !it.isEmpty() }.map { it.toInt() }.toIntArray()
    }

    override fun part1(input: IntArray): Any {
        var idx: Int = 0
        var count: Int = 0
        while (idx >= 0 && idx < input.size) {
            val value = input[idx]
            input[idx]++
            count++
            idx += value
        }
        return count
    }

    override fun part2(input: IntArray): Any {
        return 0
    }


}