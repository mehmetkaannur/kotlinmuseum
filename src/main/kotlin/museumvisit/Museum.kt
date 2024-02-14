package museumvisit

class Museum(val name: String, val entrance: MuseumRoom) {
    var admitted: Int = 0

    val outside = Outside()
    val rooms = mutableSetOf<MuseumRoom>(entrance)
    val turnstiles = mutableListOf<Pair<MuseumRoom, MuseumRoom?>>()

    fun entranceHasCapacity(): Boolean = entrance.hasCapacity()

    fun enter() {
        if (!entranceHasCapacity()) {
            throw UnsupportedOperationException()
        } else {
            admitted++
            entrance.enter()
        }
    }

    override fun toString(): String {
        val sentence = StringBuilder()
        sentence.append(name + "\n")
        rooms.forEach { room ->
            // find where the room leads to
            val leadingTo = mutableListOf<String>()
            turnstiles.forEach {
                if (it.first.name == room.name) {
                    if (it.second == null) {
                        leadingTo.add("Outside")
                    } else {
                        leadingTo.add(it.second!!.name)
                    }
                }
            }
            sentence.append("${room.name} leads to: ${leadingTo.joinToString(", ")}\n")
        }

        return sentence.toString()
    }

    fun addRoom(room: MuseumRoom) {
        rooms.forEach {
            if (it.name == room.name) {
                throw IllegalArgumentException()
            }
        }
        rooms.add(room)
    }

    fun connectRoomTo(room1: MuseumRoom, room2: MuseumRoom?) {
        if (room2 == null) {
            if (rooms.filter { it.name == room1.name }.size != 1) {
                throw IllegalArgumentException()
            } else if (room1 to null in turnstiles) {
                throw IllegalArgumentException()
            } else {
                turnstiles.add(room1 to null)
            }
        } else {
            if (rooms.filter { it.name == room1.name || it.name == room2.name }.size != 2) {
                throw IllegalArgumentException()
            } else if (room1.name == room2.name) {
                throw IllegalArgumentException()
            } else if (room1 to room2 in turnstiles) {
                throw IllegalArgumentException()
            } else {
                turnstiles.add(room1 to room2)
            }
        }
    }

    fun connectRoomToExit(room: MuseumRoom) {
        connectRoomTo(room, null)
    }

    fun checkWellFormed(): Boolean {
        // Check for unreachable rooms
        val reachableRooms = findAllReachableRooms()
        val unreachableRooms = rooms.filterNot { reachableRooms.contains(it) }
        if (unreachableRooms.isNotEmpty()) {
            throw UnreachableRoomsException(unreachableRooms.toSet())
        }

        // Check for rooms from which exit is impossible
        val roomsWithExit = findRoomsWithExit(reachableRooms)
        val roomsWithNoExit = rooms.filterNot { roomsWithExit.contains(it) }
        if (roomsWithNoExit.isNotEmpty()) {
            throw CannotExitMuseumException(roomsWithNoExit.toSet())
        }

        return true
    }

    private fun findAllReachableRooms(): Set<MuseumRoom> {
        val visited = mutableSetOf<MuseumRoom>()
        val stack = mutableListOf<MuseumRoom>(rooms.first())

        while (stack.isNotEmpty()) {
            val current = stack.removeAt(stack.lastIndex)
            if (visited.add(current)) {
                val neighbors = turnstiles.filter { it.first == current }
                    .mapNotNull { it.second }
                stack.addAll(neighbors)
            }
        }

        return visited
    }

    private fun findRoomsWithExit(reachableRooms: Set<MuseumRoom>): List<MuseumRoom> {
        var connectingTo = mutableListOf<MuseumRoom?>(null)
        val roomsVisited = mutableListOf<MuseumRoom>()
        for (i in 0..<rooms.size) {
            val roomsConnectedToThat = connectingTo.map { exitableRoom ->
                reachableRooms.filter { room ->
                    turnstiles.contains(room to exitableRoom)
                }
            }.flatten()

            connectingTo = roomsConnectedToThat.toMutableList()
            roomsVisited.addAll(roomsConnectedToThat)
        }
        return roomsVisited
    }
}
