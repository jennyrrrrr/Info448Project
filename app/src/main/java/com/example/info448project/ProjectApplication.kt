package com.example.info448project

import android.app.Application
import com.example.info448project.manager.UserManager

class ProjectApplication: Application() {
    lateinit var userManager: UserManager

    override fun onCreate() {
        super.onCreate()

        userManager = UserManager(this)
    }
}