package com.example.tccmobile.ui.screens.loginScreen

data class LoginState(
    val matricula: String = "",
    val senha: String = "",
    val isLoading: Boolean = false,
    val loginError: String? = null
)
