package com.example.h260210.kotroomrxlivemain.model.listeners

import com.example.h260210.kotroomrxlivemain.model.User

interface ISearchUserListener {
    fun onUserFound(user: User)
    fun onUserNotFound(message: String)
}