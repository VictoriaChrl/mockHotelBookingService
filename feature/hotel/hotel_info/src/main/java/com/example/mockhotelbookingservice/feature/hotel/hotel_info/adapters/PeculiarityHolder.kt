package com.example.mockhotelbookingservice.feature.hotel.hotel_info.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.mockhotelbookingservice.feature.hotel.hotel_info.databinding.PeculiarityItemBinding

class PeculiarityHolder(
    private val binding: PeculiarityItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(peculiarityInfo: String) {
        binding.peculiarity.text = peculiarityInfo

    }
}