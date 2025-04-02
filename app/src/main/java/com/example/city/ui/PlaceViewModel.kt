package com.example.city.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.city.data.model.Category
import com.example.city.data.model.CityScreen
import com.example.city.data.model.Place
import com.example.city.data.repository.PlaceRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlaceViewModel(application: Application) : AndroidViewModel(application){
    private var _lastCategoryScreen = CityScreen.HIKING

    private val _currentScreen = MutableStateFlow(CityScreen.HIKING)
    val currentScreen : StateFlow<CityScreen> = _currentScreen

    private val _selectedPlaceId= MutableStateFlow<String?>(null)
    val selectedPlaceId: StateFlow<String?> = _selectedPlaceId

    val places: List<Place> = PlaceRepository.allPlaces

    fun navigateTo(screen: CityScreen) {
        _currentScreen.value = screen
    }

    fun showPlaceDetail(placeId: String) {
        _lastCategoryScreen = _currentScreen.value
        _selectedPlaceId.value = placeId
        _currentScreen.value = CityScreen.DETAIL
    }

    fun getPlacesByCategory(category: Category): List<Place> {
        return PlaceRepository.allPlacesByCategory(category)
    }

    fun getPlaceById(id: String): Place? {
        return PlaceRepository.getPlaceById(id)
    }

    fun goBackToCategory() {
        _currentScreen.value = _lastCategoryScreen
    }

}