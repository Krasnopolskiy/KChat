package com.models

import com.utils.TokenGenerator
import kotlinx.serialization.Serializable
import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random

@Serializable
data class Room(val creator: String, val name: String) {
    val code = TokenGenerator.generateHexToken(name.hashCode(), 3)
    val users = mutableSetOf<String>(creator)
    val banned = mutableSetOf<String>()
    val messages = mutableListOf<Message>()
}