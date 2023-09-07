package com.example.mockhotelbookingservice.shared.hotel.core.domain.entity

import com.google.gson.annotations.SerializedName

data class TourDetails(
    val id: Int,
    val hotelName: String,
    val hotelAddress: String,
    val hotelRating: Int,
    val ratingName: String,
    val departure: String,
    val arrivalCountry: String,
    val tourDateStart: String,
    val tourDateStop: String,
    val numberOfNights: Int,
    val room: String,
    val nutrition: String,
    val tourPrice: Int,
    val fuelCharge: Int,
    val serviceCharge: Int
)