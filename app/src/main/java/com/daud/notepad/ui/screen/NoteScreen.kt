package com.daud.notepad.ui.screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.daud.notepad.base.BaseEventHandler
import com.daud.notepad.ui.viewmodel.NoteViewModel

@Composable
fun NoteScreen(
    navHostController: NavHostController,
    context: Context = LocalContext.current,
    viewModel: NoteViewModel? = null
) { BaseEventHandler(viewModel?.onIsLoadingState, viewModel?.onShowMessageState)

}