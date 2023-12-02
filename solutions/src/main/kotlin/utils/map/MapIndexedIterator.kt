package utils.map

import utils.Point

class MapIndexedIterator<T>(private val map: Map2D<T>): Iterator<Pair<Point,T>> {
    private var x: Int = 0
    private var y: Int = 0

    override fun hasNext(): Boolean {
        return x < map.width && y < map.height
    }

    override fun next(): Pair<Point,T> {
        val c = Point(x,y) to map[x,y]
        if(++x == map.width) {
            x = 0
            y++
        }
        return c
    }
}
