package com.daud.notepad.base

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daud.notepad.utils.OperationTag
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    protected val _onShowLoadingState = mutableStateOf(false)
    val onIsLoadingState: State<Boolean> = _onShowLoadingState

    val onShowMessageState = mutableStateOf<String?>(null)

    protected fun executeSuspendedFlow(
        operationTag: OperationTag,
        haveToCollectAll: Boolean = false,
        suspendedFlow: suspend () -> Flow<Any?>?
    ) {
        viewModelScope.launch {
            suspendedFlow()?.onStart {
                _onShowLoadingState.value = true
            }?.catch {
                _onShowLoadingState.value = false
                //onShowMessageState.value = postMessage(it.localizedMessage)
                forThisApiOnlyCallingThis(operationTag, it)
            }?.onCollectFlow(haveToCollectAll) { result: Any? ->
                _onShowLoadingState.value = false
                when (result) {
                    null -> onShowMessageState.value = postMessage(null)
                    else -> {
                        onSucceessCollectFlow(operationTag, result)
                    }
                }
            }
        }
    }

    private suspend fun Flow<Any?>.onCollectFlow(
        haveToCollectAll: Boolean,
        onCollectFlow: (Any?) -> Unit
    ) = when (haveToCollectAll) {
        true -> collect { onCollectFlow.invoke(it) }
        false -> collectLatest { onCollectFlow.invoke(it) }
    }

    private fun postMessage(message: String?): String = message ?: "Something went wrong!"

    protected abstract fun onSucceessCollectFlow(operationTag: OperationTag, resultResponse: Any)


    // this is not required
    private fun forThisApiOnlyCallingThis(operationTag: OperationTag, throwable: Throwable) {
        when (operationTag) {
            OperationTag.GetNotes -> {
                onShowMessageState.value = postMessage(throwable.localizedMessage)
            }
            else -> {
                onSucceessCollectFlow(operationTag, "")
            }
        }
    }
}