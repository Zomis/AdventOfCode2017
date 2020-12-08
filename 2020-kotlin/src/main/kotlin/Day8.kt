package net.zomis.aoc.y2020

object Day8 {

    val day = Days.day<Program.Code>(this) {
        input {
            Program.read(this)
        }
        part1 {
            val exec = Program.Execution(input, 0)
            val pointers = Array(input.instructions.size) { false }
            while (true) {
                if (pointers[exec.codePointer]) return@part1 exec.accumulator
                pointers[exec.codePointer] = true
                exec.next()
            }
        }
        part2 {
            val possibleChanges = input.instructions.withIndex().filter {
                it.value.type in listOf(Program.InstructionType.NO_OP, Program.InstructionType.JUMP)
            }
            for (change in possibleChanges) {
                val modifiedProgram = input.instructions.withIndex().map {
                    if (it.index != change.index) return@map it.value
                    when (it.value.type) {
                        Program.InstructionType.NO_OP -> Program.Instruction(Program.InstructionType.JUMP, it.value.value)
                        Program.InstructionType.JUMP -> Program.Instruction(Program.InstructionType.NO_OP, it.value.value)
                        else -> throw IllegalStateException(it.toString())
                    }
                }
                val exec = Program.Execution(Program.Code(modifiedProgram), 0)
                val pointers = Array(input.instructions.size) { false }
                while (true) {
                    if (pointers[exec.codePointer]) break
                    pointers[exec.codePointer] = true
                    exec.next()
                    if (exec.codePointer == input.instructions.size) return@part2 exec.accumulator
                }
            }
        }
    }

}
