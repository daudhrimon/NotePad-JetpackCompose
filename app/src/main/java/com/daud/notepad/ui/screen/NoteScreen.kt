package com.daud.notepad.ui.screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.daud.notepad.base.BaseMessageEventListener
import com.daud.notepad.ui.viewmodel.NoteViewModel

@Composable
fun NoteScreen(
    navHostController: NavHostController,
    baseMessageEventListener: BaseMessageEventListener,
    viewModel: NoteViewModel? = null
) { //BaseProgressLoader(viewModel?.onIsLoadingState, viewModel?.onShowMessageState)

}