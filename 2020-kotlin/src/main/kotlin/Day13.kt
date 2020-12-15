package net.zomis.aoc.y2020

object Day13 {

    data class BusTimes(val id: Long, val buses: List<Int?>)
    val day = Days.day<BusTimes>(this) {
        input {
            val list = toList()
            val id = list[0].toLong()
            val buses = list[1].split(",").map { it.toIntOrNull() }
            BusTimes(id, buses)
        }
        part1 {
            val min = input.id
            val max = input.id + input.buses.filterNotNull().max()!!
            val value = (min..max).first {time ->
                input.buses.filterNotNull().any { time % it == 0L }
            }
            val bus = input.buses.filterNotNull().find { value % it == 0L }!!
            val wait = value - input.id
            wait * bus
        }
        part2 {
            val requirements = input.buses.withIndex().filter { it.value != null }.map { it.index to it.value!! }
                .map { it.first % it.second to it.second }.sortedBy { it.second }
            val fixedRequirements = mutableListOf<Pair<Int, Int>>()
            var fullSeq = 1L
            var n = 0L
            for (requirement in requirements) {
                fixedRequirements.add(requirement)
                val nseq = generateSequence(n) { it + fullSeq }
                n = nseq.first {x ->
                    fixedRequirements.all { x % it.second == it.first.toLong() }
                }
                fullSeq *= requirement.second
            }
            fullSeq - n
        }
    }
}
