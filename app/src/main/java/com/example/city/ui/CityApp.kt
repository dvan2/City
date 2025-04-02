package com.example.city.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Hiking
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.city.data.model.Category
import com.example.city.data.model.CityScreen
import com.example.city.data.model.Place

@Composable
fun CityApp(viewModel: PlaceViewModel = viewModel()) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val selectedPlaceId by viewModel.selectedPlaceId.collectAsState()
    val selectedPlace = selectedPlaceId?.let { viewModel.getPlaceById(it) }

    Scaffold (
        topBar = {
            CityTopBar(
                currentScreen = currentScreen,
                selectedPlace = selectedPlace,
                onBack = { viewModel.goBackToCategory() }
            )
        },
        bottomBar = {
            CityBottomBar(currentScreen = currentScreen, onTabSelected = { viewModel.navigateTo(it) })
        }
    ){ innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentScreen) {
                // Reference showPlaceDetail for the onclick of viewModel using function reference
                CityScreen.HIKING -> CategoryScreen(Category.HIKING, viewModel::showPlaceDetail)
                CityScreen.SOCCER -> CategoryScreen(Category.SOCCER, viewModel::showPlaceDetail)
                CityScreen.FOOD -> CategoryScreen(Category.FOOD, viewModel::showPlaceDetail)
                CityScreen.DETAIL -> selectedPlaceId?.let {
                    PlaceDetailScreen(it)
                }
            }
        }
    }
}

@Composable
fun CategoryScreen(
    category: Category,
    onPlaceClick: (String) -> Unit,
    viewModel: PlaceViewModel = viewModel()
) {
    val places = viewModel.getPlacesByCategory(category)

    LazyColumn {
        items(places) { place ->
            PlaceListCard(place = place, onClick = { onPlaceClick(place.id)})
        }
    }
}

@Composable
fun CityBottomBar(
    currentScreen: CityScreen,
    onTabSelected: (CityScreen) -> Unit
) {
    val bottomNavItems = listOf(
        CityScreen.HIKING,
        CityScreen.FOOD,
        CityScreen.SOCCER
    )

    NavigationBar {
        bottomNavItems.forEach { screen->
            val icon = when (screen) {
                CityScreen.HIKING -> Icons.Default.Hiking
                CityScreen.SOCCER -> Icons.Default.SportsSoccer
                CityScreen.FOOD -> Icons.Default.Restaurant
                else -> Icons.Default.Info
            }

            val label = when (screen) {
                CityScreen.HIKING -> "Hiking"
                CityScreen.SOCCER -> "Soccer"
                CityScreen.FOOD -> "Food"
                else -> ""
            }

            NavigationBarItem(
                icon = { Icon(imageVector = icon, contentDescription = label)},
                label = { Text(label) },
                selected =  currentScreen == screen,
                onClick = { onTabSelected(screen) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityTopBar(
    currentScreen: CityScreen,
    selectedPlace: Place?,
    onBack: () -> Unit
) {
    val title = when (currentScreen) {
        CityScreen.HIKING -> "Hiking Spots"
        CityScreen.FOOD -> "Food Places"
        CityScreen.SOCCER -> "Soccer Spots"
        CityScreen.DETAIL -> selectedPlace?.name ?: "Details"
    }

    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (currentScreen == CityScreen.DETAIL) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        }
    )
}