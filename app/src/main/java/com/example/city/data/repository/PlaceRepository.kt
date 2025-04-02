package com.example.city.data.repository

import com.example.city.R
import com.example.city.data.model.Category
import com.example.city.data.model.Place


object PlaceRepository {
    fun allPlacesByCategory(category: Category): List<Place> {
        return allPlaces.filter {it.category == category }
    }

    fun getPlaceById(id: String): Place? {
        return allPlaces.find {it.id == id }
    }

    val allPlaces = listOf(
        Place(
            id = "eagle_creek",
            name="Eagle Creek",
            description = "Eagle Creek is a scenic hiking trail located in the Columbia River Gorge. It offers lush forests, waterfalls, and breathtaking views.",
            image = R.drawable.eagle_creek,
            category = Category.HIKING
        )
    )
}