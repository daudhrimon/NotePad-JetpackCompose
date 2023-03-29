package com.daud.notepad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.navigation.compose.rememberNavController
import com.daud.notepad.base.BaseEventListener
import com.daud.notepad.navigation.NavGraph
import com.daud.notepad.ui.theme.NotePadComposeTheme
import com.daud.notepad.utils.showToast

class MainActivity : ComponentActivity(), BaseEventListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotePadComposeTheme {
                NavGraph(navHostController = rememberNavController(),this)
            }
        }
    }

    override fun onBaseEvent(baseProgressLoader: Unit?, onShowMessageState: MutableState<String?>?) {
        onShowMessageState?.value?.let {
            if (it.isNotEmpty()) {
                this.showToast(it)
                onShowMessageState.value = null
            }
        }
    }
}