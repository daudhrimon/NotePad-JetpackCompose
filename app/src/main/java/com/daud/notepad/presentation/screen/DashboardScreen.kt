package com.daud.notepad.presentation.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daud.notepad.data.network.RestApiService
import com.daud.notepad.data.network.ApiService
import com.daud.notepad.data.repository.NoteRepository
import com.daud.notepad.presentation.viewmodel.NoteViewModel
import com.daud.notepad.presentation.viewmodel.NoteViewModelFactory
import com.daud.notepad.utils.showToast

@Composable
fun DashboardScreen(
    navHostController: NavHostController,
    viewModel: NoteViewModel = viewModel(
        factory = NoteViewModelFactory(
            NoteRepository(
                RestApiService.generate(ApiService::class.java)
            )
        )
    )
) {
    viewModel.onIsLoadingState.value.let {
        if (it) {
            navHostController.context.showToast("Loading")
        }
    }
    viewModel.onShowMessageState.value.let {
        if (it.isNotEmpty()) {
            navHostController.context.showToast(it)
        }
    }
    viewModel.onNoteListResponse.value?.let {
        navHostController.context.showToast(it.toString())
        Log.wtf("NOTES", it.toString())
    }
}