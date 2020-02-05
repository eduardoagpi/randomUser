package com.example.bluetoothdevicediscover.di

import com.example.bluetoothdevicediscover.data.manager.InformationUpdaterApiImpl
import com.example.bluetoothdevicediscover.data.repository.UserLocationRepositoryImpl
import com.example.bluetoothdevicediscover.data.repository.UserPhoneRepositoryImpl
import com.example.bluetoothdevicediscover.data.repository.UserRepositoryImpl
import com.example.bluetoothdevicediscover.domain.manager.InformationUpdater
import com.example.bluetoothdevicediscover.domain.repository.UserLocationRepository
import com.example.bluetoothdevicediscover.domain.repository.UserPhoneRepository
import com.example.bluetoothdevicediscover.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun providesUserRepository(): UserRepository = UserRepositoryImpl()

    @Singleton
    @Provides
    fun providesUserLocationRepository(): UserLocationRepository = UserLocationRepositoryImpl()

    @Singleton
    @Provides
    fun providesUserPhoneRepository(): UserPhoneRepository = UserPhoneRepositoryImpl()

    @Singleton
    @Provides
    fun providesInformationUpdater(
        userRepository: UserRepository,
        userLocationRepository: UserLocationRepository,
        userPhoneRepository: UserPhoneRepository
    ): InformationUpdater =
        InformationUpdaterApiImpl(userLocationRepository, userPhoneRepository, userRepository)
}
