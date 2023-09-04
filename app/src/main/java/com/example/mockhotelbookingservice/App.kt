package com.example.mockhotelbookingservice

import android.app.Application
import com.example.mockhotelbookingservice.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App: Application(), HasAndroidInjector {

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().applicationBind(this).build().inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return injector
    }
}