package com.plugins

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*

fun Application.configureAuthentication() {
    install(Authentication) {
        session<UserSession>("auth-session") {
            validate { validateSession(it) }
            challenge { call.respondRedirect("/") }
        }
    }
}