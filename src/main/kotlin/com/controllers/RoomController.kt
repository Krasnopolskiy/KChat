package com.controllers

import com.models.Message
import com.models.Room
import com.models.User
import com.utils.*
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo
import kotlin.random.Random

object RoomController {
    private val db = KMongo.createClient().coroutine.getDatabase("KChat")
    private val rooms = db.getCollection<Room>()

    suspend fun retrieveRoom(code: String) = rooms.findOne(Room::code eq code)

    suspend fun createRoom(user: User, roomName: String): Room {
        val code = Room.generateHexToken(roomName)
        if (retrieveRoom(code) != null) throw RoomNameIsNotFreeException()
        val room = Room(user.name, roomName)
        rooms.insertOne(room)
        return room
    }

    suspend fun retrieveRoom(user: User, code: String) =
        rooms.findOne(Room::code eq code)?.apply {
            if (user.name !in users) throw UserNotInRoomException()
        }

    suspend fun joinUser(user: User, room: Room) {
        room.users += user.name
        rooms.updateOne(Room::name eq room.name, room)
    }

    suspend fun addMessage(room: Room, message: Message) {
        room.messages += message
        rooms.updateOne(Room::name eq room.name, room)
    }
}