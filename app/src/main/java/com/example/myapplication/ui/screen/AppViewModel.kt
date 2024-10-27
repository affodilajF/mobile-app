package com.example.myapplication.ui.screen

import com.example.myapplication.domain.repositories.PersonRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.model.PersonEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val personRepository: PersonRepository
) : ViewModel() {
    private val _showSuccess = MutableStateFlow(false)
    val showSuccess: StateFlow<Boolean> = _showSuccess
    private val _isPersonInDatabase = MutableStateFlow<PersonEntity?>(null)
    val isPersonInDatabase: StateFlow<PersonEntity?> = _isPersonInDatabase

    fun savePerson(nik: String, name: String, phone: String, date: String, address: String, gender: String, imageUri: String?) {
        viewModelScope.launch {

            val data = personRepository.getByNIK(nik)
            if(data == null){
                val person = PersonEntity(
                    nik = nik,
                    name = name,
                    phone = phone,
                    date = date,
                    address = address,
                    gender = gender,
                    imageUri = imageUri
                )
                personRepository.insert(person)
                _showSuccess.value = true
            } else {
                _isPersonInDatabase.value = data
            }
        }
    }

    fun falseSuccess() {
        _showSuccess.value = false
    }


    private val _persons = MutableStateFlow<List<PersonEntity>>(emptyList())
    val persons: StateFlow<List<PersonEntity>> = _persons.asStateFlow()

    fun fetchAllPersons() {
        viewModelScope.launch {
            try {
                val personList = personRepository.getAllPersons()
                _persons.value = personList
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteALlPerson(){
        viewModelScope.launch {
            personRepository.deleteAll()
        }
    }

    fun resetIsPersonInDatabase(){
        _isPersonInDatabase.value = null
    }
}


