package com.views

import com.UserSession
import com.utils.InvalidRequestData
import com.controllers.RoomController
import com.plugins.Routes
import com.utils.InvalidCodeException
import com.utils.JWTHandler
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.sessions.*
import io.ktor.util.pipeline.*
import kotlinx.serialization.Serializable

@Serializable
data class RoomBlackWhiteList(val ban: List<String> = emptyList(), val unban: List<String> = emptyList())

fun getUserName(sessions: CurrentSession): String {
    val session = sessions.get("session") as UserSession
    val payload = JWTHandler.getPayload(session.token)
    return payload.getClaim("user").asString()
}

suspend fun createRoomView(context: PipelineContext<Unit, ApplicationCall>) = viewExceptionHadler(context) {
    val roomName = context.call.receiveParameters()["name"] ?: throw InvalidRequestData()
    val userName = getUserName(context.call.sessions)
    val room = RoomController.createRoom(userName, roomName)
    context.call.respondRedirect("${Routes.Room.SCOPE.path}/${room.code}")
}

suspend fun enterRoomView(context: PipelineContext<Unit, ApplicationCall>) = viewExceptionHadler(context) {
    val code = context.call.parameters["code"] ?: throw InvalidRequestData()
    val userName = getUserName(context.call.sessions)
    RoomController.enterRoom(userName, code)
    context.call.respondRedirect("${Routes.Room.SCOPE.path}/$code")
}

suspend fun retrieveRoomView(context: PipelineContext<Unit, ApplicationCall>) = viewExceptionHadler(context) {
    val code = context.call.parameters["code"] ?: throw InvalidRequestData()
    val userName = getUserName(context.call.sessions)
    RoomController.retrieveRoom(userName, code)?.also { context.call.respond(it) } ?: throw InvalidCodeException()
}

suspend fun updateRoomView(context: PipelineContext<Unit, ApplicationCall>) = viewExceptionHadler(context) {
    val code = context.call.parameters["code"] ?: throw InvalidRequestData()
    val userName = getUserName(context.call.sessions)
    val (banList, unbanList) = context.call.receive<RoomBlackWhiteList>()
    banList.forEach { RoomController.banUser(userName, it, code) }
    unbanList.forEach { RoomController.unbanUser(userName, it, code) }
    context.call.respondRedirect("${Routes.Room.SCOPE.path}/$code")
}

suspend fun deleteRoomView(context: PipelineContext<Unit, ApplicationCall>) = viewExceptionHadler(context) {
    val code = context.call.parameters["code"] ?: throw InvalidRequestData()
    val userName = getUserName(context.call.sessions)
    RoomController.deleteRoom(userName, code)
    context.call.respondRedirect(Routes.INDEX.path)
}