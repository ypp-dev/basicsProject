package com.ypp.datastore.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ypp.datastore.UserInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM userinfo")
    fun getUsers():Flow<List<UserInfo>>

//    fun findByName(name:String):List<UserInfo>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserInfo(userInfo: UserInfo)
}