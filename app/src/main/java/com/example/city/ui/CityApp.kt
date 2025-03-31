package com.example.city.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
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
fun HikingScreen() {
    Text("Hiking")
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