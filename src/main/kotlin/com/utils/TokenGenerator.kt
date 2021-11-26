package com.utils

import kotlin.random.Random

object TokenGenerator {
    fun generateHexToken(seed: Int, length: Int): String {
        val random = Random(seed)
        return List(length) { random.nextInt(256) }.joinToString("") { "%2x".format(it) }
    }
}