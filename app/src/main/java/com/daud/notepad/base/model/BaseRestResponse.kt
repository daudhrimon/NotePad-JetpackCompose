package com.daud.notepad.base.model

data class BaseRestResponse<T> (
    val code: Int,
    val messages: List<String>,
    val data: T?
)
