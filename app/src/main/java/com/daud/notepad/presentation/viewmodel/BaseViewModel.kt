package com.daud.notepad.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    protected val _onIsLoadingState = mutableStateOf(false)
    val onIsLoadingState: State<Boolean> = _onIsLoadingState

    protected val _onShowMessageState = mutableStateOf("")
    val onShowMessageState: State<String> = _onShowMessageState

    protected fun executedSuspendedFlow(
        operationTag: String,
        codeBlock: suspend () -> Flow<Any?>?
    ) {
        viewModelScope.launch() {
            codeBlock()?.onStart {
                _onIsLoadingState.value = true
            }?.catch {
              _onShowMessageState.value = it.localizedMessage ?: "Something went wrong"
            }?.collect {
                onCollectFlow(operationTag,it)
            }
        }
    }

    protected abstract fun onCollectFlow(
        operationTag: String,
        resultData: Any?
    )
}