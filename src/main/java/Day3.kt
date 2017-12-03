import java.util.*

class Day3: Day<Int> {

    override fun part1(input: Int): Any {
        var layer = 1
        var number = 1
        var x = 0
        var y = 0
        var stepsAway = 0
        var quadrant = 0
        while (number < input) {
            number += layer * 2 * 4
            layer++
            x++
            y++
            stepsAway += 2
        }
        while (number > input) {
            number -= (layer - 1)
            quadrant++
            println("Step down to " + number)
        }
        if (quadrant % 2 == 1) {
            stepsAway /= 2
            val diff = Math.abs(number - input)
            println("Special num $number input $input layer $layer x $x y $y quad $quadrant stepsAway $stepsAway diff $diff")
            return stepsAway + diff
        }
        println("Result num $number input $input layer $layer x $x y $y quad $quadrant stepsAway $stepsAway")

        return stepsAway - Math.abs(number - input)
    }

    override fun part2(input: Int): Any {
        return 0
    }

    override fun parse(text: String): Int {
        return text.toInt()
    }


}