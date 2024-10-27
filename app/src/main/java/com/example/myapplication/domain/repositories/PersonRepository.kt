package com.example.myapplication.domain.repositories

import com.example.myapplication.data.local.dao.PersonDao
import com.example.myapplication.data.local.model.PersonEntity
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.android.scopes.ViewScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@ViewModelScoped
class PersonRepository @Inject constructor(private val personDao: PersonDao) {
    suspend fun insert(person: PersonEntity) {
        personDao.insert(person)
    }

    suspend fun getAllPersons(): List<PersonEntity> {
        return personDao.getAllPersons()
    }

    suspend fun deleteAll() { // Menambahkan fungsi untuk menghapus semua data
        personDao.deleteAll()
    }

    suspend fun getByNIK(nik: String): PersonEntity? {
        return personDao.getPersonByNIK(nik)
    }
}
