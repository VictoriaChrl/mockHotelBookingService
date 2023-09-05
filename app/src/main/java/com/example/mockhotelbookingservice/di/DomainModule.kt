package com.example.mockhotelbookingservice.di

import com.example.mockhotelbookingservice.shared.hotel.core.domain.repository.BookingRepository
import com.example.mockhotelbookingservice.shared.hotel.core.domain.usecase.GetHotelInfoUseCase
import com.example.mockhotelbookingservice.shared.hotel.core.domain.usecase.GetRoomListUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideGetHotelInfoUseCase(repository: BookingRepository): GetHotelInfoUseCase {
        return GetHotelInfoUseCase(repository)
    }

    @Provides
    fun provideGetRoomListUseCase(repository: BookingRepository): GetRoomListUseCase {
        return GetRoomListUseCase(repository)
    }
}