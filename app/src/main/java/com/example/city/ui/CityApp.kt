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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.city.data.model.Place
import com.example.city.ui.navigation.BottomNavItem

@Composable
fun CityApp() {
    val navController = rememberNavController()

    Scaffold (
        bottomBar = { CityBottomBar(navController)}
    ){ innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            CityNavGraph(navController)
        }
    }
}

@Composable
fun CityNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Hiking.route
    ) {
        composable(BottomNavItem.Hiking.route) {
            HikingScreen()
        }
        composable(BottomNavItem.Food.route) {
            FoodScreen()
        }
        composable(BottomNavItem.Soccer.route) {
            SoccerScreen()
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
fun HikingScreen(viewModel: PlaceViewModel = viewModel()) {
    val hikingPlaces = viewModel.getPlacesByCategory(Category.HIKING)

    LazyColumn {
        items(hikingPlaces) { place ->
            PlaceCard(place)
        }
    }
}

@Composable
fun PlaceCard(place: Place) {
    val context = LocalContext.current
    val imageBitmap = remember(place.image) {
        val inputStream = context.assets.open(place.image)
        BitmapFactory.decodeStream(inputStream).asImageBitmap()
    }

    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Image(
                bitmap = imageBitmap,
                contentDescription = place.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = place.name,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = place.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


@Composable
fun CityBottomBar(navController: NavController) {
    val bottomNavItems = listOf(
        BottomNavItem.Hiking,
        BottomNavItem.Soccer,
        BottomNavItem.Food,
    )

    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route

    NavigationBar {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(item.icon, contentDescription = item.label)
                },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}