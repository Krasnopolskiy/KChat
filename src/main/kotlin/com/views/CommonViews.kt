package com.views

import com.controllers.MainController
import com.controllers.UserController
import com.plugins.ErrorMessage
import com.plugins.Routes
import com.plugins.UserSession
import com.utils.InvalidRequestData
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.sessions.*
import io.ktor.util.pipeline.*

suspend inline fun wrapView(context: PipelineContext<Unit, ApplicationCall>, function: () -> Unit) {
    try {
        function()
    } catch (e: Exception) {
        context.call.sessions.set(ErrorMessage(e.message ?: "Something went wrong"))
        context.call.respondRedirect(Routes.ERROR.path)
    }
}

suspend fun errorPageView(context: PipelineContext<Unit, ApplicationCall>) =
    context.call.respondText(
        context::class.java.classLoader.getResource("error.html")!!.readText(),
        ContentType.Text.Html
    )

suspend fun indexPageView(context: PipelineContext<Unit, ApplicationCall>) =
    context.call.respondText(
        context::class.java.classLoader.getResource("index.html")!!.readText(),
        ContentType.Text.Html
    )

suspend fun homePageView(context: PipelineContext<Unit, ApplicationCall>) =
    context.call.respondText(
        context::class.java.classLoader.getResource("home.html")!!.readText(),
        ContentType.Text.Html
    )

suspend fun loginPageView(context: PipelineContext<Unit, ApplicationCall>) =
    context.call.respondText(
        context::class.java.classLoader.getResource("login.html")!!.readText(),
        ContentType.Text.Html
    )

suspend fun registerPageView(context: PipelineContext<Unit, ApplicationCall>) =
    context.call.respondText(
        context::class.java.classLoader.getResource("register.html")!!.readText(),
        ContentType.Text.Html
    )

suspend fun registrationView(context: PipelineContext<Unit, ApplicationCall>) = wrapView(context) {
    val parameters = context.call.receiveParameters()
    val name = parameters["name"] ?: throw InvalidRequestData()
    val password = parameters["password"] ?: throw InvalidRequestData()
    MainController.registerUser(name, password)
    val token = MainController.loginUser(name, password)
    context.call.sessions.set(UserSession(token))
    context.call.respondRedirect(Routes.HOME.path)
}

suspend fun loginView(context: PipelineContext<Unit, ApplicationCall>) = wrapView(context) {
    val parameters = context.call.receiveParameters()
    val name = parameters["name"] ?: throw InvalidRequestData()
    val password = parameters["password"] ?: throw InvalidRequestData()
    val token = MainController.loginUser(name, password)
    context.call.sessions.set(UserSession(token))
    context.call.respondRedirect(Routes.HOME.path)
}

suspend fun roomsView(context: PipelineContext<Unit, ApplicationCall>) =
    context.call.respondText(
        context::class.java.classLoader.getResource("rooms.html")!!.readText(),
        ContentType.Text.Html
    )

suspend fun roomView(context: PipelineContext<Unit, ApplicationCall>) =
    context.call.respondText(
        context::class.java.classLoader.getResource("room.html")!!.readText(),
        ContentType.Text.Html
    )

suspend fun logoutView(context: PipelineContext<Unit, ApplicationCall>) {
    context.call.sessions.clear("session")
    context.call.respondRedirect(Routes.INDEX.path)
}