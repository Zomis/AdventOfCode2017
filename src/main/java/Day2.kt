import org.junit.Assert
import java.util.regex.Pattern

fun highLowDiff(numbers: IntArray): Int {
    return numbers.max()!! - numbers.min()!!
}

fun process(values: Array<IntArray>): Int {
    return values.fold(0, { sum, next ->  sum + highLowDiff(next) })
}

fun main(args: Array<String>) {
    val test = arrayOf(
        intArrayOf(5, 1, 9, 5),
        intArrayOf(7, 5, 3),
        intArrayOf(2, 4, 6, 8)
    )
    Assert.assertEquals(18, process(test))

    println(process(readText(Res::class.java.classLoader.getResource("day2").readText())))
}

fun readText(data: String): Array<IntArray> {
    return data.split('\n').map { s -> s.trim().split(Pattern.compile("\\s+"))
        .map { it.trim().toInt() }.toIntArray() }.toTypedArray()
}
