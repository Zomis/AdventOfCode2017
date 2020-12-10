package net.zomis.aoc.y2020

typealias DayPart<T> = Inputs<T>.() -> Any
typealias InputMapping<T> = Sequence<String>.() -> T
interface Inputs<T> {
    val input: T
}
interface Day<T> {
    val day: Int
    fun input(mapping: InputMapping<T>)
    fun part1(function: DayPart<T>)
    fun part2(function: DayPart<T>)

    fun initialize()
    fun mapInput(rawInput: Sequence<String>): T
    fun runPart1(input: T): Any?
    fun runPart2(input: T): Any?
}
data class InputsImpl<T>(override val input: T): Inputs<T>
class DayImpl<T>(override val day: Int, val implementation: Day<T>.() -> Unit): Day<T> {
    private var input: InputMapping<T>? = null
    private var part1: DayPart<T>? = null
    private var part2: DayPart<T>? = null

    override fun input(mapping: InputMapping<T>) {
        this.input = mapping
    }

    override fun part1(function: DayPart<T>) {
        this.part1 = function
    }

    override fun part2(function: DayPart<T>) {
        this.part2 = function
    }

    override fun mapInput(rawInput: Sequence<String>): T {
        return this.input!!.invoke(rawInput)
    }

    override fun runPart1(input: T): Any? {
        if (this.part1 == null) return null
        return this.part1!!.invoke(InputsImpl(input))
    }

    override fun runPart2(input: T): Any? {
        if (this.part2 == null) return null
        return this.part2!!.invoke(InputsImpl(input))
    }

    override fun initialize() {
        this.implementation.invoke(this)
    }

}
object Days {

    fun <T> day(owner: Any, function: Day<T>.() -> Unit): Day<T> {
        val number = owner::class.simpleName!!.substring(3).toInt()
        return DayImpl(number, function)
    }

    fun <T> day(number: Int, function: Day<T>.() -> Unit): Day<T> {
        return DayImpl(number, function)
    }

    fun <T> run(days: Iterable<Day<T>>) {
        // TODO: Coroutines
        for (day in days) {
            day.initialize()
            val rawInputStream = Days::class.java.classLoader.getResourceAsStream("day${day.day}.txt")
            if (rawInputStream == null) {
                println("Missing input for day ${day.day}") // TODO: Try to fetch automatically
                continue
            }
            val rawInput = rawInputStream.bufferedReader().lineSequence().toList() // Use a coroutine producer thingy?

            val mappedInput = day.mapInput(rawInput.asSequence())
            // TODO: Two coroutines that depend on a mapped input coroutine
            val part1result = day.runPart1(mappedInput)
            println(part1result)
            val part2result = day.runPart2(mappedInput)
            println(part2result)
        }
    }

}

fun main() {
    val days = listOf(
        Day10.day
    )
    Days.run(days)
}
