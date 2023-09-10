package com.example.mockhotelbookingservice.feature.hotel.hotel_info.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mockhotelbookingservice.feature.hotel.hotel_info.R
import com.example.mockhotelbookingservice.feature.hotel.hotel_info.adapters.PeculiarityAdapter
import com.example.mockhotelbookingservice.feature.hotel.hotel_info.adapters.PhotoAdapter
import com.example.mockhotelbookingservice.feature.hotel.hotel_info.databinding.FragmentHotelBinding
import com.example.mockhotelbookingservice.feature.hotel.hotel_info.presentation.HotelInfoViewModel
import com.example.mockhotelbookingservice.feature.hotel.hotel_info.presentation.HotelUiState
import com.example.mockhotelbookingservice.shared.hotel.core.utils.formatWithSpaces
import com.example.mockhotelbookingservice.shared.hotel.core.utils.navigate
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class HotelFragment : Fragment() {

    private var _binding: FragmentHotelBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: HotelInfoViewModel

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentHotelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[HotelInfoViewModel::class.java]
        viewModel.getHotelInfo()

        binding.pickRoomButton.setOnClickListener {
            navigate(R.id.action_hotelFragment_to_roomListFragment2, data = binding.hotelName.text.toString())
        }

        viewModel.state.observe(viewLifecycleOwner, ::processState)

    }



    private fun processState(state: HotelUiState) {
        when (state) {
            is HotelUiState.Complete -> renderCompleteState(state)
            HotelUiState.Error.NoInternet -> renderErrorNoInternetState()
            is HotelUiState.Error.Unknown -> renderErrorUnknownState()
            HotelUiState.Initial -> Unit
            HotelUiState.Loading -> renderLoadingState()
        }
    }
    private fun renderCompleteState(state: HotelUiState.Complete) {
        loadPeculiarities(state.info.aboutTheHotel.peculiarities)

        binding.apply {
            viewPager.adapter = PhotoAdapter(state.info.imageUrls,requireContext())
            TabLayoutMediator(tabLayout,viewPager){_,_->}.attach()

            rating.text = state.info.rating.toString()
            ratingName.text = state.info.ratingName
            hotelName.text = state.info.name
            hotelAddress.text = state.info.address
            hotelPrice.text = formatWithSpaces(state.info.minimalPrice.toLong())
            hotelPriceSum.text = state.info.priceForIt
            aboutHotelContentText.text = state.info.aboutTheHotel.description

            progressBar.isVisible = false
            hotelLayout.isVisible = true
        }
    }
    private fun loadPeculiarities(list: List<String>){
        val peculiarities = PeculiarityAdapter()

        val flexLayoutManager =  FlexboxLayoutManager(requireContext())
        flexLayoutManager.apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
            alignItems = AlignItems.FLEX_START
            flexWrap = FlexWrap.WRAP
        }

        binding.peculiarityList.apply{
            adapter = peculiarities
            layoutManager = flexLayoutManager
        }

        peculiarities.submitList(list)
    }

    private fun renderErrorNoInternetState() {
        binding.apply {
            progressBar.isVisible = false
            errorText.isVisible = true
            errorText.text = getString(com.example.mockhotelbookingservice.shared.hotel.core.R.string.error_internet_text)
            hotelLayout.isVisible = false
        }
    }

    private fun renderErrorUnknownState() {
        binding.apply {
            progressBar.isVisible = false
            errorText.isVisible = true
            errorText.text = getString(com.example.mockhotelbookingservice.shared.hotel.core.R.string.error_unknown_text)
            hotelLayout.isVisible = false
        }
    }

    private fun renderLoadingState() {
        binding.apply {
            progressBar.isVisible = true
            errorText.isVisible = false
            hotelLayout.isVisible = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}