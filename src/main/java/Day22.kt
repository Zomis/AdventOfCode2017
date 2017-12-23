class Day22: Day<Array<BooleanArray>> {
    override fun parse(text: String): Array<BooleanArray> {
        return text.lines().map { it.toCharArray().map { it == '#' }.toBooleanArray() }.toTypedArray()
    }

    override fun part1(input: Array<BooleanArray>): Any {
        val map = mutableMapOf<Pair<Int, Int>, Boolean>()
        val origoY = input.size / 2
        val origoX = input[origoY].size / 2
        input.forEachIndexed({y, it ->
            it.forEachIndexed({x, v ->
                map[Pair(x - origoX, y - origoY)] = v
            })
        })

        var virusX = 0
        var virusY = 0
        var dir = Dir4.UP
        var count = 0
        for (i in 0 until 10000) {
            val pair = Pair(virusX, virusY)
            if (map[pair] == true) {
                dir = dir.rotateRight()
                map[pair] = false
            } else {
                dir = dir.rotateLeft()
                count++
                map[pair] = true
            }
            virusX += dir.x
            virusY += dir.y
            // map[pair] = !map.getOrElse(pair, {false})
        }
        // 5520 is too low
        return count
    }

    override fun part2(input: Array<BooleanArray>): Any {
        return 0
    }

}