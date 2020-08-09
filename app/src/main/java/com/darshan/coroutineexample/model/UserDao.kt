package com.darshan.coroutineexample.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Query("SELECT * from user WHERE userName=:userName")
    suspend fun getUser(userName: String): User

    @Query("DELETE FROM user WHERE id=:id")
    suspend fun deleteUser(id: Long)

}