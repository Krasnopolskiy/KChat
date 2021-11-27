package com.plugins

import com.views.roomSocketView
import io.ktor.http.cio.websocket.*
import io.ktor.websocket.*
import java.time.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*

enum class WebSocketRoutes(val path: String) {
    CHAT("/room/{code}/chat")
}

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    routing {
        authenticate("auth-session") {
            webSocket(WebSocketRoutes.CHAT.path) { roomSocketView(this) }
        }
    }
}
