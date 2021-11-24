package com.controllers

import com.models.Room
import com.utils.*
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo

object RoomController {
    private val db = KMongo.createClient().coroutine.getDatabase("KChat")
    private val rooms = db.getCollection<Room>()

    private suspend fun retrieveRoom(code: String) = rooms.findOne(Room::code eq code)

    suspend fun retrieveRoom(userName: String, code: String) =
        rooms.findOne(Room::code eq code)?.apply {
            if (userName !in users) throw UserNotInRoomException()
        }

    suspend fun createRoom(userName: String, roomName: String): Room {
        val code = TokenGenerator.generateHexToken(roomName.hashCode(), 3)
        if (retrieveRoom(code) != null) throw RoomNameIsNotFreeException()
        val room = Room(userName, roomName)
        rooms.insertOne(room)
        return room
    }

    private suspend fun addUser(userName: String, code: String) {
        val room = retrieveRoom(code) ?: throw InvalidCodeException()
        room.users += userName
        rooms.updateOne(Room::name eq room.name, room)
    }

    suspend fun enterRoom(userName: String, code: String) {
        val room = retrieveRoom(code)?.apply {
            if (userName in banned) throw UserWasBannedException()
        } ?: throw InvalidCodeException()
        addUser(userName, room.name)
        UserController.addRoom(userName, room.name)
    }

    suspend fun banUser(userName: String, subjectName: String, code: String) {
        val room = retrieveRoom(code) ?: throw InvalidCodeException()
        if (userName != room.creator) throw UserHasNoRightException()
        if (subjectName in room.users) {
            room.users -= subjectName
            room.banned += subjectName
            rooms.updateOne(Room::name eq room.name, room)
            UserController.removeRoom(subjectName, room.name)
        }
    }

    suspend fun unbanUser(userName: String, subjectName: String, code: String) {
        val room = retrieveRoom(code) ?: throw InvalidCodeException()
        if (userName != room.creator) throw UserHasNoRightException()
        if (subjectName in room.banned) {
            room.users += subjectName
            room.banned -= subjectName
            rooms.updateOne(Room::name eq room.name, room)
            UserController.addRoom(subjectName, room.name)
        }
    }

    suspend fun deleteRoom(userName: String, code: String) {
        val room = retrieveRoom(code) ?: throw InvalidCodeException()
        if (userName != room.creator) throw UserHasNoRightException()
        room.users.forEach { UserController.removeRoom(it, room.name) }
        rooms.deleteOne(Room::name eq room.name)
    }
}