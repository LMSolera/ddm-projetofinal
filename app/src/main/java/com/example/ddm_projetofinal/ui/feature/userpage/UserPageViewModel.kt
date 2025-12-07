package com.example.ddm_projetofinal.ui.feature.userpage

import android.media.session.MediaSessionManager.RemoteUserInfo
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ddm_projetofinal.data.local.UserLocalRepository
import com.example.ddm_projetofinal.data.repository.AppRepositoryImpl
import com.example.ddm_projetofinal.model.Trip
import com.example.ddm_projetofinal.model.User
import com.example.ddm_projetofinal.ui.feature.login.LoginUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UserPageUiState (
    val isLoading: Boolean = false,
    val error: Boolean? = null,
    val message: String = "",
    val updatedCredentials: Boolean = false,
    val newUserCredentials: User? = null
)

class UserPageViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(UserPageUiState())
    val uiState: StateFlow<UserPageUiState> = _uiState.asStateFlow()

    var localRepository: UserLocalRepository? = null

    var repository = AppRepositoryImpl()

    fun updateUser (userInfo: User) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val newUser = User (
                id = userInfo.id,
                name = userInfo.name,
                email = userInfo.email,
                password = userInfo.password,
            )

            val result = repository.updateUser(newUser)

            result.onFailure {
                _uiState.value = _uiState.value.copy(
                    error = true,
                    isLoading = false,
                    message = "Falha ao tentar atualizar dados de usuário."
                )
            }.onSuccess {
                _uiState.value = _uiState.value.copy(
                    error = false,
                    isLoading = false,
                    message = "Dados de usuário atualizados com sucesso!\nReiniciado credenciais em 2 segundos.",
                    updatedCredentials = true,
                    newUserCredentials = newUser)

                localRepository?.let {
                    it.insert(
                        id = newUser.id,
                        name = newUser.name,
                        email = newUser.email,
                        password = newUser.password
                    )
                }
            }
        }
    }

    fun removeFromLocalRepo (id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            localRepository?.let {
                it.delete(id = id)
            }
        }
    }
}

