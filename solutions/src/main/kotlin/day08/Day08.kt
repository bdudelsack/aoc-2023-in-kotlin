package day08

import utils.checkResult
import utils.println
import utils.readInput

data class Node(
    val key: String,
    val left: String? = null,
    val right: String? = null,
) {
    val isStart: Boolean = key.last() == 'A'
    val isEnd: Boolean = key.last() == 'Z'
}


fun part1(lines: List<String>): Int {
    val nodeMap: MutableMap<String, Node> = mutableMapOf()
    val directions = lines.first().toList()

    lines.drop(2).forEach { line ->
        val (key, children) = line.split(" = ")
        val (l, r) = children.trim('(',')').split(", ")

        nodeMap[key] = Node(key, l, r)
    }

    var currentNode = "AAA"
    var steps = 0
    while(currentNode != "ZZZ") {
        directions.forEach { d ->
            when(d) {
                'L' -> { currentNode = nodeMap[currentNode]!!.left!! }
                'R' -> { currentNode = nodeMap[currentNode]!!.right!! }
            }

            steps++
        }
    }

    return steps
}

fun part2(lines: List<String>): Long {
    val nodeMap: MutableMap<String, Node> = mutableMapOf()
    val directions = lines.first().toList()

    lines.drop(2).forEach { line ->
        val (key, children) = line.split(" = ")
        val (l, r) = children.trim('(',')').split(", ")

        nodeMap[key] = Node(key, l, r)
    }

    val startNodes = nodeMap.values.filter {  it.isStart }
    val res = startNodes.map { startNode ->
        var steps = 0
        var currentNode = startNode
        while(!currentNode.isEnd) {
            directions.forEach { d ->
                when(d) {
                    'L' -> { currentNode = nodeMap[currentNode.left]!! }
                    'R' -> { currentNode = nodeMap[currentNode.right]!! }
                }
                steps++
            }
        }

        Pair(startNode.key, steps)
    }

    return lcm(res.toMap().values.map { it.toLong() }.toLongArray())

}

// https://stackoverflow.com/questions/4201860/how-to-find-gcd-lcm-on-a-set-of-numbers

private fun gcd(a: Long, b: Long): Long {
    var a = a
    var b = b
    while (b > 0) {
        val temp = b
        b = a % b // % is remainder
        a = temp
    }
    return a
}

private fun lcm(a: Long, b: Long): Long {
    return a * (b / gcd(a, b))
}

private fun lcm(input: LongArray): Long {
    var result = input[0]
    for (i in 1 until input.size) result = lcm(result, input[i])
    return result
}

fun main() {
    val testInput00 = readInput("day08/test00")
    checkResult(part1(testInput00), 2)

    val testInput01 = readInput("day08/test01")
    checkResult(part1(testInput01), 6)

    val testInput02 = readInput("day08/test02")
    checkResult(part2(testInput02), 6)

    val input = readInput("day08/input")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
