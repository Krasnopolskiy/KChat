package com.models

import kotlinx.serialization.Serializable

@Serializable
data class Room(val name: String, val creator: User) {
    val users = mutableListOf<User>()
    val admins = mutableListOf<User>()
    val banned = mutableListOf<User>()
    val messages = mutableListOf<Message>()
}