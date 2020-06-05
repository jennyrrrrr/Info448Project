package com.example.info448project

import android.app.Application
import com.example.info448project.manager.AccountManager
import com.example.info448project.manager.DataManager

class ProjectApp: Application() {
    lateinit var accountManager: AccountManager
    lateinit var dataManager: DataManager

    override fun onCreate() {
        super.onCreate()

        accountManager = AccountManager()
        dataManager = DataManager(this)
    }
}