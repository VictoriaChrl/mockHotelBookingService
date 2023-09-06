package com.example.mockhotelbookingservice.feature.hotel.room_list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.mockhotelbookingservice.feature.hotel.room_list.databinding.ItemRoomBinding
import com.example.mockhotelbookingservice.shared.hotel.core.domain.entity.Room

class RoomAdapter:
    ListAdapter<Room, RoomHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomHolder {
        return RoomHolder(
            ItemRoomBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RoomHolder, position: Int) {

        holder.bind(getItem(position))
    }

    companion object {

        private val DiffCallback = object : DiffUtil.ItemCallback<Room>() {
            override fun areItemsTheSame(oldItem: Room, newItem: Room): Boolean {
                return (oldItem == newItem)
            }

            override fun areContentsTheSame(oldItem: Room, newItem: Room): Boolean {
                return (oldItem == newItem)
            }
        }
    }
}