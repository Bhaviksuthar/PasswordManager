package com.mew.practicalpasswordmanager.DataAndDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PasswordModel::class], version = 2, exportSchema = false)
abstract class PasswordDB : RoomDatabase(){

    abstract fun getDao() : PasswordDao

    companion object{
        var INSTANCE : PasswordDB? = null

        @Synchronized
        fun getInstance(context: Context) : PasswordDB{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context,PasswordDB::class.java,"PassDB")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }
}