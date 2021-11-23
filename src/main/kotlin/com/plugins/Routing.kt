package com.plugins

import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.http.content.*

fun Application.configureRouting() {
    routing {
        get("/") { indexView(this) }
        post("/register") { registrationView(this) }
        post("/register/anonymous") { anonymousRegistrationView(this) }
        static("/static") { resources("static") }
    }
}
