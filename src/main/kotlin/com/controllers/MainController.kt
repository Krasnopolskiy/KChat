package com.controllers

import com.models.Message
import com.models.Room
import com.models.User
import com.utils.InvalidCodeException
import com.utils.InvalidSessionException

object MainController {
    suspend fun registerUser(name: String, password: String) = UserController.registerUser(name, password)

    suspend fun loginUser(name: String, password: String) = UserController.loginUser(name, password)

    suspend fun createRoom(userName: String, roomName: String): Room {
        val user = UserController.retrieveUser(userName) ?: throw InvalidSessionException()
        return RoomController.createRoom(user, roomName).also { UserController.joinRoom(user, it) }
    }

    suspend fun enterRoom(userName: String, code: String) {
        val user = UserController.retrieveUser(userName) ?: throw InvalidSessionException()
        val room = RoomController.retrieveRoom(code) ?: throw InvalidCodeException()
        UserController.joinRoom(user, room)
        RoomController.joinUser(user, room)
    }

    suspend fun retrieveUser(userName: String) =
        UserController.retrieveUser(userName) ?: throw InvalidSessionException()

    suspend fun retrieveRoom(userName: String, code: String) =
        UserController.retrieveUser(userName)?.let { user ->
            RoomController.retrieveRoom(user, code) ?: throw InvalidCodeException()
        } ?: throw InvalidSessionException()

    suspend fun addMessage(user: User, room: Room, text: String) =
        Message(user.name, text).also { message -> RoomController.addMessage(room, message) }
}