package day11

import utils.Point
import utils.checkResult
import utils.map.Map2D
import utils.readInput
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

typealias GalaxyMap = Map2D<Char>

fun GalaxyMap.expandedDistanceFn(expansion: Int = 1): (Point, Point) -> Long {
    val emptyCols = cols().mapIndexed { index, chars ->  Pair(index, chars) }.filter { it.second.all { c -> c == '.' } }.map { it.first }
    val emptyRows = rows().mapIndexed { index, chars ->  Pair(index, chars) }.filter { it.second.all { c -> c == '.' } }.map { it.first }

    return { pt1: Point, pt2: Point ->
        val dx = emptyCols.count { it > min(pt1.x, pt2.x) && it < max(pt1.x, pt2.x) }.toLong()
        val dy = emptyRows.count { it > min(pt1.y, pt2.y) && it < max(pt1.y, pt2.y) }.toLong()

        abs(pt2.x - pt1.x).toLong() + abs(pt2.y - pt1.y).toLong() + (dx + dy) * expansion - dx - dy
    }
}

fun GalaxyMap.calculate(distance: (pt1: Point, p2: Point) -> Long): Long {
    val galaxies = findPoints { it == '#' }.map { it.first }.toList()
    return buildList {
        (0 until galaxies.size - 1).forEach { i ->
            (i + 1 until galaxies.size).forEach { j ->
                add(Pair(galaxies[i], galaxies[j]))
            }
        }
    }.sumOf { distance(it.first, it.second) }
}

fun part(lines: List<String>, expansion: Int = 2): Long {
    val map = GalaxyMap.readFromLines(lines)
    val distanceFn = map.expandedDistanceFn(expansion)
    return map.calculate(distanceFn)
}

fun main() {
    val testInput = readInput("day11/test")
    checkResult(part(testInput), 374)
    checkResult(part(testInput, 10), 1030)
    checkResult(part(testInput, 100), 8410)

    val input = readInput("day11/input")
    println("Part 1: ${part(input)}")
    println("Part 2: ${part(input, 1000000)}")
}
