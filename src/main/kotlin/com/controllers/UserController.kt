package com.controllers

import com.models.Room
import com.models.User
import com.models.UserCredentials
import com.utils.JWTHandler
import com.utils.PasswordHadler
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo

object UserController {
    private val db = KMongo.createClient().coroutine.getDatabase("KChat")
    private val users = db.getCollection<User>()
    private val credentials = db.getCollection<UserCredentials>()

    suspend fun retrieveUser(name: String) = users.findOne(User::name eq name)

    suspend fun addRoom(userName: String, roomName: String) {
        val user = retrieveUser(userName)!!
        user.rooms.add(roomName)
        users.updateOne(User::name eq userName, user)
    }

    suspend fun registerUser(name: String, password: String) {
        if (retrieveUser(name) != null) throw UserNameIsNotFreeException()
        User(name).also {
            users.insertOne(it)
            credentials.insertOne(UserCredentials(name, PasswordHadler.hashPassword(password)))
        }
    }

    suspend fun loginUser(name: String, password: String): String {
        credentials.findOne(UserCredentials::name eq name)?.also {
            if (PasswordHadler.checkPassword(password, it.hash)) return JWTHandler.generateToken(name)
        }
        throw InvalidCredentialsException()
    }
}