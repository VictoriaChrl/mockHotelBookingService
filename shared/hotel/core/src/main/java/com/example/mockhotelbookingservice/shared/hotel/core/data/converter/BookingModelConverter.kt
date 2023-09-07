package com.example.mockhotelbookingservice.shared.hotel.core.data.converter

import com.example.mockhotelbookingservice.shared.hotel.core.data.model.AboutTheHotelModel
import com.example.mockhotelbookingservice.shared.hotel.core.data.model.HotelInfoModel
import com.example.mockhotelbookingservice.shared.hotel.core.data.model.RoomModel
import com.example.mockhotelbookingservice.shared.hotel.core.data.model.TourDetailsModel
import com.example.mockhotelbookingservice.shared.hotel.core.domain.entity.AboutTheHotel
import com.example.mockhotelbookingservice.shared.hotel.core.domain.entity.HotelInfo
import com.example.mockhotelbookingservice.shared.hotel.core.domain.entity.Room
import com.example.mockhotelbookingservice.shared.hotel.core.domain.entity.TourDetails
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

    fun convertModelToTourDetails(from: TourDetailsModel): TourDetails =
        TourDetails(
            id = from.id,
            hotelName = from.hotelName,
            hotelAddress = from.hotelAddress,
            hotelRating = from.hotelRating,
            ratingName = from.ratingName,
            departure = from.departure,
            arrivalCountry = from.arrivalCountry,
            tourDateStart = from.tourDateStart,
            tourDateStop = from.tourDateStop,
            numberOfNights = from.numberOfNights,
            room = from.room,
            nutrition = from.nutrition,
            tourPrice = from.tourPrice,
            fuelCharge = from.fuelCharge,
            serviceCharge =from.serviceCharge
        )

    fun convertModelToRoom(from: RoomModel): Room =
        Room(
            id = from.id,
            name = from.name,
            price = from.price,
            pricePer = from.pricePer,
            peculiarities = from.peculiarities,
            imageUrls = from.imageUrls
        )
}