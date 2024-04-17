package com.example.flightsearch.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearch.data.database.Favorite
import com.example.flightsearch.data.model.AirportRoute
import com.example.flightsearch.data.model.AirportRouteDetails
import com.example.flightsearch.data.model.FavoriteDetails
import com.example.flightsearch.data.repository.FlightSearchRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeScreenViewModel(
    private val flightSearchRepository: FlightSearchRepository
) : ViewModel() {

    val favoriteRoutes = flightSearchRepository.getFavoriteRoutesStream()
        .map { it.mapFavoriteRoutesListToAirportRouteDetailsList() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            emptyList()
        )

    suspend fun removeFavoriteAirportRouteDetails(airportRouteDetails: AirportRouteDetails) {
        removeFavorite(
            Favorite(
                id = airportRouteDetails.airportRoute.favoriteId,
                departureCode = airportRouteDetails.airportRoute.departureIataCode,
                destinationCode = airportRouteDetails.airportRoute.destinationIataCode)
        )
    }
    private suspend fun removeFavorite(favorite: Favorite) {
        flightSearchRepository.deleteFavoriteRoute(favorite)
    }
}

fun AirportRoute.mapFavoriteRoutesToAirportRouteDetails(): AirportRouteDetails {
    return AirportRouteDetails(this,FavoriteDetails.IS_FAVORITE)
}
fun List<AirportRoute>.mapFavoriteRoutesListToAirportRouteDetailsList(): List<AirportRouteDetails> {
    val favoriteDetails = mutableListOf<AirportRouteDetails>()
    this.forEach { airportRoute ->
        favoriteDetails.add(airportRoute.mapFavoriteRoutesToAirportRouteDetails())
    }
    return favoriteDetails
}