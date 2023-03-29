package com.daud.notepad.base

import androidx.compose.runtime.MutableState

interface BaseEventListener {
    fun onBaseEvent(baseProgressLoader: Unit? = null, onShowMessageState: MutableState<String?>?)
}