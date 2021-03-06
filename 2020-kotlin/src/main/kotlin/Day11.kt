package net.zomis.aoc.y2020

import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.sin

typealias Day11CountCheck = (previous: Day11.Map2D<Char>, x: Int, y: Int) -> Int
typealias Day11ChangeRules = (current: Boolean, count: Int) -> Boolean
object Day11 {

    class Map2D<T>(val width: Int, val height: Int, val default: (x: Int, y: Int) -> T) {
        private val map: MutableList<MutableList<T>> = (0 until height).map {y ->
            (0 until width).map {x ->
                default(x, y)
            }.toMutableList()
        }.toMutableList()

        val yIndices = map.indices
        val xIndices = map[0].indices

        fun get(x: Int, y: Int): T = map[y][x]
        fun copy(): Map2D<T> = Map2D(width, height) { x, y -> map[y][x] }
        fun print() {
            println()
            for (y in map.indices) {
                println(map[y].joinToString(""))
            }
        }

        fun inRange(x: Int, y: Int): Boolean = x in xIndices && y in yIndices
        fun set(x: Int, y: Int, value: T) {
            map[y][x] = value
        }

        fun all(): Sequence<T> = map.asSequence().flatMap { it.asSequence() }
        fun inRange(pos: Point): Boolean = inRange(pos.x.toInt(), pos.y.toInt())
        fun get(pos: Point): T = get(pos.x.toInt(), pos.y.toInt())
    }
    data class Point(val x: Long, val y: Long) {
        operator fun plus(other: Point): Point = Point(this.x + other.x, this.y + other.y)
        fun repeatUntil(function: (Point) -> Boolean): Point {
            var next = this
            while (!function(next)) {
                next += this
            }
            return next
        }

        fun rotate(degrees: Int): Point {
            return when (degrees) {
                0 -> this
                90, -270 -> Point(-y, x)
                -180, 180 -> Point(-x, -y)
                -90, 270 -> Point(y, -x)
                else -> throw IllegalArgumentException("$degrees")
            }
        }

        operator fun minus(other: Point): Point = Point(this.x - other.x, this.y - other.y)
        operator fun times(value: Int): Point = Point(this.x * value, this.y * value)

        val manhattanDistance = this.x.absoluteValue + this.y.absoluteValue
    }

    object Directions {
        val fourDirections = listOf(Point(1, 0), Point(0, 1), Point(-1, 0), Point(0, -1))
        val eightDirections = listOf(Point(1, 1), Point(-1, 1), Point(1, -1), Point(-1, -1)) + fourDirections
    }

    val day = Days.day<Map2D<Char>>(this) {
        input {
            val list = map {
                it.toList()
            }.toList()
            Map2D(list[0].size, list.size) { x, y -> list[y][x] }
        }
        part1 {
            val countCheck: Day11CountCheck = {previous, x, y ->
                Directions.eightDirections
                    .map { it + Point(x.toLong(), y.toLong()) }
                    .filter { previous.inRange(it) }
                    .count { previous.get(it) == '#' }
            }
            val rules: Day11ChangeRules = {current, count ->
                var nextValue = current
                if (!current) {
                    nextValue = (count == 0)
                }
                if (current && count >= 4) {
                    nextValue = false
                }
                nextValue
            }
            countSeats(input, countCheck, rules)
        }
        part2 {
            val countCheck: Day11CountCheck = {previous, x, y ->
                val pos = Point(x.toLong(), y.toLong())
                val list = Directions.eightDirections
                    .map { direction ->
                        direction.repeatUntil { !previous.inRange(it + pos) || previous.get(it + pos) != '.' }
                    }.map { it + pos }
                list.filter { previous.inRange(it) }.count { previous.get(it) == '#' }
            }
            val rules: Day11ChangeRules = {current, count ->
                var nextValue = current
                if (!current) {
                    nextValue = (count == 0)
                }
                if (current && count >= 5) {
                    nextValue = false
                }
                nextValue
            }
            countSeats(input, countCheck, rules)
        }
    }

    private fun countSeats(input: Map2D<Char>, countCheck: Day11CountCheck, rules: Day11ChangeRules): Int {
        val current = input
        var changed = true
        while (changed) {
            changed = false
            val previous = current.copy()
            for (y in current.yIndices) {
                for (x in current.xIndices) {
                    if (current.get(x, y) == '.') continue
                    val count = countCheck(previous, x, y)
                    val old = current.get(x, y) == '#'
                    val next = rules(old, count)
                    changed = changed || (next != old)
                    current.set(x, y, if (next) '#' else 'L')
                }
            }
        }
        return current.all().count { it == '#' }
    }

}
