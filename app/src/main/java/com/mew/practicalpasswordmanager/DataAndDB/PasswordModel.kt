package com.mew.practicalpasswordmanager.DataAndDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PasswordTable")
data class PasswordModel(
    @PrimaryKey(autoGenerate = true)
    var id : Int,

    @ColumnInfo(name = "AccountType")
    var accountType : String,

    @ColumnInfo(name = "UsernameOrGmail")
    var username_Gmail : String,

    @ColumnInfo(name = "Password")
    var password : String,

    @ColumnInfo(name = "key")
    var key : String
)