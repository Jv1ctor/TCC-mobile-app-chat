package com.example.tccmobile.ui.screens.registerScreen

data class RegisterState(
    val nome: String = "",
    val matricula: String = "",
    val email: String = "",
    val telefone: String = "",
    val curso: String = "",
    val senha: String = "",
    val confirmarSenha: String = "",
    val isLoading: Boolean = false,
    val registerError: String? = null
)