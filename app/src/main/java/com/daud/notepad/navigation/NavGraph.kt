package com.daud.notepad.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.daud.notepad.base.BaseEventListener
import com.daud.notepad.ui.screen.DashboardScreen
import com.daud.notepad.ui.screen.NoteScreen

@Composable
fun NavGraph(
    navHostController: NavHostController,
    baseEventListener: BaseEventListener
) {
    NavHost(
        navController = navHostController,
        startDestination = NavRoute.DashboardScreen.route
    ) {
        composable(route = NavRoute.DashboardScreen.route) {
            DashboardScreen(navHostController = navHostController, baseEventListener)
        }
        composable(route = NavRoute.NoteScreen.route) {
            NoteScreen(navHostController = navHostController, baseEventListener)
        }
    }
}