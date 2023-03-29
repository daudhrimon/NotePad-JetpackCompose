package com.daud.notepad.base

import androidx.compose.runtime.MutableState

interface BaseMessageEventListener {
    fun onShowMessageEvent(onShowMessageState: MutableState<String?>)
}