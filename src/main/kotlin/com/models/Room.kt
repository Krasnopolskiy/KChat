package com.models

import kotlinx.serialization.Serializable

@Serializable
data class Room(val creator: String, val name: String) {
    val users = mutableSetOf<String>(creator)
    val banned = mutableSetOf<String>()
    val messages = mutableListOf<Message>()
}