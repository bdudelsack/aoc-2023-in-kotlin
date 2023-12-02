package utils.map

class RowsAccess<T>(private val map: Map2D<T>): Iterable<Row<T>> {
    val size: Int get() = map.height
    operator fun get(y: Int) = Row(map, y)
    override fun iterator(): Iterator<Row<T>> = RowsIterator(map)
    fun listIterator(): ListIterator<Row<T>> = RowsIterator(map)

    operator fun get(range: IntRange): List<Row<T>> {
        return toList().subList(range.first, range.last)
    }
}

class Row<T>(private val map: Map2D<T>, private val y: Int): Iterable<T> {
    val size get() = map.width

    operator fun get(x: Int): T {
        return map[x,y]
    }

    override fun iterator(): Iterator<T> = RowIterator(map, y)

    operator fun get(range: IntRange): List<T> {
        return toList().subList(range.first, range.last)
    }
}

class RowsIterator<T>(private val map: Map2D<T>): IndexedIterator<Row<T>>(map.height - 1) {
    override fun get(i: Int): Row<T> = map.rows[i]
}

class RowIterator<T>(private val map: Map2D<T>, private val y: Int) : IndexedIterator<T>(map.width -1) {
    override fun get(i: Int): T = map[i, y]
}

