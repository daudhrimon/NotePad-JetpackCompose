package com.daud.notepad.data.repository

import com.daud.notepad.base.network.model.GenericResponse
import com.daud.notepad.data.model.Note
import com.daud.notepad.data.model.NoteResponse
import com.daud.notepad.data.network.ApiService
import com.daud.notepad.data.network.KtorService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NoteRepository(
    private val apiService: ApiService,
    private val ktorService: KtorService? = null
) {
    suspend fun getNotes(): Flow<GenericResponse<List<NoteResponse?>?>> = flow {
        emit(apiService.getNotes())
    }.flowOn(Dispatchers.IO)

    suspend fun addNote(note: Note): Flow<NoteResponse?> = flow {
        emit(apiService.addNotes(note = note))
    }.flowOn(Dispatchers.IO)

    suspend fun deleteNote(id: Int): Flow<NoteResponse?> = flow {
        emit(apiService.deleteNote(id = id))
    }.flowOn(Dispatchers.IO)

    suspend fun updateNote(id: Int, note: Note): Flow<NoteResponse?> = flow {
        emit(apiService.updateNote(id = id, note = note))
    }.flowOn(Dispatchers.IO)
}