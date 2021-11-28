package com.models

import kotlinx.serialization.Serializable

@Serializable
class User(val name: String) {
    val rooms = mutableSetOf<HashMap<String, String>>()
}
