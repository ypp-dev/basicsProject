package com.ypp.datastore

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserInfo(
//    @PrimaryKey (autoGenerate = true)val userid:Int,
    @PrimaryKey
    @ColumnInfo(name = "user_name")val userName:String,
    @ColumnInfo (name = "user_password")val password:String)
