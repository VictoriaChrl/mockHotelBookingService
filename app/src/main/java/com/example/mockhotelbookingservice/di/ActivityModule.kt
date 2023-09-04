package com.example.mockhotelbookingservice.di

import com.example.mockhotelbookingservice.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun injectActivity(): MainActivity
}