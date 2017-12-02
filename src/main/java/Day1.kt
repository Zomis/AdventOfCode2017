import org.junit.Assert

class Day1: Day<IntArray> {
    override fun parse(text: String): IntArray {
        return text.toCharArray().map { Character.digit(it, 10) }.toIntArray()
    }

    override fun part1(input: IntArray): Any {
        return perform(input, { arr, idx -> arr[(idx + 1) % arr.size] })
    }

    override fun part2(input: IntArray): Any {
        return perform(input, { arr, idx -> arr[(idx + arr.size / 2) % arr.size] })
    }

    fun perform(numbers: IntArray, otherOperation: (IntArray, Int) -> Int): Int {
        return numbers.foldIndexed(0, { index, current: Int, value ->
            val other: Int = otherOperation.invoke(numbers, index)
            val add: Int = if (other == value) value else 0
            current + add
        })
    }

}

fun main(args: Array<String>) {
    val d = Day1()

    val text = d.javaClass.classLoader.getResource("day1").readText().split('\n')
    text.filter { it.contains('=') }.forEach {
        val before = it.substring(0, it.indexOf('='))
        val after = it.substring(it.indexOf('=') + 1).trim()
        Assert.assertEquals("Mismatch for input " + before, after.toInt(), d.part1(d.parse(before)))
    }
    val parsed = d.parse(text[text.size - 1])
    println(d.part1(parsed))
    println(d.part2(parsed))
}
