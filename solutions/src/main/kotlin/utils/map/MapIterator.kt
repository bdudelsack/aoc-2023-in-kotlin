package utils.map

class MapIterator<T>(private val map: Map2D<T>): Iterator<T> {
    private var x: Int = 0
    private var y: Int = 0

    override fun hasNext(): Boolean {
        return x < map.width && y < map.height
    }

    override fun next(): T {
        val c: T = map[x,y]
        if(++x == map.width) {
            x = 0
            y++
        }
        return c
    }
}
