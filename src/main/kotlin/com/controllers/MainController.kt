package com.controllers

object MainController {
    suspend fun enterRoom(userName: String, roomName: String) {
        RoomController.createRoomIfNotExists(userName, roomName)
        RoomController.addUser(userName, roomName)
        UserController.addRoom(userName, roomName)
    }
}