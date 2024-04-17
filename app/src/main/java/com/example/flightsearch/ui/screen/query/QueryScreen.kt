package com.example.flightsearch.ui.screen.query

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.R
import com.example.flightsearch.data.database.Airport
import com.example.flightsearch.data.model.AirportRoute
import com.example.flightsearch.data.model.AirportRouteDetails
import com.example.flightsearch.data.model.FavoriteDetails
import com.example.flightsearch.ui.screen.AppViewModelProvider
import com.example.flightsearch.ui.screen.components.FlightSearchBar
import com.example.flightsearch.ui.screen.components.NothingToShow
import com.example.flightsearch.ui.screen.components.SearchBarUiState
import com.example.flightsearch.ui.screen.home.FlightRoute
import com.example.flightsearch.ui.screen.navigation.Navigation
import kotlinx.coroutines.launch

object QueryScreen : Navigation{
    override val route: String
        get() = "Query_screen_route"
    override val title: Int
        get() = R.string.query_title
}
@Composable
fun QueryScreen(
    modifier: Modifier = Modifier,
    queryViewModel: QueryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val queryUiState by queryViewModel.queryUiState.collectAsState()

    val favoriteRoutes by queryViewModel.favoriteRoutes.collectAsState()

    val coroutine = rememberCoroutineScope()
    LaunchedEffect(key1 = queryUiState.searchBarUiState.query) {
        queryViewModel.fetchAutoCompletionSuggestions(queryUiState.searchBarUiState.query)
    }

    QueryDetails(
        searchBarUiState = queryUiState.searchBarUiState,
        onQueryChange = queryViewModel::updateSearchBarQuery,
        onActiveChange = queryViewModel::updateSearchActive,
        onSearch = {
            if (queryUiState.searchBarUiState.searchSuggestions.isNotEmpty()){
                queryViewModel.fetchRoutes(queryUiState.searchBarUiState.searchSuggestions[0].iataCode)
                queryViewModel.updateSearchBarQuery(queryUiState.searchBarUiState.searchSuggestions[0].iataCode)
                queryViewModel.updateSearchActive(false)
            } else {
                queryViewModel.updateSearchActive(false)
            }
        },
        onSearchSuggestionClicked = {
            queryViewModel.updateSearchBarQuery(it)
            queryViewModel.fetchRoutes(it)
            queryViewModel.updateSearchActive(false)
        },
        onSearchBarBackClicked = queryViewModel::resetSearchState,
        onSearchBarClearClicked = queryViewModel::clearSearch,
        routesDetails = queryUiState.routesDetails,
        selectedAirport = queryUiState.selectedAirport,
        onFavoriteIconClicked = {
            coroutine.launch {
                queryViewModel.updateAddFavoriteAirportDetailsRoute(it)
            }
        },
        favoriteRoutes = favoriteRoutes,
        modifier = modifier
    )
}
@Composable
fun QueryDetails(
    modifier: Modifier = Modifier,
    searchBarUiState: SearchBarUiState,
    onQueryChange :(String) -> Unit,
    onActiveChange :(Boolean) -> Unit,
    onSearch :(String) -> Unit,
    onSearchSuggestionClicked :(String) -> Unit,
    onSearchBarBackClicked :() -> Unit,
    onSearchBarClearClicked :() -> Unit,
    routesDetails: List<AirportRouteDetails>,
    selectedAirport: Airport,
    onFavoriteIconClicked: (AirportRouteDetails) -> Unit,
    favoriteRoutes: List<AirportRouteDetails>
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        FlightSearchBar(
            searchBarUiState = searchBarUiState,
            onQueryChange = onQueryChange,
            onActiveChange = onActiveChange,
            onSearch = onSearch,
            onSearchSuggestionClicked = onSearchSuggestionClicked,
            onSearchBarBackClicked = onSearchBarBackClicked,
            onSearchBarClearClicked = onSearchBarClearClicked,
            modifier = Modifier
                .fillMaxWidth()
        )
        QueryResultsList(
            routesDetails = routesDetails,
            selectedAirport = selectedAirport,
            onFavoriteIconClicked = onFavoriteIconClicked,
            favoriteRoutes = favoriteRoutes
        )
    }
}
@Composable
fun QueryResultsList(
    modifier: Modifier = Modifier,
    routesDetails: List<AirportRouteDetails>,
    selectedAirport: Airport,
    onFavoriteIconClicked: (AirportRouteDetails) -> Unit,
    favoriteRoutes: List<AirportRouteDetails>
) {
    if (routesDetails.isNotEmpty()) {
        routesDetails.checkIfIsFavorite(favoriteRoutes)
        LazyColumn(
            modifier = modifier
        ) {
            item {
                QueryResultsTitle(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = dimensionResource(id = R.dimen.padding_medium),
                            bottom = dimensionResource(id = R.dimen.padding_small)
                        ),
                    passengers = selectedAirport.passengers,
                    selectedIataCode = selectedAirport.iataCode
                )
            }
            items(routesDetails) { routeDetails ->
                Surface(
                    tonalElevation = 3.dp,
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier
                        .padding(bottom = dimensionResource(id = R.dimen.padding_medium))
                ) {
                    FlightRoute(
                        airportRouteDetails = routeDetails,
                        onFavoriteIconClicked = onFavoriteIconClicked,
                        modifier = Modifier
                            .padding(dimensionResource(id = R.dimen.padding_large))
                    )
                }
            }
        }
    } else {
        NothingToShow(
            message = "No routes searched yet",
            modifier = modifier)
    }
}
@Composable
fun QueryResultsTitle(
    modifier: Modifier =Modifier,
    passengers: Int,
    selectedIataCode: String
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.routes_from,selectedIataCode),
            style  = MaterialTheme.typography.labelMedium
        )
        Text(
            text = stringResource(id = R.string.plane_capacity,passengers),
            style = MaterialTheme.typography.labelMedium
        )
    }
}


