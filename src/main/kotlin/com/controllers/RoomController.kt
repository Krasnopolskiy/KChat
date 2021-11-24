package com.controllers

import com.models.Room
import com.models.User
import com.utils.InvalidCodeException
import com.utils.RoomNameIsNotFreeException
import com.utils.UserNotInRoomException
import com.utils.UserWasBannedException
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo

object RoomController {
    private val db = KMongo.createClient().coroutine.getDatabase("KChat")
    private val rooms = db.getCollection<Room>()

    suspend fun retrieveRoom(code: String) = rooms.findOne(Room::code eq code)

    suspend fun retrieveRoom(userName: String, code: String) =
        rooms.findOne(Room::code eq code)?.apply {
            if (userName !in users) throw UserNotInRoomException()
        }

    suspend fun createRoom(userName: String, roomName: String): Room {
        if (retrieveRoom(userName, roomName) != null) throw RoomNameIsNotFreeException()
        val room = Room(userName, roomName)
        rooms.insertOne(room)
        return room
    }

    private suspend fun addUser(user: User, room: Room) {
        room.users.add(user.name)
        rooms.updateOne(Room::name eq room.name, room)
    }

    suspend fun enterRoom(userName: String, code: String) {
        val room = retrieveRoom(code)?.apply {
            if (userName in banned) throw UserWasBannedException()
        } ?: throw InvalidCodeException()
        val user = UserController.retrieveUser(userName)!!
        addUser(user, room)
        UserController.addRoom(user, room)
    }
}