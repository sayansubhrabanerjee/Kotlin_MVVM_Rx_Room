package com.example.h260210.kotroomrxlivemain.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "user_table")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    var userId: Int,

    @ColumnInfo(name = "user_name")
    var username: String,

    @ColumnInfo(name = "email_id")
    var emailId: String
)