package museumvisit

open class Museum(open val name: String, val entrance: MuseumRoom) {
    var admitted = 0
        private set

    var rooms: MutableList<MuseumRoom> = mutableListOf(entrance)

    var turnstiles: MutableList<Pair<MuseumRoom, MuseumRoom>> = mutableListOf()

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

    fun connectRoomToExit(
        fromRoom: MuseumRoom,
        exit: Outside = Outside("Outside"),
    ) {
        if (fromRoom !in rooms || Pair(fromRoom, exit) in turnstiles) {
            throw UnsupportedOperationException()
        } else {
            turnstiles.add(Pair(fromRoom, exit))
        }
    }

    fun connectRoomTo(fromRoom: MuseumRoom, toRoom: MuseumRoom) {
        if (fromRoom !in rooms || toRoom !in rooms ||
            fromRoom == toRoom || Pair(fromRoom, toRoom) in turnstiles
        ) {
            throw UnsupportedOperationException()
        } else {
            turnstiles.add(Pair(fromRoom, toRoom))
        }
    }

//    open fun checkWellFormed() {
//        var reachableList: MutableSet<MuseumRoom> = mutableSetOf()
//        val newReachableList: MutableSet<MuseumRoom> = mutableSetOf(rooms[0])
//        var exitableList: MutableSet<MuseumRoom> = mutableSetOf()
//        val newExitableList: MutableSet<MuseumRoom> = mutableSetOf(rooms[0])
//
//        while (reachableList.size != newReachableList.size) {
//            reachableList = newReachableList
//            for (room in reachableList) {
//                for (turnstile in turnstiles) {
//                    if (room == turnstile.first) {
//                        newReachableList.add(turnstile.second)
//                    }
//                }
//            }
//        }
//        for (turnstile in turnstiles) {
//            if (turnstile.second is Outside) {
//                newExitableList.add(turnstile.first)
//            }
//        }
//
//        while (exitableList.size != newExitableList.size) {
//            exitableList = newExitableList
//            for (room in exitableList) {
//                for (turnstile in turnstiles) {
//                    if (room == turnstile.second) {
//                        newExitableList.add(turnstile.first)
//                    }
//                }
//            }
//        }
//
//        val unreachableList: Set<MuseumRoom> = rooms.toSet() - reachableList.toSet()
//        val unexitableList: Set<MuseumRoom> = rooms.toSet() - exitableList.toSet()
//
//        if (unreachableList.size != 0) {
//            throw UnreachableRoomsException(unreachableList.toSet())
//        } else if (unexitableList.size != 0) {
//            throw CannotExitMuseumException(unexitableList.toSet())
//        } else {
//            return
//        }
//    }

    open fun checkWellFormed() {
        val reachableList: MutableList<MuseumRoom> = mutableListOf(rooms[0])
        val exitableList: MutableList<MuseumRoom> = mutableListOf()
        val unreachableList: MutableList<MuseumRoom> = mutableListOf()
        val unexitableList: MutableList<MuseumRoom> = mutableListOf()
        for (room in rooms) {
            for (i in 0..turnstiles.size) {
                for (turnstile in turnstiles) {
                    if (room == turnstile.second && turnstile.first in reachableList) {
                        reachableList.add(room)
                    }
                }
            }
            if (room !in reachableList) {
                unreachableList.add(room)
            }
        }
        for (turnstile in turnstiles) {
            if (turnstile.second is Outside) {
                exitableList.add(turnstile.first)
            }
        }
        for (room in rooms) {
            for (i in 0..turnstiles.size) {
                for (turnstile in turnstiles) {
                    if (room == turnstile.first && turnstile.second in exitableList) {
                        exitableList.add(room)
                    }
                }
            }
            if (room !in exitableList) {
                unexitableList.add(room)
            }
        }
        if (unreachableList.size != 0) {
            throw UnreachableRoomsException(unreachableList.toSet())
        } else if (unexitableList.size != 0) {
            throw CannotExitMuseumException(unexitableList.toSet())
        } else {
            return
        }
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("$name\n")
        for (room in rooms) {
            stringBuilder.append("$room leads to: ")
            for (turnstile in turnstiles) {
                if (room == turnstile.first) {
                    stringBuilder.append(turnstile.second).append(", ")
                }
            }
            stringBuilder.deleteAt(stringBuilder.lastIndex)
                .deleteAt(stringBuilder.lastIndex).append("\n")
        }
        return stringBuilder.toString()
    }
}

class UnreachableRoomsException(val unreachable: Set<MuseumRoom>) :
    Exception() {
    private val stringBuilder = StringBuilder()
    override fun toString(): String {
        for (room in unreachable) {
            if (unreachable.size == 1) {
                stringBuilder.append("$room")
            } else {
                stringBuilder.append("$room, ")
            }
        }
        val newString = stringBuilder.toString()
        return "Unreachable rooms: $newString"
    }
}

class CannotExitMuseumException(val unexitable: Set<MuseumRoom>) : Exception() {
    private val stringBuilder = StringBuilder()
    override fun toString(): String {
        for (room in unexitable) {
            if (unexitable.size == 1) {
                stringBuilder.append("$room")
            } else {
                stringBuilder.append("$room, ")
            }
        }
        val newString = stringBuilder.toString()
        return "Unreachable rooms: $newString"
    }
}
