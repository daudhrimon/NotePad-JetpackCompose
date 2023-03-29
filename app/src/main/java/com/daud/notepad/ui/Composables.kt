package com.daud.notepad.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.daud.notepad.R
import com.daud.notepad.data.model.NoteResponse
import com.daud.notepad.ui.theme.ContentBgColor
import com.daud.notepad.ui.theme.Red

@Composable
fun SearchBar(
    searchValue: String,
    onSearchValueChange: (String) -> Unit
) {
    TextField(
        value = searchValue,
        onValueChange = onSearchValueChange,
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(50.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = ContentBgColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.search_notes),
                style = TextStyle(
                    color = Color.DarkGray, fontSize = 14.sp, fontWeight = FontWeight.Normal
                )
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search, contentDescription = "", tint = Red
            )
        },
        trailingIcon = {
            if (searchValue.isNotEmpty()) {
                IconButton(
                    onClick = { onSearchValueChange("") }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Close, contentDescription = "", tint = Red
                    )
                }
            }
        }
    )
}

@Composable
fun NoteEachRow(
    noteItem: NoteResponse?,
    onUpdateClickListener: () -> Unit,
    onDeleteClickListener: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(ContentBgColor)
            .clickable(onClick = { onUpdateClickListener() })
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = noteItem?.title ?: "",
                    style = TextStyle(
                        color = Color.Black, fontSize = 22.sp, fontWeight = FontWeight.W600
                    ),
                    modifier = Modifier
                        .weight(0.7f)
                        .align(Alignment.CenterVertically)
                )

                IconButton(
                    onClick = { noteItem?.id?.let { onDeleteClickListener.invoke() } },
                    modifier = Modifier.weight(0.3f)
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "", tint = Red)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = noteItem?.description ?: "",
                style = TextStyle(
                    color = Color.Black.copy(alpha = 0.6f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            )

            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = noteItem?.updated_at ?: "T".split("T")[0],
                style = TextStyle(
                    color = Color.Black.copy(alpha = 0.3f),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }

}