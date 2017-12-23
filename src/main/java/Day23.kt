class Day23: Day<List<Day18.Instruction18>> {
    val day18 = Day18()
    override fun parse(text: String): List<Day18.Instruction18> {
        return day18.parse(text)
    }

    override fun part1(input: List<Day18.Instruction18>): Any {
        val prog = Day18.Program(0, input, mutableMapOf(), {_, _ -> 0}, {_, _ -> 0})
        var count = 0
        while (!prog.stopped) {
            val result = prog.runStep()
            if (result.first?.type == "mul") {
                count++
            }
        }
        return count
    }

    override fun part2(input: List<Day18.Instruction18>): Any {
        return 0
    }

}