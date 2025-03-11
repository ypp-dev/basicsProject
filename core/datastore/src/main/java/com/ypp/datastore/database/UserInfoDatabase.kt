package com.ypp.datastore.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ypp.datastore.UserInfo
import com.ypp.datastore.dao.UserDao

@Database(entities = [UserInfo::class], version = 1)
abstract class UserInfoDatabase : RoomDatabase() {
    abstract fun userDao():UserDao
}