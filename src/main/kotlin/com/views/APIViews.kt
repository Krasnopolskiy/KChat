package com.views

import com.ErrorMessage
import com.UserSession
import com.utils.InvalidRequestData
import com.controllers.RoomController
import com.controllers.UserController
import com.plugins.Routes
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
        context.call.sessions.set(ErrorMessage(e.message ?: "Something went wrong"))
        context.call.respondRedirect(Routes.ERROR.path)
    }
}

@Serializable
data class RoomBlackWhiteList(val ban: List<String> = emptyList(), val unban: List<String> = emptyList())

fun getUserName(sessions: CurrentSession): String {
    val session = sessions.get("session") as UserSession
    val payload = JWTHandler.getPayload(session.token)
    return payload.getClaim("user").asString()
}

suspend fun registrationView(context: PipelineContext<Unit, ApplicationCall>) = wrapAPIView(context) {
    val parameters = context.call.receiveParameters()
    val name = parameters["name"] ?: throw InvalidRequestData()
    val password = parameters["password"] ?: throw InvalidRequestData()
    UserController.registerUser(name, password)
    val token = UserController.loginUser(name, password)
    context.call.sessions.set(UserSession(token))
    context.call.respondRedirect(Routes.HOME.path)
}

suspend fun loginView(context: PipelineContext<Unit, ApplicationCall>) = wrapAPIView(context) {
    val parameters = context.call.receiveParameters()
    val name = parameters["name"] ?: throw InvalidRequestData()
    val password = parameters["password"] ?: throw InvalidRequestData()
    val token = UserController.loginUser(name, password)
    context.call.sessions.set(UserSession(token))
    context.call.respondRedirect(Routes.HOME.path)
}

suspend fun createRoomView(context: PipelineContext<Unit, ApplicationCall>) = wrapAPIView(context) {
    val roomName = context.call.receiveParameters()["name"] ?: throw InvalidRequestData()
    val userName = getUserName(context.call.sessions)
    val room = RoomController.createRoom(userName, roomName)
    context.call.respondRedirect("${Routes.ROOM.path}/${room.code}")
}

suspend fun enterRoomView(context: PipelineContext<Unit, ApplicationCall>) = wrapAPIView(context) {
    val code = context.call.request.queryParameters["code"] ?: throw InvalidRequestData()
    val userName = getUserName(context.call.sessions)
    RoomController.enterRoom(userName, code)
    context.call.respondRedirect("${Routes.ROOM.path}/${code}")
}

suspend fun retrieveUserView(context: PipelineContext<Unit, ApplicationCall>) = wrapAPIView(context) {
    val userName = getUserName(context.call.sessions)
    val user = UserController.retrieveUser(userName)!!
    context.call.respond(hashMapOf("user" to user, "rooms" to RoomController.retrieveRoomPreview(userName)))
}

suspend fun retrieveRoomView(context: PipelineContext<Unit, ApplicationCall>) = wrapAPIView(context) {
    val code = context.call.parameters["code"] ?: throw InvalidRequestData()
    val userName = getUserName(context.call.sessions)
    val room = RoomController.retrieveRoom(userName, code) ?: throw InvalidCodeException()
    context.call.respond(hashMapOf("room" to room))
}

suspend fun updateRoomView(context: PipelineContext<Unit, ApplicationCall>) = wrapAPIView(context) {
    val code = context.call.parameters["code"] ?: throw InvalidRequestData()
    val userName = getUserName(context.call.sessions)
    val (banList, unbanList) = context.call.receive<RoomBlackWhiteList>()
    banList.forEach { RoomController.banUser(userName, it, code) }
    unbanList.forEach { RoomController.unbanUser(userName, it, code) }
    val room = RoomController.retrieveRoom(userName, code)
    context.call.respond(hashMapOf("room" to room))
}

suspend fun deleteRoomView(context: PipelineContext<Unit, ApplicationCall>) = wrapAPIView(context) {
    val code = context.call.parameters["code"] ?: throw InvalidRequestData()
    val userName = getUserName(context.call.sessions)
    RoomController.deleteRoom(userName, code)
    context.call.respondRedirect(Routes.HOME.path)
}