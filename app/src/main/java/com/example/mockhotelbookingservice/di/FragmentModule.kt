package com.example.mockhotelbookingservice.di

import com.example.mockhotelbookingservice.feature.hotel.hotel_info.ui.HotelFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeHotelFragment(): HotelFragment

}