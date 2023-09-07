package com.example.mockhotelbookingservice.di


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mockhotelbookingservice.feature.hotel.hotel_info.presentation.HotelInfoViewModel
import com.example.mockhotelbookingservice.feature.hotel.room_booking.presentation.RoomBookingViewModel
import com.example.mockhotelbookingservice.feature.hotel.room_list.presentation.RoomListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface PresentationModule {
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HotelInfoViewModel::class)
    fun bindHotelInfoViewModel(viewModel: HotelInfoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RoomListViewModel::class)
    fun bindRoomListViewModel(viewModel: RoomListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RoomBookingViewModel::class)
    fun bindRoomBookingViewModel(viewModel: RoomBookingViewModel): ViewModel
}