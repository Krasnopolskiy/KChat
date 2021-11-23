package com.plugins

import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.content.*
import io.ktor.response.*

fun Application.configureRouting() {
    routing {
        get("/") { indexView(this) }
        post("/register") { registrationView(this) }
        post("/login") { loginView(this) }
        static("/static") { resources("static") }
    }
}
