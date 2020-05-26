package com.example.info448project.manager

import android.content.Context
import android.util.Log

class UserManager(context: Context) {
    var nickName: String? = null
    var location: String? = null
    var bio: String? = null
    var userName :String? = null

    fun changeUsername(newNickName: String) {
        this.nickName = newNickName
    }

    fun changeLocation(newLocation: String) {
        this.location = newLocation
    }

    fun changeBio(newBio: String) {
        this.bio = newBio
    }

    fun login(userName: String, password: String) {
        Log.i("jen", "$userName")
    }

    fun signOut() {

    }
}