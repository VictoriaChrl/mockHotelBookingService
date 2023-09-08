package com.example.mockhotelbookingservice.feature.hotel.room_booking.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mockhotelbookingservice.feature.hotel.room_booking.adapters.Guest
import com.example.mockhotelbookingservice.feature.hotel.room_booking.adapters.NewGuestAdapter
import com.example.mockhotelbookingservice.feature.hotel.room_booking.databinding.FragmentRoomBookingBinding
import com.example.mockhotelbookingservice.feature.hotel.room_booking.presentation.RoomBookingUiState
import com.example.mockhotelbookingservice.feature.hotel.room_booking.presentation.RoomBookingViewModel
import com.example.mockhotelbookingservice.feature.hotel.room_booking.util.OnEditTextChangedListener
import com.example.mockhotelbookingservice.shared.hotel.core.R
import com.example.mockhotelbookingservice.shared.hotel.core.data.formatWithSpaces
import com.example.mockhotelbookingservice.shared.hotel.core.data.generateOrdinalNumberString
import com.example.mockhotelbookingservice.shared.hotel.core.data.makeToast
import com.example.mockhotelbookingservice.shared.hotel.core.domain.entity.Room
import com.example.mockhotelbookingservice.shared.hotel.core.navigateBack
import com.example.mockhotelbookingservice.shared.hotel.core.navigationData
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class RoomBookingFragment : Fragment(), OnEditTextChangedListener {

    private var _binding: FragmentRoomBookingBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: RoomBookingViewModel

    private val guestMutableList =
        mutableListOf(Guest(title = "Первый турист"), Guest(title = "Второй турист"))

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentRoomBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[RoomBookingViewModel::class.java]
        viewModel.getTourDetails()
        viewModel.state.observe(viewLifecycleOwner, ::processState)
//        viewModel.phoneNumberSuccessEvent.observe(viewLifecycleOwner, ::processState)


        val guestAdapter = NewGuestAdapter(guestMutableList, this)
        binding.guestList.adapter = guestAdapter


        binding.apply {
            toolbar.apply {
                setNavigationIcon(R.drawable.arrow_back)
                setNavigationOnClickListener {
                    navigateBack()
                }
            }

            payButton.setOnClickListener {

            }

            addGuestButton.setOnClickListener {
                addGuest(guestAdapter)
            }
        }

    }

    private fun addGuest(adapter: NewGuestAdapter) {
        val guestCount = generateOrdinalNumberString(guestMutableList.indices.last)
        if (guestCount == null) {
            makeToast(
                binding.root.rootView,
                com.example.mockhotelbookingservice.feature.hotel.room_booking.R.string.toast_limit_guests_fragment.toString()
            )
        } else {
            guestMutableList.add(
                Guest(
                    getString(
                        com.example.mockhotelbookingservice.feature.hotel.room_booking.R.string.new_guest_title_fragment,
                        guestCount
                    )
                )
            )
            adapter.update(guestMutableList)
        }
    }

    private fun processState(state: RoomBookingUiState) {
        when (state) {
            is RoomBookingUiState.Complete -> renderCompleteState(state)
            RoomBookingUiState.Error.NoInternet -> renderErrorNoInternetState()
            is RoomBookingUiState.Error.Unknown -> renderErrorUnknownState()
            RoomBookingUiState.Initial -> Unit
            RoomBookingUiState.Loading -> renderLoadingState()
        }
    }

    private fun renderCompleteState(state: RoomBookingUiState.Complete) {
        binding.apply {
            fromText.text = state.details.departure
            countryText.text = state.details.arrivalCountry
            datesText.text = getString(
                com.example.mockhotelbookingservice.feature.hotel.room_booking.R.string.dates_text_fragment,
                state.details.tourDateStart,
                state.details.tourDateStop
            )
            numberOfNightsText.text = getString(
                com.example.mockhotelbookingservice.feature.hotel.room_booking.R.string.number_of_nights_text_fragment,
                state.details.numberOfNights.toString()
            )
            hotelNameText.text = state.details.hotelName
            roomText.text = state.details.room
            nutritionText.text = state.details.nutrition
            tourPriceText.text = getString(
                com.example.mockhotelbookingservice.feature.hotel.room_booking.R.string.price_text_fragment,
                formatWithSpaces(state.details.tourPrice.toLong())
            )
            fuelChargeText.text = getString(
                com.example.mockhotelbookingservice.feature.hotel.room_booking.R.string.price_text_fragment,
                formatWithSpaces(state.details.fuelCharge.toLong())
            )
            serviceChargeText.text = getString(
                com.example.mockhotelbookingservice.feature.hotel.room_booking.R.string.price_text_fragment,
                formatWithSpaces(state.details.serviceCharge.toLong())
            )
            state.details.apply {

                val total = formatWithSpaces((tourPrice + fuelCharge + serviceCharge).toLong())
                totalPriceText.text = getString(
                    com.example.mockhotelbookingservice.feature.hotel.room_booking.R.string.price_text_fragment,
                    total
                )

                payButton.text = getString(
                    com.example.mockhotelbookingservice.feature.hotel.room_booking.R.string.price_total_text_fragment,
                    total
                )

            }

            rating.text = state.details.hotelRating.toString()
            ratingName.text = state.details.ratingName
            hotelName.text = state.details.hotelName
            hotelAddress.text = state.details.hotelAddress

        }
    }


    private fun renderErrorNoInternetState() {

    }

    private fun renderErrorUnknownState() {

    }

    private fun renderLoadingState() {

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onTextChanged(position: Int, text: String) {
        binding.payButton.setOnClickListener {
            if(text==""){
                makeToast(binding.root.rootView, "Заполните все данные")
            }
        }
    }
}