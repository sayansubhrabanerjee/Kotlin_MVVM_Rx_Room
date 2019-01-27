package com.example.h260210.kotroomrxlivemain.model.listeners

interface IDeleteAllUsersListener {
    fun onAllUsersDeleted()
    fun onDeleteAllUsersError(errorMessage: String)
}