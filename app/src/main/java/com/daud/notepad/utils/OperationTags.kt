package com.daud.notepad.utils

sealed class OperationTags(val TAG: String) {
    object GetNotes : OperationTags("GetNotes")
    object AddNote : OperationTags("Note Added")
    object UpdateNote : OperationTags("Note Updated")
    object DeleteNote : OperationTags("Note Deleted")
}
