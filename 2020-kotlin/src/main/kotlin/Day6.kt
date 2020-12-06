package net.zomis.aoc.y2020

object Day6 {

    data class GroupAnswers(val answers: List<String>)
    val day = Days.day<List<GroupAnswers>>(6) {
        input {
            this.joinToString("\n").split("\n\n").map {
                GroupAnswers(it.split("\n"))
            }
        }
        part1 {
            input.map { it.answers.flatMap { s -> s.asIterable() }.distinct().count() }.sum()
        }
        part2 {
            input.map { it.answers.map { s -> s.toSet() }.reduce { acc, set -> acc.intersect(set) }.count() }.sum()
        }
    }

}
