package com.example.ddm_projetofinal.ui.feature.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddm_projetofinal.data.local.UserLocalRepository
import com.example.ddm_projetofinal.data.repository.AppRepositoryImpl
import com.example.ddm_projetofinal.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RegisterUiState (
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val user: User? = null,
    val registerSucess: Boolean = false
)

class RegisterViewModel : ViewModel()  {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    var localRepository: UserLocalRepository? = null

    var repository = AppRepositoryImpl()

    fun register (userName: String, userEmail: String, userPassword: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val newUser = User(
                id = (userEmail + userPassword).hashCode().toString(),
                name = userName,
                email = userEmail,
                password = userPassword
            )

            val checkResult = repository.checkUserByEmail(userEmail)

            checkResult.onSuccess { checkedUser ->
                if (checkedUser.email == userEmail) {
                    _uiState.value = _uiState.value.copy(errorMessage = "Email inserido já está em uso.")
                    _uiState.value = _uiState.value.copy(isLoading = false)
                } else {
                    val result = repository.insertUser(newUser)
                    result.onSuccess { returnedUser ->
                        _uiState.value = _uiState.value.copy(
                            user = returnedUser,
                            registerSucess = true
                        )

                    }.onFailure { exception ->
                        _uiState.value = _uiState.value.copy(errorMessage = "Falha ao tentar registrar novo usuário.")
                        _uiState.value = _uiState.value.copy(isLoading = false)
                    }
                }
            }.onFailure { exception ->
                _uiState.value = _uiState.value.copy(errorMessage = "Falha ao tentar verificar disponibilidade do email.")
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}