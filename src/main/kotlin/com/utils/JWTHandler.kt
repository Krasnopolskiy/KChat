package com.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.Payload
import io.ktor.auth.jwt.*
import java.util.*

object JWTHandler {
    private const val secret = "change me"
    private const val issuer = "GigaNigga"
    private val algorithm = Algorithm.HMAC512(secret)

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    private fun expiresAt() =
        Date(System.currentTimeMillis() + 3_600_000 * 24) // 24 hours

    fun getPayload(token: String): Payload {
        val decodedToken = verifier.verify(token)
        val credential = JWTCredential(decodedToken)
        return credential.payload
    }

    fun generateToken(name: String): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withClaim("user", name)
        .withExpiresAt(expiresAt())
        .sign(algorithm)
}