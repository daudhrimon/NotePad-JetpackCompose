package com.daud.notepad.data.network

import com.daud.notepad.BuildConfig
import com.daud.notepad.data.model.Note
import com.daud.notepad.data.model.NoteResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*

class KtorService(
    private val httpClient: HttpClient,
    private val baseUrl: String = BuildConfig.BASE_URL
) {
    suspend fun getNotes(): List<NoteResponse> {
        return httpClient.get {
            url(baseUrl + "todo")
        }.body()
    }

    @OptIn(InternalAPI::class)
    suspend fun addNotes(note: Note): NoteResponse {
        return httpClient.post {
            url(baseUrl + "todo/")
            contentType(ContentType.Application.Json)
            body = note
        }.body()
    }

    suspend fun deleteNote(id: Int): NoteResponse {
        return httpClient.delete {
            url(baseUrl + "todo/$id/")
        }.body()
    }

    @OptIn(InternalAPI::class)
    suspend fun updateNote(id: Int, note: Note): NoteResponse {
        return httpClient.put {
            url(baseUrl + "todo/$id/")
            contentType(ContentType.Application.Json)
            body = note
        }.body()
    }
}