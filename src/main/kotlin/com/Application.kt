package com

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.serialization.json
import com.plugins.*
import io.ktor.application.*

import io.ktor.features.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(ContentNegotiation) {
            json()
        }
        configureRouting()
        configureSockets()
    }.start(wait = true)
}
