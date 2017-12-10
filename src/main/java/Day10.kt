class Day10: Day<IntArray> {
    override fun parse(text: String): IntArray {
        return text.split(",").map { it.toInt() }.toIntArray()
    }

    override fun part1(input: IntArray): Any {
        val array = (0..255).toMutableList()
        var currentPosition = 0
        var skip = 0
        for (len in input) {
            reverse(array, currentPosition, len)
            currentPosition += len + skip
            skip++
        }
        return array[0] * array[1]
    }

    private fun reverse(array: MutableList<Int>, currentPosition: Int, len: Int) {
        for (i in 0 until len/2) {
            /*
            * curr i  len   A B
            * 0    0   2    0 1
            * 0    1   2    ---
            * 0    2   23   2  20
            * 2    2   8    4
            * */

            val switchIndexA = (currentPosition + i) % array.size
            val switchIndexB = (currentPosition + len - 1 - i ) % array.size
            val temp = array[switchIndexA]
            array[switchIndexA] = array[switchIndexB]
            array[switchIndexB] = temp
        }
    }

    override fun part2(input: IntArray): Any {
        return 0
    }


}