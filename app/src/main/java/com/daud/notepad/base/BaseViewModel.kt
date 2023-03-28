package com.daud.notepad.base

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daud.notepad.utils.OperationTag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    protected val _onShowLoadingState = mutableStateOf(false)
    val onIsLoadingState: State<Boolean> = _onShowLoadingState

    protected val _onShowMessageState = mutableStateOf("")
    val onShowMessageState: State<String> = _onShowMessageState

    protected fun executeSuspendedFlow(
        operationTag: OperationTag,
        suspendedFlow: suspend () -> Flow<Any?>?
    ) {
        viewModelScope.launch() {
            suspendedFlow()?.onStart {
                _onShowLoadingState.value = true
            }?.catch {
                _onShowLoadingState.value = false
                _onShowMessageState.value = it.localizedMessage ?: "Something went wrong!"
            }?.collect {
                _onShowLoadingState.value = false
                when(it) {
                    null -> _onShowMessageState.value = "Something went wrong!"
                    else -> onSuccessCollectFlow(operationTag, it)
                }
            }
        }
    }

    protected abstract fun onSuccessCollectFlow(
        operationTag: OperationTag,
        resultData: Any
    )
}