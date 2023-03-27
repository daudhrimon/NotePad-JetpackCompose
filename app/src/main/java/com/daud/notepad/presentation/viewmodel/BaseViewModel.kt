package com.daud.notepad.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    protected val errorPlaceHolder =  "Something went wrong"

    protected val _onIsLoadingState = mutableStateOf(false)
    val onIsLoadingState: State<Boolean> = _onIsLoadingState

    protected val _onShowMessageState = mutableStateOf("")
    val onShowMessageState: State<String> = _onShowMessageState
}