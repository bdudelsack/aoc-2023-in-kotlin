package utils.map

class ColsAccess<T>(private val map: Map2D<T>): Iterable<Col<T>> {
    val size: Int get() = map.width
    operator fun get(x: Int) = Col(map, x)
    override fun iterator(): Iterator<Col<T>> = ColsIterator(map)
    fun listIterator(): ListIterator<Col<T>> = ColsIterator(map)

    operator fun get(range: IntRange): List<Col<T>> {
        return toList().subList(range.first, range.last)
    }
}

class Col<T>(private val map: Map2D<T>, private val x: Int): Iterable<T> {
    val size get() = map.height

    operator fun get(y: Int): T {
        return map[x,y]
    }

    override fun iterator(): Iterator<T> = ColIterator(map, x)

    operator fun get(range: IntRange): List<T> {
        return toList().subList(range.first, range.last)
    }
}

class ColsIterator<T>(private val map: Map2D<T>): IndexedIterator<Col<T>>(map.width - 1) {
    override fun get(i: Int): Col<T> = map.cols[i]
}

class ColIterator<T>(private val map: Map2D<T>, private val x: Int): IndexedIterator<T>(map.height - 1) {
    override fun get(i: Int): T = map[x, i]

}
