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
}

class UnreachableRoomException(val unreachable: Set<MuseumRoom>) : Exception()

class CannotExitMuseumException(val unexitable: Set<MuseumRoom>) : Exception()
