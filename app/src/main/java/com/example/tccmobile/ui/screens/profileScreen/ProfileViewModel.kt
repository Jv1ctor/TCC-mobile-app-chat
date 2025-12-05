package com.example.tccmobile.ui.screens.profileScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tccmobile.ui.screens.registerScreen.RegisterState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileState())
    val uiState = _uiState.asStateFlow()
    
    init {
        // Altere para UserType.LIBRARIAN para testar o perfil da bibliotecária
        loadUserProfile(loggedInUserType = UserType.STUDENT)
    }

    private fun loadUserProfile(loggedInUserType: UserType) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, userType = UserType.LOADING) }
            delay(1000)

            when (loggedInUserType) {
                UserType.STUDENT -> {
                    // Dados de exemplo do Aluno
                    val registerData = RegisterState(
                        nome = "Ana Costa",
                        email = "ana.costa@unifor.br",
                        telefone = "(85) 3477-3000"
                    )
                }
                UserType.LIBRARIAN -> {
                    val grantedPermissions = setOf(
                        AppPermission.MANAGE_TICKETS,
                        AppPermission.TRANSFER_TICKETS
                    )

                    //Dados de exemplo do Bibliotecario
                    val librarianData = Librarian(
                        nameLibrarian = "Ana Costa",
                        role = "Bibliotecária Plena",
                        emailLibrarian = "ana.costa@unifor.br",
                        phoneLibrarian = "(85) 3477-3000",
                        corrigidos = 45,
                        pendentes = 8,
                        avaliacaoMedia = 4.8f,
                        tempoMedio = "2.3 dias",
                        permissoes = generatePermissions(grantedPermissions)
                    )

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            userType = UserType.LIBRARIAN,
                            librarian = librarianData,
                            errorMessage = null
                        )
                    }
                }
                else -> {

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            userType = UserType.ERROR,
                            errorMessage = "Tipo de usuário não reconhecido ou erro de autenticação."
                        )
                    }
                }
            }
        }
    }

    fun onLogoutClick(onLogoutSuccess: () -> Unit) {
        viewModelScope.launch {
            // Inicia o estado de carregamento de logout
            _uiState.update { it.copy(isLoading = true) }
            delay(500) // Simula tempo de logout

            _uiState.update { it.copy(isLoading = false) }
            onLogoutSuccess()
        }
    }
}