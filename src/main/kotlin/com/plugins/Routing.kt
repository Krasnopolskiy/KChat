package com.plugins

import com.views.*
import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.content.*

enum class APIRoutes(val path: String) {
    REGISTER("/register"),
    LOGIN("/login"),
    SCOPE("/api"),
    USER("/user");

    enum class Room(val path: String) {
        SCOPE("/room"),
        ROOM("/{code}"),
        ENTER("/enter");
    }
}

enum class Routes(val path: String) {
    ERROR("/error"),
    INDEX("/"),
    REGISTER("/register"),
    LOGIN("/login"),
    LOGOUT("/logout"),
    HOME("/home"),
    ROOMS("/rooms"),
    UNREAD("/unread"),
    ROOM("/room")
}

fun Application.configureRouting() {
    routing {
        post(APIRoutes.LOGIN.path) { loginView(this) }
        post(APIRoutes.REGISTER.path) { registrationView(this) }
        route(APIRoutes.SCOPE.path) {
            get(APIRoutes.USER.path) { retrieveUserView(this) }
            authenticate("auth-session") {
                route(APIRoutes.Room.SCOPE.path) {
                    post { createRoomView(this) }
                    get(APIRoutes.Room.ENTER.path) { enterRoomView(this) }
                    get(APIRoutes.Room.ROOM.path) { retrieveRoomView(this) }
                    put(APIRoutes.Room.ROOM.path) { updateRoomView(this) }
                    delete(APIRoutes.Room.ROOM.path) { deleteRoomView(this) }
                }
            }
        }

        get(Routes.ERROR.path) { errorPageView(this) }
        get(Routes.INDEX.path) { indexPageView(this) }
        get(Routes.LOGIN.path) { loginPageView(this) }
        get(Routes.REGISTER.path) { registerPageView(this) }
        authenticate("auth-session") {
            get(Routes.HOME.path) { homePageView(this) }
            get(Routes.ROOMS.path) { roomsView(this) }
            get(Routes.UNREAD.path) { }
            get(Routes.LOGOUT.path) { logoutView(this) }
        }
        static("/") { resources("") }
    }
}
