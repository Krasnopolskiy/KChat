package com

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.plugins.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(ContentNegotiation) { jackson() }

        install(CORS) {
            // TODO DEVELOPMENT-ONLY
            host("localhost:3000")
            allowCredentials = true
        }

        configureSessions()
        configureAuthentication()
        configureSockets()
        configureRouting()
    }.start(wait = true)
}
