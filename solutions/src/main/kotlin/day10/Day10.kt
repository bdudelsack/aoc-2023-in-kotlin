package day10

import utils.Point
import utils.checkResult
import utils.map.Map2D
import utils.println
import utils.readInput

typealias PipeMap = Map2D<Char>

val pipes = mapOf(
    '║' to listOf( Point(0, -1), Point(0, 1)),
    '═' to listOf( Point(-1, 0), Point(1, 0)),
    '╚' to listOf( Point(0, -1), Point(1, 0)),
    '╝' to listOf( Point(0, -1), Point(-1, 0)),
    '╗' to listOf( Point(-1, 0), Point(0, 1)),
    '╔' to listOf( Point(1, 0), Point(0, 1)),
)

fun PipeMap.connectedPoints(pt: Point): List<Point> {
    return pipes[get(pt)]?.map { it + pt }?.filter { isValid(it) } ?: emptyList()
}

fun part1(lines: List<String>): Int {
    val charMap = "|-LJ7F".toList().zip("║═╚╝╗╔".toList()).toMap()
    val map = PipeMap.readFromLines(lines) { c,_,_ -> charMap[c] ?: c }
    val start = map.findPoint { it == 'S' }!!

    var steps = 1
    var prev = start
    var current = map.neighbors(prev).first {
        map.connectedPoints(it).contains(prev)
    }

    while(current != start) {
        steps++
        val next = map.connectedPoints(current).first { it != prev }
        prev = current
        current = next
    }

    return steps / 2
}

fun PipeMap.floodFill(pt: Point, char: Char) {
    set(pt, char)
    neighbors(pt).filter { get(it) == '.' }.toSet().forEach { floodFill(it, char) }
}

fun PipeMap.rayCol(pt: Point): Int {
    return cols()[pt.x].drop(pt.y + 1).count {it in "═".toList() }
}

fun PipeMap.rayRow(pt: Point): Int {
    return rows()[pt.y].drop(pt.x + 1).count {it in "║".toList() }
}

fun part2(lines: List<String>): Int {
    val charMap = "|-LJ7F".toList().zip("║═╚╝╗╔".toList()).toMap()
    val map = PipeMap.readFromLines(lines) { c,_,_ -> charMap[c] ?: c }
    val start = map.findPoint { it == 'S' }!!

    var prev = start
    var current = map.neighbors(prev).first {
        map.connectedPoints(it).contains(prev)
    }

    val connections = map.neighbors(start).filter { start in map.connectedPoints(it) }.map { it - start }
    val startChar = pipes.entries.first {
        it.value == connections
    }.key

    val newMap = PipeMap.create(map.width, map.height) { _,_ -> '.' }

    newMap[start] = startChar

    while(current != start) {
        newMap[current] = map[current]
        val next = map.connectedPoints(current).first { it != prev }
        prev = current
        current = next
    }

    for(y in (0 until newMap.height)) {
        var score = 0
        for(x in (0 until newMap.width)) {
            val c = newMap[x,y]
            when(c) {
                '.' -> { newMap[x,y] = if(score % 2 == 0) 'O' else 'I' }
                in listOf('╚','╝','║') -> { score += 1 }
            }
        }
    }

//    newMap.print()

    return newMap.count { it == 'I' }
}

fun main() {
    val testInput01 = readInput("day10/test01")
    checkResult(part1(testInput01), 4)
    val testInput02 = readInput("day10/test02")
    checkResult(part1(testInput02), 8)

    val testInput03 = readInput("day10/test03")
    checkResult(part2(testInput03), 4)
    val testInput04 = readInput("day10/test04")
    checkResult(part2(testInput04), 4)
    val testInput05 = readInput("day10/test05")
    checkResult(part2(testInput05), 8)
    val testInput06 = readInput("day10/test06")
    checkResult(part2(testInput06), 10)

    val input = readInput("day10/input")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
