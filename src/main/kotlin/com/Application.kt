package com

import com.controllers.JWTService
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.serialization.json
import com.plugins.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.sessions.*

data class UserSession(val token: String) : Principal

fun validateSession(session: UserSession): Principal? {
    return try {
        val token = JWTService.verifier.verify(session.token)
        val credential = JWTCredential(token)
        require(credential.payload.getClaim("user").asString().isNotEmpty())
        JWTPrincipal(credential.payload)
    } catch (e: Exception) {
        null
    }
}

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(ContentNegotiation) {
            json()
        }

        install(Sessions) { cookie<UserSession>("session") }

        install(Authentication) {
            session<UserSession>("auth-session") {
                validate { validateSession(it) }
                challenge { call.respondRedirect("/") }
            }
        }

        configureRouting()
        configureSockets()
    }.start(wait = true)
}
