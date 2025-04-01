package com.example.city.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.city.data.model.Category
import com.example.city.data.model.CityScreen
import com.example.city.data.model.Place
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlaceViewModel(application: Application) : AndroidViewModel(application){
    private val _currentScreen = MutableStateFlow<CityScreen>(CityScreen.HIKING)
    val currentScreen : StateFlow<CityScreen> = _currentScreen

    private val _selectedPlaceId= MutableStateFlow<String?>(null)
    val selectedPlaceId: StateFlow<String?> = _selectedPlaceId

    private val _places = MutableStateFlow<List<Place>>(emptyList())
    val places :StateFlow<List<Place>> = _places

    init {
        loadPlaces()
    }

    fun navigateTo(screen: CityScreen) {
        _currentScreen.value = screen
    }

    fun showPlaceDetail(placeId: String) {
        _selectedPlaceId.value = placeId
        _currentScreen.value = CityScreen.DETAIL
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