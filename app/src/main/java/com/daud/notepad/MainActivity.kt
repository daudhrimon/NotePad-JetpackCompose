package com.daud.notepad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.daud.notepad.navigation.NavGraph
import com.daud.notepad.ui.theme.NotePadComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotePadComposeTheme {
                NavGraph(navHostController = rememberNavController())
            }
        }
    }
}