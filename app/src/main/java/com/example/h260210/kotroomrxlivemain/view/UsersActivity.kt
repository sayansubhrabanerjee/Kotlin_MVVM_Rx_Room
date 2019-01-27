package com.example.h260210.kotroomrxlivemain.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.h260210.kotroomrxlivemain.R

class UsersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        addHomeFragment()
    }

    private fun addHomeFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.layout_container, HomeFragment())
            .commit()
    }
}
