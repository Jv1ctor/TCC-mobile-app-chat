package com.example.tccmobile.ui.screens.profileScreen

data class Student(
    val nameStudent: String,
    val matricula: String,
    val emailStudent: String,
    val phoneStudent: String = "",
    val curso: String = "",
    val tccsEnviados: Int = 0,
    val tccsEmAnalise: Int = 0
)

data class Librarian(
    val nameLibrarian: String,
    val role: String, // Cargo ou Função
    val emailLibrarian: String,
    val phoneLibrarian: String = "", // Opcional
    val recebidos: Int = 0,
)


enum class UserType {
    STUDENT,
    LIBRARIAN,
    ERROR,
    LOADING
}

data class ProfileState(
    val isLoading: Boolean = true,
    val userType: UserType = UserType.LOADING,
    val student: Student? = null,
    val librarian: Librarian? = null,
    val errorMessage: String? = null
)