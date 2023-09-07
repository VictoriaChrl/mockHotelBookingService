package com.example.mockhotelbookingservice.feature.hotel.room_booking.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.mockhotelbookingservice.feature.hotel.room_booking.databinding.ItemGuestBinding

class NewGuestAdapter :
    ListAdapter<Guest, NewGuestHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewGuestHolder {
        return NewGuestHolder(
            ItemGuestBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewGuestHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {

        private val DiffCallback = object : DiffUtil.ItemCallback<Guest>() {
            override fun areItemsTheSame(oldItem: Guest, newItem: Guest): Boolean {
                return (oldItem == newItem)
            }

            override fun areContentsTheSame(oldItem: Guest, newItem: Guest): Boolean {
                return (oldItem == newItem)
            }
        }
    }
}



