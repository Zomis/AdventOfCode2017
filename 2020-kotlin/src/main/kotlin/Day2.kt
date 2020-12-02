package net.zomis.aoc.y2020

object Day2 {

    data class Input(val min: Int, val max: Int, val char: Char, val string: String) {
        fun valid(): Boolean {
            val actual = string.count { it == char }
            return actual in min..max
        }
        fun valid2(): Boolean {
            val ch1 = string[min - 1]
            val ch2 = string[max - 1]
            return (ch1 == char).xor(ch2 == char)
        }
    }
    val day = Days.day<Sequence<Input>>(2) {
        input {
            map {
                val (min, max, char, string) = Regex("(\\d+)-(\\d+) (\\w+): (\\w+)").find(it)!!.destructured
                Input(min.toInt(), max.toInt(), char[0], string)
            }
        }
        part1 {
            input.count { it.valid() }
        }
        part2 { input.count { it.valid2() } }
//        test("""""") {}
    }

}
