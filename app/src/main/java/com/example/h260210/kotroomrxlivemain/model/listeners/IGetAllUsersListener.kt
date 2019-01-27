package com.example.h260210.kotroomrxlivemain.model.listeners

import com.example.h260210.kotroomrxlivemain.model.User

interface IGetAllUsersListener {

    fun onAllUsersPopulated(users: MutableList<User>)
    fun onGetAllUsersError(errorMessage: String)
}