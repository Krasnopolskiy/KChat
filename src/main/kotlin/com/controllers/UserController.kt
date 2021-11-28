package com.controllers

import com.models.Room
import com.models.User
import com.models.UserCredentials
import com.utils.InvalidCredentialsException
import com.utils.JWTHandler
import com.utils.UserNameIsNotFreeException
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo
import java.security.MessageDigest

object UserController {
    object PasswordService {
        private val hashAlgorithm = MessageDigest.getInstance("SHA-256")

        fun hashPassword(password: String): ByteArray = hashAlgorithm.digest(password.toByteArray())
        fun checkPassword(password: String, hash: ByteArray) = hash contentEquals hashPassword(password)
    }

    private val db = KMongo.createClient().coroutine.getDatabase("KChat")
    private val users = db.getCollection<User>()
    private val credentials = db.getCollection<UserCredentials>()

    suspend fun retrieveUser(name: String) = users.findOne(User::name eq name)

    suspend fun registerUser(name: String, password: String) {
        if (retrieveUser(name) != null) throw UserNameIsNotFreeException()
        users.insertOne(User(name))
        credentials.insertOne(UserCredentials(name, PasswordService.hashPassword(password)))
    }

    suspend fun loginUser(name: String, password: String): String {
        credentials.findOne(UserCredentials::name eq name)?.also {
            if (PasswordService.checkPassword(password, it.hash)) return JWTHandler.generateToken(name)
        }
        throw InvalidCredentialsException()
    }

    suspend fun joinRoom(user: User, room: Room) {
        user.rooms += hashMapOf("code" to room.code, "name" to room.name)
        users.updateOne(User::name eq user.name, user)
    }
}