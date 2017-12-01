import org.junit.Assert

fun count(numbers: IntArray): Int {
    return numbers.foldIndexed(0, { index, current: Int, value ->
        val other: Int = numbers[(index + 1) % numbers.size]
        val add: Int = if (other == value) value else 0
        current + add
    })
}

fun count(value: String): Int {
    return count(value.toCharArray().map { Character.digit(it, 10) }.toIntArray())
}

class Day1 {}

fun main(args: Array<String>) {

    val text = Day1::class.java.classLoader.getResource("day1").readText().split('\n')
    text.filter { it.contains('=') }.forEach {
        val before = it.substring(0, it.indexOf('='))
        val after = it.substring(it.indexOf('=') + 1).trim()
        Assert.assertEquals("Mismatch for input " + before, after.toInt(), count(before))
    }
    println(count(text[text.size - 1]))

}