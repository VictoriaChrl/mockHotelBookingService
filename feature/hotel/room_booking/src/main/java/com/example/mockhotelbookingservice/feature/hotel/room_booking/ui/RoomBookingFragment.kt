package com.example.mockhotelbookingservice.feature.hotel.room_booking.ui

import android.content.Context
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mockhotelbookingservice.feature.hotel.room_booking.adapters.Guest
import com.example.mockhotelbookingservice.feature.hotel.room_booking.adapters.NewGuestAdapter
import com.example.mockhotelbookingservice.feature.hotel.room_booking.adapters.NewGuestHolder
import com.example.mockhotelbookingservice.feature.hotel.room_booking.databinding.FragmentRoomBookingBinding
import com.example.mockhotelbookingservice.feature.hotel.room_booking.presentation.RoomBookingUiState
import com.example.mockhotelbookingservice.feature.hotel.room_booking.presentation.RoomBookingViewModel
import com.example.mockhotelbookingservice.shared.hotel.core.R
import com.example.mockhotelbookingservice.shared.hotel.core.utils.afterTextChanged
import com.example.mockhotelbookingservice.shared.hotel.core.utils.formatWithSpaces
import com.example.mockhotelbookingservice.shared.hotel.core.utils.generateOrdinalNumberString
import com.example.mockhotelbookingservice.shared.hotel.core.utils.makeToast
import com.example.mockhotelbookingservice.shared.hotel.core.utils.navigate
import com.example.mockhotelbookingservice.shared.hotel.core.utils.navigateBack
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import kotlin.random.Random

class RoomBookingFragment : Fragment() {

    private var _binding: FragmentRoomBookingBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: RoomBookingViewModel

    private lateinit var guestAdapter: NewGuestAdapter

    private val guestMutableList =
        mutableListOf(Guest(id = 1, title = "Первый турист"))

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


        guestAdapter = NewGuestAdapter(guestMutableList)
        binding.guestList.adapter = guestAdapter

        binding.apply {
            toolbar.apply {
                setNavigationIcon(R.drawable.arrow_back)
                setNavigationOnClickListener {
                    navigateBack()
                }
            }


            addGuestButton.setOnClickListener {
                addGuest(guestAdapter)
            }

            editMail.afterTextChanged {
                updateUiMail()
            }

            editPhone.afterTextChanged {
                updateUiPhoneNumber()
            }

            payButton.setOnClickListener {
                if (viewModel.isMailValid(editMail.text.toString()) || viewModel.isPhoneNumberValid(
                        editPhone.text.toString()
                    )
                ) {
                    if (checkAllGuestInputs()) {
                        navigate(com.example.mockhotelbookingservice.feature.hotel.room_booking.R.id.action_roomBookingFragment_to_successPaymentFragment)
                    } else {
                        makeToast(
                            binding.root.rootView, getString(
                                com.example.mockhotelbookingservice.feature.hotel.room_booking.R.string.toast_check_edit_fragment
                            )
                        )
                    }
                } else {
                    makeToast(
                        binding.root.rootView,
                        getString(
                            com.example.mockhotelbookingservice.feature.hotel.room_booking.R.string.toast_check_edit_fragment
                        )
                    )
                }
            }

        }

        addListenerToPhoneNumberEditText()

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

            progressBar.isVisible = false
            roomBookingLayout.isVisible = true
        }
    }


    private fun renderErrorNoInternetState() {
        binding.apply {
            progressBar.isVisible = false
            errorText.isVisible = true
            errorText.text =
                getString(com.example.mockhotelbookingservice.shared.hotel.core.R.string.error_internet_text)
            roomBookingLayout.isVisible = false
        }
    }

    private fun renderErrorUnknownState() {
        binding.apply {
            progressBar.isVisible = false
            errorText.isVisible = true
            errorText.text =
                getString(com.example.mockhotelbookingservice.shared.hotel.core.R.string.error_unknown_text)
            roomBookingLayout.isVisible = false
        }
    }

    private fun renderLoadingState() {
        binding.apply {
            progressBar.isVisible = true
            errorText.isVisible = false
            roomBookingLayout.isVisible = false
        }
    }

    private fun addGuest(adapter: NewGuestAdapter) {
        val guestCount = generateOrdinalNumberString(guestMutableList.size)
        if (guestCount == null) {
            makeToast(
                binding.root.rootView,
                getString(
                    com.example.mockhotelbookingservice.feature.hotel.room_booking.R.string.toast_limit_guests_fragment
                )
            )
        } else {
            guestMutableList.add(
                Guest(
                    Random.nextLong(),
                    getString(
                        com.example.mockhotelbookingservice.feature.hotel.room_booking.R.string.new_guest_title_fragment,
                        guestCount
                    )
                )
            )
            adapter.update(guestMutableList)
        }
    }

    private fun updateUiMail() {
        binding.apply {
            if (!viewModel.isMailValid(editMail.text.toString())) {
                inputMail.boxBackgroundColor = getColor(requireContext(), R.color.edit_error_color)
            } else {
                inputMail.boxBackgroundColor =
                    getColor(requireContext(), R.color.edit_text_background_color)
            }
        }
    }

    private fun updateUiPhoneNumber() {
        binding.apply {
            if (!viewModel.isPhoneNumberValid(editPhone.text.toString())) {
                inputPhone.boxBackgroundColor =
                    getColor(requireContext(), R.color.edit_error_color)
            } else {
                inputPhone.boxBackgroundColor =
                    getColor(requireContext(), R.color.edit_text_background_color)
            }
        }
    }

    private fun checkAllGuestInputs(): Boolean {
        binding.apply {
            val isAllFilled = mutableListOf<Boolean>()
            for (i in 0 until guestAdapter.itemCount) {
                val holder = guestList.findViewHolderForAdapterPosition(i)
                if (holder is NewGuestHolder) {
                    val isFilled = holder.onItemClick()
                    isAllFilled.add(isFilled)
                }
            }
            guestAdapter.notifyDataSetChanged()
            return isAllFilled.all { it }
        }
    }

    private fun addListenerToPhoneNumberEditText() {
        binding.apply {

            editPhone.addTextChangedListener(object : PhoneNumberFormattingTextWatcher() {

                private var backspacingFlag = false

                private var editedFlag = false

                private var cursorComplement = 0

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                    cursorComplement = s?.length?.minus(editPhone.selectionStart) ?: 0

                    backspacingFlag = count > after
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    val string = s.toString()

                    val phone = string.replace("[^\\d]".toRegex(), "")

                    if (!editedFlag) {

                        if (phone.length >= 9 && !backspacingFlag) {

                            editedFlag = true

                            val ans = "+7(${phone.substring(1, 4)}) ${
                                phone.substring(
                                    4,
                                    7
                                )
                            }-${phone.substring(7, 9)}-${phone.substring(9)}"
                            editPhone.setText(ans)

                            editPhone.setSelection(editPhone.text?.length!! - cursorComplement)

                        } else if (phone.length >= 4 && !backspacingFlag) {
                            editedFlag = true
                            val ans = "+7(${phone.substring(1, 4)}) ${phone.substring(4)}"
                            editPhone.setText(ans)
                            editPhone.setSelection(editPhone.text!!.length - cursorComplement)
                        }

                    } else {
                        editedFlag = false
                    }
                }
            })

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
