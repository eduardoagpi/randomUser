package com.example.bluetoothdevicediscover.di;

import com.example.bluetoothdevicediscover.app.application.MyApplication;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {
        AppModule.class,
        ViewModelModule.class,
        RepositoryModule.class
})
public interface AppComponent extends AndroidInjector<MyApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<MyApplication> {
    }
}
