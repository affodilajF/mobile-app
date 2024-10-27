package com.example.myapplication.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.local.dao.PersonDao
//import com.example.myapplication.data.local.dao.PersonDao
import com.example.myapplication.data.local.model.PersonEntity

@Database(entities = [PersonEntity::class], version = 4,  exportSchema = false)
abstract class PersonDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
}