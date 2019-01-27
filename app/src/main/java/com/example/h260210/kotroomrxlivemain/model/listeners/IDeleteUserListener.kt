package com.example.h260210.kotroomrxlivemain.model.listeners

import com.example.h260210.kotroomrxlivemain.model.User

interface IDeleteUserListener {
    fun onUserDeleted(user: User)
    fun onDeleteUserError(errorMessage: String)
}