package com.example.bluetoothdevicediscover.domain.repository

import com.example.bluetoothdevicediscover.domain.entity.PhoneInfo

interface UserPhoneRepository {
    suspend fun findPhonesForUser(userId: String): List<PhoneInfo>?

    suspend fun setPhonesForUser(userId: String, phones: List<PhoneInfo>)
}