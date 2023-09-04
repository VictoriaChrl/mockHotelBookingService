package com.example.mockhotelbookingservice.feature.hotel.hotel_info.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.mockhotelbookingservice.feature.hotel.hotel_info.databinding.PeculiarityItemBinding

class PeculiarityAdapter :
    ListAdapter<String, PeculiarityHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeculiarityHolder {
        return PeculiarityHolder(
            PeculiarityItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PeculiarityHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {

        private val DiffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return (oldItem == newItem)
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return (oldItem == newItem)
            }
        }
    }
}



