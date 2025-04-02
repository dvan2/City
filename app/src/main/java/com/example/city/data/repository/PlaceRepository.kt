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
            description = R.string.eagle_creek_des,
            image = R.drawable.eagle_creek,
            category = Category.HIKING
        ),
        Place(
            id = "multnomah_falls",
            name = "Multnomah Falls",
            description = R.string.multnomah_falls_desc,
            image = R.drawable.multnomah_falls,
            category = Category.HIKING
        ),
        Place(
            id = "silver_falls",
            name = "Silver Falls",
            description = R.string.silver_falls_desc,
            image = R.drawable.silver_falls,
            category = Category.HIKING
        ),
        Place(
            id = "forest_park",
            name = "Forest Park",
            description = R.string.forest_park_desc,
            image = R.drawable.forest_park,
            category = Category.HIKING
        )



    )
}