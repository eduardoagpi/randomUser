package com.example.bluetoothdevicediscover.di

import android.app.Application
import android.content.Context
import com.example.bluetoothdevicediscover.app.activity.RandomUserActivity
import dagger.Binds
import dagger.Module
import javax.inject.Singleton
import com.example.bluetoothdevicediscover.app.application.MyApplication
import dagger.android.ContributesAndroidInjector


@Module
abstract class AppModule {
    @Binds
    @Singleton
    // Singleton annotation isn't necessary (in this case since Application instance is unique)
    // but is here for convention.
    abstract fun application(app: MyApplication): Application

    @Binds
    @Singleton
    abstract fun provideContext(application: Application): Context


    @ContributesAndroidInjector
    internal abstract fun contributeMainActivityInjector(): RandomUserActivity

}
