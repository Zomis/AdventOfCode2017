import org.junit.Assert
import java.util.regex.Pattern

class Day2 {

    fun highLowDiff(numbers: IntArray): Int {
        return numbers.max()!! - numbers.min()!!
    }

    fun process(values: Array<IntArray>): Int {
        return values.fold(0, { sum, next ->  sum + highLowDiff(next) })
    }

    fun readText(data: String): Array<IntArray> {
        return data.split('\n').map { s -> s.trim().split(Pattern.compile("\\s+"))
                .map { it.trim().toInt() }.toIntArray() }.toTypedArray()
    }
}

fun main(args: Array<String>) {
    val test = arrayOf(
            intArrayOf(5, 1, 9, 5),
            intArrayOf(7, 5, 3),
            intArrayOf(2, 4, 6, 8)
    )
    val d2 = Day2()
    Assert.assertEquals(18, d2.process(test))

    println(d2.process(d2.readText(Res::class.java.classLoader.getResource("day2").readText())))
}
