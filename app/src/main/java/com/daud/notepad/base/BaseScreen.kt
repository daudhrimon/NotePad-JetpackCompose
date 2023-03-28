package com.daud.notepad.base

import android.content.Context
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.window.Dialog
import com.daud.notepad.ui.theme.Red
import com.daud.notepad.utils.showToast

@Composable
fun BaseScreen(
    context: Context,
    onIsLoadingState: State<Boolean>?,
    onShowMessageState: State<String>?
) {
    onIsLoadingState?.value?.let {
        if (it) {
            Dialog(onDismissRequest = { }) {
                CircularProgressIndicator(color = Red)
            }
        }
    }

    onShowMessageState?.value?.let {
        if (it.isNotEmpty()) {
            context.showToast(it)
        }
    }
}