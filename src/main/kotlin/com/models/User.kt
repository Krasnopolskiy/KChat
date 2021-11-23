package com.models

import kotlinx.serialization.Serializable

@Serializable
class User(val name: String, val hashedPassword: ByteArray) : UserContext()
