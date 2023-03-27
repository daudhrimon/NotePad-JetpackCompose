package com.daud.notepad.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.daud.notepad.data.model.Note
import com.daud.notepad.data.model.NoteResponse
import com.daud.notepad.data.repository.NoteRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : BaseViewModel() {
    private val _onNoteListResponse = mutableStateOf<List<NoteResponse?>?>(null)
    val onNoteListResponse: State<List<NoteResponse?>?> = _onNoteListResponse

    init {
        getNotes()
    }

    override fun onCollectFlow(operationTag: String, resultData: Any?) {
        when(operationTag) {
            "getNotes"-> {
                _onNoteListResponse.value = resultData as List<NoteResponse?>?
            }
            "addNote"-> {
                getNotes()
            }
            "updateNote"-> {
                getNotes()
            }
            ""-> {
                getNotes()
            }
        }
    }

    private fun getNotes() {
        executedSuspendedFlow("getNotes"){ repository.getNotes() }
    }

    fun addNote(note: Note) {
        executedSuspendedFlow("addNote"){ repository.addNote(note) }
    }

    fun updateNote(id: Int, note: Note) {
        executedSuspendedFlow("updateNote") { repository.updateNote(id = id, note = note)}
    }

    fun deleteNote(id: Int) {
        executedSuspendedFlow("deleteNote") { repository.deleteNote(id = id) }
    }
}

class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java))
            return NoteViewModel(repository) as T
        throw java.lang.IllegalArgumentException("Unknown ViewModel Class")
    }
}