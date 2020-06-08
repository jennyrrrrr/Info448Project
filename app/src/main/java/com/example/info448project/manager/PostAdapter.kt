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

class PostAdapter(initialListOfPosts: List<Post>): RecyclerView.Adapter<PostAdapter.PostViewHolder>() {


    private var listOfPosts: List<Post> = initialListOfPosts.toList() // This is so we create a duplicate of the list passed in
    private var dummy = "test"
   // var onPersonClickListener: ((person: Person) -> Unit)? = null

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
        listOfPosts = newlist
        notifyDataSetChanged() //todo if it doesnt work check here
    }

//todo update list later
    /*
   fun change(newPeople: List<Person>) {
        // Normal way up applying updates to list
//        listOfPeople = newPeople
//        notifyDataSetChanged()

        // Animated way of applying updates to list
        val callback = PersonDiffCallback(listOfPeople, newPeople)
        val diffResult = DiffUtil.calculateDiff(callback)
        diffResult.dispatchUpdatesTo(this)

        // We update the list
        listOfPeople = newPeople


    }
*/
    //todo update this dramatically
    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val smTvNickname = itemView.findViewById<TextView>(R.id.smTvNickname)
        private val smTvUsername = itemView.findViewById<TextView>(R.id.smTvUsername)
        private val tvPostContent = itemView.findViewById<TextView>(R.id.tvPostContent)



    fun bind(post: Post, position: Int) {
            smTvNickname.text = post.posterName
            smTvUsername.text = post.posterUserName
            tvPostContent.text = post.postContent


/* //todo make the on click listener later
            itemView.setOnClickListener {
                onPersonClickListener?.invoke(person)
            }
            */

        }
    }



}
