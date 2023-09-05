package com.example.mockhotelbookingservice.feature.hotel.room_list.presentation

import com.example.mockhotelbookingservice.shared.hotel.core.domain.entity.Room

sealed interface RoomUiState {

    object Initial : RoomUiState

    object Loading : RoomUiState

    data class Complete(val info: List<Room>) : RoomUiState

    sealed interface Error : RoomUiState {

        object NoInternet : Error

        object Unknown : Error
    }

}
