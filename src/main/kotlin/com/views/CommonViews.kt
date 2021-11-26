package com.views

import com.plugins.Routes
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.sessions.*
import io.ktor.util.pipeline.*
import kotlinx.serialization.Serializable

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

suspend fun logoutView(context: PipelineContext<Unit, ApplicationCall>) {
    context.call.sessions.clear("session")
    context.call.respondRedirect(Routes.INDEX.path)
}