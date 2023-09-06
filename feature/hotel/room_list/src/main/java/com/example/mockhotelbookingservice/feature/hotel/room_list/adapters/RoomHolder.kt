package com.example.mockhotelbookingservice.feature.hotel.room_list.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.mockhotelbookingservice.feature.hotel.room_list.databinding.ItemRoomBinding
import com.example.mockhotelbookingservice.shared.hotel.core.domain.entity.Room
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.tabs.TabLayoutMediator


class RoomHolder (
    private val binding: ItemRoomBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val peculiarities = PeculiarityAdapter()

    fun bind(room: Room) {

        val flexLayoutManager =  FlexboxLayoutManager(itemView.context)
        flexLayoutManager.flexDirection = FlexDirection.ROW
        flexLayoutManager.justifyContent = JustifyContent.FLEX_START
        flexLayoutManager.alignItems = AlignItems.FLEX_START
        flexLayoutManager.flexWrap = FlexWrap.WRAP

        binding.apply {
            roomName.text = room.name
            roomPrice.text = room.price.toString()
            roomPriceSum.text = room.pricePer
            peculiarityList.apply{
                adapter = peculiarities
                layoutManager = flexLayoutManager
            }
            peculiarities.submitList(room.peculiarities)
            viewPager.adapter = PhotoAdapter(room.imageUrls,itemView.context)
            TabLayoutMediator(tabLayout,viewPager){_,_->}.attach()
        }


    }
}