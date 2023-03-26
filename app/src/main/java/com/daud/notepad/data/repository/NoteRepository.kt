package com.daud.notepad.data.repository

import com.daud.notepad.data.model.Note
import com.daud.notepad.data.model.NoteResponse
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun addNote(note: Note): Flow<NoteResponse>

    suspend fun getNotes(): Flow<List<NoteResponse>>

    suspend fun deleteNote(id: Int): Flow<NoteResponse>

    suspend fun updateNote(id: Int,note: Note): Flow<NoteResponse>
}