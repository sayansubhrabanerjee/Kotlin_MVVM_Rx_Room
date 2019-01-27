package com.example.h260210.kotroomrxlivemain.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import io.reactivex.Flowable
import io.reactivex.Single


@Dao
interface IUserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertUserRx(user: User)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertUserLiveData(user: User)

    @Query("select * from user_table")
    fun getAllUsersRx(): Flowable<MutableList<User>>

    @Query("select * from user_table")
    fun getAllUsersLiveData(): LiveData<MutableList<User>>

    @Query("select * from user_table where user_name = :username")
    fun searchUser(username: String): Single<User>

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("delete from user_table")
    fun deleteAllUsers()
}