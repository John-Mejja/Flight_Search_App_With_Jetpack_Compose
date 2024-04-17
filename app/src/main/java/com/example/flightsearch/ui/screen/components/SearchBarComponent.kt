package com.example.flightsearch.ui.screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.flightsearch.R
import com.example.flightsearch.data.model.AirportIataAndName

data class SearchBarUiState(
    val query: String = "",
    val active: Boolean = false,
    val searchSuggestions: List<AirportIataAndName> = emptyList()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchBar(
    modifier: Modifier = Modifier,
    searchBarUiState: SearchBarUiState,
    onQueryChange :(String) -> Unit,
    onActiveChange :(Boolean) -> Unit,
    onSearch :(String) -> Unit,
    onSearchSuggestionClicked :(String) -> Unit,
    onSearchBarBackClicked :() -> Unit,
    onSearchBarClearClicked :() -> Unit
){
    SearchBar(
        query = searchBarUiState.query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = searchBarUiState.active,
        onActiveChange = onActiveChange,
        shape = SearchBarDefaults.fullScreenShape,
        leadingIcon = {
            if (searchBarUiState.active) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back),
                    modifier = Modifier
                        .clickable { onSearchBarBackClicked() }
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search)
                )
            }
        },
        trailingIcon = {
            if (searchBarUiState.query.isNotBlank()) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(id = R.string.clear),
                    modifier = Modifier
                        .clickable {
                            onSearchBarClearClicked()
                        })
            }
        },
        placeholder = {
            if (searchBarUiState.query.isBlank()) {
                Text(
                    text = stringResource(id = R.string.search_bar_placeholder)
                )
            }
        },
        modifier = modifier
    ) {
        LazyColumn {
            items( searchBarUiState.searchSuggestions) { suggestedAirport ->
                SearchSuggestion(
                    suggestedAirport = suggestedAirport,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onSearchSuggestionClicked(suggestedAirport.iataCode)
                        }
                )
                HorizontalDivider(
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
                )
            }
        }
    }
}

@Composable
fun SearchSuggestion(
    modifier: Modifier = Modifier,
    suggestedAirport: AirportIataAndName
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        Text(
            text = stringResource(id = R.string.airport_iata_code,suggestedAirport.iataCode),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .padding( end = dimensionResource(id = R.dimen.padding_small))
        )
        Text(
            text = suggestedAirport.name,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .weight(1f)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun FlightSearchBarPreview(){
    val mockAirportsList = List (10) { AirportIataAndName("JOH","John Major")}
    FlightSearchBar(
        searchBarUiState = SearchBarUiState( searchSuggestions = mockAirportsList),
        onQueryChange = {},
        onActiveChange = {},
        onSearch = {},
        onSearchSuggestionClicked = {},
        onSearchBarBackClicked = {},
        onSearchBarClearClicked = {}
    )
}
@Preview(showBackground = true)
@Composable
fun SearchSuggestionPreview(){
    val mockAirport = AirportIataAndName("JKA","John Kenyatta International Airport")
    SearchSuggestion(
        suggestedAirport = mockAirport
    )
}

