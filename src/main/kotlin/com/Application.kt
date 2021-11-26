package com

import com.controllers.UserController
import com.utils.JWTHandler
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.serialization.json
import com.plugins.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.sessions.*

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

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(ContentNegotiation) { jackson() }

        install(CORS) {
            // TODO DEVELOPMENT-ONLY
            host("localhost:3000")
            allowCredentials = true
        }

        install(Sessions) {
            cookie<UserSession>("session") { cookie.httpOnly = false }
            cookie<ErrorMessage>("error") { cookie.httpOnly = false }
        }

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
