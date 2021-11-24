package com.views

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.util.pipeline.*

suspend inline fun viewExceptionHadler(context: PipelineContext<Unit, ApplicationCall>, function: () -> Unit) {
    try {
        function()
    } catch (e: Exception) {
        context.call.respond(HttpStatusCode.BadRequest, e.message ?: "Something went wrong")
    }
}