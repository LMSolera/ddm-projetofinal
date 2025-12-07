package com.example.ddm_projetofinal.ui.feature.expenses

import android.util.Log
import androidx.core.app.NotificationCompat.MessagingStyle.Message
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ddm_projetofinal.data.repository.AppRepositoryImpl
import com.example.ddm_projetofinal.model.Expense
import com.example.ddm_projetofinal.model.Trip
import com.example.ddm_projetofinal.ui.feature.trips.TripsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.exp

data class ExpensesUiState (
    val isLoading: Boolean = false,
    val recentExpenses: List<Expense> = emptyList(),
    val recentTrips: List<Trip> = emptyList(),
    val temporaryTripTitle: String = "",
    val message: String = "",
    val error: Boolean? = null,
    val selectedContent: String = "overall"
    // 'overall' = Visão Geral ; 'creation' = Nova Despesa ; 'listing' = Despesas
)

class ExpensesViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ExpensesUiState())
    val uiState: StateFlow<ExpensesUiState> = _uiState.asStateFlow()

    var repository = AppRepositoryImpl()

    fun updateSelectedContent (content: String) {
        _uiState.value = _uiState.value.copy(
            selectedContent = content
        )
    }

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

    fun getTripById (tripId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val searchResult = repository.getTripById(tripId)

            searchResult.onSuccess { trip ->
                _uiState.value = _uiState.value.copy(
                    temporaryTripTitle = trip.title,
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

    fun insertNewExpense(userId: String, tripId: String, observation: String, date: String, value: Double, type: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val newExpense = Expense (
                id = (tripId + userId + observation + date).hashCode().toString(),
                ownerId = userId,
                tripId = tripId,
                observation = observation,
                date = date,
                value = value,
                type = type
            )

            val result = repository.insertExpense(newExpense)

            result.onSuccess {
                _uiState.value = _uiState.value.copy(
                    message = "Despesa inserida com sucesso!",
                    isLoading = false,
                    error = false,
                    selectedContent = "creation")
                getExpenses(userId)
            }.onFailure { exception ->
                _uiState.value = _uiState.value.copy(
                    message = "Erro ao inserir despesa.\n" + exception.message,
                    isLoading = false,
                    error = true,
                    selectedContent = "creation")
            }
        }
    }

    fun deleteExpense (expense: Expense) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val result = repository.deleteExpense(expense.id)

            result.onFailure { exception ->
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
            getExpenses(expense.ownerId)
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }
}
