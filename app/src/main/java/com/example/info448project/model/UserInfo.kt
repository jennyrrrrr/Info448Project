package com.example.info448project.model
import com.google.firebase.database.Exclude

data class UserInfo (
    val userId: String,
    val nickName: String,
    val email: String,
    val bio: String,
    val location: String

){
    // [START post_to_map]
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "userId" to userId,
            "nickName" to nickName,
            "email" to email,
            "bio" to bio,
            "location" to location
        )
    }
    // [END post_to_map]
}