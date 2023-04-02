package com.daud.notepad.base.network.model

import java.io.IOException

sealed class BaseApiEvents<out S : Any?, out E : Any?> {

    data class Loading(val isLoading: Boolean) : BaseApiEvents<Nothing, Nothing>()

    data class Success<S : Any>(val data: S) : BaseApiEvents<S, Nothing>()

    data class ApiError<E : Any>(val errorBody: E, val code: Int) : BaseApiEvents<Nothing, E>()

    data class NetworkError(val error: IOException) : BaseApiEvents<Nothing, Nothing>()

    data class UnknownError(val error: Throwable?) : BaseApiEvents<Nothing, Nothing>()
}

typealias GenericResponse<G> = BaseApiEvents<G,Nothing>
