package com.example.mockhotelbookingservice.di

import com.example.mockhotelbookingservice.feature.hotel.hotel_info.ui.HotelFragment
import com.example.mockhotelbookingservice.feature.hotel.room_booking.ui.RoomBookingFragment
import com.example.mockhotelbookingservice.feature.hotel.room_list.ui.RoomListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeHotelFragment(): HotelFragment

    @ContributesAndroidInjector
    abstract fun contributeRoomListFragment(): RoomListFragment
    @ContributesAndroidInjector
    abstract fun contributeRoomBookingFragment(): RoomBookingFragment
}