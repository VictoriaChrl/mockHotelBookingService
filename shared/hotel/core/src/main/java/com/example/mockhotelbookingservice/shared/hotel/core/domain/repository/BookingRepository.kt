package com.example.mockhotelbookingservice.shared.hotel.core.domain.repository

import com.example.mockhotelbookingservice.shared.hotel.core.domain.entity.HotelInfo
import com.example.mockhotelbookingservice.shared.hotel.core.domain.entity.Room
import com.example.mockhotelbookingservice.shared.hotel.core.domain.entity.TourDetails


interface BookingRepository {
    suspend fun getHotelInfo(): HotelInfo
    suspend fun getRoomList(): List<Room>
    suspend fun getTourDetails(): TourDetails
}