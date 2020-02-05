package com.example.bluetoothdevicediscover.di

import androidx.lifecycle.ViewModel
import com.example.bluetoothdevicediscover.app.viewmodel.RandomUserViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
abstract class ViewModelModule {

    @MapKey
    internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

    @Binds
    @IntoMap
    @ViewModelKey(RandomUserViewModel::class)
    internal abstract fun providesRandomUserViewModel(viewModel: RandomUserViewModel): ViewModel

}
