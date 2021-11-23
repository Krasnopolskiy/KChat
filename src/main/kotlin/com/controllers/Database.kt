package com.controllers

import com.models.AnonymousUser
import com.models.User
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo

object Database {
    private val db = KMongo.createClient().coroutine.getDatabase("KChat")
    private val users = db.getCollection<User>()
    private val anonymousUsers = db.getCollection<AnonymousUser>()

    suspend fun isNameFree(name: String) = users.findOne(User::name eq name) == null

    suspend fun isAnonymousNameFree(name: String) = anonymousUsers.findOne(AnonymousUser::name eq name) == null

    suspend fun registerAnonymousUser(user: AnonymousUser) {
        anonymousUsers.insertOne(user)
    }

    suspend fun registerUser(user: User) {
        users.insertOne(user)
    }
}