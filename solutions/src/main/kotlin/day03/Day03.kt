package day03

import utils.Point
import utils.checkResult
import utils.map.Map2D
import utils.readInput

data class MapCell(val content: Char, var markedBy: MutableSet<Point> = mutableSetOf()) {
    val marked get() = markedBy.isNotEmpty()

    override fun toString(): String {
        val red = "\u001b[31m"
        val reset = "\u001b[0m"

        return if(marked) "$red${content}$reset" else content.toString()
    }
}

fun part1(lines: List<String>): Int {
    val map = Map2D.readFromLines(lines) { c,_,_ -> MapCell(c) }

    val symbols = map.indexedIterator().asSequence().filter { !it.second.content.isDigit() && it.second.content != '.' }

    symbols.forEach { (pt, _) ->
        (-1..1).forEach { x ->
            (-1 .. 1).forEach { y ->
                val target = pt + Point(x,y)
                if(target.x >= 0 && target.y >= 0 && target.x < map.width && target.y < map.height) {
                    map[target].markedBy.add(pt)
                }
            }
        }
    }

//    map.print()

    val numbers = buildList {

        map.rows().forEach { row ->
            var currentNumber: String = ""
            var marked = false

            row.forEach { c ->
                if (c.content.isDigit()) {
                    currentNumber += c.content
                    marked = marked || c.marked
                } else {
                    if (currentNumber.isNotEmpty()) {
                        if (marked) {
                            add(currentNumber)
                        }
                        currentNumber = ""
                        marked = false
                    }
                }
            }

            if (currentNumber.isNotEmpty()) {
                if (marked) {
                    add(currentNumber)
                }
                currentNumber = ""
            }

            marked = false
        }
    }

    return numbers.sumOf { it.toInt() }
}

fun part2(lines: List<String>): Int {
    val map = Map2D.readFromLines(lines) { c,_,_ -> MapCell(c) }

    val symbols = map.indexedIterator().asSequence().filter { it.second.content == '*' }
    symbols.forEach { (pt, _) ->
        (-1..1).forEach { x ->
            (-1 .. 1).forEach { y ->
                val target = pt + Point(x,y)
                if(target.x >= 0 && target.y >= 0 && target.x < map.width && target.y < map.height) {
                    map[target].markedBy.add(pt)
                }
            }
        }
    }

//    map.print()

    val gears: MutableMap<Point,List<Int>> = mutableMapOf()
    map.rows().forEach { row ->
        var currentNumber = ""
        var markedBy: Set<Point> = emptySet()

        row.forEach { c ->
            if (c.content.isDigit()) {
                currentNumber += c.content
                markedBy = markedBy + c.markedBy
            } else {
                if (currentNumber.isNotEmpty()) {
                    markedBy.forEach { gears[it] = (gears[it] ?: emptyList()) + listOf(currentNumber.toInt()) }
                    currentNumber = ""
                    markedBy = emptySet()
                }
            }
        }

        if (currentNumber.isNotEmpty()) {
            if (markedBy.isNotEmpty()) {
                markedBy.forEach { gears[it] = (gears[it] ?: emptyList()) + listOf(currentNumber.toInt()) }
            }
            currentNumber = ""
        }

        markedBy = emptySet()
    }

    return gears.filter { it.value.size == 2 }.mapValues { it.value.reduce { acc, s ->  acc * s} }.values.sum()
}

fun main() {
    val testInput = readInput("day03/test")
    checkResult(part1(testInput), 4361)
    checkResult(part2(testInput), 467835)

    val input = readInput("day03/input")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
