package com.example.flightsearch.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.R
import com.example.flightsearch.data.database.Airport
import com.example.flightsearch.data.model.AirportRoute
import com.example.flightsearch.data.model.AirportRouteDetails
import com.example.flightsearch.data.model.FavoriteDetails
import com.example.flightsearch.ui.screen.AppViewModelProvider
import com.example.flightsearch.ui.screen.components.NothingToShow
import com.example.flightsearch.ui.screen.navigation.Navigation
import com.example.flightsearch.ui.theme.FlightSearchTheme
import kotlinx.coroutines.launch

object Home: Navigation {
    override val route: String
        get() = "home_screen_route"
    override val title: Int
        get() = R.string.home_title
}
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeVieModel: HomeScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val favoriteRoutes by homeVieModel.favoriteRoutes.collectAsState()

    val coroutine = rememberCoroutineScope()
    HomeDetails(
        favoriteRoutes = favoriteRoutes,
        modifier = modifier,
        onFavoriteIconClicked = {
            coroutine.launch {
                homeVieModel.removeFavoriteAirportRouteDetails(it)
            }
        }

    )
}

@Composable
fun HomeDetails(
    modifier: Modifier = Modifier,
    favoriteRoutes: List<AirportRouteDetails>,
    onFavoriteIconClicked: (AirportRouteDetails) -> Unit
){
    Column(
        modifier = modifier ,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        HomeFavoriteScreen(
            favoriteRoutes = favoriteRoutes,
            onFavoriteIconClicked = onFavoriteIconClicked
        )
    }
}
@Composable
fun HomeFavoriteScreen(
    modifier: Modifier = Modifier,
    favoriteRoutes: List<AirportRouteDetails>,
    onFavoriteIconClicked: (AirportRouteDetails) -> Unit
) {
    Column(
        modifier = modifier
            ,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
    ) {
        Text(
            text = stringResource(id = R.string.favorite_route),
            style = MaterialTheme.typography.labelLarge
        )
        if (favoriteRoutes.isNotEmpty()){
            FlightRoutes(
                airportRoutesDetails = favoriteRoutes,
                onFavoriteIconClicked = onFavoriteIconClicked
            )
        } else {
            NothingToShow(
                message = "No favorite book added yet"
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun HomeDetailsPreview() {
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
    val mockAirportRoutesDetails = List (6) { AirportRouteDetails(mockAirportRoute,FavoriteDetails.IS_FAVORITE)}
    FlightSearchTheme {
        HomeDetails(
            favoriteRoutes = mockAirportRoutesDetails,
            onFavoriteIconClicked = {}
        )
    }
}
@Preview(showBackground = true)
@Composable
fun HomeFavoriteScreenPreview() {
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
    val mockAirportRoutesDetails = List (6) { AirportRouteDetails(mockAirportRoute,FavoriteDetails.IS_FAVORITE)}
    HomeFavoriteScreen(
        favoriteRoutes = mockAirportRoutesDetails,
        onFavoriteIconClicked = {}
    )
}


