package com.models

import kotlinx.serialization.Serializable

@Serializable
class User(val name: String) : Model() {
    val rooms = mutableListOf<Int>()
}
