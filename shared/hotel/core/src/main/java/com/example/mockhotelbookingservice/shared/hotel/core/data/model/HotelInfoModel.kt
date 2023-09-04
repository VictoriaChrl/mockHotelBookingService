package com.example.mockhotelbookingservice.shared.hotel.core.data.model

import com.google.gson.annotations.SerializedName

data class HotelInfoModel (
    val id: Int,
    val name: String,
    @field:SerializedName("adress")
    val address: String,
    @field:SerializedName("minimal_price")
    val minimalPrice: Int,
    @field:SerializedName("price_for_it")
    val priceForIt: String,
    val rating: Int,
    @field:SerializedName("rating_name")
    val ratingName: String,
    @field:SerializedName("image_urls")
    val imageUrls: List<String>,
    @field:SerializedName("about_the_hotel")
    val aboutTheHotel: AboutTheHotelModel
)