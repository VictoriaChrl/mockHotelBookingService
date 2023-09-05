package com.example.mockhotelbookingservice.shared.hotel.core.domain.usecase

import com.example.mockhotelbookingservice.shared.hotel.core.domain.entity.HotelInfo
import com.example.mockhotelbookingservice.shared.hotel.core.domain.repository.BookingRepository

class GetHotelInfoUseCase(private val repository: BookingRepository) {

    suspend operator fun invoke(): HotelInfo = repository.getHotelInfo()
}