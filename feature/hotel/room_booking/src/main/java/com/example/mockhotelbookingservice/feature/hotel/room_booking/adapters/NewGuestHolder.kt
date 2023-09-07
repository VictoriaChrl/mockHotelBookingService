package com.example.mockhotelbookingservice.feature.hotel.room_booking.adapters

import android.os.Build
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.mockhotelbookingservice.feature.hotel.room_booking.databinding.ItemGuestBinding
import com.example.mockhotelbookingservice.shared.hotel.core.R.drawable

class NewGuestHolder(
    private val binding: ItemGuestBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(guest: Guest) {
        binding.apply {
            infoCustomerTitle.text = guest.title
            expandableIcon.setOnClickListener {
                if (hiddenView.visibility == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(infoCustomerCardView, AutoTransition())
                    hiddenView.visibility = View.GONE
                    expandableIcon.setImageResource(drawable.ic_arrow_up)
                } else {
                    TransitionManager.beginDelayedTransition(infoCustomerCardView, AutoTransition())
                    hiddenView.visibility = View.VISIBLE
                    expandableIcon.setImageResource(drawable.ic_arrow_down)
                }
            }
        }

    }
}

data class Guest(
    val title: String
)