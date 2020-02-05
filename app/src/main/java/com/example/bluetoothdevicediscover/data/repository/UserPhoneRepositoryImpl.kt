package com.example.bluetoothdevicediscover.data.repository

import com.example.bluetoothdevicediscover.domain.entity.PhoneInfo
import com.example.bluetoothdevicediscover.domain.repository.UserPhoneRepository

class UserPhoneRepositoryImpl: UserPhoneRepository {

    private val phonesByUser = mutableListOf<Pair<String, PhoneInfo>>()

    override suspend fun findPhonesForUser(userId: String) = phonesByUser
            .filter { it.first == userId }
            .map { it.second }

    override suspend fun setPhonesForUser(userId: String, phones: List<PhoneInfo>) {
        phones.forEach { phonesByUser.add(Pair(userId, it)) }
    }
}