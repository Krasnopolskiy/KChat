package com.plugins

import com.views.*
import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.content.*

enum class APIRoutes(val path: String) {
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
    ROOM("/room/{code}")
}

fun Application.configureRouting() {
    routing {
        authenticate("auth-session") {
            route(APIRoutes.SCOPE.path) {
                get(APIRoutes.USER.path) { retrieveUserView(this) }
                route(APIRoutes.Room.SCOPE.path) {
                    post { createRoomView(this) }
                    get(APIRoutes.Room.ENTER.path) { enterRoomView(this) }
                    get(APIRoutes.Room.ROOM.path) { retrieveRoomView(this) }
                }
            }
        }

        get(Routes.ERROR.path) { errorPageView(this) }
        get(Routes.INDEX.path) { indexPageView(this) }
        get(Routes.LOGIN.path) { loginPageView(this) }
        get(Routes.REGISTER.path) { registerPageView(this) }
        post(Routes.LOGIN.path) { loginView(this) }
        post(Routes.REGISTER.path) { registrationView(this) }

        authenticate("auth-session") {
            get(Routes.HOME.path) { homePageView(this) }
            get(Routes.ROOMS.path) { roomsView(this) }
            get(Routes.ROOM.path) { roomView(this) }
            get(Routes.LOGOUT.path) { logoutView(this) }
        }

        static("/static") { resources("") }
    }
}
