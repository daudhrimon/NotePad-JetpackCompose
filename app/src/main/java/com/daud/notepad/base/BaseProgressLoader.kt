package com.daud.notepad.base

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.window.Dialog
import com.daud.notepad.ui.theme.Red

@Composable
fun BaseProgressLoader(
    onIsLoadingState: State<Boolean>?
) {
    onIsLoadingState?.value?.let {
        if (it) {
            Dialog(onDismissRequest = { }) {
                CircularProgressIndicator(color = Red)
            }
        }
    }
}