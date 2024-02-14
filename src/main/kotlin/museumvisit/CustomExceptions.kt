package museumvisit

fun turnRoomsToString(rooms: Set<MuseumRoom>): String {
    return rooms.sortedBy { it.name.first() }.map { it.name }.joinToString(separator = ", ")
}

class UnreachableRoomsException(val rooms: Set<MuseumRoom>): Exception() {
    override fun toString(): String {
        return "Unreachable rooms: ${turnRoomsToString(rooms)}"
    }
}

class CannotExitMuseumException(val rooms: Set<MuseumRoom>): Exception() {
    override fun toString(): String {
        return "Impossible to leave museum from: ${turnRoomsToString(rooms)}"
    }
}

