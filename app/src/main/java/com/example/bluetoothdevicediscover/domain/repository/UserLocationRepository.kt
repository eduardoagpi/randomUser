package com.example.bluetoothdevicediscover.domain.repository

import com.example.bluetoothdevicediscover.domain.entity.Location

interface UserLocationRepository {

    suspend fun getLocationByUserId(userId: String): Location?

    suspend fun updateLocation(userId: String, location: Location)
}