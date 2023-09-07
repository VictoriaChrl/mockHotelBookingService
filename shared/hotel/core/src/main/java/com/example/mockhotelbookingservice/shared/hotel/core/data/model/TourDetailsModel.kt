package com.example.mockhotelbookingservice.shared.hotel.core.data.model

import com.google.gson.annotations.SerializedName

data class TourDetailsModel(
    val id: Int,
    @field:SerializedName("hotel_name")
    val hotelName: String,
    @field:SerializedName("hotel_adress")
    val hotelAddress: String,
    @field:SerializedName("horating")
    val hotelRating: Int,
    @field:SerializedName("rating_name")
    val ratingName: String,
    val departure: String,
    @field:SerializedName("arrival_country")
    val arrivalCountry: String,
    @field:SerializedName("tour_date_start")
    val tourDateStart: String,
    @field:SerializedName("tour_date_stop")
    val tourDateStop: String,
    @field:SerializedName("number_of_nights")
    val numberOfNights: Int,
    val room: String,
    val nutrition: String,
    @field:SerializedName("tour_price")
    val tourPrice: Int,
    @field:SerializedName("fuel_charge")
    val fuelCharge: Int,
    @field:SerializedName("service_charge")
    val serviceCharge: Int
)