package com.example.h260210.kotroomrxlivemain.model.listeners

import com.example.h260210.kotroomrxlivemain.model.User

interface IAddUserListener {
    fun onUserAdded(user: User)
    fun onAddUserError(errorMessage: String)
}