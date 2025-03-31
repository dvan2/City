package com.example.city.data.model


enum class Category {
    HIKING, FOOD, SOCCER
}

data class Place(
    val id: String,
    val name: String,
    val description: String,
    val image: String,
    val category: Category
)
