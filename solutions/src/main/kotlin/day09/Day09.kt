package day09

import utils.checkResult
import utils.println
import utils.readInput
import utils.splitNumbers

fun difference(sequence: List<Long>) = sequence.windowed(2).map { (a, b) -> b - a }

fun part1(lines: List<String>) = lines.sumOf { line ->
    val numbers = line.splitNumbers<Long>()
    val steps = mutableListOf(numbers)

    while (steps.last().any { it != 0L }) {
        steps.add(difference(steps.last()))
    }

    steps.sumOf { it.last() }
}

fun part2(lines: List<String>): Long = lines.sumOf { line ->
    val numbers = line.splitNumbers<Long>()
    val steps = mutableListOf(numbers)

    while (steps.last().any { it != 0L }) {
        steps.add(difference(steps.last()))
    }

    var t = steps.dropLast(1).map { it.first() }
    while (t.size > 1) {
        t = t.subList(0, t.size - 2) + listOf(t[t.size - 2] - t[t.size - 1])
    }

    t.first()
}

fun main() {
    val testInput = readInput("day09/test")
    checkResult(part1(testInput), 114)
    checkResult(part2(testInput), 2)

    val input = readInput("day09/input")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
