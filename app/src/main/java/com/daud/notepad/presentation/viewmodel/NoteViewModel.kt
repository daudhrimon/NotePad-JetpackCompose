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

    private fun getNotes() {
        viewModelScope.launch {
            repository.getNotes().onStart {
                _onIsLoadingState.value = true
            }.catch {
                _onShowMessageState.value = it.localizedMessage ?: errorPlaceHolder
            }.collect {
                _onNoteListResponse.value = it
            }
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            repository.addNote(note = note).onStart {
                _onIsLoadingState.value = true
            }.catch {
                _onShowMessageState.value = it.localizedMessage ?: errorPlaceHolder
            }.collect {
                getNotes()
                _onShowMessageState.value = "Note Added"
            }
        }
    }

    fun updateNote(id: Int, note: Note) {
        viewModelScope.launch {
            repository.updateNote(id = id, note = note).onStart {
                _onIsLoadingState.value = true
            }.catch {
                _onShowMessageState.value = it.localizedMessage ?: errorPlaceHolder
            }.collect {
                getNotes()
                _onShowMessageState.value = "Note Updated"
            }
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch {
            repository.deleteNote(id = id).onStart {
                _onIsLoadingState.value = true
            }.catch {
                _onShowMessageState.value = it.localizedMessage ?: errorPlaceHolder
            }.collect {
                getNotes()
                _onShowMessageState.value = "Note Deleted"
            }
        }
    }
}

class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java))
            return NoteViewModel(repository) as T
        throw java.lang.IllegalArgumentException("Unknown ViewModel Class")
    }
}