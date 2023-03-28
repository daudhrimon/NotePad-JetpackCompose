package com.daud.notepad.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daud.notepad.base.BaseViewModel
import com.daud.notepad.data.model.Note
import com.daud.notepad.data.model.NoteResponse
import com.daud.notepad.data.repository.NoteRepository
import com.daud.notepad.utils.OperationTag

class NoteViewModel(private val repository: NoteRepository) : BaseViewModel() {
    private val _onNoteListResponse = mutableStateOf<List<NoteResponse?>?>(null)
    val onNoteListResponse: State<List<NoteResponse?>?> = _onNoteListResponse

    init {
        attemptGetNotes()
    }

    override fun onSuccessCollectFlow(operationTag: OperationTag, resultData: Any) {
        when (operationTag) {
            OperationTag.GetNotes -> {
                _onNoteListResponse.value = resultData as List<NoteResponse?>?
            }
            OperationTag.AddNote -> {
                attemptGetNotes()
                _onShowMessageState.value = "Note Added"
            }
            OperationTag.UpdateNote -> {
                attemptGetNotes()
                _onShowMessageState.value = "Note Updated"
            }
            OperationTag.DeleteNote -> {
                attemptGetNotes()
                _onShowMessageState.value = "Note Deleted"
            }
        }
    }

    private fun attemptGetNotes() {
        executeSuspendedFlow(OperationTag.GetNotes) { repository.getNotes() }
    }

    fun attemptAddNote(note: Note) {
        executeSuspendedFlow(OperationTag.AddNote) { repository.addNote(note) }
    }

    fun attemptUpdateNote(id: Int, note: Note) {
        executeSuspendedFlow(OperationTag.UpdateNote) { repository.updateNote(id = id, note = note) }
    }

    fun attemptDeleteNote(id: Int) {
        executeSuspendedFlow(OperationTag.DeleteNote) { repository.deleteNote(id = id) }
    }
}

class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java))
            return NoteViewModel(repository) as T
        throw java.lang.IllegalArgumentException("Unknown ViewModel Class")
    }
}