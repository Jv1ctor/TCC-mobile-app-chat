package com.example.tccmobile.navigation

object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val NEW_TICKET = "new-ticket"
    const val TICKET = "ticket/{id}"

    const val BIBLIO_TICKETS = "biblio_tickets"

    const val BIBLIO_DASHBOARD = "biblio_dashboard"

    fun ticket(id: String) = "ticket/$id"
    const val TICKET_DETAIL = "ticket_detail"
    const val PROFILE = "profile"
}