package com.example.tccmobile.ui.screens.registerScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tccmobile.data.entity.Student
import com.example.tccmobile.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterState())
    val uiState = _uiState.asStateFlow()

    // --- Funções de atualização ---
    fun onNomeChange(v: String) {
        _uiState.update { it.copy(nome = v) }
    }
    fun onMatriculaChange(v: String) {
        _uiState.update { it.copy(matricula = v) }
    }
    fun onEmailChange(v: String) {
        _uiState.update { it.copy(email = v) }
    }
    fun onTelefoneChange(v: String) {
        _uiState.update { it.copy(telefone = v) }
    }
    fun onCursoChange(v: String) {
        _uiState.update { it.copy(curso = v) }
    }
    fun onSenhaChange(v: String) {
        _uiState.update { it.copy(senha = v) }
    }
    fun onConfirmarSenhaChange(v: String) {
        _uiState.update { it.copy(confirmarSenha = v) }
    }

    fun onRegisterClick(onRegisterSuccess: () -> Unit) { // Adicionando callback
        viewModelScope.launch {
            if (_uiState.value.senha != _uiState.value.confirmarSenha) {
                _uiState.update { it.copy(registerError = "As senhas não conferem.") }
                return@launch
            }

            _uiState.update { it.copy(isLoading = true, registerError = null) }

            val student = Student(
                name = _uiState.value.nome,
                email = _uiState.value.email,
                registry = _uiState.value.matricula,
                course = _uiState.value.curso,
            )

            val result = authRepository.signUpStudent(student = student, password = _uiState.value.senha)

            _uiState.update { it.copy(isLoading = false) }


            if(result){
                onRegisterSuccess() // Chama o callback de sucesso

            }else{
                _uiState.update { it.copy(registerError = "Erro ao registrar. Tente novamente.") }
            }
        }
    }
}