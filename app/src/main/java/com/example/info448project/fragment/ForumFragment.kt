package com.example.info448project.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.info448project.Comment
import com.example.info448project.Post

import com.example.info448project.R
import com.example.info448project.manager.ForumDataManager
import com.example.info448project.manager.PostAdapter
import kotlinx.android.synthetic.main.fragment_forum.*

/* Documentation:
Overall Fragment architecture - This fragment fetches data in JSON format,
then displays it in a recyclerview

To do:
- set up json
- fetch json
- store json in variable
- set up one tab
- display json in recyclerview
- set up multiple tabs
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



        val initialPost = Post(
            posterName = "Mr Test",
            posterUserName = "@testytest",
            smallImageURL = "www.someplace",
            dateTime = "1:45 5.13.15",
            postContent = "I am posting a thing!",
            comments = listOf<Comment>()
        )
        val blankListForNow = listOf<Post>(initialPost)

        val postAdapter = PostAdapter(blankListForNow)

        rvForumPosts.adapter = postAdapter


        forumDataManager.fetchPosts(){ theResponse  ->
            postAdapter.updateList(theResponse)
            Log.i("jhoupps",  "updated the list!")
        } //lets see if this goes asyncrhonously
     //   Log.i("jhoupps",  "it was called!")
        //todo - on click show comments?
        /*
        // Set on item Click for the adapter
        personAdapter.onPersonClickListener = { somePerson: Person ->

            val intent = Intent(this, PersonActivity::class.java)
//            intent.putExtra(NAME_KEY, name)
////            intent.putExtra(POSITION_KEY, pos)
//
            intent.putExtra(PERSON_KEY, somePerson)

            startActivity(intent)
            */

    }


    fun setUpRecyclerView(generalListOfPosts: List<Post>){
        /*
        val postAdapter = PostAdapter(generalListOfPosts)

        rvForumPosts.adapter = postAdapter
        Log.i("jhoupps",  "it was called!")
*/
    }
}
