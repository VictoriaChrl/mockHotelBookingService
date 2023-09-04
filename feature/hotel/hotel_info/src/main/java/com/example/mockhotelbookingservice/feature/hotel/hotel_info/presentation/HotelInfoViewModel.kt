package com.example.mockhotelbookingservice.feature.hotel.hotel_info.presentation

import androidx.lifecycle.*
import com.example.mockhotelbookingservice.shared.hotel.core.domain.usecase.GetHotelInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException

class HotelInfoViewModel @Inject constructor(
    private val getHotelInfoUseCase: GetHotelInfoUseCase
) : ViewModel() {

    private val _state: MutableLiveData<HotelUiState> =
        MutableLiveData(HotelUiState.Initial)
    val state: LiveData<HotelUiState> = _state

    fun getHotelInfo() {
        _state.value = HotelUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try{
                _state.postValue(HotelUiState.Complete(getHotelInfoUseCase()))
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
            is NoRouteToHostException -> _state.postValue(HotelUiState.Error.NoInternet)

            else -> _state.postValue(HotelUiState.Error.Unknown)
        }
    }
}