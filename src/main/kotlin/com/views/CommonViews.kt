package com.views

import com.UserSession
import com.controllers.UserController
import com.plugins.Routes
import com.utils.InvalidRequestData
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.sessions.*
import io.ktor.util.pipeline.*
import kotlinx.serialization.Serializable

@Serializable
class Credentials(val name: String, val password: String)

suspend fun indexView(context: PipelineContext<Unit, ApplicationCall>) = viewExceptionHadler(context) {
    context.call.respondText(
        context::class.java.classLoader.getResource("index.html")!!.readText(),
        ContentType.Text.Html
    )
}

suspend fun registrationView(context: PipelineContext<Unit, ApplicationCall>) = viewExceptionHadler(context) {
    val credentials = context.call.receiveOrNull<Credentials>() ?: throw InvalidRequestData()
    UserController.registerUser(credentials.name, credentials.password)
    val token = UserController.loginUser(credentials.name, credentials.password)
    context.call.sessions.set(UserSession(token))
    context.call.respondRedirect(Routes.INDEX.path)
}

suspend fun loginView(context: PipelineContext<Unit, ApplicationCall>) = viewExceptionHadler(context) {
    val credentials = context.call.receiveOrNull<Credentials>() ?: throw InvalidRequestData()
    val token = UserController.loginUser(credentials.name, credentials.password)
    context.call.sessions.set(UserSession(token))
    context.call.respondRedirect(Routes.INDEX.path)
}

suspend fun logoutView(context: PipelineContext<Unit, ApplicationCall>) = viewExceptionHadler(context) {
    context.call.sessions.clear("session")
    context.call.respondRedirect(Routes.INDEX.path)
}