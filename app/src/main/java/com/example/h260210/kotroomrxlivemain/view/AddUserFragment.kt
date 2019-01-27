package com.example.h260210.kotroomrxlivemain.view


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.h260210.kotroomrxlivemain.R
import com.example.h260210.kotroomrxlivemain.model.User
import com.example.h260210.kotroomrxlivemain.model.listeners.IAddUserListener
import com.example.h260210.kotroomrxlivemain.model.listeners.ISearchUserListener
import com.example.h260210.kotroomrxlivemain.view.extensions.showToast
import com.example.h260210.kotroomrxlivemain.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_add_user.*
import kotlinx.android.synthetic.main.fragment_add_user.view.*

class AddUserFragment : Fragment(), View.OnClickListener,
    ISearchUserListener, IAddUserListener {

    private var mUserViewModel: UserViewModel? = null
    private lateinit var mUserId: String
    private lateinit var mUserName: String
    private lateinit var mEmail: String
    private lateinit var mUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mUserViewModel = ViewModelProviders.of(this@AddUserFragment).get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_user, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        view.button_add_user_page_save.setOnClickListener(this)
    }

    private fun isUserInfoValid(): Boolean {
        mUserId = editText_add_user_page_user_id.text.toString().trim()
        mUserName = editText_add_user_page_user_name.text.toString().trim()
        mEmail = editText_add_user_page_email.text.toString().trim()

        return mUserId.isNotBlank()
                && mUserName.isNotBlank()
                && mEmail.isNotBlank()
    }

    private fun isExistingUser() {
        if (isUserInfoValid()) {
            mUserViewModel!!.searchUserRx(mUserName, this)
        } else {
            context!!.showToast("Fields are mandatory")
        }
    }

    private fun addUser() {
        mUser = User(userId = mUserId.toInt(), username = mUserName, emailId = mEmail)
        mUserViewModel!!.addUserRx(mUser, this)
        //mUserViewModel!!.addUserLiveData(mUser)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            v.button_add_user_page_save.id -> isExistingUser()
        }
    }

    override fun onUserFound(user: User) {
        context!!.showToast("Existing User: ${user.username}")
    }

    override fun onUserNotFound(message: String) {
        addUser()
    }

    override fun onUserAdded(user: User) {
        context!!.showToast("${user.username} inserted successfully!")
    }

    override fun onAddUserError(errorMessage: String) {
        context!!.showToast("Error in insertion: $errorMessage")
    }


}
