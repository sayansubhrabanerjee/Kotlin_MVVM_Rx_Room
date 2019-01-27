package com.example.h260210.kotroomrxlivemain.view


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.h260210.kotroomrxlivemain.R
import com.example.h260210.kotroomrxlivemain.model.listeners.IDeleteAllUsersListener
import com.example.h260210.kotroomrxlivemain.view.extensions.showToast
import com.example.h260210.kotroomrxlivemain.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment(), View.OnClickListener
    , IDeleteAllUsersListener {

    private var mUserViewModel: UserViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mUserViewModel = ViewModelProviders.of(this@HomeFragment).get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        initViews(view)
        return view
    }

    private fun initViews(v: View) {
        v.button_home_add_user.setOnClickListener(this)
        v.button_home_view_users.setOnClickListener(this)
        v.button_home_update_user.setOnClickListener(this)
        v.button_home_delete_user.setOnClickListener(this)
        v.button_home_delete_all_users.setOnClickListener(this)
    }

    private fun inflateAddUserFragment() {
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.layout_container, AddUserFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun inflateViewUsersFragment() {
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.layout_container, ViewUsersFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun inflateUpdateUserFragment() {
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.layout_container, UpdateUserFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun inflateDeleteUserFragment() {
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.layout_container, DeleteUserFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun deleteAllUsers() {
        mUserViewModel!!.deleteAllUsersRx(this)
    }

    private fun displayDeleteAllUsersAlertDialog() {
        val alertDialog = android.support.v7.app.AlertDialog.Builder(context!!)
            .setTitle("Delete All Users")
            .setMessage("Do you really want to delete all users?")
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
                deleteAllUsers()}
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }.create()

        alertDialog.show()
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_home_add_user -> inflateAddUserFragment()
            R.id.button_home_view_users -> inflateViewUsersFragment()
            R.id.button_home_update_user -> inflateUpdateUserFragment()
            R.id.button_home_delete_user -> inflateDeleteUserFragment()
            R.id.button_home_delete_all_users -> displayDeleteAllUsersAlertDialog()
        }
    }

    override fun onAllUsersDeleted() {
        context!!.showToast("All users deleted successfully!")
    }

    override fun onDeleteAllUsersError(errorMessage: String) {
        context!!.showToast("Delete all users error: $errorMessage")
    }

}
