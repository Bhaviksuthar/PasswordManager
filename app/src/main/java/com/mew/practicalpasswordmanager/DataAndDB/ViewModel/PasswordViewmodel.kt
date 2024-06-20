package com.mew.practicalpasswordmanager.DataAndDB.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mew.practicalpasswordmanager.DataAndDB.PasswordModel
import com.mew.practicalpasswordmanager.DataAndDB.Repository.PasswordRepository

class PasswordViewmodel(application: Application) : AndroidViewModel(application){

    private val _passwords = MutableLiveData<List<PasswordModel>?>()
    val passwords: LiveData<List<PasswordModel>> get() = repository.getAllData


    var repository = PasswordRepository(application)
    var getAllData = repository.getAllData

    fun insert(passwordModel: PasswordModel){
        repository.add(passwordModel)
    }

    fun update(passwordModel: PasswordModel){
        repository.update(passwordModel)
    }

    fun delete(id : Int){
        repository.delete(id)
    }

    fun getAllData2() : List<PasswordModel>{
        return repository.getAllData2()
    }


    private fun loadPasswords() {
        // Load passwords from your data source
        val loadedPasswords =  passwords// Replace with actual data loading logic
        _passwords.value = loadedPasswords.value
    }

    fun updatePassword(updatedPassword: PasswordModel) {
        // Perform update logic
        // Update the LiveData after the operation
        val updatedList = _passwords.value?.map {
            if (it.id == updatedPassword.id) updatedPassword else it
        }
        _passwords.value = updatedList
    }

    fun deletePassword(id: Int) {
        // Perform delete logic
        // Update the LiveData after the operation
        val updatedList = _passwords.value?.filter { it.id != id }
        _passwords.value = updatedList
    }
}