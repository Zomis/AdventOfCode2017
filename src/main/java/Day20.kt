class Day20: Day<List<Day20.PosVecAcc>> {

    override fun parse(text: String): List<PosVecAcc> {
        return text.lines().map { it.split(", ") }.map { PosVecAcc(xyz(it[0]), xyz(it[1]), xyz(it[2])) }
    }

    private fun xyz(text: String): XYZ {
        val pos = text.substringAfter("=<")
            .substringBefore(">")
            .split(",")
            .map { it.toInt() }
        return XYZ(pos[0], pos[1], pos[2])
    }

    override fun part1(input: List<PosVecAcc>): Any {
        val min = input.minBy { it.acc.manhattanDistance() }!!
        val matches = input.filter { it.acc.manhattanDistance() == min.acc.manhattanDistance() }
        val minVelocity = matches.minBy { it.velocity.manhattanDistance() }!!
        return input.indexOf(minVelocity)
    }

    override fun part2(input: List<PosVecAcc>): Any {
        return 0
    }

    data class XYZ(val x: Int, val y: Int, val z: Int) {
        fun manhattanDistance(): Int {
            return Math.abs(x) + Math.abs(y) + Math.abs(z)
        }
        fun length(): Double {
            return Math.sqrt((x*x + y*y + z*z).toDouble())
        }
    }
    data class PosVecAcc(val pos: XYZ, val velocity: XYZ, val acc: XYZ)



}