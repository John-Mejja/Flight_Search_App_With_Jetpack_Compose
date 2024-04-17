package com.example.flightsearch.data.model

import androidx.room.ColumnInfo

data class AirportIataAndName(
    @ColumnInfo(name = "iata_code")
    val iataCode: String = "",
    val name: String = ""
)

data class AirportRoute(
    val favoriteId : Int = 0,
    val departureIataCode: String = "",
    val departureName: String = "",
    val destinationIataCode: String = "",
    val destinationName: String = ""
)

data class AirportRouteDetails(
    val airportRoute: AirportRoute = AirportRoute(),
    var favoriteDetails: FavoriteDetails = FavoriteDetails.IS_NOT_FAVORITE
)
enum class FavoriteDetails {
    IS_FAVORITE,
    IS_NOT_FAVORITE
}
