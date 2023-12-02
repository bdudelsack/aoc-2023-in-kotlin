package day01

import utils.checkResult
import utils.readInput

fun part1(lines: List<String>): Int {
    return lines.map { line ->
        line.first { it.isDigit() } + "" + line.last { it.isDigit() }
    }.sumOf {
        it.toInt()
    }
}

fun part2(lines: List<String>): Int {
    val numbers = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

    fun forwardSearch(str: String): Int {
        val regex = "([0-9]|${numbers.joinToString("|")})".toRegex()
        val match = regex.find(str)
        val number = match?.groupValues?.get(1) ?: throw Error("Not match found: $str")

        return if(number.length == 1) number.toInt() else numbers.indexOf(number) + 1
    }

    fun backwardSearch(str: String): Int {
        val regex = "([0-9]|${numbers.joinToString("|") { it.reversed() }})".toRegex()
        val match = regex.find(str.reversed())
        val number = match?.groupValues?.get(1) ?: throw Error("Not match found: $str")

        return if(number.length == 1) number.toInt() else numbers.indexOf(number.reversed()) + 1
    }

    return lines.sumOf {
        (forwardSearch(it).toString() + backwardSearch(it).toString()).toInt()
    }
}

fun main() {
    val testInput00 = readInput("day01/test00")
    checkResult(part1(testInput00), 142)

    val testInput01 = readInput("day01/test01")
    checkResult(part2(testInput01), 281)

    val input = readInput("day01/input")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
