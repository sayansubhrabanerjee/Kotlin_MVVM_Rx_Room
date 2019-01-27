package com.example.h260210.kotroomrxlivemain.model.listeners

import com.example.h260210.kotroomrxlivemain.model.User

interface IUpdateUserListener {
    fun onUserUpdated(user: User)
    fun onUpdateUserError(errorMessage: String)
}