package com.models

import kotlinx.serialization.Serializable

@Serializable
data class Message(val user: String, val text: String) {
    val timestamp = System.currentTimeMillis()
}