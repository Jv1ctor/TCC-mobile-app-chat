package com.example.tccmobile.ui.screens.loginScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val uiState = _uiState.asStateFlow()

    fun onMatriculaChange(newValue: String) {
        _uiState.update { it.copy(matricula = newValue) }
    }

    fun onSenhaChange(newValue: String) {
        _uiState.update { it.copy(senha = newValue) }
    }

    fun onLoginClick(onLoginSuccess: () -> Unit) { // Adicionando callback
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            kotlinx.coroutines.delay(1500)

            if (_uiState.value.matricula == "123" && _uiState.value.senha == "123") {
                _uiState.update { it.copy(isLoading = false, loginError = null) }
                onLoginSuccess() // Chama o callback de sucesso
            } else {
                _uiState.update { it.copy(isLoading = false, loginError = "Matrícula ou senha inválida.") }
            }
        }
    }
}