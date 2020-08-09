package com.darshan.coroutineexample.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val userName: String,
    @ColumnInfo(name = "password_hash")
    val password: Int,
    val address: String,
    val info: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}