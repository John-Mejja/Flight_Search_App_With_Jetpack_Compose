package com.example.flightsearch.data.repository

import com.example.flightsearch.data.database.Airport
import com.example.flightsearch.data.database.Favorite
import com.example.flightsearch.data.model.AirportRoute
import com.example.flightsearch.data.model.AirportIataAndName
import kotlinx.coroutines.flow.Flow

interface FlightSearchRepository {
    suspend fun getAutoCompletionSuggestions(query: String): List<AirportIataAndName>

    fun getFavoriteRoutesStream(): Flow<List<AirportRoute>>

    suspend fun getAirport(iataCode: String): Airport

    suspend fun getOtherAirportsIataCodeAndName(iataCode: String): List<AirportIataAndName>

    suspend fun insertFavoriteRoute( favorite: Favorite )

    suspend fun deleteFavoriteRoute( favorite: Favorite )

}