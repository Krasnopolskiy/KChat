package com.views

import com.controllers.RoomController
import com.controllers.UserController
import com.models.Room
import com.models.User
import io.ktor.http.cio.websocket.*
import io.ktor.sessions.*
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.collections.LinkedHashSet

suspend inline fun wrapWebSocket(session: DefaultWebSocketSession, function: () -> Unit) {
    try {
        function()
    } catch (e: Exception) {
        session.send(Json.encodeToString(hashMapOf("error" to (e.message ?: "Something went wrong"))))
    }
}

data class Connection(val session: DefaultWebSocketSession) {
    companion object {
        val lastId = AtomicInteger(0)
    }
    val id = lastId.getAndIncrement()
}

val connections = mutableMapOf<String, MutableSet<Connection>>()

suspend fun parseRequest(webSocketSession: WebSocketServerSession): Pair<User, Room> {
    val userName = getUserName(webSocketSession.call.sessions)
    val user = UserController.retrieveUser(userName)!!
    val roomCode = webSocketSession.call.parameters["code"]!!
    val room = RoomController.retrieveRoom(userName, roomCode)!!
    return Pair(user, room)
}

suspend fun roomSocketView(session: DefaultWebSocketSession) = wrapWebSocket(session) {
    val (user, room) = parseRequest(session as WebSocketServerSession)
    val connection = Connection(session)
    if (connections[room.code] == null)
        connections[room.code] = Collections.synchronizedSet(LinkedHashSet())
    connections[room.code]!!.add(connection)
    for (frame in session.incoming) {
        frame as? Frame.Text ?: continue
        val text = frame.readText()
        RoomController.sendMessage(user, room, text)
    }
}