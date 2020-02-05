package com.example.bluetoothdevicediscover.data.networking

import com.example.bluetoothdevicediscover.data.entity.RootApiResult
import retrofit2.http.GET

interface RandomUserAPI {

    @GET("api")
    suspend fun getRandomUser(): RootApiResult
}