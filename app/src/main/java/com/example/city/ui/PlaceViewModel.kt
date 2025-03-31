package com.example.city.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.city.data.model.Category
import com.example.city.data.model.Place
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlaceViewModel(application: Application) : AndroidViewModel(application){
    private val _places = MutableStateFlow<List<Place>>(emptyList())
    val places :StateFlow<List<Place>> = _places

    init {
        loadPlaces()
    }

    private fun loadPlaces() {
        viewModelScope.launch {
            val context = getApplication<Application>()
            val json = context.assets.open("places.json").bufferedReader().use { it.readText() }
            val type = object : TypeToken<List<Place>>() {}.type
            val allPlaces: List<Place> = Gson().fromJson(json, type)
            _places.value = allPlaces
        }
    }

    fun getPlacesByCategory(category: Category): List<Place> {
        return _places.value.filter {it.category == category }
    }
}