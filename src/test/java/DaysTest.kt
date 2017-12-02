import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class DaysTest {

    @TestFactory
    fun tests(): List<DynamicTest> {
        return arrayOf(
            testPartOne(Day1(), "1122", 3),
            testPartOne(Day1(), "1111", 4),
            testPartOne(Day1(), "1234", 0),
            testPartOne(Day1(), "91212129", 9),

            testPartOneOnly(Day2(), arrayOf(
                    intArrayOf(5, 1, 9, 5),
                    intArrayOf(7, 5, 3),
                    intArrayOf(2, 4, 6, 8)
            ), 18),
            testPartTwoOnly(Day2(), arrayOf(
                    intArrayOf(5, 9, 2, 8),
                    intArrayOf(9, 4, 7, 3),
                    intArrayOf(3, 8, 6, 5)
            ), 9),
            DynamicTest.dynamicTest("End", {})
        ).toList()
    }

    private fun <T> testPartOneOnly(day: Day<T>, input: T, result: Any): DynamicTest {
        val dayName = day.javaClass.name
        return DynamicTest.dynamicTest("Test $dayName with input $input", {
            assertEquals(result, day.part1(input), "Mismatch for $dayName with input $input")
        })
    }

    private fun <T> testPartTwoOnly(day: Day<T>, input: T, result: Any): DynamicTest {
        val dayName = day.javaClass.name
        return DynamicTest.dynamicTest("Test $dayName with input $input", {
            assertEquals(result, day.part2(input), "Mismatch for $dayName with input $input")
        })
    }

    private fun <T> testPartOne(day: Day<T>, input: String, result: Any): DynamicTest {
        val dayName = day.javaClass.name
        return DynamicTest.dynamicTest("Test $dayName with input $input", {
            assertEquals(result, day.part1(day.parse(input)), "Mismatch for $dayName with input $input")
        })
    }

}