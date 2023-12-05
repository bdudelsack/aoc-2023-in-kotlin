package day05

import utils.checkResult
import utils.readInput
import kotlin.system.exitProcess

fun part1(lines: List<String>): Long {
    val seeds = lines.first().removePrefix("seeds: ").splitNumbers()
    val maps = lines.drop(2).split { it.isEmpty() }.map {
        it.drop(1).map {
            val (d, s, c) = it.trim().splitNumbers()
            Pair((s..s + c), d - s)
        }
    }

    return seeds.minOf { seed ->
        var current = seed
        maps.forEach { map -> map.firstOrNull { it.first.contains(current) }?.also { t -> current += t.second } }
        current
    }
}


fun part2(lines: List<String>): Long {
    val seedsRanges = lines.first().removePrefix("seeds: ").splitNumbers().chunked(2).map {
        (it[0] until it[0] + it[1])
    }

    val maps = lines.drop(2).split { it.isEmpty() }.map {
        it.drop(1).map {
            val (d, s, c) = it.trim().splitNumbers()
            Pair((s..s + c), d - s)
        }
    }

    var currentRanges = seedsRanges
    maps.forEach { map ->
        currentRanges = currentRanges.flatMap { currentRange ->
            buildList {
                var notMapped = listOf(currentRange)
                map.forEach { (mapRange,_) ->
                    notMapped = buildList {
                        notMapped.forEach { range ->
                            addAll(range.subtract(mapRange))
                        }
                    }
                }

                addAll(notMapped)

                map.forEach { (mapRange, offset) ->
                    currentRange.intersect(mapRange)?.also {
                        add(it.first + offset .. it.last + offset)
                    }
                }
            }
        }
    }

    return currentRanges.minOf { it.first }
}

fun main() {
    val testInput = readInput("day05/test")
    checkResult(part1(testInput), 35)
    checkResult(part2(testInput), 46)

    val input = readInput("day05/input")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun<T> List<T>.split(predicate: (T) -> Boolean): List<List<T>> {
    return buildList {
        var list = this@split

        while(list.isNotEmpty()) {
            val slice = list.takeWhile { !predicate(it) }
            add(slice)
            list = list.drop(slice.size + 1)
        }
    }
}

private fun String.splitNumbers() = this.trim().split(" +".toRegex()).map { it.toLong() }

private fun Set<Long>.ranges(): List<LongRange> {
    var current = this.toSortedSet()
    return buildList<LongRange> {

        while(current.isNotEmpty()) {
            if(current.size == 1) {
                add((current.first() .. current.first()))
                break
            }

            val windowed = current.windowed(2)
            val chunk = windowed.takeWhile { (a, b) -> b - a == 1L }

            add(chunk.first().first()..chunk.last().last())
            current = current.drop(chunk.size + 1).toSortedSet()
        }
    }
}

private fun LongRange.subtract(range: LongRange): List<LongRange> {
    val maxFirst = Math.max(start, range.first)
    val minLast = Math.min(last, range.last)

    if(minLast < maxFirst) {
        return listOf(this)
    }

    val cutter = (maxFirst .. minLast)

    val res = buildList {
        if(cutter.first > first) {
            add(first until cutter.first,)
        }

        if(cutter.last  < last) {
            add(cutter.last + 1  .. last)
        }
    }


    return res
}

private fun LongRange.intersect(range: LongRange): LongRange? {
    val maxFirst = Math.max(start, range.first)
    val minLast = Math.min(last, range.last)

    if(minLast < maxFirst) {
        return null
    }

    val res = (maxFirst .. minLast)

    return res
}