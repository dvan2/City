package com.example.city.data.model

import androidx.annotation.DrawableRes


enum class Category{
    HIKING, FOOD, SOCCER
}

enum class CityScreen{
    HIKING, SOCCER, FOOD, DETAIL
}

data class Place(
    val id: String,
    val name: String,
    val description: String,
    @DrawableRes val image: Int,
    val category: Category
)
