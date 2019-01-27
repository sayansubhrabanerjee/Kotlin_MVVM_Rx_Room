package com.example.h260210.kotroomrxlivemain.view


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.h260210.kotroomrxlivemain.R
import com.example.h260210.kotroomrxlivemain.model.User
import com.example.h260210.kotroomrxlivemain.model.listeners.IGetAllUsersListener
import com.example.h260210.kotroomrxlivemain.view.adapter.UsersAdapter
import com.example.h260210.kotroomrxlivemain.view.extensions.showToast
import com.example.h260210.kotroomrxlivemain.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_view_users.view.*

class ViewUsersFragment : Fragment(), IGetAllUsersListener {

    private var mUsers: MutableList<User>? = mutableListOf()
    private var mUserViewModel: UserViewModel? = null
    private var mUserAdapter: UsersAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUserViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_users, container, false)

        initViews(view)
        setAdapterLiveData(view)
        //displayAllUsersLiveData()
        displayAllUsersRx()

        return view
    }

    private fun initUserViewModel() {
        mUserViewModel = ViewModelProviders.of(this@ViewUsersFragment).get(UserViewModel::class.java)
    }

    private fun initViews(view: View) {
        val linearLayoutManager = LinearLayoutManager(view.context)
        view.recycler_view_user.addItemDecoration(DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL))
        view.recycler_view_user.layoutManager = linearLayoutManager
    }

    private fun setAdapterLiveData(view: View) {
        mUserAdapter = UsersAdapter(view.context, mUsers)
        view.recycler_view_user.adapter = mUserAdapter

    }

    private fun displayAllUsersLiveData() {
        mUserViewModel!!.mGetAllUsersLiveData.observe(this@ViewUsersFragment, Observer {
            mUserAdapter!!.setUserData(it!!)
        })
    }

    private fun displayAllUsersRx() {
        mUserViewModel!!.getAllUsersRx(this)
    }


    override fun onAllUsersPopulated(users: MutableList<User>) {
       mUserAdapter!!.setUserData(users)
    }

    override fun onGetAllUsersError(errorMessage: String) {
        context!!.showToast("Error Viewing Users: $errorMessage")
    }


}
