package com.example.info448project.manager

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AccountManager() {
    var location: String? = null
    var bio: String? = null
    var nickname :String? = null
    var email :String? = null
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var userId: String

    fun getUserInfo() {
        firebaseFirestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        userId = auth.currentUser!!.uid

        val docRef = firebaseFirestore.collection("users").document(userId)
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("jen", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                Log.d("jen", "Current data: ${snapshot.data}")
                this.nickname = snapshot.getString("nickname")
                this.email = snapshot.getString("email")
                this.bio = snapshot.getString("bio")
                this.location = snapshot.getString("location")
            } else {
                Log.d("jen", "Current data: null")
            }
        }
    }

    fun setUserLocation(newLocation: String) {
        this.location = newLocation
        firebaseFirestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        userId = auth.currentUser!!.uid
        val userInfo = hashMapOf(
            "nickname" to this.nickname,
            "email" to this.email,
            "location" to this.location,
            "bio" to this.bio
        )
        val docRef = firebaseFirestore.collection("users").document(userId).update(userInfo as Map<String, Any>)
            .addOnSuccessListener {
                Log.d("jen", "User with ID: $userId set location to $location")
            }
            .addOnFailureListener { e ->
                Log.w("jen", "Error updating user: $userId  with the location: $location", e)
            }
    }
  
    fun updateUserInfo(location: String, bio: String, nickname: String) {
        this.location = location
        this.bio = bio
        this.nickname = nickname
    }
}