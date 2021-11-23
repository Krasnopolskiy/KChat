package com.plugins

import com.UserSession
import com.controllers.AuthService
import com.controllers.InvalidRequestData
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.sessions.*
import io.ktor.util.pipeline.*
import kotlinx.serialization.Serializable

@Serializable
class Credentials(val name: String, val password: String)

suspend fun indexView(context: PipelineContext<Unit, ApplicationCall>) = context.call.respondText(
    context::class.java.classLoader.getResource("index.html")!!.readText(),
    ContentType.Text.Html
)

suspend fun registrationView(context: PipelineContext<Unit, ApplicationCall>) {
    try {
        val credentials = context.call.receiveOrNull<Credentials>() ?: throw InvalidRequestData()
        AuthService.registerUser(credentials.name, credentials.password)
        val token = AuthService.loginUser(credentials.name, credentials.password)
        context.call.respond(mapOf("token" to token))
    } catch (e: Exception) {
        context.call.respond(HttpStatusCode.BadRequest, e.message ?: "Something went wrong")
    }
}

suspend fun loginView(context: PipelineContext<Unit, ApplicationCall>) {
    try {
        val credentials = context.call.receiveOrNull<Credentials>() ?: throw InvalidRequestData()
        val token = AuthService.loginUser(credentials.name, credentials.password)
        context.call.sessions.set(UserSession(token))
        context.call.respond(mapOf("token" to token))
    } catch (e: Exception) {
        context.call.respond(HttpStatusCode.BadRequest, e.message ?: "Something went wrong")
    }
}