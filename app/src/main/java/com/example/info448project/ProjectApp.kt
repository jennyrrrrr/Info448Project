package com.example.info448project

import android.app.Application
import com.example.info448project.manager.AccountManager

class ProjectApp: Application() {
    lateinit var accountManager: AccountManager

    override fun onCreate() {
        super.onCreate()

        accountManager = AccountManager()
    }
}