package com.example.info448project

//Holds the large list of all posts
data class AllPosts(
    val general: List<Post>,
    val supplies: List<Post>,
    val help: List<Post>
)

//This data class holds one Post object from the JSON data
data class Post(
    val posterName: String,
    val posterUserName: String,
    val smallImageURL: String,
    val dateTime: String,
    val postContent: String,
    val comments: List<Post>
)




