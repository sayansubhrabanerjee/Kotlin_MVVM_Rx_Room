package com.example.h260210.kotroomrxlivemain.view


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.h260210.kotroomrxlivemain.R
import com.example.h260210.kotroomrxlivemain.model.User
import com.example.h260210.kotroomrxlivemain.model.listeners.IDeleteUserListener
import com.example.h260210.kotroomrxlivemain.model.listeners.ISearchUserListener
import com.example.h260210.kotroomrxlivemain.view.extensions.showToast
import com.example.h260210.kotroomrxlivemain.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_delete_user.*
import kotlinx.android.synthetic.main.fragment_delete_user.view.*

class DeleteUserFragment : Fragment(), View.OnClickListener,
    ISearchUserListener, IDeleteUserListener {

    private var mUserViewModel: UserViewModel? = null
    private lateinit var mUsername: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mUserViewModel = ViewModelProviders.of(this@DeleteUserFragment).get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_delete_user, container, false)

        initViews(view)

        return view
    }

    private fun initViews(view: View) {
        view.button_delete_user_page_delete.setOnClickListener(this)
    }

    private fun isUserInfoValid(): Boolean {
        mUsername = editText_delete_user_page_user_name.text.toString().trim()

        return mUsername.isNotBlank()
    }

    private fun checkIfExistingUser() {
        if (isUserInfoValid()) {
            mUserViewModel!!.searchUserRx(mUsername, this)
        }
    }

    private fun deleteUser(user: User) {
        mUserViewModel!!.deleteUserRx(user, this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.button_delete_user_page_delete -> checkIfExistingUser()
        }
    }

    override fun onUserFound(user: User) {
        deleteUser(user)
    }

    override fun onUserNotFound(message: String) {
        context!!.showToast("User is not registered! $message")
    }

    override fun onUserDeleted(user: User) {
        context!!.showToast("${user.username} deleted successfully!")
    }

    override fun onDeleteUserError(errorMessage: String) {
        context!!.showToast("Error deleting User: $errorMessage")
    }
}
