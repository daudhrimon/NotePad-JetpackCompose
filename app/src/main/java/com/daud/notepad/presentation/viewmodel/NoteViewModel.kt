package com.daud.notepad.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daud.notepad.data.model.Note
import com.daud.notepad.data.model.NoteResponse
import com.daud.notepad.data.repository.NoteRepository
import com.daud.notepad.utils.OperationTags

class NoteViewModel(private val repository: NoteRepository) : BaseViewModel() {
    private val _onNoteListResponse = mutableStateOf<List<NoteResponse?>?>(null)
    val onNoteListResponse: State<List<NoteResponse?>?> = _onNoteListResponse

    init {
        attemptGetNotes()
    }

    override fun onSuccessCollectFlow(operationTag: String, resultData: Any) {
        when(operationTag) {
            OperationTags.GetNotes.TAG-> {
                _onNoteListResponse.value = resultData as List<NoteResponse?>?
            }
            OperationTags.AddNote.TAG-> {
                attemptGetNotes()
                _onShowMessageState.value = OperationTags.AddNote.TAG
            }
            OperationTags.UpdateNote.TAG-> {
                attemptGetNotes()
                _onShowMessageState.value = OperationTags.UpdateNote.TAG
            }
            OperationTags.DeleteNote.TAG-> {
                attemptGetNotes()
                _onShowMessageState.value = OperationTags.DeleteNote.TAG
            }
        }
    }

    private fun attemptGetNotes() {
        executeSuspendedFlow(OperationTags.GetNotes.TAG){ repository.getNotes() }
    }

    fun attemptAddNote(note: Note) {
        executeSuspendedFlow(OperationTags.AddNote.TAG){ repository.addNote(note) }
    }

    fun attemptUpdateNote(id: Int, note: Note) {
        executeSuspendedFlow(OperationTags.UpdateNote.TAG) { repository.updateNote(id = id, note = note)}
    }

    fun attemptDeleteNote(id: Int) {
        executeSuspendedFlow(OperationTags.DeleteNote.TAG) { repository.deleteNote(id = id) }
    }
}

class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java))
            return NoteViewModel(repository) as T
        throw java.lang.IllegalArgumentException("Unknown ViewModel Class")
    }
}