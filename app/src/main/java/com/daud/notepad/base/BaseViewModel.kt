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
        haveToCollectAll: Boolean = false,
        suspendedFlow: suspend () -> Flow<Any?>?
    ) {
        viewModelScope.launch {
            suspendedFlow()?.apply {
                onStart {
                    _onShowLoadingState.value = true
                }
                catch {
                    _onShowLoadingState.value = false
                    //onShowMessageState.value = postMessage(it.localizedMessage)
                    forThisApiOnlyCallingThis(operationTag, it)
                }
                when (haveToCollectAll) {
                    true -> collect { onCollectFlow(operationTag, it) }
                    false -> collectLatest { onCollectFlow(operationTag, it) }
                }
            }
        }
    }

    private fun postMessage(message: String?): String = message ?: "Something went wrong!"

    private fun onCollectFlow(operationTag: OperationTag, result: Any?) {
        _onShowLoadingState.value = false
        when (result) {
            null -> onShowMessageState.value = postMessage(null)
            else -> onSuccessCollectFlow(operationTag, result)
        }
    }

    protected abstract fun onSuccessCollectFlow(operationTag: OperationTag, resultData: Any)


    // this is not required
    private fun forThisApiOnlyCallingThis(operationTag: OperationTag, throwable: Throwable) {
        when (operationTag) {
            OperationTag.GetNotes -> {
                onShowMessageState.value = postMessage(throwable.localizedMessage)
            }
            else -> {
                onSuccessCollectFlow(operationTag, "")
            }
        }
    }
}