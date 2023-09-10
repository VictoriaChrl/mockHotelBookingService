package com.example.mockhotelbookingservice.feature.hotel.room_list.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mockhotelbookingservice.shared.hotel.core.R
import com.example.mockhotelbookingservice.feature.hotel.room_list.adapters.RoomAdapter
import com.example.mockhotelbookingservice.feature.hotel.room_list.databinding.FragmentRoomListBinding
import com.example.mockhotelbookingservice.feature.hotel.room_list.presentation.RoomListViewModel
import com.example.mockhotelbookingservice.feature.hotel.room_list.presentation.RoomUiState
import com.example.mockhotelbookingservice.shared.hotel.core.domain.entity.Room
import com.example.mockhotelbookingservice.shared.hotel.core.utils.navigateBack
import com.example.mockhotelbookingservice.shared.hotel.core.utils.navigationData
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class RoomListFragment: Fragment() {

    private var _binding: FragmentRoomListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: RoomListViewModel

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentRoomListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.apply{
            setNavigationIcon(R.drawable.arrow_back)
            setNavigationOnClickListener {
               navigateBack()
            }
            title = navigationData
        }

        viewModel = ViewModelProvider(this, viewModelFactory)[RoomListViewModel::class.java]
        viewModel.getRoomList()

        viewModel.state.observe(viewLifecycleOwner, ::processState)

    }

    private fun processState(state: RoomUiState) {
        when (state) {
            is RoomUiState.Complete -> renderCompleteState(state)
            RoomUiState.Error.NoInternet -> renderErrorNoInternetState()
            is RoomUiState.Error.Unknown -> renderErrorUnknownState()
            RoomUiState.Initial -> Unit
            RoomUiState.Loading -> renderLoadingState()
        }
    }

    private fun renderCompleteState(state: RoomUiState.Complete) {
        loadRoomList(state.info)
        binding.apply {
            progressBar.isVisible = false
            roomListLayout.isVisible = true
        }
    }

    private fun loadRoomList(list: List<Room>){
        val roomAdapter = RoomAdapter()
        binding.roomList.adapter = roomAdapter
        roomAdapter.submitList(list)
    }

    private fun renderErrorNoInternetState() {
        binding.apply {
            progressBar.isVisible = false
            errorText.isVisible = true
            errorText.text = getString(com.example.mockhotelbookingservice.shared.hotel.core.R.string.error_internet_text)
            roomListLayout.isVisible = false
        }
    }

    private fun renderErrorUnknownState() {
        binding.apply {
            progressBar.isVisible = false
            errorText.isVisible = true
            errorText.text = getString(com.example.mockhotelbookingservice.shared.hotel.core.R.string.error_unknown_text)
            roomListLayout.isVisible = false
        }
    }

    private fun renderLoadingState() {
        binding.apply {
            progressBar.isVisible = true
            errorText.isVisible = false
            roomListLayout.isVisible = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}