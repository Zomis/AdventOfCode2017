import org.junit.Assert
import java.util.regex.Pattern

class Day2: Day<Array<IntArray>> {
    override fun part1(input: Array<IntArray>): Any {
        return perform(input, { highLowDiff(it) })
    }

    override fun part2(input: Array<IntArray>): Any {
        return perform(input, { divisible(it) })
    }

    private fun divisible(numbers: IntArray): Int {
        numbers.forEach { a ->
            numbers.forEach { b ->
                if (a != b && a % b == 0) {
                    return a / b
                }
            }
        }
        return 0
    }

    override fun parse(text: String): Array<IntArray> {
        return text.split('\n').map { s -> s.trim().split(Pattern.compile("\\s+"))
                .map { it.trim().toInt() }.toIntArray() }.toTypedArray()
    }

    fun highLowDiff(numbers: IntArray): Int {
        return numbers.max()!! - numbers.min()!!
    }

    fun perform(values: Array<IntArray>, operation: (IntArray) -> Int): Int {
        return values.fold(0, { sum, next -> sum + operation.invoke(next) })
    }

}

fun main(args: Array<String>) {
    val test = arrayOf(
        intArrayOf(5, 1, 9, 5),
        intArrayOf(7, 5, 3),
        intArrayOf(2, 4, 6, 8)
    )
    val d = Day2()
    Assert.assertEquals(18, d.part1(test))

    val text = Day::class.java.classLoader.getResource("day2").readText()
    val parsed = d.parse(text)

    val test2 = arrayOf(
        intArrayOf(5, 9, 2, 8),
        intArrayOf(9, 4, 7, 3),
        intArrayOf(3, 8, 6, 5)
    )
    Assert.assertEquals(9, d.part2(test2))

    println(d.part1(parsed))
    println(d.part2(parsed))
}
