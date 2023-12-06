package day06

import utils.checkResult
import utils.readInput
import utils.splitNumbers
import utils.to

fun part1(lines: List<String>): Int = lines[0].removePrefix("Time:").splitNumbers<Int>().zip(
    lines[1].removePrefix("Distance:").splitNumbers<Int>()
).map { (tt, d) ->
    (0..tt).map { t -> (tt - t) * t }.count { it > d }
}.reduce { acc, i -> acc * i }

fun part2(lines: List<String>): Int {
    val tt = lines[0].removePrefix("Time: ").trim().replace(" ", "").to<Long>()
    val d = lines[1].removePrefix("Distance: ").trim().replace(" ", "").to<Long>()

    return (0..tt).map { t -> (tt - t) * t }.count { it > d }
}

fun main() {
    val testInput = readInput("day06/test")
    checkResult(part1(testInput), 288)
    checkResult(part2(testInput), 71503)

    val input = readInput("day06/input")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}


