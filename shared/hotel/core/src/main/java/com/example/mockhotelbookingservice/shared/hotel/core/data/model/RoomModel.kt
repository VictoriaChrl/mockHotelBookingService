package com.example.mockhotelbookingservice.shared.hotel.core.data.model

import com.google.gson.annotations.SerializedName

data class RoomModel (
    val id: Int,
    val name: String,
    val price: Int,
    @field:SerializedName("price_per")
    val pricePer: String,
    val peculiarities: List<String>,
    @field:SerializedName("image_urls")
    val imageUrls: List<String>
)