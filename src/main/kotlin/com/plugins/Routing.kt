package com.plugins

import com.indexView
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.request.*

fun Application.configureRouting() {
    routing {
        get("/") { indexView(this) }
        static("/static") { resources("static") }
    }
}
