package com.mew.practicalpasswordmanager.DataAndDB.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.mew.practicalpasswordmanager.DataAndDB.PasswordDB
import com.mew.practicalpasswordmanager.DataAndDB.PasswordDao
import com.mew.practicalpasswordmanager.DataAndDB.PasswordModel

class PasswordRepository(context: Context) {

    var db : PasswordDB = PasswordDB.getInstance(context)
    var dao : PasswordDao = db.getDao()

    var getAllData : LiveData<List<PasswordModel>> = dao.getAllData()

    fun add(passwordModel: PasswordModel){
        dao.add(passwordModel)
    }

    fun update(passwordModel: PasswordModel){
        dao.update(passwordModel)
    }

    fun delete(id : Int){
        dao.delete(id)
    }

    fun getAllData2() : List<PasswordModel>{
       return dao.getAllData2()
    }
}