package museumvisit

open class Museum(open val name: String, val entrance: MuseumRoom) {
    var admitted = 0
        private set

    var rooms: MutableList<MuseumRoom> = mutableListOf(entrance)

    var turnstiles: MutableList<Pair<MuseumRoom, MuseumRoom?>> = mutableListOf()

    inner class Outside(name: String) : MuseumRoom(name, Int.MAX_VALUE) {

        override var occupancy = 0

        override fun enter() {
            occupancy += 1
        }
    }

    fun entranceHasCapacity() = this.entrance.hasCapacity()

    fun enter() {
        if (entranceHasCapacity()) {
            admitted += 1
            this.entrance.enter()
        } else {
            throw UnsupportedOperationException()
        }
    }

    fun addRoom(room: MuseumRoom) {
        if (room in rooms) {
            throw IllegalArgumentException()
        } else {
            rooms.add(room)
        }
    }

    fun connectRoomTo(fromRoom: MuseumRoom, exit: Outside) {
        if (fromRoom !in rooms || Pair(fromRoom, exit) in turnstiles) {
            throw UnsupportedOperationException()
        } else {
            turnstiles.add(Pair(fromRoom, exit))
        }
    }

    fun connectRoomToExit(fromRoom: MuseumRoom, toRoom: MuseumRoom) {
        if (fromRoom !in rooms || toRoom !in rooms ||
            fromRoom == toRoom || Pair(fromRoom, toRoom) in turnstiles
        ) {
            throw UnsupportedOperationException()
        } else {
            turnstiles.add(Pair(fromRoom, toRoom))
        }
    }
}
