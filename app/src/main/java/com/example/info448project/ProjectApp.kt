package com.example.info448project

import android.app.Application
import com.example.info448project.manager.AccountManager
import com.example.info448project.manager.DataManager
import com.example.info448project.manager.WorkBackgroundManager

class ProjectApp: Application() {
    lateinit var accountManager: AccountManager
    lateinit var dataManager: DataManager
    lateinit var workBackgroundManager: WorkBackgroundManager

    override fun onCreate() {
        super.onCreate()

        accountManager = AccountManager()
        dataManager = DataManager(this)
        workBackgroundManager = WorkBackgroundManager(this)
    }
}