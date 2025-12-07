package com.example.ddm_projetofinal.ui.feature.trips

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddm_projetofinal.data.repository.AppRepositoryImpl
import com.example.ddm_projetofinal.model.Expense
import com.example.ddm_projetofinal.model.Trip
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TripsUiState (
    val isLoading: Boolean = false,
    val recentTrips: List<Trip> = emptyList(),
    val recentExpenses: List<Expense> = emptyList(),
)

class TripsViewModel: ViewModel() {


    private val _uiState = MutableStateFlow(TripsUiState())
    val uiState: StateFlow<TripsUiState> = _uiState.asStateFlow()

    var repository = AppRepositoryImpl()

    fun getRecentTrips (userId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val searchResult = repository.getTripsByUser(userId)

            searchResult.onSuccess { trips ->
                _uiState.value = _uiState.value.copy(
                    recentTrips = trips,
                    isLoading = false
                )

            }
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    fun insertNewTrip (userId: String, title: String, date: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val newTrip = Trip (
                id = (userId.toString() + title).hashCode().toString(),
                ownerId = userId.toString(),
                title = title,
                date = date
            )

            val result = repository.insertTrip(tripInfo = newTrip)

            result.onFailure { exception ->
                getRecentTrips(userId)
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
            getRecentTrips(userId)
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    fun updateTrip (trip: Trip) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val newTrip = Trip (
                id = trip.id,
                ownerId = trip.ownerId,
                title = trip.title,
                date = trip.date
            )

            val result = repository.updateTrip(trip)

            result.onFailure { exception ->
                getRecentTrips(trip.ownerId)
                _uiState.value = _uiState.value.copy(isLoading = false)
                exception.message?.let { Log.e("Debugging", it) }
            }
            getRecentTrips(trip.ownerId)
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    fun deleteTrip (trip: Trip) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val result = repository.deleteTrip(trip.id.toString())

            result.onFailure { exception ->
                getRecentTrips(trip.ownerId)
                _uiState.value = _uiState.value.copy(isLoading = false)
                exception.message?.let { Log.e("Debugging", it) }
            }
            getRecentTrips(trip.ownerId)
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }
}