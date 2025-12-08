package com.example.ddm_projetofinal.ui.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddm_projetofinal.data.local.UserLocalRepository
import com.example.ddm_projetofinal.data.repository.AppRepositoryImpl
import com.example.ddm_projetofinal.model.User
import kotlinx.coroutines.Dispatchers
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

    var localRepository: UserLocalRepository? = null

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
                localRepository?.let {
                    it.insert(
                        id = user.id,
                        name = user.name,
                        email = user.email,
                        password = user.password
                    )
                }
            }.onFailure { exception ->
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Falha no login.\n" + exception.message
                )
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun checkForCachedLogin () {
        viewModelScope.launch(Dispatchers.IO) {
            var cachedUser: User? = null
            localRepository?.let {
                cachedUser = it.getSavedLogin()
            }
            cachedUser?.let {
                login(it.email, it.password)
            }
        }
    }
}