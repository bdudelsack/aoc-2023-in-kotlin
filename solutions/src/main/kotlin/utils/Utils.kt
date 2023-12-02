package utils

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.math.abs

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("solutions/src/main/kotlin", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)


fun<T> checkResult(result: T, expected: T) = check(result == expected) { "Check failed. Expected '$expected' got '$result'." }

data class Point(val x: Int, val y: Int) {
    companion object {
        fun fromString(input: String): Point {
            val (x, y) = input.trim().split(",").map { it.toInt() }
            return Point(x, y)
        }
    }

    operator fun plus(pt: Point): Point {
        return Point(x + pt.x , y + pt.y)
    }

    operator fun minus(pt: Point): Point {
        return Point(x - pt.x, y - pt.y)
    }

    fun dist(pt: Point): Point {
        return Point(abs(x - pt.x), abs(y - pt.y))
    }
}

data class Line(val start: Point, val end: Point) {
    companion object {
        fun fromString(input: String): Line {
            val (start, end) = input.trim().split("->").map { Point.fromString(it) }
            return Line(start, end)
        }
    }
}