@Preview(showBackground = true)
@Composable
fun QueryDetailsPreview() {
    val selectedAirport = Airport(0,"JKA","Jomo Kenyatta International Airport", 62)
    val otherAirport = Airport(0,"JAA","Just Another Airport", 62)
    val mockAirportRoute =
        AirportRoute(
            0,
            selectedAirport.iataCode,
            selectedAirport.name,
            otherAirport.iataCode,
            otherAirport.name
        )
    val mockAirportRoutesDetails = List (6) { AirportRouteDetails(mockAirportRoute, FavoriteDetails.IS_NOT_FAVORITE)}
    QueryDetails(
        searchBarUiState = SearchBarUiState(),
        onQueryChange = {},
        onActiveChange = {},
        onSearch = {},
        onSearchSuggestionClicked = {},
        onSearchBarBackClicked = { },
        onSearchBarClearClicked = { },
        routesDetails = mockAirportRoutesDetails,
        selectedAirport = selectedAirport,
        onFavoriteIconClicked = {},
        favoriteRoutes = emptyList()
    )
}
@Preview(showBackground = true)
@Composable
fun QueryResultsListPreview() {
    val selectedAirport = Airport(0,"JKA","Jomo Kenyatta International Airport", 62)
    val otherAirport = Airport(0,"JAA","Just Another Airport", 62)
    val mockAirportRoute =
        AirportRoute(
            0,
            selectedAirport.iataCode,
            selectedAirport.name,
            otherAirport.iataCode,
            otherAirport.name
        )
    val mockAirportRoutesDetails = List (6) { AirportRouteDetails(mockAirportRoute,FavoriteDetails.IS_NOT_FAVORITE)}
    QueryResultsList(
        routesDetails = mockAirportRoutesDetails,
        selectedAirport = selectedAirport,
        onFavoriteIconClicked = {},
        favoriteRoutes = emptyList()
    )
}
@Preview(showBackground = true)
@Composable
fun QueryResultsTitlePreview() {
    QueryResultsTitle(
        passengers = 10,
        selectedIataCode = "JKA"
    )
}
private fun List<AirportRouteDetails>.checkIfIsFavorite(favoriteRoutes: List<AirportRouteDetails>): List<AirportRouteDetails> {
    this.forEach { airportRouteDetails ->
        val airportRouteDetailsDepartureCode = airportRouteDetails.airportRoute.departureIataCode
        val airportRouteDetailsDestinationCode = airportRouteDetails.airportRoute.destinationIataCode
        favoriteRoutes.forEach { favoriteRoute ->
            val favoriteRoutesDepartureCode = favoriteRoute.airportRoute.departureIataCode
            val favoriteRoutesDestinationCode = favoriteRoute.airportRoute.destinationIataCode
            if (airportRouteDetailsDepartureCode == favoriteRoutesDepartureCode && airportRouteDetailsDestinationCode == favoriteRoutesDestinationCode) {
                airportRouteDetails.favoriteDetails = FavoriteDetails.IS_FAVORITE
            }
        }
    }
    return this
}
