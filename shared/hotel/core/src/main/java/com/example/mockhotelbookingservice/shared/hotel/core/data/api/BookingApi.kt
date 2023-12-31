package com.example.mockhotelbookingservice.shared.hotel.core.data.api

import com.example.mockhotelbookingservice.shared.hotel.core.data.model.HotelInfoModel
import com.example.mockhotelbookingservice.shared.hotel.core.data.model.RoomModel
import com.example.mockhotelbookingservice.shared.hotel.core.data.model.TourDetailsModel
import retrofit2.http.*

interface BookingApi {

    @GET("35e0d18e-2521-4f1b-a575-f0fe366f66e3")
    suspend fun getHotelInfo(): HotelInfoModel

    @GET("f9a38183-6f95-43aa-853a-9c83cbb05ecd")
    suspend fun getRoomList(): Rooms

    @GET("e8868481-743f-4eb2-a0d7-2bc4012275c8")
    suspend fun getTourDetails(): TourDetailsModel
}

data class Rooms(val rooms: List<RoomModel>)
