package com.example.mockhotelbookingservice.shared.hotel.core.domain.usecase

import com.example.mockhotelbookingservice.shared.hotel.core.domain.entity.HotelInfo
import com.example.mockhotelbookingservice.shared.hotel.core.domain.repository.BookingRepository

class GetHotelInfoUseCase(private val repository: BookingRepository) {

    suspend operator fun invoke(): HotelInfo = repository.getHotelInfo()
//    try {
//        emit(Resource.Loading())
//        val items = repository.getAllProducts().toAllProductItems()
//        emit(Resource.Success(items))
//    } catch (e: HttpException) {
//        emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
//    } catch (e: IOException) {
//        emit(Resource.Error("Couldn't reach server. Check your internet connection."))
//    }
}