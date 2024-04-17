package com.example.flightsearch.ui.screen.query

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearch.data.database.Airport
import com.example.flightsearch.data.database.Favorite
import com.example.flightsearch.data.model.AirportIataAndName
import com.example.flightsearch.data.model.AirportRoute
import com.example.flightsearch.data.model.AirportRouteDetails
import com.example.flightsearch.data.model.FavoriteDetails
import com.example.flightsearch.data.repository.FlightSearchRepository
import com.example.flightsearch.data.repository.UserPreferenceRepository
import com.example.flightsearch.ui.screen.components.SearchBarUiState
import com.example.flightsearch.ui.screen.home.mapFavoriteRoutesListToAirportRouteDetailsList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QueryViewModel(
    private val flightSearchRepository: FlightSearchRepository,
    private val userPreferenceRepository: UserPreferenceRepository
): ViewModel() {

    private val _queryUiState = MutableStateFlow(QueryUiState())
    val queryUiState = _queryUiState.asStateFlow()

    val favoriteRoutes = flightSearchRepository.getFavoriteRoutesStream()
        .map { it.mapFavoriteRoutesListToAirportRouteDetailsList() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            emptyList()
        )

    init {
        viewModelScope.launch {
            userPreferenceRepository.flightQuery.collect {query ->
                updateSearchBarQuery(query)
                if (query.isNotBlank()){ fetchRoutes(query) }
            }
        }
    }

    fun fetchRoutes(iataCode: String) {
        viewModelScope.launch {
            userPreferenceRepository.saveFlightQueryPreference(iataCode)
            val selectedAirport = flightSearchRepository.getAirport(iataCode)
            val otherAirports = flightSearchRepository.getOtherAirportsIataCodeAndName(iataCode)
            val routesDetails = otherAirports.mapAirportIataCodeAndNameListToAirportRouteDetailsList(selectedAirport)
            updateRoutes(routesDetails)
            updateSelectedAirport(selectedAirport)
        }
    }

    suspend fun updateAddFavoriteAirportDetailsRoute(airportRouteDetails: AirportRouteDetails) {
        if (airportRouteDetails.favoriteDetails == FavoriteDetails.IS_NOT_FAVORITE) {
            addFavorite(
                Favorite(
                    departureCode = airportRouteDetails.airportRoute.departureIataCode,
                    destinationCode = airportRouteDetails.airportRoute.destinationIataCode)
            )
        }

    }
    private suspend fun addFavorite(favorite: Favorite) {
        flightSearchRepository.insertFavoriteRoute(favorite)
    }

    private fun updateRoutes(routesDetails: List<AirportRouteDetails>) {
        _queryUiState.update { currentUiState ->
            currentUiState.copy(
                routesDetails = routesDetails
            )
        }
    }
    private fun updateSelectedAirport(selectedAirport: Airport) {
        _queryUiState.update { currentUiState ->
            currentUiState.copy(
                selectedAirport = selectedAirport
            )
        }
    }
    suspend fun fetchAutoCompletionSuggestions(query: String){
        val searchSuggestions = flightSearchRepository.getAutoCompletionSuggestions(query)
        updateSearchSuggestion(searchSuggestions)
    }

    //Search bar functions
    private fun updateSearchSuggestion(searchSuggestions: List<AirportIataAndName>) {
        _queryUiState.update { currentUiState ->
            currentUiState.copy(
                searchBarUiState = currentUiState.searchBarUiState.copy(
                    searchSuggestions = searchSuggestions
                )
            )
        }
    }
    fun updateSearchBarQuery(query: String) {
        _queryUiState.update { currentUiState ->
            currentUiState.copy(
                searchBarUiState = currentUiState.searchBarUiState.copy(
                    query = query
                )
            )
        }
    }
    fun updateSearchActive(active: Boolean) {
        _queryUiState.update { currentUiState ->
            currentUiState.copy(
                searchBarUiState = currentUiState.searchBarUiState.copy(
                    active = active
                )
            )
        }
    }
    fun resetSearchState(active: Boolean = false) {
        _queryUiState.update { currentUiState ->
            currentUiState.copy(
                searchBarUiState = currentUiState.searchBarUiState.copy(
                    active = active,
                    query = ""
                )
            )
        }
    }
    fun clearSearch() {
        resetSearchState(queryUiState.value.searchBarUiState.active)
    }
}

data class QueryUiState(
    val searchBarUiState: SearchBarUiState = SearchBarUiState(),
    val routesDetails: List<AirportRouteDetails> = emptyList(),
    val selectedAirport: Airport = Airport(0,"","",0)
)

fun AirportIataAndName.mapAirportIataAndNameToAirportRouteDetails(selectedAirport: Airport): AirportRouteDetails {
    val departureIataCode = selectedAirport.iataCode
    val departureName = selectedAirport.name
    val destinationIataCode = this.iataCode
    val destinationName = this.name
    val airportRoute = AirportRoute(0,departureIataCode, departureName, destinationIataCode, destinationName)
    return AirportRouteDetails(airportRoute,FavoriteDetails.IS_NOT_FAVORITE)
}
fun List<AirportIataAndName>.mapAirportIataCodeAndNameListToAirportRouteDetailsList(selectedAirport: Airport) : List<AirportRouteDetails> {
    val routes = mutableListOf<AirportRouteDetails>()
    this.forEach { airportIataAndName ->
        routes.add(airportIataAndName.mapAirportIataAndNameToAirportRouteDetails(selectedAirport))
    }
    return routes.toList()
}