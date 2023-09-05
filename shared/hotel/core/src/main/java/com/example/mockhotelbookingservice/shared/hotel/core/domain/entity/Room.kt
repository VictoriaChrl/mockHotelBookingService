package com.example.mockhotelbookingservice.shared.hotel.core.domain.entity

data class Room(
    val id: Int,
    val name: String,
    val price: Int,
    val pricePer: String,
    val peculiarities: List<String>,
    val imageUrls: List<String>
)