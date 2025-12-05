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

enum class AppPermission(val displayName: String, val isSpecial: Boolean = false) {
    MANAGE_TICKETS("Gerenciar Tickets"),
    TRANSFER_TICKETS("Transferir Tickets"),
    FINALIZE_TICKETS("Finalizar Tickets"),
    // O ADMINISTRADOR é marcado como especial
    ADMINISTRATOR("Administrador", isSpecial = true);
}

data class PermissionItem(
    val nome: String,
    val hasPermission: Boolean,
    val isSpecial: Boolean
)
fun generatePermissions(permissionsGranted: Set<AppPermission>): List<PermissionItem> {
    return AppPermission.entries.map { appPermission ->
        PermissionItem(
            nome = appPermission.displayName,
            hasPermission = permissionsGranted.contains(appPermission),
            isSpecial = appPermission.isSpecial
        )
    }
}

data class Librarian(
    val nameLibrarian: String,
    val role: String, // Cargo ou Função
    val emailLibrarian: String,
    val phoneLibrarian: String = "", // Opcional
    val corrigidos: Int = 0,
    val pendentes: Int = 0,
    val avaliacaoMedia: Float = 0f,
    val tempoMedio: String = "",
    val permissoes: List<PermissionItem> = emptyList()
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