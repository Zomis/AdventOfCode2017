inline fun MutableMap<String, Long>.numberOrMapValue(value: String): Long {
    val number = Regex("-?\\d+").matches(value)
    return if (number) value.toLong() else getOrElse(value, { 0 })
}

class Day18: Day<List<Day18.Instruction18>> {
    val SOUND_VALUE = "SOUND_VALUE"

    override fun parse(text: String): List<Instruction18> {
        return text.lines()
            .map { it.split(" ") }
            .map { Instruction18(it[0], it[1], it.getOrElse(2, {_ -> ""})) }
    }

    override fun part1(input: List<Instruction18>): Any {
        val vals = mutableMapOf<String, Long>()
        val prog = Program(input, vals, { instruction, values -> values[SOUND_VALUE] = values.numberOrMapValue(instruction.first); 1},
        { instruction, values -> if (values.getOrElse(instruction.first, {0}) != 0.toLong()) values[instruction.first] = values.getOrElse(SOUND_VALUE, {0}); 1})

        while (prog.instruction in 0..input.size) {
            val result = prog.runStep()
            if (result.first.type == "rcv" && vals[SOUND_VALUE] ?: 0 != 0.toLong()) {
                return vals[SOUND_VALUE] ?: 0
            }
        }

        return vals[SOUND_VALUE] ?: 0
    }

    class Program(val instructions: List<Instruction18>,
            private val values: MutableMap<String, Long>, val sndAction: (Instruction18, MutableMap<String, Long>) -> Int,
              val rcvAction: (Instruction18, MutableMap<String, Long>) -> Int) {
        var instruction = 0

        fun runStep(): Pair<Instruction18, Int> {
            val ins = instructions[instruction]
            val result = perform(values, ins)
            instruction += result
            return Pair(ins, result)
        }

        private fun perform(values: MutableMap<String, Long>, instruction: Instruction18): Int {
            println("Perform $instruction at $values")
            // Thread.sleep(100)
            when (instruction.type) {
                "set" -> values[instruction.first] = values.numberOrMapValue(instruction.second)
                "add" -> values[instruction.first] = values.getOrElse(instruction.first, {0}) + values.numberOrMapValue(instruction.second)
                "mul" -> values[instruction.first] = values.getOrElse(instruction.first, {0}) * values.numberOrMapValue(instruction.second)
                "mod" -> values[instruction.first] = values.getOrElse(instruction.first, {0}) % values.numberOrMapValue(instruction.second)
                "rcv" -> return rcvAction(instruction, values)
                "snd" -> return sndAction(instruction, values)
                "jgz" -> {
                    if (values[instruction.first] ?: 0 > 0) {
                        val value: Long = values.numberOrMapValue(instruction.second)
                        println("Jump $value")
                        return value.toInt()
                    }
                }
            }
            return 1
        }
    }

    override fun part2(input: List<Instruction18>): Any {
        return 0
    }

    data class Instruction18(val type: String, val first: String, val second: String)



}