package com.example.info448project.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.info448project.Post

import com.example.info448project.R
import com.example.info448project.manager.ForumDataManager
import com.example.info448project.manager.PostAdapter
import kotlinx.android.synthetic.main.fragment_forum.*

/* Documentation:
Overall Fragment architecture - This fragment fetches data in JSON format,
then displays it in a recyclerview

To do:
- display number of comments on a post
- set up multiple tabs
- expandable comments
- set up ability to modify data
 */
class ForumFragment : Fragment() {
    companion object {
        val TAG: String = ForumFragment::class.java.simpleName
    }

    //for getting the context
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forum, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val forumDataManager = ForumDataManager(requireActivity().applicationContext) //this should get me the context according to stack overflow
        Log.i("jhoupps",  "about to fetch posts")

        btnBack.visibility=View.GONE




        val initialPost = Post(
            posterName = "Mr Test",
            posterUserName = "@testytest",
            smallImageURL = "www.someplace",
            dateTime = "1:45 5.13.15",
            postContent = "I am posting a thing!",
            comments = listOf<Post>()
        )
        val blankListForNow = listOf<Post>(initialPost)

        val postAdapter = PostAdapter(blankListForNow)


        rvForumPosts.adapter = postAdapter


        forumDataManager.fetchPosts(){ theResponse  ->
            postAdapter.updateList(theResponse)
            Log.i("jhoupps",  "updated the list!")
            rvForumPosts.visibility = View.VISIBLE
        } //lets see if this goes asyncrhonously

        // Set on item Click for the adapter
        postAdapter.onPostClickListener = {
            var newList = it.comments.toMutableList()
            newList.add(0, it)
            postAdapter.goToCommentList(newList)
            btnBack.visibility=View.VISIBLE

        }

        btnBack.setOnClickListener {
            postAdapter.leaveCommentList()
            btnBack.visibility = View.GONE
        }

    }

}
