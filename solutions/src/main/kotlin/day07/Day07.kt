package day07

import utils.checkResult
import utils.readInput

enum class Combination(val rank: Int) {
    FIVE_OF_A_KIND(7),
    FOUR_OF_A_KIND(6),
    FULL_HOUSE(5),
    THREE_OF_A_KIND(4),
    TWO_PAIR(3),
    ONE_PAIR(2),
    HIGH_CARD(1)
}

data class Hand(val input: String, val joker: Char? = null) : Comparable<Hand> {
    private val hand: Map<Char, Int> = if(joker == null) {
        input.toList().sorted().groupingBy { it }.eachCount()
    } else {
        input.replace('J', joker).toList().sorted().groupingBy { it }.eachCount()
    }
    private val cards = if(joker == null) "AKQJT98765432" else "AKQT98765432J"

    private val combination by lazy {
        if(hand.values.max() == 5) {
            Combination.FIVE_OF_A_KIND
        } else if(hand.values.max() == 4) {
            Combination.FOUR_OF_A_KIND
        } else if(hand.values.contains(3) && hand.values.contains(2)) {
            Combination.FULL_HOUSE
        } else if(hand.values.max() == 3) {
            Combination.THREE_OF_A_KIND
        } else if(hand.values.count { it == 2 } == 2) {
            Combination.TWO_PAIR
        } else if(hand.values.max() == 2) {
            Combination.ONE_PAIR
        } else {
            Combination.HIGH_CARD
        }
    }

    val cardRanks by lazy { input.toList().map { cards.length - cards.indexOf(it) } }

    fun mutateJoker(): Hand {
        val filteredCards = hand.filter { it.key != 'J' }
        if(filteredCards.isEmpty()) {
            // ALL JOKERS
            return Hand(input, 'A')
        }

        val max = filteredCards.maxOf { it.value }

        val candidates = filteredCards.filter { it.value == max }.map { it.key }.sortedBy { cards.indexOf(it) }

        return Hand(input, candidates.first())
    }

    override fun compareTo(other: Hand): Int {
        if(combination.rank < other.combination.rank) {
            return -1
        } else if(combination.rank > other.combination.rank) {
            return 1
        }

        return cardRanks.compareTo(other.cardRanks)
    }

    override fun toString(): String {
        return "$input - $combination"
    }
}

fun List<Int>.compareTo(other: List<Int>): Int {
    if(size != other.size) throw IllegalStateException("Lists have different lengths")
    indices.forEach { n ->
        if(get(n) > other.get(n)) {
            return 1
        } else if(get(n) < other.get(n)) {
            return -1
        }
    }

    return 0
}

fun part1(lines: List<String>): Int {
    val res = lines.map { line ->
        val (cards, bid) = line.split(" ")
        Pair(Hand(cards),bid.toInt())
    }.sortedBy { it.first }.mapIndexed { index, (hand, bid) ->
        Pair(hand, bid * (index + 1))
    }.sumOf { it.second }


    return res
}

fun part2(lines: List<String>): Int {
    val res = lines.map { line ->
        val (cards, bid) = line.split(" ")
        Pair(Hand(cards).mutateJoker(),bid.toInt())
    }.sortedBy { it.first }.mapIndexed { index, (hand, bid) ->
        Pair(hand, bid * (index + 1))
    }.sumOf { it.second }

    return res
}

fun main() {
    val testInput = readInput("day07/test")
    checkResult(part1(testInput), 6440)
    checkResult(part2(testInput), 5905)

    val input = readInput("day07/input")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
