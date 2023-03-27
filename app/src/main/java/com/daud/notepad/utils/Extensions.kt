package com.daud.notepad.utils

import android.content.Context
import android.widget.Toast

fun Context.showToast(
    toast: String,
    duration: Int = Toast.LENGTH_SHORT
) = Toast.makeText(this, toast, duration).show()