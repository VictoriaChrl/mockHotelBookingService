package com.example.mockhotelbookingservice.shared.hotel.core.domain.usecase

import com.example.mockhotelbookingservice.shared.hotel.core.domain.entity.Room
import com.example.mockhotelbookingservice.shared.hotel.core.domain.repository.BookingRepository

class GetRoomListUseCase (private val repository: BookingRepository) {

    suspend operator fun invoke(): List<Room> = repository.getRoomList()
}