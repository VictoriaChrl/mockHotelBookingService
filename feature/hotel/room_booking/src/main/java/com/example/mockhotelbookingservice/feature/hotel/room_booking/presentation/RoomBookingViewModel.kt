package com.example.mockhotelbookingservice.feature.hotel.room_booking.presentation

import androidx.lifecycle.*
import com.example.mockhotelbookingservice.shared.hotel.core.domain.usecase.GetTourDetailsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException

class RoomBookingViewModel @Inject constructor(
    private val getTourDetailsUseCase: GetTourDetailsUseCase
) : ViewModel() {

    private val _state: MutableLiveData<RoomBookingUiState> =
        MutableLiveData(RoomBookingUiState.Initial)
    val state: LiveData<RoomBookingUiState> = _state

    fun getTourDetails() {
        _state.value = RoomBookingUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try{
                _state.postValue(RoomBookingUiState.Complete(getTourDetailsUseCase()))
            }catch (exception: Exception){
                handleException(exception)
            }
        }
    }

    private fun handleException(exception: Exception){
        when (exception) {
            is SSLHandshakeException,
            is ConnectException,
            is UnknownHostException,
            is NoRouteToHostException -> _state.postValue(RoomBookingUiState.Error.NoInternet)

            else -> _state.postValue(RoomBookingUiState.Error.Unknown)
        }
    }
}