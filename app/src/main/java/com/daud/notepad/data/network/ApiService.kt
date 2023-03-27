package com.daud.notepad.data.network

import com.daud.notepad.data.model.Note
import com.daud.notepad.data.model.NoteResponse
import retrofit2.http.*

interface ApiService {

    @GET("todo/")
    suspend fun getNotes(): List<NoteResponse?>?

    @POST("todo/")
    suspend fun addNotes(
        @Body note: Note
    ): NoteResponse?

    @PUT("todo/{id}/")
    suspend fun updateNote(
        @Path("id") id: Int,
        @Body note: Note
    ): NoteResponse?

    @DELETE("todo/{id}/")
    suspend fun deleteNote(
        @Path("id") id: Int
    ): NoteResponse?
}