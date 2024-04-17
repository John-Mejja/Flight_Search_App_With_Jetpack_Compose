package com.example.flightsearch.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flightsearch.data.model.AirportRoute
import com.example.flightsearch.data.model.AirportIataAndName
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightDao {

    @Query("SELECT iata_code,name FROM airport WHERE iata_code LIKE '%' || :query || '%' OR name LIKE '%' || :query || '%' ORDER BY iata_code")
    suspend fun getSearchSuggestions(query: String): List<AirportIataAndName>

    @Query("SELECT  f.id AS favoriteId,f.departure_code AS departureIataCode, a1.name AS departureName, f.destination_code AS destinationIataCode, a2.name AS destinationName FROM favorite f JOIN airport a1 ON f.departure_code = a1.iata_code JOIN airport a2 ON f.destination_code = a2.iata_code ORDER BY f.destination_code")
    fun getFavoriteRoute(): Flow<List<AirportRoute>>

    @Query("SELECT * FROM airport WHERE iata_code = :iataCode")
    suspend fun getAirport(iataCode: String): Airport

    @Query("SELECT iata_code,name FROM airport WHERE iata_code != :iataCode ORDER BY iata_code")
    suspend fun getOtherAirportsNotMatchingIataCode ( iataCode: String): List<AirportIataAndName>

    @Insert( onConflict = OnConflictStrategy.IGNORE )
    suspend fun insert(favorite: Favorite)

    @Delete
    suspend fun delete( favorite: Favorite )

}

