package com.plugins

import com.views.*
import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.content.*

fun Application.configureRouting() {
    routing {
        get("/") { indexView(this) }
        post("/register") { registrationView(this) }
        post("/login") { loginView(this) }
        authenticate("auth-session") {
            route("/room") {
                post { createRoomView(this) }
                get("/enter/{code}") { enterRoomView(this) }
                get("/{code}") { roomView(this) }
                put("/{code}") {}
                delete("/{code}") {}
            }
        }
        static("/static") { resources("static") }
    }
}
