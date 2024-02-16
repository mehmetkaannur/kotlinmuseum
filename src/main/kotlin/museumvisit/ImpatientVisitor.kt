package museumvisit

import java.io.PrintStream
import kotlin.random.Random

class ImpatientVisitor(val name: String, val printer: PrintStream, val museum: Museum) : Runnable {
    override fun run() {
        var entered = museum.enter()
        while (entered == null) {
            printer.println("$name could not get into ${museum.name} but will try again soon.")
            Thread.sleep(10)
            printer.println("$name is ready to try again.")
            entered = museum.enter()
        }
        printer.println("$name has entered ${museum.name}")

        var currentRoom: MuseumRoom? = museum.entrance
        printer.println("$name has entered ${currentRoom!!.name}.")
        Thread.sleep(Random.nextLong(1, 200))

        while (currentRoom != null) {
            val roomsToGo = museum.turnstiles.filter { it.first == currentRoom }.map { it.second }
            val chosenRoom = roomsToGo.random()
            printer.println("$name wants to leave ${currentRoom.name}.")
            if (museum.passThroughTurnstile(currentRoom, chosenRoom)) {
                printer.println("$name has left ${currentRoom.name}.")
                currentRoom = chosenRoom
                if (currentRoom == null) {
                    printer.println("$name has entered Outside.")
                } else {
                    printer.println("$name has entered ${currentRoom.name}.")
                }
                Thread.sleep(Random.nextLong(1, 200))
            } else {
                printer.println("$name failed to leave ${currentRoom.name} but will try again soon.")
                Thread.sleep(10)
                printer.println("$name is ready to try leaving ${currentRoom.name} again.")
            }
        }
        printer.println("$name has left ${museum.name}.")
        return
    }
}