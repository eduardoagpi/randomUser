package com.example.bluetoothdevicediscover.data.repository

import com.example.bluetoothdevicediscover.domain.entity.Location
import com.example.bluetoothdevicediscover.domain.repository.UserLocationRepository

class UserLocationRepositoryImpl: UserLocationRepository {
    private val usersWithLocation = mutableMapOf<String, Location>()

    override suspend fun getLocationByUserId(userId: String) = usersWithLocation[userId]

    override suspend fun updateLocation(userId: String, location: Location) {
        usersWithLocation[userId] = location
    }
}