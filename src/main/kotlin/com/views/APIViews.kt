package com.views

import com.controllers.MainController
import com.utils.InvalidRequestData
import com.controllers.RoomController
import com.controllers.UserController
import com.plugins.APIRoutes
import com.plugins.Routes
import com.plugins.UserSession
import com.utils.InvalidCodeException
import com.utils.JWTHandler
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.sessions.*
import io.ktor.util.pipeline.*
import kotlinx.serialization.Serializable

suspend inline fun wrapAPIView(context: PipelineContext<Unit, ApplicationCall>, function: () -> Unit) {
    try {
        function()
    } catch (e: Exception) {
        context.call.respond(hashMapOf("error" to (e.message ?: "Something went wrong")))
    }
}

fun getUserName(sessions: CurrentSession): String {
    val session = sessions.get("session") as UserSession
    val payload = JWTHandler.getPayload(session.token)
    return payload.getClaim("user").asString()
}

suspend fun createRoomView(context: PipelineContext<Unit, ApplicationCall>) = wrapAPIView(context) {
    val roomName = context.call.receiveParameters()["name"] ?: throw InvalidRequestData()
    val userName = getUserName(context.call.sessions)
    val room = MainController.createRoom(userName, roomName)
    context.call.respondRedirect("${APIRoutes.Room.SCOPE.path}/${room.code}")
}

suspend fun enterRoomView(context: PipelineContext<Unit, ApplicationCall>) = wrapAPIView(context) {
    val code = context.call.request.queryParameters["code"] ?: throw InvalidRequestData()
    val userName = getUserName(context.call.sessions)
    MainController.enterRoom(userName, code)
    context.call.respondRedirect("${APIRoutes.Room.SCOPE.path}/${code}")
}

suspend fun retrieveUserView(context: PipelineContext<Unit, ApplicationCall>) = wrapAPIView(context) {
    val userName = getUserName(context.call.sessions)
    val user = MainController.retrieveUser(userName)
    context.call.respond(hashMapOf("user" to user))
}

suspend fun retrieveRoomView(context: PipelineContext<Unit, ApplicationCall>) = wrapAPIView(context) {
    val code = context.call.parameters["code"] ?: throw InvalidRequestData()
    val userName = getUserName(context.call.sessions)
    val room = MainController.retrieveRoom(userName, code)
    context.call.respond(hashMapOf("room" to room))
}
