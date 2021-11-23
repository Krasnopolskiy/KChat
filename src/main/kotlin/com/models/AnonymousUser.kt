package com.models

import kotlinx.serialization.Serializable

@Serializable
class AnonymousUser(val name: String) : UserContext()
