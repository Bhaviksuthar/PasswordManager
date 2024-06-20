package com.mew.practicalpasswordmanager.DataAndDB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PasswordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(passwordModel: PasswordModel)

    @Update
    fun update(passwordModel: PasswordModel)

    @Query("DELETE FROM PasswordTable WHERE id=:id")
    fun delete(id : Int)

    @Query("SELECT * FROM PasswordTable")
    fun getAllData() : LiveData<List<PasswordModel>>

    @Query("SELECT * FROM PasswordTable")
    fun getAllData2() : List<PasswordModel>
}