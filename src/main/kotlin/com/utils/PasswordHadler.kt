package com.utils

import java.security.MessageDigest

object PasswordHadler {
    private val hashAlgorithm = MessageDigest.getInstance("SHA-256")

    fun hashPassword(password: String) = hashAlgorithm.digest(password.toByteArray())

    fun checkPassword(password: String, hash: ByteArray) = hash contentEquals hashPassword(password)
}