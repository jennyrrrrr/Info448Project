package com.example.info448project.manager

import android.util.Log
import com.example.info448project.Post
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.info448project.R
import org.w3c.dom.Text
import java.lang.Error

class PostAdapter(initialListOfPosts: List<Post>): RecyclerView.Adapter<PostAdapter.PostViewHolder>() {


    private var listOfPosts: List<Post> = initialListOfPosts.toList() // This is so we create a duplicate of the list passed in
    var onPostClickListener: ((post: Post) -> Unit)? = null
    var overallList = listOf<Post>()
    var isInComments = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        Log.i("jhoupps",  "A view holder was created!")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun getItemCount() = listOfPosts.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int): Unit {
        val person = listOfPosts[position]
        holder.bind(person, position)
    }

    fun updateList(newlist: List<Post>){
        overallList = newlist
        listOfPosts = newlist
        notifyDataSetChanged() //todo if it doesnt work check here
    }

    fun goToCommentList(newlist: List<Post>){
        if(!isInComments) {
            overallList = listOfPosts
            listOfPosts = newlist
            notifyDataSetChanged() 
        }
    }

    fun leaveCommentList(){
        listOfPosts = overallList
        notifyDataSetChanged()
    }


    //todo update this dramatically
    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val smTvNickname = itemView.findViewById<TextView>(R.id.smTvNickname)
        private val smTvUsername = itemView.findViewById<TextView>(R.id.smTvUsername)
        private val tvPostContent = itemView.findViewById<TextView>(R.id.tvPostContent)
        private val tvNumOfComments = itemView.findViewById<TextView>(R.id.tvNumOfComments)



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
