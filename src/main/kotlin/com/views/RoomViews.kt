package com.views

import com.UserSession
import com.controllers.InvalidRequestData
import com.controllers.MainController
import com.controllers.UserController
import com.utils.JWTHandler
import com.models.Room
import com.models.User
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.sessions.*
import io.ktor.util.pipeline.*

fun getUserName(sessions: CurrentSession): String {
    val session = sessions.get("session") as UserSession
    val payload = JWTHandler.getPayload(session.token)
    return payload.getClaim("user").asString()
}

suspend fun createRoomView(context: PipelineContext<Unit, ApplicationCall>) {
    try {
        val roomName = context.call.receiveParameters()["name"] ?: throw InvalidRequestData()
        val userName = getUserName(context.call.sessions)
        MainController.enterRoom(userName, roomName)
        context.call.respond("Added")
    } catch (e: Exception) {
        context.call.respond(HttpStatusCode.BadRequest, e.message ?: throw e)
    }
}