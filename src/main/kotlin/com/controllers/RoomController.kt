package com.controllers

import com.models.Room
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo

object RoomController {
    private val db = KMongo.createClient().coroutine.getDatabase("KChat")
    private val rooms = db.getCollection<Room>()

    private suspend fun retrieveRoom(userName: String, roomName: String) =
        rooms.findOne(Room::name eq roomName)?.apply {
            if (userName in banned) throw UserWasBannedException()
        }

    suspend fun createRoomIfNotExists(userName: String, roomName: String) {
        if (retrieveRoom(userName, roomName) == null) rooms.insertOne(Room(userName, roomName))
    }

    suspend fun addUser(userName: String, roomName: String) {
        val room = retrieveRoom(userName, roomName)!!
        room.users.add(userName)
        rooms.updateOne(Room::name eq roomName, room)
    }

    suspend fun createRoom(userName: String, roomName: String) {
        try {
            if (retrieveRoom(userName, roomName) != null) throw RoomNameIsNotFreeException()
            rooms.insertOne(Room(userName, roomName))
        } catch (e: UserWasBannedException) {
            throw RoomNameIsNotFreeException()
        }
    }
}