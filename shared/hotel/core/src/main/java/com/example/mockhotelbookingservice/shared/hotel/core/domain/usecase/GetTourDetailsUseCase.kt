package com.example.mockhotelbookingservice.shared.hotel.core.domain.usecase

import com.example.mockhotelbookingservice.shared.hotel.core.domain.entity.TourDetails
import com.example.mockhotelbookingservice.shared.hotel.core.domain.repository.BookingRepository

class GetTourDetailsUseCase(private val repository: BookingRepository) {

    suspend operator fun invoke(): TourDetails = repository.getTourDetails()
}