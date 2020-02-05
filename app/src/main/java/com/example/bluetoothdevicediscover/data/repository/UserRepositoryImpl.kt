package com.example.bluetoothdevicediscover.data.repository

import com.example.bluetoothdevicediscover.domain.entity.User
import com.example.bluetoothdevicediscover.domain.repository.UserRepository

const val NOT_FOUND = -1
class UserRepositoryImpl: UserRepository {
    private val users = mutableListOf<User>()

    override suspend fun getUserById(userId: String) = users.firstOrNull { it.id == userId }

    override suspend fun updateUser(user: User) {
        val existingUserIndex = users.indexOfFirst { it.id == user.id }
        if (existingUserIndex == NOT_FOUND) {
            users.add(user)
        } else {
            users.removeAt(existingUserIndex)
            users.add(existingUserIndex, user)
        }
    }

    override suspend fun getPreviousUser(userId: String): User? {
        val userPivotIndex = users.indexOfFirst { it.id == userId }
        return if (userPivotIndex == 0) {
            null
        } else {
            users[userPivotIndex - 1]
        }
    }

    override suspend fun getNextUser(userId: String): User? {
        val userIdIndex = users.indexOfFirst { it.id == userId }
        return if (userIdIndex == NOT_FOUND || userIdIndex == users.size-1) {
            null
        } else {
            users[userIdIndex+1]
        }
    }
}