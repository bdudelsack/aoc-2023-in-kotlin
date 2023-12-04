package day04

import utils.checkResult
import utils.readInput
import kotlin.math.pow

data class Card(val id: Int, val winningNumbers: Set<Int>, val numbers: Set<Int>, var multiplier: Int = 1) {
    val count by lazy { numbers.intersect(winningNumbers).count() }
    val score by lazy { 2.0.pow(count - 1).toInt() }

    override fun toString() = "Card $id: $winningNumbers | $numbers x$multiplier ($count)"

    companion object {
        fun fromLine(line: String): Card {
            val (id, rest) = line.split(":")
            val (winning, numbers) = rest.split("|")

            return Card(
                id.removePrefix("Card ").trim().toInt(),
                winning.splitNumbers(),
                numbers.splitNumbers()
            )
        }

        private fun String.splitNumbers() = this.trim().split(" +".toRegex()).map { it.toInt() }.toSet()
    }
}

fun List<Card>.applyMultipliers() = also {
    forEachIndexed { index, card ->
        (1..card.count).forEach { n -> get(index + n).multiplier += card.multiplier }
    }
}

fun part1(lines: List<String>) = lines.sumOf { Card.fromLine(it).score }
fun part2(lines: List<String>) = lines.map { Card.fromLine(it) }.applyMultipliers().sumOf { it.multiplier }

fun main() {
    val testInput = readInput("day04/test")
    checkResult(part1(testInput), 13)
    checkResult(part2(testInput), 30)

    val input = readInput("day04/input")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
