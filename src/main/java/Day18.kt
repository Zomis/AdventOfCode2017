class Day18: Day<List<Day18.Instruction18>> {
    val SOUND_VALUE = "SOUND_VALUE"

    override fun parse(text: String): List<Instruction18> {
        return text.lines()
            .map { it.split(" ") }
            .map { Instruction18(it[0], it[1], it.getOrElse(2, {_ -> ""})) }
    }

    override fun part1(input: List<Instruction18>): Any {
        val values = mutableMapOf<String, Long>()
        var instruction = 0
        while (instruction in 0..input.size) {
            val ins = input[instruction]
            val result = perform(values, ins)
            instruction += result
            if (ins.type == "rcv" && values[SOUND_VALUE] ?: 0 != 0.toLong()) {
                return values[SOUND_VALUE] ?: 0
            }
        }
        return values[SOUND_VALUE] ?: 0
    }

    private fun perform(values: MutableMap<String, Long>, instruction: Instruction18): Int {
        println("Perform $instruction at $values")
        // Thread.sleep(100)
        when (instruction.type) {
            "set" -> values[instruction.first] = mapValue(values, instruction.second)
            "add" -> values[instruction.first] = values.getOrElse(instruction.first, {0}) + mapValue(values, instruction.second)
            "mul" -> values[instruction.first] = values.getOrElse(instruction.first, {0}) * mapValue(values, instruction.second)
            "mod" -> values[instruction.first] = values.getOrElse(instruction.first, {0}) % mapValue(values, instruction.second)
            "rcv" -> if (values.getOrElse(instruction.first, {0}) != 0.toLong()) values[instruction.first] = values.getOrElse(SOUND_VALUE, {0})
            "snd" -> values[SOUND_VALUE] = mapValue(values, instruction.first)
            "jgz" -> {
                if (values[instruction.first] ?: 0 > 0) {
                    val value: Long = mapValue(values, instruction.second)
                    println("Jump $value")
                    return value.toInt()
                }
            }
        }
        return 1
    }

    private fun mapValue(values: MutableMap<String, Long>, value: String): Long {
        val number = Regex("-?\\d+").matches(value)
        return if (number) value.toLong() else values.getOrElse(value, { 0 })
    }

    override fun part2(input: List<Instruction18>): Any {
        return 0
    }

    data class Instruction18(val type: String, val first: String, val second: String)



}