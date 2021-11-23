package com.models

import kotlinx.serialization.Serializable

@Serializable
data class Dialog(val user: User) {
    val messages = mutableListOf<Message>()
}