package com.daud.notepad.base.network.model

import com.daud.notepad.base.model.BaseRestResponse
import java.io.IOException

sealed class BaseResponse<out T : Any, out U : Any> {
    data class Loading(val isLoading : Boolean) : BaseResponse<Nothing, Nothing>()
    data class Success<T : Any>(val body: T) : BaseResponse<T, Nothing>()

    data class ApiError<U : Any>(val errorBody: U, val code: Int) : BaseResponse<Nothing, U>()

    data class NetworkError(val error: IOException) : BaseResponse<Nothing, Nothing>()

    data class UnknownError(val error: Throwable?) : BaseResponse<Nothing, Nothing>()
}

typealias GenericResponse<S> = BaseResponse<S, GenericError>

typealias GenericBaseRestResponse<S> = BaseResponse<BaseRestResponse<S>, GenericError>