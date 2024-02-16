package museumvisit

fun createArtGallery(): Museum {
    val entrance = MuseumRoom("Entrance hall", 20)
    val exhibition = MuseumRoom("Exhibition room", 5)
    val gallery = Museum("Art gallery", entrance)
    gallery.addRoom(exhibition)
    gallery.connectRoomTo(entrance, exhibition)
    gallery.connectRoomToExit(exhibition)
    return gallery
}

fun createAnimalSanctuary(): Museum {
    val entrance = MuseumRoom("Entrance hall", 20)
    val bats = MuseumRoom("Bats", 10)
    val lizards = MuseumRoom("Lizards", 10)
    val snakes = MuseumRoom("Snakes", 10)
    val insects = MuseumRoom("Insects", 10)
    val gift = MuseumRoom("Gift shop", 20)
    val sanctuary = Museum("Animal sanctuary", entrance)
    sanctuary.addRoom(bats)
    sanctuary.addRoom(lizards)
    sanctuary.addRoom(insects)
    sanctuary.addRoom(gift)
    sanctuary.addRoom(snakes)
    sanctuary.connectRoomTo(entrance, bats)
    sanctuary.connectRoomTo(bats, lizards)
    sanctuary.connectRoomTo(lizards, insects)
    sanctuary.connectRoomTo(lizards, gift)
    sanctuary.connectRoomTo(insects, snakes)
    sanctuary.connectRoomTo(insects, gift)
    sanctuary.connectRoomTo(snakes, entrance)
    sanctuary.connectRoomToExit(gift)
    return sanctuary
}

fun createAnimalSanctuaryWithUnreachableRooms(): Museum {
    val entrance = MuseumRoom("Entrance hall", 20)
    val bats = MuseumRoom("Bats", 10)
    val lizards = MuseumRoom("Lizards", 10)
    val snakes = MuseumRoom("Snakes", 10)
    val insects = MuseumRoom("Insects", 10)
    val gift = MuseumRoom("Gift shop", 20)
    val sanctuary = Museum("Animal sanctuary", entrance)
    sanctuary.addRoom(bats)
    sanctuary.addRoom(lizards)
    sanctuary.addRoom(insects)
    sanctuary.addRoom(gift)
    sanctuary.addRoom(snakes)
    sanctuary.connectRoomTo(bats, lizards)
    sanctuary.connectRoomTo(lizards, insects)
    sanctuary.connectRoomTo(lizards, gift)
    sanctuary.connectRoomTo(insects, snakes)
    sanctuary.connectRoomTo(insects, gift)
    sanctuary.connectRoomTo(snakes, entrance)
    sanctuary.connectRoomToExit(gift)
    return sanctuary
}

fun createAnimalSanctuaryWithRoomsThatDoNotLeadToExit(): Museum {
    val entrance = MuseumRoom("Entrance hall", 20)
    val bats = MuseumRoom("Bats", 10)
    val lizards = MuseumRoom("Lizards", 10)
    val snakes = MuseumRoom("Snakes", 10)
    val insects = MuseumRoom("Insects", 10)
    val gift = MuseumRoom("Gift shop", 20)
    val sanctuary = Museum("Animal sanctuary", entrance)
    sanctuary.addRoom(bats)
    sanctuary.addRoom(lizards)
    sanctuary.addRoom(insects)
    sanctuary.addRoom(gift)
    sanctuary.addRoom(snakes)
    sanctuary.connectRoomTo(entrance, bats)
    sanctuary.connectRoomTo(bats, lizards)
    sanctuary.connectRoomTo(lizards, insects)
    sanctuary.connectRoomTo(lizards, gift)
    sanctuary.connectRoomTo(insects, snakes)
    sanctuary.connectRoomToExit(gift)
    return sanctuary
}

fun main() {
    println(createArtGallery().toString())
}