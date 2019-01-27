package com.example.h260210.kotroomrxlivemain.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.h260210.kotroomrxlivemain.R
import com.example.h260210.kotroomrxlivemain.model.User
import kotlinx.android.synthetic.main.list_users.view.*

class UsersAdapter(private val mContext: Context, private var mUsers: MutableList<User>?) :
    RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): UserViewHolder {
        val itemView = LayoutInflater.from(mContext).inflate(R.layout.list_users, parent, false)
        return UserViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mUsers!!.size
    }

    override fun onBindViewHolder(userViewHolder: UserViewHolder, position: Int) {
        val user = mUsers!![position]
        userViewHolder.bindUserData(user, position)
    }

    fun setUserData(users: MutableList<User>) {
        mUsers!!.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindUserData(user: User?, position: Int) {
            itemView.textView_userId.text = user!!.userId.toString()
            itemView.textView_username.text = user.username
            itemView.textView_email.text = user.emailId
        }
    }
}