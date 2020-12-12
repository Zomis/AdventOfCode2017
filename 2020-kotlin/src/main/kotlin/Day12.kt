package net.zomis.aoc.y2020

import kotlin.math.absoluteValue

object Day12 {

    data class Ship(val x: Long, val y: Long, val direction: Day11.Point) {
        fun move(next: ShipInstruction): Ship {
            return when (next.instruction) {
                'N' -> Ship(x, y - next.value, direction)
                'S' -> Ship(x, y + next.value, direction)
                'W' -> Ship(x - next.value, y, direction)
                'E' -> Ship(x + next.value, y, direction)
                'L' -> Ship(x, y, direction.rotate(-next.value.toInt()))
                'R' -> Ship(x, y, direction.rotate(next.value.toInt()))
                'F' -> Ship(x + direction.x * next.value, y + direction.y * next.value, direction)
                else -> throw IllegalArgumentException("$next")
            }
        }
    }
    data class ShipInstruction(val instruction: Char, val value: Long)
    val day = Days.day<List<ShipInstruction>>(this) {
        input {
            map { ShipInstruction(it[0], it.substring(1).toLong()) }.toList()
        }
        part1 {
            val ship = input.fold(Ship(0, 0, Day11.Point(1, 0))) { ship, next ->
                ship.move(next)
            }
            ship.x.absoluteValue + ship.y.absoluteValue
        }
        part2 {
            fun move(next: ShipInstruction, ship: Day11.Point, waypoint: Day11.Point): Pair<Day11.Point, Day11.Point> {
                if (next.instruction in listOf('F', 'R', 'L')) {
                    println(ship to waypoint)
                    println("Move $next")
                }
                val pos = when (next.instruction) {
                    'N' -> ship to waypoint.plus(Day11.Point(0, -next.value))
                    'S' -> ship to waypoint.plus(Day11.Point(0, next.value))
                    'W' -> ship to waypoint.plus(Day11.Point(-next.value, 0))
                    'E' -> ship to waypoint.plus(Day11.Point(next.value, 0))
                    'L' -> ship to waypoint.rotate(-next.value.toInt())
                    'R' -> ship to waypoint.rotate(next.value.toInt())
                    'F' -> ship.plus(waypoint * next.value.toInt()) to waypoint
                    else -> throw IllegalArgumentException("$next")
                }
                println(pos)
                println()
                return pos
            }
            val (ship, waypoint) = input.fold(Day11.Point(0, 0) to Day11.Point(10, -1)) { acc, next ->
                move(next, acc.first, acc.second)
            }
            println(ship to waypoint)
            ship.manhattanDistance
            // 38041 is too low
        }
    }
}
