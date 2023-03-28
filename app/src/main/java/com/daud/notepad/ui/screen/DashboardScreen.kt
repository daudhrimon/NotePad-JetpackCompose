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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daud.notepad.base.BaseApiService
import com.daud.notepad.base.BaseEventHandler
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
import kotlin.collections.ArrayList

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DashboardScreen(
    navHostController: NavHostController,
    context: Context = LocalContext.current,
    viewModel: NoteViewModel = viewModel(
        factory = NoteViewModelFactory(
            NoteRepository(
                BaseApiService.generate(ApiService::class.java)
            )
        )
    )
) { BaseEventHandler(viewModel.onIsLoadingState, viewModel.onShowMessageState)

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

            viewModel.onNoteListResponse.value?.let { list->
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(20.dp),
                ) {
                    val filterList: List<NoteResponse?> = if (searchValue.value.isEmpty()) {
                        list
                    } else {
                        val result: ArrayList<NoteResponse?> = arrayListOf()

                        for (temp in list) {
                            if ((temp?.title?:"").lowercase(Locale.getDefault()).contains(
                                    searchValue.value.lowercase(
                                        Locale.getDefault()
                                    )
                                ) || (temp?.description?:"").lowercase(Locale.getDefault()).contains(
                                    searchValue.value.lowercase(Locale.getDefault())
                                )
                            ) {
                                result.add(temp)
                            }
                        }
                        result
                    }

                    this.items(
                        filterList,
                        key = { it?.id?:0 }
                    ) {
                        it?.let {
                            NoteEachRow(note = it,{},{})
                        }
                    }
                }
            }
        }
    }
}