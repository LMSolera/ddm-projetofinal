package com.example.ddm_projetofinal.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddm_projetofinal.data.repository.AppRepositoryImpl
import com.example.ddm_projetofinal.model.Expense
import com.example.ddm_projetofinal.model.Trip
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeUiState (
    val isLoading: Boolean = false,
    val recentTrips: List<Trip> = emptyList(),
    val recentExpenses: List<Expense> = emptyList()
)

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

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

    fun getExpenses (userId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val searchResult = repository.getExpensesByUser(userId)

            searchResult.onSuccess { expenses ->
                _uiState.value = _uiState.value.copy(
                    recentExpenses = expenses,
                    isLoading = false
                )
            }
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }
}