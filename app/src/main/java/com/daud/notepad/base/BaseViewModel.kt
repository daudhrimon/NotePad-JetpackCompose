package com.daud.notepad.base

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daud.notepad.utils.OperationTag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    protected val _onShowLoadingState = mutableStateOf(false)
    val onIsLoadingState: State<Boolean> = _onShowLoadingState

    val onShowMessageState = mutableStateOf<String?>(null)

    protected fun executeSuspendedFlow(
        operationTag: OperationTag,
        suspendedFlow: suspend () -> Flow<Any?>?
    ) {
        viewModelScope.launch() {
            suspendedFlow()?.onStart {
                _onShowLoadingState.value = true
            }?.catch {
                /*_onShowLoadingState.value = false
                _onShowMessageState.value = it.localizedMessage ?: "Something went wrong!"*/
                when (operationTag) {
                    OperationTag.GetNotes -> {
                        _onShowLoadingState.value = false
                        onShowMessageState.value = it.localizedMessage ?: "Something went wrong!"
                    }
                    else -> {
                        onSuccessCollectFlow(operationTag,"")
                    }
                }
            }?.collect {
                _onShowLoadingState.value = false
                when(it) {
                    null -> onShowMessageState.value = "Something went wrong!"
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