package com.controllers

import com.models.User
import com.models.UserCredentials
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo
import java.security.MessageDigest

object AuthService {
    private val db = KMongo.createClient().coroutine.getDatabase("KChat")
    private val hashAlgorithm = MessageDigest.getInstance("SHA-256")

    private fun hashPassword(password: String) = hashAlgorithm.digest(password.toByteArray())

    private fun checkPassword(password: String, hash: ByteArray) = hash contentEquals hashPassword(password)

    suspend fun registerUser(name: String, password: String) {
        val users = db.getCollection<User>()
        if (users.findOne(User::name eq name) != null) throw UserNameIsNotFreeException()
        User(name).also {
            users.insertOne(it)
            db.getCollection<UserCredentials>().insertOne(
                UserCredentials(name, hashPassword(password))
            )
        }
    }

    suspend fun loginUser(name: String, password: String): String {
        val credentials = db.getCollection<UserCredentials>()
        credentials.findOne(UserCredentials::name eq name)?.also {
            if (checkPassword(password, it.hash)) return JWTService.generateToken(name)
        }
        throw InvalidCredentialsException()
    }
}