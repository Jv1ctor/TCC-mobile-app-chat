package com.example.tccmobile.ui.screens.loginScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tccmobile.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val uiState = _uiState.asStateFlow()

    fun onMatriculaChange(newValue: String) {
        _uiState.update { it.copy(matricula = newValue) }
    }

    fun onSenhaChange(newValue: String) {
        _uiState.update { it.copy(senha = newValue) }
    }

    fun setIsStudent(v: Boolean){
        _uiState.update { it.copy(isStudent = v) }
    }

    fun onLoginClick(onLoginSuccess: (value: Boolean) -> Unit) { // Adicionando callback
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }


            if(_uiState.value.matricula.isEmpty() || _uiState.value.senha.isEmpty()){
                _uiState.update { it.copy(isLoading = false, loginError = "Matrícula ou senha inválida.") }
                return@launch
            }


            val isAuth = authRepository.signIn(
                registry = _uiState.value.matricula,
                password = _uiState.value.senha
            )

            if(!isAuth) {
                _uiState.update { it.copy(isLoading = false, loginError = "Matrícula ou senha inválida.") }
                return@launch
            }

            val isStudent = authRepository.getIsStudent()
            setIsStudent(isStudent)
            _uiState.update { it.copy(isLoading = false, loginError = null) }
            onLoginSuccess(_uiState.value.isStudent) // Chama o callback de sucesso
        }
    }
}