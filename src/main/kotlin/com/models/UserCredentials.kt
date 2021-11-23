package com.models

import kotlinx.serialization.Serializable

@Serializable
data class UserCredentials(val name: String, val hash: ByteArray) : Model()
