package com.example.bluetoothdevicediscover.domain.repository

import com.example.bluetoothdevicediscover.domain.entity.User

interface UserRepository {
    suspend fun getUserById(userId: String): User?
    suspend fun updateUser(user: User)
    suspend fun getPreviousUser(userId: String): User?
    suspend fun getNextUser(userId: String): User?
}