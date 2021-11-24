package com.plugins

import com.views.*
import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.content.*

enum class Routes(val path: String) {
    INDEX("/"),
    REGISTER("/register"),
    LOGIN("/login"),
    LOGOUT("/logout"),
    HOME("/home"),
    CHATS("/chats"),
    UNREAD("/unread");

    enum class Room(val path: String) {
        SCOPE("/room"),
        ROOM("/{code}"),
        ENTER("${ROOM.path}/enter");
    }
}

fun Application.configureRouting() {
    routing {
        get(Routes.INDEX.path) { indexView(this) }
        post(Routes.REGISTER.path) { registrationView(this) }
        post(Routes.LOGIN.path) { loginView(this) }
        authenticate("auth-session") {
            route(Routes.Room.SCOPE.path) {
                post { createRoomView(this) }
                get(Routes.Room.ENTER.path) { enterRoomView(this) }
                get(Routes.Room.ROOM.path) { retrieveRoomView(this) }
                put(Routes.Room.ROOM.path) { updateRoomView(this) }
                delete(Routes.Room.ROOM.path) { deleteRoomView(this) }
            }
            get(Routes.LOGOUT.path) { logoutView(this) }
        }
        static("/static") { resources("static") }
    }
}
