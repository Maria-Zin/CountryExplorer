package ru.fefu.countryexplorer.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.fefu.countryexplorer.data.Country
import ru.fefu.countryexplorer.data.CountryRepository

sealed class UiState {
    object Loading : UiState()
    data class Success(val countries: List<Country>) : UiState()
    data class Error(val message: String) : UiState()
    object Empty : UiState()
}

class CountryViewModel : ViewModel() {
    val repository = CountryRepository()

    private var searchJob: Job? = null

    private val _uiState = mutableStateOf<UiState>(UiState.Loading)
    val uiState: UiState
        get() = _uiState.value

    private val _favourites = mutableStateOf<Set<String>>(emptySet())
    val favourites: Set<String>
        get() = _favourites.value

    private var allCountriesCache: List<Country> = emptyList()

    init {
        loadCountries()
    }

    fun loadCountries() {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                val result = repository.getAllCountries()

                if (result.isSuccess) {
                    val countries = result.getOrDefault(emptyList())
                    allCountriesCache = countries // Сохраняем в кэш
                    _uiState.value = if (countries.isEmpty()) {
                        UiState.Empty
                    } else {
                        UiState.Success(countries)
                    }
                } else {
                    _uiState.value = UiState.Error("Не удалось загрузить страны")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Ошибка: ${e.message ?: "Проверьте интернет"}")
            }
        }
    }

    fun searchCountries(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            try {
                delay(400)
                _uiState.value = UiState.Loading
                val result = repository.searchCountries(query)

                if (result.isSuccess) {
                    val countries = result.getOrDefault(emptyList())
                    _uiState.value = if (countries.isEmpty()) {
                        UiState.Empty
                    } else {
                        UiState.Success(countries)
                    }
                } else {
                    _uiState.value = UiState.Error("Поиск не удался")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Ошибка поиска: ${e.message}")
            }
        }
    }

    fun findCountryByName(name: String): Country? {
        return allCountriesCache.find { it.name == name }
    }

    fun toggleFavourite(countryName: String) {
        val newFavourites = _favourites.value.toMutableSet()
        if (newFavourites.contains(countryName)) {
            newFavourites.remove(countryName)
        } else {
            newFavourites.add(countryName)
        }
        _favourites.value = newFavourites
    }

    fun isFavourite(countryName: String): Boolean {
        return _favourites.value.contains(countryName)
    }
}