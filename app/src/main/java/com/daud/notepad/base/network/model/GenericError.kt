package com.daud.notepad.base.network.model

data class GenericError (
    val status: Throwable,
    val messages: List<String>
)
