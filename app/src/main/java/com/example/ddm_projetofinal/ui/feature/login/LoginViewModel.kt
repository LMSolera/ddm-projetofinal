package com.example.ddm_projetofinal.ui.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddm_projetofinal.data.repository.AppRepositoryImpl
import com.example.ddm_projetofinal.model.User
import com.example.ddm_projetofinal.ui.feature.register.RegisterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState (
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val user: User? = null,
    val loginSucess: Boolean = false
)

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    var repository = AppRepositoryImpl()

    fun login (email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val loginResult = repository.login(email, password)

            loginResult.onSuccess { user ->
                _uiState.value = _uiState.value.copy (
                    user = user,
                    loginSucess = true
                )
            }.onFailure { exception ->
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Falha no login.\n" + exception.message
                )
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}