package net.zomis.aoc.y2020

object Program {

    enum class InstructionType(val code: String) {
        ACCUMULATE("acc"), JUMP("jmp"), NO_OP("nop")
    }
    class Instruction(val type: InstructionType, val value: Int)
    class Code(val instructions: List<Instruction>)
    class Execution(val code: Code, var accumulator: Int) {
        var codePointer = 0

        fun next(): Instruction {
            val instruction = code.instructions[codePointer]
            when (instruction.type) {
                InstructionType.ACCUMULATE -> {
                    accumulator += instruction.value
                    codePointer++
                }
                InstructionType.JUMP -> codePointer += instruction.value
                InstructionType.NO_OP -> {
                    codePointer++
                }
            }
            return instruction
        }
    }
    fun read(sequence: Sequence<String>): Code {
        val instructions = sequence.map {line ->
            val instructionType = InstructionType.values().first { it.code == line.substringBefore(' ') }
            val instructionValue = line.substringAfter(' ')
            Instruction(instructionType, instructionValue.toInt())
        }.toList()
        return Code(instructions)
    }

}