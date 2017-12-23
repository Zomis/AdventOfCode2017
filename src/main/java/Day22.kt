class Day22: Day<Array<BooleanArray>> {
    override fun parse(text: String): Array<BooleanArray> {
        return text.lines().map { it.toCharArray().map { it == '#' }.toBooleanArray() }.toTypedArray()
    }

    enum class NodeState {
        CLEAN, WEAKENED, INFECTED, FLAGGED
    }

    fun rotate1(state: NodeState): (Dir4) -> Dir4 {
        return when (state) {
            NodeState.INFECTED -> Dir4::rotateRight
            NodeState.CLEAN -> Dir4::rotateLeft
            else -> throw IllegalStateException(state.toString())
        }
    }

    fun stateChange1(state: NodeState): NodeState {
        return when (state) {
            NodeState.CLEAN -> NodeState.INFECTED
            NodeState.INFECTED -> NodeState.CLEAN
            else -> throw IllegalStateException(state.toString())
        }
    }

    fun run(map: MutableMap<Pair<Int, Int>, NodeState>, rotate: (NodeState) -> (Dir4) -> Dir4, stateChange: (NodeState) -> NodeState): Int {
        var virusX = 0
        var virusY = 0
        var dir = Dir4.UP
        var count = 0
        for (i in 0 until 10000) {
            val pair = Pair(virusX, virusY)
            val oldState = map[pair] ?: NodeState.CLEAN
            dir = rotate(oldState)(dir)
            val nextState = stateChange(oldState)
            map[pair] = nextState
            if (nextState == NodeState.INFECTED) {
                count++
            }
            virusX += dir.x
            virusY += dir.y
        }
        return count
    }

    override fun part1(input: Array<BooleanArray>): Any {
        val map = mutableMapOf<Pair<Int, Int>, NodeState>()
        val origoY = input.size / 2
        val origoX = input[origoY].size / 2
        input.forEachIndexed({y, it ->
            it.forEachIndexed({x, v ->
                map[Pair(x - origoX, y - origoY)] = if (v) NodeState.INFECTED else NodeState.CLEAN
            })
        })

        return run(map, ::rotate1, ::stateChange1)
    }

    override fun part2(input: Array<BooleanArray>): Any {
        return 0
    }

}