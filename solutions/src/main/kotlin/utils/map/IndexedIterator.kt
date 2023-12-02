package utils.map

abstract class IndexedIterator<T>(private val max: Int): ListIterator<T> {
    private var i = -1;

    abstract fun get(i: Int): T

    override fun hasNext(): Boolean = i < max
    override fun hasPrevious(): Boolean = i > 0

    override fun next() = get(++i)
    override fun previous() = get(--i)

    override fun nextIndex() = i + 1
    override fun previousIndex() = i - 1
}
