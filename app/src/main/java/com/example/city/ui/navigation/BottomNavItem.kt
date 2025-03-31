package com.example.city.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem (
    val route: String,
    val label: String,
    val icon: ImageVector
){
    object Hiking: BottomNavItem("hiking", "Hiking", Icons.Filled.Info)
    object Soccer: BottomNavItem("soccer", "Soccer", Icons.Filled.Info)
    object Food: BottomNavItem("food", "Food", Icons.Filled.Info)
}