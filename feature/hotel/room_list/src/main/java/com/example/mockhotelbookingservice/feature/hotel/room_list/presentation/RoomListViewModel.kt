package com.example.mockhotelbookingservice.feature.hotel.room_list.presentation

import android.util.Log
import androidx.lifecycle.*
import com.example.mockhotelbookingservice.shared.hotel.core.domain.usecase.GetRoomListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException

class RoomListViewModel @Inject constructor(
    private val getRoomListUseCase: GetRoomListUseCase
) : ViewModel() {

    private val _state: MutableLiveData<RoomUiState> =
        MutableLiveData(RoomUiState.Initial)
    val state: LiveData<RoomUiState> = _state

    fun getRoomList() {
        _state.value = RoomUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try{
                _state.postValue(RoomUiState.Complete(getRoomListUseCase()))
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
            is NoRouteToHostException -> _state.postValue(RoomUiState.Error.NoInternet)

            else -> _state.postValue(RoomUiState.Error.Unknown)
        }
    }
}