package com.example.flightsearch.ui.screen.home

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearch.R
import com.example.flightsearch.data.database.Airport
import com.example.flightsearch.data.model.AirportRoute
import com.example.flightsearch.data.model.AirportRouteDetails
import com.example.flightsearch.data.model.FavoriteDetails

@Composable
fun FlightRoutes(
    modifier: Modifier = Modifier,
    airportRoutesDetails: List<AirportRouteDetails>,
    onFavoriteIconClicked: (AirportRouteDetails) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large))
    ) {
        items(items = airportRoutesDetails) { airportRouteDetails ->
            Surface(
                tonalElevation = 3.dp
            ) {
                FlightRoute(
                    airportRouteDetails = airportRouteDetails,
                    onFavoriteIconClicked = onFavoriteIconClicked,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_small))
                )
            }
        }
    }
}
@Composable
fun FlightRoute(
    modifier: Modifier = Modifier,
    airportRouteDetails: AirportRouteDetails,
    onFavoriteIconClicked :(AirportRouteDetails) -> Unit
){
    val airportRoute = airportRouteDetails.airportRoute
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            RouteDetails(
                airportIataCode = airportRoute.departureIataCode,
                airportName = airportRoute.departureName,
                routeTitle = R.string.departure
            )
            RouteDetails(
                airportIataCode = airportRoute.destinationIataCode,
                airportName = airportRoute.destinationName,
                routeTitle = R.string.arrival
            )
        }
        Surface(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_medium))
        ){
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = stringResource(id = R.string.favorite_icon_content_description),
                tint = if (airportRouteDetails.favoriteDetails == FavoriteDetails.IS_FAVORITE) Color.Yellow else Color.White,
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.favorite_icon_size))
                    .clickable {
                        onFavoriteIconClicked(airportRouteDetails)
                    }
            )
        }
    }
}
@Composable
fun RouteDetails(
    modifier: Modifier = Modifier,
    airportIataCode: String,
    airportName: String,
    @StringRes routeTitle: Int
){
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = routeTitle),
            style = MaterialTheme.typography.labelSmall
        )
        RouteDetail(
            airportIataCode = airportIataCode,
            airportName = airportName,
            modifier = Modifier
                .padding(start = dimensionResource(id = R.dimen.padding_medium))
        )
    }
}

@Composable
fun RouteDetail(
    modifier: Modifier = Modifier,
    airportName: String,
    airportIataCode: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = airportIataCode,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(end = dimensionResource(id = R.dimen.padding_medium))
        )
        Text(
            text = airportName,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Preview(showBackground = true)
@Composable
fun FlightRoutesPreview() {
    val selectedAirport = Airport(0,"JKA","Jomo Kenyatta International Airport", 62)
    val otherAirport = Airport(0,"JAA","Just Another Airport", 62)
    val mockAirportRoute = AirportRoute(0,selectedAirport.iataCode,selectedAirport.name,otherAirport.iataCode,otherAirport.name)
    val mockAirportRouteDetails =
        List (6){ AirportRouteDetails(mockAirportRoute, FavoriteDetails.IS_NOT_FAVORITE) }
    FlightRoutes(
        airportRoutesDetails = mockAirportRouteDetails,
        onFavoriteIconClicked = {}
    )
}
@Preview(showBackground = true)
@Composable
fun FlightRoutePreview(){
    val selectedAirport = Airport(0,"JKA","Jomo Kenyatta International Airport", 62)
    val otherAirport = Airport(0,"JAA","Just Another Airport", 62)
    val mockAirportRoute = AirportRoute(0,selectedAirport.iataCode,selectedAirport.name,otherAirport.iataCode,otherAirport.name)
    val mockAirportRouteDetails = AirportRouteDetails(mockAirportRoute,FavoriteDetails.IS_NOT_FAVORITE)
    FlightRoute(
        airportRouteDetails = mockAirportRouteDetails,
        onFavoriteIconClicked = {}
    )
}
@Preview( showBackground = true )
@Composable
fun FlightRouteDetailPreview() {
    val mockData = Airport(0,"JKA","Jomo Kenyatta International Airport", 62)
    RouteDetails(
        airportName = mockData.name,
        airportIataCode = mockData.iataCode,
        routeTitle = R.string.departure
    )
}
@Preview( showBackground = true )
@Composable
fun RoutePreview() {
    RouteDetail(
        airportName = "Jomo Kenyatta International Airport",
        airportIataCode = "JKA"
    )
}