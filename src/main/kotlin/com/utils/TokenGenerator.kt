package com.utils

import kotlin.random.Random

object TokenGenerator {
    fun generateHexToken(seed: Int, length: Int) =
        Random(seed).nextBytes(length).joinToString("") { "%2x".format(it) }
}