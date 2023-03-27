package com.daud.notepad.presentation.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daud.notepad.data.network.RestApiService
import com.daud.notepad.data.network.ApiService
import com.daud.notepad.data.repository.NoteRepository
import com.daud.notepad.presentation.viewmodel.NoteViewModel
import com.daud.notepad.presentation.viewmodel.NoteViewModelFactory

@Composable
fun DashboardScreen(
    navHostController: NavHostController,
    viewModel: NoteViewModel = viewModel(factory = NoteViewModelFactory(
            NoteRepository(
                RestApiService.generate(ApiService::class.java)
            )
        )
    )
) {
    viewModel.onIsLoadingState.value.let {
        if (it) {
            Toast.makeText(navHostController.context,"Loading",Toast.LENGTH_SHORT).show()
        }
    }
    viewModel.onShowMessageState.value.let {
        if (it.isNotEmpty()) {
            Toast.makeText(navHostController.context,it,Toast.LENGTH_SHORT).show()
            Log.wtf("Message",it)
        }
    }
    viewModel.onNoteListResponse.value.let {
        Log.wtf("NOTES",it.toString())
    }
}