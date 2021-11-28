package com.models

import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
data class Room(val creator: String, val name: String) {
    companion object {
        private const val tokenLength = 3

        fun generateHexToken(name: String) =
            Random(name.hashCode()).nextBytes(tokenLength).joinToString("") { "%02x".format(it) }
    }

    val code = generateHexToken(name)
    val users = mutableSetOf<String>(creator)
    val messages = mutableListOf<Message>()
}