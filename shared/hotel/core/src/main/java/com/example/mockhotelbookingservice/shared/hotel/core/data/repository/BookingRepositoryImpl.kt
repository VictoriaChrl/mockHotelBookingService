package com.example.mockhotelbookingservice.shared.hotel.core.data.repository

import com.example.mockhotelbookingservice.shared.hotel.core.data.api.BookingApi
import com.example.mockhotelbookingservice.shared.hotel.core.data.converter.BookingModelConverter
import com.example.mockhotelbookingservice.shared.hotel.core.domain.entity.HotelInfo
import com.example.mockhotelbookingservice.shared.hotel.core.domain.entity.Room
import com.example.mockhotelbookingservice.shared.hotel.core.domain.repository.BookingRepository
import javax.inject.Inject

class BookingRepositoryImpl @Inject constructor(
    private val bookingApi: BookingApi,
    private val converter: BookingModelConverter
) : BookingRepository {

    override suspend fun getHotelInfo(): HotelInfo {
        return converter.convertModelToHotelInfo(bookingApi.getHotelInfo())
    }


    override suspend fun getRoomList(): List<Room> {
        return bookingApi.getRoomList().map{
            rooms -> converter.convertModelToRoom(rooms)
        }
    }

}