package com.daud.notepad.ui.screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daud.notepad.base.BaseEventHandler
import com.daud.notepad.base.BaseApiService
import com.daud.notepad.data.network.ApiService
import com.daud.notepad.data.repository.NoteRepository
import com.daud.notepad.ui.viewmodel.NoteViewModel
import com.daud.notepad.ui.viewmodel.NoteViewModelFactory

@Composable
fun DashboardScreen(
    navHostController: NavHostController,
    context: Context = LocalContext.current,
    viewModel: NoteViewModel = viewModel(
        factory = NoteViewModelFactory(
            NoteRepository(
                BaseApiService.generate(ApiService::class.java)
            )
        )
    )
) { BaseEventHandler(viewModel.onIsLoadingState, viewModel.onShowMessageState)

    viewModel.onNoteListResponse.value?.let {

    }
}