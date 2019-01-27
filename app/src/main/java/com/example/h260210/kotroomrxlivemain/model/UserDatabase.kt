package com.example.h260210.kotroomrxlivemain.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context


@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): IUserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase? {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: createUserDatabase(context).also { INSTANCE = it }
            }
        }

        private fun createUserDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, UserDatabase::class.java, "user_database")
                .fallbackToDestructiveMigration()
                .build()

        private fun destroyInstance() {
            INSTANCE = null
        }
    }
}