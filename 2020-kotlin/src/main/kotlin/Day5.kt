package net.zomis.aoc.y2020

object Day5 {

    data class BoardingPass(val string: String) {

        fun row(): Int = range(0..127, string.substring(0, 7).asIterable()) { it == 'B' }
        fun column(): Int = range(0..7, string.substring(7).asIterable()) { it == 'R' }
        fun range(initial: IntRange, chars: Iterable<Char>, upperHalf: (Char) -> Boolean): Int {
            var range = initial
            for (ch in chars) {
                range = if (upperHalf(ch)) {
                    range.upperHalf()
                } else {
                    range.lowerHalf()
                }
            }
            return range.single()
        }
        fun seatId(): Int = row() * 8 + column()

        override fun toString(): String = "Row ${row()} Column ${column()}"
    }
    val day = Days.day<Sequence<BoardingPass>>(5) {
        input {
            map { BoardingPass(it) }
        }
        part1 {
            input.map { it.seatId() }.max()!!
        }
        part2 {
            val allSeats = input.map { it.seatId() }.toSet()
            val range = allSeats.min()!!..allSeats.max()!!
            range.find { !allSeats.contains(it) && allSeats.contains(it - 1) && allSeats.contains(it + 1) }!!
        }
    }

}

private fun IntRange.lowerHalf(): IntRange {
    val count = this.count()
    return this.first until (this.first + count / 2)
}

private fun IntRange.upperHalf(): IntRange {
    val count = this.count()
    return (this.first + count/2)..this.last
}
