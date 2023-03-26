package com.daud.notepad.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.daud.notepad.presentation.screen.DashboardScreen
import com.daud.notepad.presentation.screen.NoteScreen

@Composable
fun NavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = NavRoute.DashboardScreen.route
    ) {
        composable(route = NavRoute.DashboardScreen.route) {
            DashboardScreen(navHostController = navHostController)
        }
        composable(route = NavRoute.NoteScreen.route) {
            NoteScreen(navHostController = navHostController)
        }
    }
}