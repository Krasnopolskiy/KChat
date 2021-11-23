package com.controllers

import com.auth0.jwt.*
import com.auth0.jwt.algorithms.Algorithm
import com.models.AnonymousUser
import com.models.User
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import java.security.MessageDigest
import java.util.*


object AuthService {
    private val secret = "danger"
    private val issuer = "ktor.io"
    private val algorithm = Algorithm.HMAC512(secret)
    private val hashAlgorithm = MessageDigest.getInstance("SHA-256")

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    private fun generateToken(name: String): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withClaim("userId", name)
        .withExpiresAt(expiresAt())
        .sign(algorithm)

    private fun hashPassword(password: String) = hashAlgorithm.digest(password.toByteArray())

    private fun checkPassword(password: String, hash: ByteArray) = hash contentEquals hashPassword(password)

    suspend fun registerUser(name: String): String? {
        val user = AnonymousUser(name)
        if (!Database.isAnonymousNameFree(name)) return null
        Database.registerAnonymousUser(user)
        return generateToken(name)
    }

    suspend fun registerUser(name: String, password: String): String? {
        val user = User(name, hashPassword(password))
        if (!Database.isNameFree(name)) return null
            Database.registerUser(user)
        return generateToken(name)
    }

    private fun expiresAt() =
        Date(System.currentTimeMillis() + 3_600_000 * 24) // 24 hours
}