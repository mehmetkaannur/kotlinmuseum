package museumvisit

open class MuseumRoom(open val name: String, open val capacity: Int) {

    init {
        if (capacity <= 0) {
            throw IllegalArgumentException()
        }
    }

    open var occupancy = 0

    fun hasCapacity(): Boolean = occupancy < capacity

    open fun enter() {
        if (hasCapacity()) {
            occupancy += 1
        } else {
            throw UnsupportedOperationException()
        }
    }

    fun exit() {
        if (capacity > 0) {
            occupancy += 1
        } else {
            throw UnsupportedOperationException()
        }
    }

    override fun toString(): String {
        return name
    }
}

class Outside(exitName: String) : MuseumRoom(exitName, Int.MAX_VALUE) {

    override var occupancy = 0

    override fun enter() {
        occupancy += 1
    }
}
