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
                post { createRoomView(this) }                    // create new room
                get("/{name}") {}       // view or enter room
                put("/{name}") {}       // update room banned
                delete("/{name}") {}    // delete room
            }
        }
        static("/static") { resources("static") }
    }
}
