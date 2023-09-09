package com.example.mockhotelbookingservice.feature.hotel.room_booking.ui

import android.content.Context
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mockhotelbookingservice.feature.hotel.room_booking.adapters.Guest
import com.example.mockhotelbookingservice.feature.hotel.room_booking.adapters.NewGuestAdapter
import com.example.mockhotelbookingservice.feature.hotel.room_booking.adapters.NewGuestHolder
import com.example.mockhotelbookingservice.feature.hotel.room_booking.databinding.FragmentRoomBookingBinding
import com.example.mockhotelbookingservice.feature.hotel.room_booking.presentation.RoomBookingUiState
import com.example.mockhotelbookingservice.feature.hotel.room_booking.presentation.RoomBookingViewModel
import com.example.mockhotelbookingservice.feature.hotel.room_booking.util.OnEditBackgroundChangedListener
import com.example.mockhotelbookingservice.shared.hotel.core.R
import com.example.mockhotelbookingservice.shared.hotel.core.data.afterTextChanged
import com.example.mockhotelbookingservice.shared.hotel.core.data.formatWithSpaces
import com.example.mockhotelbookingservice.shared.hotel.core.data.generateOrdinalNumberString
import com.example.mockhotelbookingservice.shared.hotel.core.data.makeToast
import com.example.mockhotelbookingservice.shared.hotel.core.navigate
import com.example.mockhotelbookingservice.shared.hotel.core.navigateBack
import dagger.android.support.AndroidSupportInjection
import java.util.Locale
import javax.inject.Inject
import kotlin.random.Random

class RoomBookingFragment : Fragment() {

    private var _binding: FragmentRoomBookingBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: RoomBookingViewModel

    lateinit private var guestAdapter: NewGuestAdapter

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

        addListenerToPhoneNumberEditText()
//        binding.editPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher("RU"))

        binding.apply {
            toolbar.apply {
                setNavigationIcon(R.drawable.arrow_back)
                setNavigationOnClickListener {
                    navigateBack()
                }
            }

            payButton.setOnClickListener {
                if (viewModel.isMailValid(editMail.text.toString()) || viewModel.isPhoneNumberValid(
                        editPhone.text.toString()
                    )
                ) {
                    val isAllFilled = mutableListOf<Boolean>()
                    for (i in 0 until guestAdapter.itemCount) {
                        val holder = guestList.findViewHolderForAdapterPosition(i)
                        if (holder is NewGuestHolder) {
                            val isFilled = holder.onItemClick()
                            isAllFilled.add(isFilled)
                        }
                    }
                    guestAdapter.notifyDataSetChanged()
                    val allTrue = isAllFilled.all { it }
                    if (allTrue) {
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

            addGuestButton.setOnClickListener {
                addGuest(guestAdapter)
            }

            editMail.afterTextChanged {
                updateUiMail()
            }

            editPhone.afterTextChanged {
                updateUiPhoneNumber()
            }
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

    private fun addListenerToPhoneNumberEditText() {
        binding.apply {
            editPhone.addTextChangedListener(object : PhoneNumberFormattingTextWatcher() {
                // we need to know if the user is erasing or inputing some new character
                private var backspacingFlag = false
                // we need to block the :afterTextChanges method to be called again after we just replaced the EditText text
                private var editedFlag = false
                // we need to mark the cursor position and restore it after the edition
                private var cursorComplement = 0

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // we store the cursor local relative to the end of the string in the EditText before the edition
                    cursorComplement = s?.length?.minus(editPhone.selectionStart) ?: 0
                    // we check if the user is inputting or erasing a character
                    backspacingFlag = count > after
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // nothing to do here =D
                }

                override fun afterTextChanged(s: Editable?) {
                    val string = s.toString()
                    // what matters are the phone digits beneath the mask, so we always work with a raw string with only digits
                    val phone = string.replace("[^\\d]".toRegex(), "")

                    // if the text was just edited, :afterTextChanged is called another time... so we need to verify the flag of edition
                    // if the flag is false, this is an original user-typed entry. so we go on and do some magic
                    if (!editedFlag) {
                        // we start verifying the worst case, many characters mask need to be added
                        // example: 999999999 <- 6+ digits already typed
                        // masked: (999) 999-999
                        if (phone.length >= 9 && !backspacingFlag) {
                            // we will edit. next call on this textWatcher will be ignored
                            editedFlag = true
                            // here is the core. we substring the raw digits and add the mask as convenient
                            val ans = "+7(${phone.substring(1, 4)}) ${phone.substring(4, 7)}-${phone.substring(7, 9)}-${phone.substring(9)}"
                            editPhone.setText(ans)
                            // we deliver the cursor to its original position relative to the end of the string
                            editPhone.setSelection(editPhone.text?.length!! - cursorComplement)

                            // we end at the most simple case, when just one character mask is needed
                            // example: 99999 <- 3+ digits already typed
                            // masked: (999) 99
                        } else if (phone.length >= 4 && !backspacingFlag) {
                            editedFlag = true
                            val ans = "+7(${phone.substring(1, 4)}) ${phone.substring(4)}"
                            editPhone.setText(ans)
                            editPhone.setSelection(editPhone.text!!.length - cursorComplement)
                        }
                        // We just edited the field, ignoring this cycle of the watcher and getting ready for the next
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
