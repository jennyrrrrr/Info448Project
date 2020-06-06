package com.example.info448project.manager

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import android.util.Log
import com.example.info448project.AllPosts
import com.example.info448project.Post
import com.google.gson.Gson

class ForumDataManager(context: Context) {
    val queue = Volley.newRequestQueue(context)

    //Fetching the JSON from the internet
    fun fetchPosts(): List<Post> {
        var genPosts = listOf<Post>()

        //Call the JSON from the api
        // Instantiate the RequestQueue.
        val url = "https://raw.githubusercontent.com/rongt2-1861545/Info448Project/jay_dev/sampleForumData/sampleForumPosts.json"
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                Log.i("jhoupps", "Response is: ${response.substring(0, 500)}")
                genPosts = parseData(response) //todo change this to be not just general chat

            },
            Response.ErrorListener { Log.i("jhoupps",  "That didn't work!") })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
        return genPosts

    }



    //Parse the data that was fetched in JSON form
    //Uses GSON
    fun parseData(theData: String): List<Post> {
        val gson = Gson()
        val allPosts: AllPosts = gson.fromJson(theData, AllPosts::class.java)
        Log.i("jhoupps","HELLO I AM PARSING DATA")

        allPosts.general.let { //todo change this once i add more tabs
            Log.i("jhoupps", "Parsed a content of: $it")
        }
        return allPosts.general


    }


}

