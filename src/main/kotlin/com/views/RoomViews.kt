package com.views

import com.UserSession
import com.utils.InvalidRequestData
import com.controllers.RoomController
import com.utils.InvalidCodeException
import com.utils.JWTHandler
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
        val room = RoomController.createRoom(userName, roomName)
        context.call.respondRedirect("/room/${room.code}")
    } catch (e: Exception) {
        context.call.respond(HttpStatusCode.BadRequest, e.message ?: "Something went wrong")
    }
}

suspend fun enterRoomView(context: PipelineContext<Unit, ApplicationCall>) {
    try {
        val code = context.call.parameters["code"] ?: throw InvalidRequestData()
        val userName = getUserName(context.call.sessions)
        RoomController.enterRoom(userName, code)
        context.call.respondRedirect("/room/$code")
    } catch (e: Exception) {
        context.call.respond(HttpStatusCode.BadRequest, e.message ?: "Something went wrong")
    }
}

suspend fun roomView(context: PipelineContext<Unit, ApplicationCall>) {
    try {
        val code = context.call.parameters["code"] ?: throw InvalidRequestData()
        val userName = getUserName(context.call.sessions)
        RoomController.retrieveRoom(userName, code)?.also {
            context.call.respond(it)
        } ?: throw InvalidCodeException()
    } catch (e: Exception) {
        context.call.respond(HttpStatusCode.BadRequest, e.message ?: "Something went wrong")
    }
}