package com

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.util.pipeline.*

suspend fun indexView(context: PipelineContext<Unit, ApplicationCall>) = context.call.respondText(
    context::class.java.classLoader.getResource("index.html")!!.readText(),
    ContentType.Text.Html
)
