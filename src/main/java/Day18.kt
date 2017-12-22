import java.util.*
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

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
        val prog = Program(0, input, vals, { instruction, values -> values[SOUND_VALUE] = values.numberOrMapValue(instruction.first); 1},
        { instruction, values -> if (values.getOrElse(instruction.first, {0}) != 0.toLong()) values[instruction.first] = values.getOrElse(SOUND_VALUE, {0}); 1})

        while (prog.instruction in 0..input.size) {
            val result = prog.runStep()
            if (result.first?.type == "rcv" && vals[SOUND_VALUE] ?: 0 != 0.toLong()) {
                return vals[SOUND_VALUE] ?: 0
            }
        }

        return vals[SOUND_VALUE] ?: 0
    }

    class Program(val progId: Int, val instructions: List<Instruction18>,
            private val values: MutableMap<String, Long>, val sndAction: (Instruction18, MutableMap<String, Long>) -> Int,
              val rcvAction: (Instruction18, MutableMap<String, Long>) -> Int) {
        var instruction = 0
        var stopped = false

        fun runStep(): Pair<Instruction18?, Int> {
            if (instruction in 0 until instructions.size) {
                val ins = instructions[instruction]
                val result = perform(values, ins)
                instruction += result
                return Pair(ins, result)
            }
            this.stopped = true
            return Pair(null, 0)
        }

        private fun perform(values: MutableMap<String, Long>, instruction: Instruction18): Int {
            println("Program $progId Performs ${this.instruction}: $instruction at $values")
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
                        // println("Jump $value")
                        return value.toInt()
                    }
                }
            }
            return 1
        }
    }

    private fun snd(queue: BlockingQueue<Long>): (Instruction18, MutableMap<String, Long>) -> Int {
        return { instruction, values -> queue.put(values.numberOrMapValue(instruction.first)); 1 }
    }

    private fun rcv(queue: BlockingQueue<Long>, programs: List<Program>, thisIndex: Int): (Instruction18, MutableMap<String, Long>) -> Int {
        return lit@ { instruction, values ->

            val thisProgram  = programs[thisIndex]
            if (queue.isEmpty()) {
                if (!thisProgram.stopped) {
                    println("Stopping $thisIndex")
                }
                thisProgram.stopped = true
                return@lit 0
            }
            thisProgram.stopped = false
            val taken = queue.take()
            println("Resuming $thisIndex Queue size is ${queue.size}")
            values[instruction.first] = taken
            1
        }
    }

    override fun part2(input: List<Instruction18>): Any {
        val queue1 = LinkedBlockingQueue<Long>()
        val queue2 = LinkedBlockingQueue<Long>()
        val programs = mutableListOf<Program>()
        val prog1 = Program(0, input, mutableMapOf(Pair("p", 0.toLong())), snd(queue2), rcv(queue1, programs, 0))
        val prog2 = Program(1, input, mutableMapOf(Pair("p", 1.toLong())), snd(queue1), rcv(queue2, programs, 1))
        programs.add(prog1)
        programs.add(prog2)

        var count = 0
        val sc = Scanner(System.`in`)
        while (!prog1.stopped || !prog2.stopped) {
            prog1.runStep()
            val inst = prog2.runStep()
            if (inst.first?.type == "snd") {
                count++
                println("Count is now $count")
                sc.nextLine()
            }
        }
        return count
    }

    data class Instruction18(val type: String, val first: String, val second: String)



}