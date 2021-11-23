package com.plugins

import com.controllers.AuthService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.util.pipeline.*

suspend fun indexView(context: PipelineContext<Unit, ApplicationCall>) = context.call.respondText(
    context::class.java.classLoader.getResource("index.html")!!.readText(),
    ContentType.Text.Html
)

suspend fun anonymousRegistrationView(context: PipelineContext<Unit, ApplicationCall>) {
    val name = context.call.receive<Parameters>()["name"]
    if (name == null) context.call.respond(HttpStatusCode.BadRequest)
    else AuthService.registerUser(name)?.also {
        context.call.respond(mapOf("token" to it))
    } ?: context.call.respond("Name is already taken")
}

suspend fun registrationView(context: PipelineContext<Unit, ApplicationCall>) {
    val (name, password) = context.call.receive<Parameters>().run { listOf(this["name"], this["password"]) }
    if (name == null || password == null) context.call.respond(HttpStatusCode.BadRequest)
    else AuthService.registerUser(name, password)?.also {
        context.call.respond(mapOf("token" to it))
    } ?: context.call.respond("Name is already taken")
}