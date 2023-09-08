package com.example.mockhotelbookingservice.feature.hotel.room_booking.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mockhotelbookingservice.feature.hotel.room_booking.databinding.FragmentSuccessPaymentBinding
import com.example.mockhotelbookingservice.shared.hotel.core.R
import com.example.mockhotelbookingservice.shared.hotel.core.navigateBack
import dagger.android.support.AndroidSupportInjection
import kotlin.random.Random

class SuccessPaymentFragment : Fragment() {

    private var _binding: FragmentSuccessPaymentBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentSuccessPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            val randomOrder = Random.nextLong().toString()
            successPaymentText.text = getString(
                com.example.mockhotelbookingservice.feature.hotel.room_booking.R.string.success_payment_text_fragment,
                randomOrder
            )

            toolbar.apply {
                setNavigationIcon(R.drawable.arrow_back)
                setNavigationOnClickListener {
                    navigateBack()
                }
            }

            successPaymentButton.setOnClickListener {
                navigateBack()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}