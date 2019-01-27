package com.example.h260210.kotroomrxlivemain.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.h260210.kotroomrxlivemain.model.User
import com.example.h260210.kotroomrxlivemain.model.UserRepository
import com.example.h260210.kotroomrxlivemain.model.listeners.*

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private var mUserRepository: UserRepository = UserRepository(application)
    var mGetAllUsersLiveData: LiveData<MutableList<User>>

    init {
        mGetAllUsersLiveData = mUserRepository.mLiveDataUsers
    }

    fun getAllUsersRx(listener: IGetAllUsersListener) {
        mUserRepository.getAllUsersRx(listener)
    }

    fun addUserRx(user: User, listener: IAddUserListener) {
        mUserRepository.insertUserRx(user, listener)
    }

    fun searchUserRx(username: String, listener: ISearchUserListener) {
        mUserRepository.searchUserRx(username, listener)
    }

    fun updateUserRx(user: User, listener: IUpdateUserListener) {
        mUserRepository.updateUserRx(user, listener)
    }

    fun deleteUserRx(user: User, listener: IDeleteUserListener) {
        mUserRepository.deleteUserRx(user, listener)
    }

    fun deleteAllUsersRx(listener: IDeleteAllUsersListener) {
        mUserRepository.deleteAllUsersRx(listener)
    }


    fun addUserLiveData(user: User) {
        mUserRepository.addUserThroughAsyncTask(user)
    }
}