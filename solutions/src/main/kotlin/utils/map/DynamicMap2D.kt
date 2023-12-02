package utils.map

import utils.Point
import kotlin.math.max
import kotlin.math.min

class DynamicMap2D<T>: HashMap<Point, T>() {

    var minX = Int.MAX_VALUE
    var minY = Int.MAX_VALUE
    var maxX = Int.MIN_VALUE
    var maxY = Int.MIN_VALUE

    val width get() = maxX - minX
    val height get() = maxY - minY

    operator fun get(x: Int, y: Int) = this[Point(x,y)]
    operator fun set(x: Int, y: Int, value: T) = value.also { this[Point(x,y)] = it }
    operator fun set(pt: Point, value: T) = value.also {
        minX = min(pt.x, minX)
        minY = min(pt.y, minY)
        maxX = max(pt.x, maxX)
        maxY = max(pt.y, maxY)
        super.put(pt, value)
    }

    fun display() {
        for(y in minY .. maxY) {
            for(x in minX .. maxX) {
                print(this[x,y] ?: '.')
            }
            println()
        }
    }
}
