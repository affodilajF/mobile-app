package com.example.myapplication.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PersonEntity")
data class PersonEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nik: String,
    val name: String,
    val phone: String,
    val date: String, // Format: yyyy-MM-dd
    val address: String,
    val gender: String,
    val imageUri: String?
)

