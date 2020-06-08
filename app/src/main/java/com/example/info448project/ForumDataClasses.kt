package com.example.info448project

//Holds All Posts
data class AllPosts(
    val general: List<Post>
    //todo - once I add more types, add them here
)

//This data class holds one artist object from the JSON data
data class Post(
    val posterName: String,
    val posterUserName: String,
    val smallImageURL: String, //todo deal with this later
    val dateTime: String, //todo put this in proper date time format
    val postContent: String,
    val comments: List<Post>
)

//expected data format
/*
"posterName":"Janice Dolly",
          "smallImageURL":"https://picsum.photos/seed/LinkinPark/50",
          "dateTime":"5:43 pm 6/5/2020",
          "body":"Is anybody feeling lonely nowadays?",
          "comments":[
 */





