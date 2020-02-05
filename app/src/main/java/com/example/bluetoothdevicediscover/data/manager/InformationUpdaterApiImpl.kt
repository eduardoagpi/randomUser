package com.example.bluetoothdevicediscover.data.manager

import com.example.bluetoothdevicediscover.data.entity.NoUserIdException
import com.example.bluetoothdevicediscover.data.entity.RandomUserApiResult
import com.example.bluetoothdevicediscover.data.mapper.RandomUserAPIMapper
import com.example.bluetoothdevicediscover.data.networking.RandomUserAPI
import com.example.bluetoothdevicediscover.domain.entity.UpdateAppInfoResult
import com.example.bluetoothdevicediscover.domain.manager.InformationUpdater
import com.example.bluetoothdevicediscover.domain.repository.UserLocationRepository
import com.example.bluetoothdevicediscover.domain.repository.UserPhoneRepository
import com.example.bluetoothdevicediscover.domain.repository.UserRepository
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class InformationUpdaterApiImpl @Inject constructor(
    private val userLocationRepository: UserLocationRepository,
    private val userPhoneRepository: UserPhoneRepository,
    private val userRepository: UserRepository
): InformationUpdater {

    private val userApi : RandomUserAPI

    init {
        val client: OkHttpClient
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        client = OkHttpClient.Builder().addInterceptor(interceptor)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()

        userApi = Retrofit.Builder()
            .baseUrl("https://randomuser.me/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(RandomUserAPI::class.java)
    }

    override suspend fun updateAppInfo(): UpdateAppInfoResult {
        try {
            var randomUser: RandomUserApiResult = userApi.getRandomUser().results.first()
            val domainObjects = RandomUserAPIMapper.map(randomUser)
            val user = domainObjects.o1
            val userLocation = domainObjects.o2
            val userPhones = domainObjects.o3

            userRepository.updateUser(user)
            userLocationRepository.updateLocation(user.id, userLocation)
            userPhoneRepository.setPhonesForUser(user.id, userPhones)
            return UpdateAppInfoResult.NewUserInfoFetched(user.id)

        } catch (e: NoUserIdException) {
            return UpdateAppInfoResult.NewUserInfoWithoutIdFetched
        } catch (e: Exception) {
            return UpdateAppInfoResult.UserInfoSourceError
        }
    }

}