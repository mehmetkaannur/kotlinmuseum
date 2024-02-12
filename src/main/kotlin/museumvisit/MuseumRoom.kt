package museumvisit

open class MuseumRoom(open val name: String, open val capacity: Int) {

    init {
        if (capacity <= 0) {
            throw IllegalArgumentException()
        }
    }

    var occupancy = 0
        private set

    fun hasCapacity(): Boolean = occupancy < capacity

    fun enter() {
        if (hasCapacity() == true) {
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
