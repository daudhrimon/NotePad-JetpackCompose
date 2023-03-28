package com.daud.notepad.ui.screen

import android.content.Context
import android.util.Log
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalContext
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daud.notepad.base.RestApiService
import com.daud.notepad.data.network.ApiService
import com.daud.notepad.data.repository.NoteRepository
import com.daud.notepad.ui.theme.Red
import com.daud.notepad.ui.viewmodel.NoteViewModel
import com.daud.notepad.ui.viewmodel.NoteViewModelFactory
import com.daud.notepad.utils.showToast

@Composable
fun DashboardScreen(
    navHostController: NavHostController,
    context: Context = LocalContext.current,
    viewModel: NoteViewModel = viewModel(
        factory = NoteViewModelFactory(
            NoteRepository(
                RestApiService.generate(ApiService::class.java)
            )
        )
    )
) {
    if (viewModel.onIsLoadingState.value) {
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator(color = Red)
        }
    }
    viewModel.onShowMessageState.value.let {
        if (it.isNotEmpty()) {
            context.showToast(it)
        }
    }
    viewModel.onNoteListResponse.value?.let {

    }
}