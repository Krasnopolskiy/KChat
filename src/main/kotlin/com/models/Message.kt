package com.models

import kotlinx.serialization.Serializable

@Serializable
data class Message(val user: User, val text: String) {
    val timestamp = System.currentTimeMillis()
}