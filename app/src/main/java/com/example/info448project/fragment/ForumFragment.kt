package com.example.info448project.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.info448project.Post
import com.example.info448project.R
import com.example.info448project.manager.ForumDataManager
import com.example.info448project.manager.PostAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_forum.*

/* Documentation:
Overall Fragment architecture - This fragment fetches data in JSON format,
then displays it in a recyclerview in three tabs of forum posts

To do:
- merge!
- squash comment number bug
- expandable posts
- set up ability to modify data
- do something with profile picture images
 */
class ForumFragment : Fragment() {
    companion object {
        val TAG: String = ForumFragment::class.java.simpleName
    }

    //Creates the view and inflates the fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forum, container, false)
    }

    //The majority of my setup code. This method runs after the view is created
    //It fetches the data, initializes the recyclerview, and specifies the controls of each button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Forum"

        //Fetching data for the forum asyncrhonously
        val forumDataManager = ForumDataManager(requireActivity().applicationContext) //this should get me the context according to stack overflow

        //Initial data for creating the recyclerview while the data is fetched
        //This is a slightly jank workaround
        //The recyclerview is initially set to be invisible while the data is fetched
        val initialPost = Post(
            posterName = "Mr Test",
            posterUserName = "@testytest",
            smallImageURL = "www.someplace",
            dateTime = "1:45 5.13.15",
            postContent = "I am posting a thing!",
            comments = listOf<Post>()
        )
        val blankListForNow = listOf<Post>(initialPost)

        //Putting the initial data in the recyclerview
        val postAdapter = PostAdapter(blankListForNow)
        rvForumPosts.adapter = postAdapter

        //Once the asyncrhonized data is fetched, this puts the actual data in the recyclerview
        forumDataManager.fetchPosts(){ theResponse  ->
            postAdapter.initializeLists(theResponse)
            rvForumPosts.visibility = View.VISIBLE
        }

        //This controls the tablayout. If the tab is changed, then the data displayed changes
        tlTabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener  {
            //Code explanation: this overwrites the OnTabSelectedListener interface, which is
            //why there are several override functions. We also include "Object" because it is
            //creating a new instantiation of an OnTabSelectedListener object
            override fun onTabReselected(tab: TabLayout.Tab?) {
                postAdapter.leaveCommentList()
                btnBack.visibility = View.GONE
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tlTabLayout?.selectedTabPosition) {
                    0 -> postAdapter.switchTab("general")
                    1 -> postAdapter.switchTab("supplies")
                    2 -> postAdapter.switchTab("help")
                }
                btnBack.visibility = View.GONE //In case the user was viewing comments at the time
            }
        })
        
        //When a user clicks on a post, it displays the comments of that post in the recyclerview
        postAdapter.onPostClickListener = {
            postAdapter.goToCommentList(it)
            btnBack.visibility=View.VISIBLE
        }

        //The back button is initially gone, and becomes visible when comments are displayed
        btnBack.visibility=View.GONE
        //The back button is used to return from the comments to the main list
        btnBack.setOnClickListener {
            postAdapter.leaveCommentList()
            btnBack.visibility = View.GONE
        }
    }
}
