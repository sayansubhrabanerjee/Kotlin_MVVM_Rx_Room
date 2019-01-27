package com.example.h260210.kotroomrxlivemain.view


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.h260210.kotroomrxlivemain.R
import com.example.h260210.kotroomrxlivemain.model.User
import com.example.h260210.kotroomrxlivemain.model.listeners.ISearchUserListener
import com.example.h260210.kotroomrxlivemain.model.listeners.IUpdateUserListener
import com.example.h260210.kotroomrxlivemain.view.extensions.showToast
import com.example.h260210.kotroomrxlivemain.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_update_user.*
import kotlinx.android.synthetic.main.fragment_update_user.view.*

class UpdateUserFragment : Fragment(), View.OnClickListener,
    ISearchUserListener, IUpdateUserListener {

    private var mUserViewModel: UserViewModel? = null
    private lateinit var mUserId: String
    private lateinit var mUsername: String
    private lateinit var mEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mUserViewModel = ViewModelProviders.of(this@UpdateUserFragment).get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_user, container, false)

        initViews(view)

        return view
    }

    private fun initViews(view: View) {
        view.button_update_user_page_update.setOnClickListener(this)
    }

    private fun isUserInfoValid(): Boolean {
        mUserId = editText_update_user_page_user_id.text.toString().trim()
        mUsername = editText_update_user_page_user_name.text.toString().trim()
        mEmail = editText_update_user_page_email.text.toString().trim()

        return mUserId.isNotBlank()
                && mUsername.isNotBlank()
                && mEmail.isNotBlank()
    }

    private fun checkIfExistingUser() {
        if (isUserInfoValid()) {
            mUserViewModel!!.searchUserRx(mUsername, this)
        }
    }

    private fun updateUser(user: User) {
        mUserViewModel!!.updateUserRx(user, this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.button_update_user_page_update -> checkIfExistingUser()
        }
    }

    override fun onUserFound(user: User) {
        user.userId = mUserId.toInt()
        user.emailId = mEmail

        updateUser(user)
    }

    override fun onUserNotFound(message: String) {
        context!!.showToast("User is not registered! $message")
    }

    override fun onUserUpdated(user: User) {
        context!!.showToast("${user.username} updated successfully!")
    }

    override fun onUpdateUserError(errorMessage: String) {
        context!!.showToast("Error updating User: $errorMessage")
    }
}
