package com.example.city.ui

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.city.data.model.Category
import com.example.city.data.model.CityScreen
import com.example.city.data.model.Place
import com.example.city.ui.navigation.BottomNavItem

@Composable
fun CityApp(viewModel: PlaceViewModel = viewModel()) {
    val currentScreen by viewModel.currentScreen.collectAsState()

    Scaffold (
        bottomBar = {
            CityBottomBar(currentScreen = currentScreen, onTabSelected = { viewModel.navigateTo(it) })
        }
    ){ innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            val selectedPlaceId by viewModel.selectedPlaceId.collectAsState()

            when (currentScreen) {
                CityScreen.HIKING -> HikingScreen(viewModel::showPlaceDetail)
                CityScreen.SOCCER -> SoccerScreen()
                CityScreen.FOOD -> FoodScreen()
                CityScreen.DETAIL -> selectedPlaceId?.let {
                    PlaceDetailScreen(it)
                }
            }
        }
    }
}

@Composable
fun SoccerScreen() {
    Text("Soccer")
}

@Composable
fun FoodScreen() {
    Text("Food Screen")
}

@Composable
fun HikingScreen(onPlaceClick: (String) -> Unit, viewModel: PlaceViewModel = viewModel()) {
    val hikingPlaces = viewModel.getPlacesByCategory(Category.HIKING)

    LazyColumn {
        items(hikingPlaces) { place ->
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
        CityScreen.FOOD
    )


    NavigationBar {
        bottomNavItems.forEach { screen->
            val icon = when (screen) {
                CityScreen.HIKING -> Icons.Default.Info
                CityScreen.SOCCER -> Icons.Default.Info
                CityScreen.FOOD -> Icons.Default.Info
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