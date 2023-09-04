package com.example.mockhotelbookingservice.shared.hotel.core.domain.entity


data class HotelInfo(
    val id: Int,
    val name: String,
    val address: String,
    val minimalPrice: Int,
    val priceForIt: String,
    val rating: Int,
    val ratingName: String,
    val imageUrls: List<String>,
    val aboutTheHotel: AboutTheHotel
)