package day02

import utils.checkResult
import utils.readInput

data class Game(val id: Int, val rounds: List<Triple<Int, Int, Int>>) {
    companion object {
        fun fromLine(line: String): Game {
            val (gameId, rest) = line.split(":")
            val rounds = rest.split(";").map { round ->
                val counts = round.split(",").map(String::trim).map {
                    val (count, color) = it.split(" ")
                    Pair(count.toInt(), color)
                }

                Triple(
                    counts.find { it.second == "red" }?.first ?: 0,
                    counts.find { it.second == "green" }?.first ?: 0,
                    counts.find { it.second == "blue" }?.first ?: 0,
                )
            }

            return Game(gameId.substring(5).toInt(), rounds)
        }
    }
}

fun part1(lines: List<String>): Int {
    return lines.map(Game::fromLine).filterNot {
        it.rounds.any { r -> r.first > 12 || r.second > 13 || r.third > 14}
    }.sumOf { it.id }
}

fun part2(lines: List<String>): Int {
    return lines.map(Game::fromLine).map { game ->
        (game.rounds.maxOf { it.first }) * (game.rounds.maxOf { it.second }) * (game.rounds.maxOf { it.third })
    }.sum()
}

fun main() {
    val testInput = readInput("day02/test")
    checkResult(part1(testInput), 8)
    checkResult(part2(testInput), 2286)

    val input = readInput("day02/input")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
