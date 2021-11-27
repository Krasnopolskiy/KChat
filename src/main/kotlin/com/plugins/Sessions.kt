package com.plugins

import com.controllers.UserController
import com.utils.JWTHandler
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.sessions.*
import kotlinx.serialization.Serializable

data class ErrorMessage(val message: String)
data class UserSession(val token: String)

suspend fun validateSession(session: UserSession): Principal? {
    return try {
        val payload = JWTHandler.getPayload(session.token)
        val name = payload.getClaim("user").asString()
        require(UserController.retrieveUser(name) != null)
        JWTPrincipal(payload)
    } catch (e: Exception) {
        null
    }
}

fun Application.configureSessions() {
    install(Sessions) {
        cookie<UserSession>("session") { cookie.httpOnly = false }
        cookie<ErrorMessage>("error") { cookie.httpOnly = false }
    }
}