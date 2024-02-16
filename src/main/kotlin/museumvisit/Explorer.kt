package museumvisit

import kotlin.system.exitProcess

fun main() {
    val museums = mapOf(
            "art gallery" to createArtGallery(),
            "animal sanctuary" to createAnimalSanctuary()
    )

    while (true) {
        println("Welcome to the Explorer!")
        println("Available museums to explore: art gallery, animal sanctuary.")
        println("Enter the name of the museum you wish to explore, or type 'exit' to quit:")

        val museumName = readlnOrNull()
        if (museumName == null || museumName.lowercase() == "exit") {
            println("Thank you for using Explorer. Goodbye!")
            exitProcess(0)
        }

        val museum = museums[museumName.lowercase()]
        if (museum == null) {
            println("Museum not found. Please try again.")
            continue
        }

        var currentRoom = museum.entrance
        while (currentRoom.name != "Outside") {
            val rooms = museum.turnstiles.filter { it.first == currentRoom }.map { it.second }

            println("You are now in the ${currentRoom.name}. Where would you like to go next?\n")
            println("Rooms: ${rooms.map { it?.name ?: "Exit" }}")
            val nextRoom = readlnOrNull()
            if (museum.rooms.map { it.name }.contains(nextRoom)) {
                currentRoom = museum.rooms.find { it.name == nextRoom }!!
            } else if (nextRoom == "Exit") {
                currentRoom = MuseumRoom("Outside", Int.MAX_VALUE)
            } else {
                println("Unknown destination. Please try again.")
            }
        }
    }
}