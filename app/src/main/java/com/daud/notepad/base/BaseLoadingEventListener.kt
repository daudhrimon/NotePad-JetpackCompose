package com.daud.notepad.base

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import com.daud.notepad.ui.theme.Red
import com.daud.notepad.utils.showToast

@Composable
fun BaseLoadingEventListener(
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