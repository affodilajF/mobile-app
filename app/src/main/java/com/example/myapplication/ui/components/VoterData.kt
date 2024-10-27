package com.example.myapplication.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.myapplication.data.local.model.PersonEntity

@Composable
fun VoterData(item: PersonEntity, expandedState: MutableState<MutableMap<Long, Boolean>>) {
    val isExpanded = expandedState.value[item.id] ?: false

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)
        .border(
            width = 1.dp,
            color = if (isExpanded) Color.Blue else Color.Gray,
            shape = MaterialTheme.shapes.medium
        )
        .background(color = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expandedState.value = expandedState.value.toMutableMap().apply {
                        this[item.id] = !isExpanded
                    }
                }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = item.name)
            Icon(
                imageVector = if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = null,
                modifier = Modifier.padding(start = 8.dp)
            )
        }


        if (isExpanded) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "NIK: ${item.nik}")
                Text(text = "Phone: ${item.phone}")
                Text(text = "Date: ${item.date}")
                Text(text = "Address: ${item.address}")
                Text(text = "Gender: ${item.gender}")
                Text("Photo : ")

                item.imageUri?.let { uri ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current)
                                    .data(uri)
                                    .apply {
                                        crossfade(true)
                                    }
                                    .build()
                            ),
                            contentDescription = "Selected Image",
                            modifier = Modifier.size(100.dp)
                        )
                    }
                }
            }
        }
    }
}