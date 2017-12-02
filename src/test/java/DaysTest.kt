import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DaysTest {

    @Test
    fun day1() {
        val d = Day1()
        val solve = { it: String -> d.part1(d.parse(it)) }
        assertEquals(3, solve.invoke("1122"))
        assertEquals(4, solve.invoke("1111"))
        assertEquals(0, solve.invoke("1234"))
        assertEquals(9, solve.invoke("91212129"))
    }

    @Test
    fun day2() {
        val d = Day2()
        val test = arrayOf(
                intArrayOf(5, 1, 9, 5),
                intArrayOf(7, 5, 3),
                intArrayOf(2, 4, 6, 8)
        )
        assertEquals(18, d.part1(test))

        val test2 = arrayOf(
                intArrayOf(5, 9, 2, 8),
                intArrayOf(9, 4, 7, 3),
                intArrayOf(3, 8, 6, 5)
        )
        assertEquals(9, d.part2(test2))
    }

}