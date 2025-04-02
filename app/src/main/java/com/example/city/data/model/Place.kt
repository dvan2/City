package com.example.city.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes


enum class Category{
    HIKING, FOOD, SOCCER
}

enum class CityScreen{
    HIKING, SOCCER, FOOD, DETAIL
}

data class Place(
    val id: String,
    val name: String,
    @StringRes val description: Int,
    @DrawableRes val image: Int,
    val category: Category
)
