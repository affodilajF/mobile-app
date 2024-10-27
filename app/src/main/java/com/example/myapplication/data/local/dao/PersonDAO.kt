package com.example.myapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.myapplication.data.local.model.PersonEntity

//import com.example.myapplication.data.local.model.PersonEntity

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(person: PersonEntity)

    @Update
    suspend fun update(person: PersonEntity)

    @Delete
    suspend fun delete(person: PersonEntity)

    @Query("SELECT * FROM PersonEntity ORDER BY id ASC")
    suspend fun getAllPersons(): List<PersonEntity>

    @Query("SELECT * FROM PersonEntity WHERE id = :personId LIMIT 1")
    suspend fun getPersonById(personId: Long): PersonEntity?

    @Query("DELETE FROM PersonEntity")
    suspend fun deleteAll()

    @Query("SELECT * FROM PersonEntity WHERE nik = :nik LIMIT 1")
    suspend fun getPersonByNIK(nik: String): PersonEntity?
}
