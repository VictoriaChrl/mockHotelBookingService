package com.example.mockhotelbookingservice.feature.hotel.room_booking.presentation

import com.example.mockhotelbookingservice.shared.hotel.core.domain.entity.TourDetails

sealed interface RoomBookingUiState {

    object Initial : RoomBookingUiState

    object Loading : RoomBookingUiState

    data class Complete(val details: TourDetails) : RoomBookingUiState

    sealed interface Error : RoomBookingUiState {

        object NoInternet : Error

        object Unknown : Error
    }

}
