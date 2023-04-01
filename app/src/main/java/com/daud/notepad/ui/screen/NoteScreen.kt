package com.daud.notepad.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.daud.notepad.base.BaseEventListener
import com.daud.notepad.base.BaseProgressLoader
import com.daud.notepad.ui.viewmodel.NoteViewModel

@Composable
fun NoteScreen(
    navHostController: NavHostController,
    baseEventListener: BaseEventListener,
    viewModel: NoteViewModel? = null
) {
    baseEventListener.onBaseEvent(
        baseProgressLoader = BaseProgressLoader(viewModel?.onIsLoadingState),
        onShowMessageState = viewModel?.onShowMessageState
    )

}