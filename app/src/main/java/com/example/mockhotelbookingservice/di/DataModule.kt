package com.example.mockhotelbookingservice.di

import com.example.mockhotelbookingservice.shared.hotel.core.data.repository.BookingRepositoryImpl
import com.example.mockhotelbookingservice.shared.hotel.core.domain.repository.BookingRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun provideBookingRepository(bookingRepositoryImpl: BookingRepositoryImpl): BookingRepository

}

