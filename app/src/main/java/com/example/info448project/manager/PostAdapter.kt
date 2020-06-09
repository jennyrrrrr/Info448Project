package com.example.info448project.manager

import android.util.Log
import com.example.info448project.Post
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.info448project.AllPosts
import com.example.info448project.R

//This class is an adapter that holds all code pertaining to the posts in the recyclerview
class PostAdapter(initialListOfPosts: List<Post>): RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    private var theorigianllistOfPosts: List<Post> = initialListOfPosts.toList() // This is so we create a duplicate of the list passed in
    var onPostClickListener: ((post: Post) -> Unit)? = null //on click listener
    private var isInComments = false //what it says on the tin

    var overallGenList = listOf<Post>() //The overall list of all posts in the General category
    var overallSupplyList = listOf<Post>() //The overall list of all posts in the Supply category
    var overallHelpList = listOf<Post>() //The overall list of all posts in the Help category
    var listBeingDisplayed = listOf<Post>() //The list that the recyclerview is currently displaying - sometimes comments
    var listCurrentlyToggled = listOf<Post>() //A copy of whichever overall list is being displayed

    //Inflates viewholder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        Log.i("jhoupps",  "A view holder was created!")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    //Binding the recyclerview to the ListBeingDisplayed list
    override fun onBindViewHolder(holder: PostViewHolder, position: Int): Unit {
        val person = listBeingDisplayed[position]
        holder.bind(person, position)
    }

    //For getting the item count of the list in the recyclerview
    override fun getItemCount() = listBeingDisplayed.size

    //A function for initializing all lists of actual data once the data is fetched from the server
    fun initializeLists(theposts: AllPosts){
        overallGenList = theposts.general
        overallSupplyList = theposts.supplies
        overallHelpList = theposts.help

        listBeingDisplayed = overallGenList
        notifyDataSetChanged()
    }

   //Displaying the comments of a post via making the list of comments the list displayed
    fun goToCommentList(commentlist: List<Post>){
        if(!isInComments) {
            listCurrentlyToggled = listBeingDisplayed //in case a change was made somehow
            listBeingDisplayed = commentlist
            notifyDataSetChanged()
        }
    }

    //Returning to the overall list of posts from the comment view
    fun leaveCommentList(){
        listBeingDisplayed = listCurrentlyToggled
        notifyDataSetChanged()
    }

    //A function for switching which overall list is the ListCurrentlyToggled
    fun switchTab(newTab: String){
        when (newTab) {
            "general" -> listCurrentlyToggled = overallGenList
            "supplies" -> listCurrentlyToggled = overallSupplyList
            "help" -> listCurrentlyToggled = overallHelpList
        }
        listBeingDisplayed = listCurrentlyToggled
        notifyDataSetChanged()
    }

    //A deprecated function for updating the list
    //todo - eventually renovate into a functionality for adding new posts
    /*
    fun updateList(newlist: List<Post>){
        listBeingDisplayed = newlist
        listCurrentlyToggled = newlist
        notifyDataSetChanged()
    } */

    //An inner class holding the data in each individual post of the recyclerview
    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val smTvNickname = itemView.findViewById<TextView>(R.id.smTvNickname)
        private val smTvUsername = itemView.findViewById<TextView>(R.id.smTvUsername)
        private val tvPostContent = itemView.findViewById<TextView>(R.id.tvPostContent)
        private val tvNumOfComments = itemView.findViewById<TextView>(R.id.tvNumOfComments)

        //A function for binding the various pieces of data to their places in the post
        fun bind(post: Post, position: Int) {
                smTvNickname.text = post.posterName
                smTvUsername.text = post.posterUserName
                tvPostContent.text = post.postContent
                try {
                    tvNumOfComments.text = post.comments.size.toString()
                } catch (e: NullPointerException) {
                    tvNumOfComments.visibility = View.INVISIBLE
                }
                itemView.setOnClickListener {
                    onPostClickListener?.invoke(post)
                }
        }
    }
}
