package com.daud.notepad.navigation

sealed class NavRoute(val route: String) {
    object DashboardScreen : NavRoute("Dashboard")
    object NoteScreen : NavRoute("Note")
}
