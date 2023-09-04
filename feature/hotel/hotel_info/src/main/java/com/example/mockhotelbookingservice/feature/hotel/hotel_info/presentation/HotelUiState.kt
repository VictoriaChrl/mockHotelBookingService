package com.example.mockhotelbookingservice.feature.hotel.hotel_info.presentation

import com.example.mockhotelbookingservice.shared.hotel.core.domain.entity.HotelInfo

sealed interface HotelUiState {

    object Initial : HotelUiState

    object Loading : HotelUiState

    data class Complete(val info: HotelInfo) : HotelUiState

    sealed interface Error : HotelUiState {

        object NoInternet : Error

        object Unknown : Error
    }

}
