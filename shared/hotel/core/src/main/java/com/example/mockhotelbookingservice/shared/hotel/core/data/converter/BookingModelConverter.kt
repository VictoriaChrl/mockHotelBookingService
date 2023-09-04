package com.example.mockhotelbookingservice.shared.hotel.core.data.converter

import com.example.mockhotelbookingservice.shared.hotel.core.data.model.AboutTheHotelModel
import com.example.mockhotelbookingservice.shared.hotel.core.data.model.HotelInfoModel
import com.example.mockhotelbookingservice.shared.hotel.core.domain.entity.AboutTheHotel
import com.example.mockhotelbookingservice.shared.hotel.core.domain.entity.HotelInfo
import javax.inject.Inject

class BookingModelConverter @Inject constructor() {

    fun convertModelToHotelInfo(from: HotelInfoModel): HotelInfo =
        HotelInfo(
            id = from.id,
            name = from.name,
            address = from.address,
            minimalPrice = from.minimalPrice,
            priceForIt = from.priceForIt,
            rating = from.rating,
            ratingName = from.ratingName,
            imageUrls = from.imageUrls,
            aboutTheHotel = convertModelToAboutTheHotel(from.aboutTheHotel)
        )

    private fun convertModelToAboutTheHotel(from: AboutTheHotelModel): AboutTheHotel =
        AboutTheHotel(
            description = from.description,
            peculiarities = from.peculiarities
        )

}