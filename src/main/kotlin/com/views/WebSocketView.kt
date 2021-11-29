package com.views

import com.controllers.MainController
import com.models.Message
import com.models.Room
import com.models.User
import com.utils.InvalidCodeException
import io.ktor.http.cio.websocket.*
import io.ktor.sessions.*
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.collections.LinkedHashSet

object SocketHandler {
    data class Connection(val session: DefaultWebSocketSession, val user: User, val room: Room) {
        companion object {
            val lastId = AtomicInteger(0)
        }

        val id = lastId.getAndIncrement()
    }

    private suspend inline fun wrapWebSocket(session: DefaultWebSocketSession, function: () -> Unit) {
        try {
            function()
        } catch (e: Exception) {
            session.send(Json.encodeToString(hashMapOf("error" to (e.message ?: "Something went wrong"))))
        }
    }

    private val connections = mutableMapOf<String, MutableSet<Connection>>()

    private suspend fun parseSession(webSocketSession: WebSocketServerSession): Pair<User, Room> {
        val userName = getUserName(webSocketSession.call.sessions)
        val code = webSocketSession.call.parameters["code"] ?: throw InvalidCodeException()
        val user = MainController.retrieveUser(userName)
        val room = MainController.retrieveRoom(userName, code)
        return Pair(user, room)
    }

    private suspend fun connect(session: DefaultWebSocketSession): Connection {
        val (user, room) = parseSession(session as WebSocketServerSession)
        val connection = Connection(session, user, room)
        if (connections[room.code] == null)
            connections[room.code] = Collections.synchronizedSet(LinkedHashSet())
        connections[room.code]!!.add(connection)
        return connection
    }

    private fun disconnect(connection: Connection) = connections[connection.room.code]?.remove(connection)

    private suspend fun broadcast(room: Room, message: Message) {
        connections[room.code]!!.forEach { connection ->
            connection.session.send(Json.encodeToString(message))
        }
    }

    suspend fun roomSocketView(session: DefaultWebSocketSession) = wrapWebSocket(session) {
        val connection = connect(session)
        connection.room.messages.forEach { message -> session.send(Json.encodeToString(message)) }
        try {
            for (frame in session.incoming) {
                frame as? Frame.Text ?: continue
                MainController.addMessage(connection.user, connection.room, frame.readText()).also { message ->
                    broadcast(connection.room, message)
                }
            }
        } catch (e: Exception) {
            println(e.localizedMessage)
        } finally {
            disconnect(connection)
        }
    }
}
