package com.example.h260210.kotroomrxlivemain.model

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.example.h260210.kotroomrxlivemain.model.listeners.*
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class UserRepository(application: Application) {

    private var mUserDao: IUserDao
    var mLiveDataUsers: LiveData<MutableList<User>>

    init {
        val userDatabase = UserDatabase.getInstance(application)
        mUserDao = userDatabase!!.userDao()
        mLiveDataUsers = mUserDao.getAllUsersLiveData()
    }

    fun getAllUsersRx(listener: IGetAllUsersListener) =
        mUserDao.getAllUsersRx()
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                listener.onAllUsersPopulated(it)
            }, {
                listener.onGetAllUsersError(it.message!!)
            })!!


    fun insertUserRx(user: User, listener: IAddUserListener) {
        Completable.fromAction { mUserDao.insertUserRx(user) }
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    listener.onUserAdded(user)
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    listener.onAddUserError(e.message!!)
                }
            })
    }

    fun searchUserRx(username: String, listener: ISearchUserListener) =
        mUserDao.searchUser(username)
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                listener.onUserFound(it)
            }, {
                listener.onUserNotFound(it.message!!)
            })!!


    fun updateUserRx(user: User, listener: IUpdateUserListener) {
        Completable.fromAction { mUserDao.updateUser(user) }
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    listener.onUserUpdated(user)
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    listener.onUpdateUserError(e.message!!)
                }
            })
    }


    fun deleteUserRx(user: User, listener: IDeleteUserListener) {
        Completable.fromAction { mUserDao.deleteUser(user) }
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    listener.onUserDeleted(user)
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    listener.onDeleteUserError(e.message!!)
                }
            })
    }


    fun deleteAllUsersRx(listener: IDeleteAllUsersListener) {
        Completable.fromAction { mUserDao.deleteAllUsers() }
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    listener.onAllUsersDeleted()
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    listener.onDeleteAllUsersError(e.message!!)
                }
            })
    }


    fun addUserThroughAsyncTask(user: User) {
        InsertUserAsyncTask(mUserDao).execute(user)
    }

    private class InsertUserAsyncTask internal constructor(private val mAsyncTaskUserDao: IUserDao) :
        AsyncTask<User, Void, Void>() {

        override fun doInBackground(vararg users: User): Void? {
            mAsyncTaskUserDao.insertUserLiveData(users[0])
            return null
        }
    }
}