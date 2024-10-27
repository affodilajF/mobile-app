package com.example.myapplication.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.myapplication.data.local.model.PersonEntity

@Composable
fun PersonDialog(person: PersonEntity?, onConfirm: () -> Unit) {
    if (person != null) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text(text = "Already in database!") },
            text = {
                Column {
                    Text(text = "NIK: ${person.nik}")
                    Text(text = "Name: ${person.name}")
                    Text(text = "Phone: ${person.phone}")
                    Text(text = "Date: ${person.date}")
                    Text(text = "Address: ${person.address}")
                    Text(text = "Gender: ${person.gender}")
                    person.imageUri?.let { uri ->
                        Image(
                            painter = rememberImagePainter(uri),
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                    }
                }
            },
            confirmButton = {
                Button(onClick = { onConfirm() }) {
                    Text("OK")
                }
            },
            dismissButton = {
            }
        )
    }
}