package com.example.info448project.manager

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import android.util.Log
import android.widget.Toast
import com.example.info448project.AllPosts
import com.google.gson.Gson

class ForumDataManager(context: Context) {
    val queue = Volley.newRequestQueue(context)

    //Fetching the JSON from the internet
    fun fetchPosts(onSuccessLambdaFunctionThing: (AllPosts) -> Unit) {
        //Call the JSON from the api
        // Instantiate the RequestQueue.
        val url = "https://raw.githubusercontent.com/rongt2-1861545/Info448Project/jay_dev/sampleForumData/sampleForumPosts.json"
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                //Log.i("jhoupps", "Response is: ${response.substring(0, 500)}")
                val allPosts = parseData(response) //todo change this to be not just general chat
                onSuccessLambdaFunctionThing(allPosts)
            },
            Response.ErrorListener {
                Log.i("jhoupps",  "There was an error fetching the data!")
            })
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    //Parse the data that was fetched in JSON form using GSON
    fun parseData(theData: String): AllPosts {
        val gson = Gson()
        val allPosts: AllPosts = gson.fromJson(theData, AllPosts::class.java)

        return allPosts
    }
}

