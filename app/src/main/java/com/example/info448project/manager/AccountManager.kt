package com.example.info448project.manager

import android.content.Context
import android.util.Log

class AccountManager(context: Context) {
//    var location: String? = null
    var location: String? = "location"
//    var bio: String? = null
    var bio: String? = "Add bio to your profile"
    var userName :String? = null
//    var userNickname :String? = null
    var userNickname :String? = "Nickname"

    fun changeNickname(newNickName: String) {
        this.userNickname = newNickName
    }

    fun changeUsername(newUserName: String) {
        this.userName = newUserName
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