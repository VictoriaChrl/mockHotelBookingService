package com.example.mockhotelbookingservice.shared.hotel.core.data.api

import com.example.mockhotelbookingservice.shared.hotel.core.data.model.HotelInfoModel
import retrofit2.http.*

interface BookingApi {

    @GET("35e0d18e-2521-4f1b-a575-f0fe366f66e3")
    suspend fun getHotelInfo(): HotelInfoModel
}
