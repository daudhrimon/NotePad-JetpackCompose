package com.daud.notepad.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daud.notepad.base.BaseApiService
import com.daud.notepad.base.BaseMessageEventListener
import com.daud.notepad.base.BaseLoadingEventListener
import com.daud.notepad.data.model.NoteResponse
import com.daud.notepad.data.network.ApiService
import com.daud.notepad.data.repository.NoteRepository
import com.daud.notepad.navigation.NavRoute
import com.daud.notepad.ui.NoteEachRow
import com.daud.notepad.ui.SearchBar
import com.daud.notepad.ui.theme.BgColor
import com.daud.notepad.ui.theme.Red
import com.daud.notepad.ui.viewmodel.NoteViewModel
import com.daud.notepad.ui.viewmodel.NoteViewModelFactory
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DashboardScreen(
    navHostController: NavHostController,
    baseMessageEventListener: BaseMessageEventListener,
    viewModel: NoteViewModel = viewModel(
        factory = NoteViewModelFactory(
            NoteRepository(
                BaseApiService.generate(ApiService::class.java)
            )
        )
    )
) { BaseLoadingEventListener(viewModel.onIsLoadingState)
    baseMessageEventListener.onShowMessageEvent(viewModel.onShowMessageState)

    LaunchedEffect(key1 = true) {
        viewModel.attemptGetNotes()
    }

    val searchValue = remember { mutableStateOf("") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navHostController.navigate(NavRoute.NoteScreen.route)
                },
                backgroundColor = Red
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "", tint = Color.White)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BgColor)
        ) {
            SearchBar(
                searchValue = searchValue.value,
                onSearchValueChange = {searchValue.value = it}
            )

            viewModel.onNoteListResponse.value?.let { noteList->
                val filterList = remember { mutableStateOf(listOf<NoteResponse?>()) }

                filterList.value = if (searchValue.value.isNotEmpty()) {
                    noteList.filter {
                        searchValue.value.lowercase() == it?.title?.lowercase() || searchValue.value == it?.description?.lowercase()
                    }
                } else {
                    noteList
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(items = filterList.value) {
                        NoteEachRow(
                            noteItem = it,
                            onUpdateClickListener = {
                            },
                            onDeleteClickListener = {
                                it?.id?.let { id -> viewModel.attemptDeleteNote(id) }
                            }
                        )
                    }
                }
            }
        }
    }
}