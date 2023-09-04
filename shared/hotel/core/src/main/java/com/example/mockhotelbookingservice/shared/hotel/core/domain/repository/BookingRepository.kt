package com.example.mockhotelbookingservice.shared.hotel.core.domain.repository

import com.example.mockhotelbookingservice.shared.hotel.core.domain.entity.HotelInfo


interface BookingRepository {

    suspend fun getHotelInfo(): HotelInfo


}