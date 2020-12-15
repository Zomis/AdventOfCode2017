package net.zomis.aoc.y2020

object Day14 {

    fun maskNumber(length: Int, index: Int): Long {
        val indexFromEnd = length - index
        return 1L.shl(indexFromEnd - 1)
    }
    fun Long.clearBits(other: Long): Long = this.xor(this.and(other))
    sealed class Write {
        data class MaskWrite(val mask: String): Write() {
            fun process(value: Long): Long {
                return value.xor(value.and(clear)).or(set)
            }

            val clear = mask.withIndex().filter { it.value == '0' }.map { maskNumber(mask.length, it.index) }.sum()
            val set = mask.withIndex().filter { it.value == '1' }.map { maskNumber(mask.length, it.index) }.sum()

            fun allAddresses(address: Long): Sequence<Long> {
                val floating = mask.withIndex().filter { it.value == 'X' }.map { maskNumber(mask.length, it.index) }

                fun recursiveFloating(remaining: List<Long>, value: Long): Sequence<Long> {
                    return sequence {
                        if (remaining.isEmpty()) {
                            yield(value)
                            return@sequence
                        }
                        val next = remaining.first()
                        val after = remaining.drop(1)
                        yieldAll(recursiveFloating(after, value.clearBits(next)))
                        yieldAll(recursiveFloating(after, value.or(next)))
                    }
                }
                return recursiveFloating(floating, address.or(set))
            }

            override fun toString(): String = "CLEAR $clear SET $set"
        }
        data class MemWrite(val address: Long, val value: Long): Write()
    }
    class MemoryWriting {
        val values = mutableMapOf<Long, Long>()
        var mask: Write.MaskWrite? = null
    }

    val day = Days.day<List<Write>>(this) {
        input {
            map {line ->
                if (line.startsWith("mask =")) {
                    val mask = line.substringAfter(" = ")
                    Write.MaskWrite(mask)
                } else {
                    val (address, value) = Regex("mem\\[(\\d+)\\] = (\\d+)").find(line)!!.destructured
                    Write.MemWrite(address.toLong(), value.toLong())
                }
            }.toList()
        }
        part1 {
            val writing = MemoryWriting()
            for (op in input) {
                when (op) {
                    is Write.MaskWrite -> writing.mask = op
                    is Write.MemWrite -> writing.values[op.address] = writing.mask!!.process(op.value)
                }
            }
            writing.values.values.sum()
        }
        part2 {
            val writing = MemoryWriting()
            for (op in input) {
                when (op) {
                    is Write.MaskWrite -> writing.mask = op
                    is Write.MemWrite -> writing.mask!!.allAddresses(op.address.toLong()).forEach {
                        writing.values[it] = op.value
                    }
                }
            }
            writing.values.values.sum()
        }
    }
}
