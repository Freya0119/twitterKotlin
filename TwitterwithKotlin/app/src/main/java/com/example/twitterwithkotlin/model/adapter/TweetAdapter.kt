package com.example.twitterwithkotlin.model.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.twitterwithkotlin.KEY_TEST
import com.example.twitterwithkotlin.R
import com.example.twitterwithkotlin.databinding.UserInterfaceItemBinding
import com.example.twitterwithkotlin.model.TweetBody
import com.example.twitterwithkotlin.model.TweetUser

class TweetAdapter(private val tweets: List<TweetBody>) : RecyclerView.Adapter<TweetAdapter.TweetHolder>() {
    class TweetHolder(private val binding: UserInterfaceItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(body: TweetBody) { //TODO: change to class User or hashMap
            Log.d(KEY_TEST, "name: ${body.user!!.name}")    //TODO: test user?=null
            binding.userInterItemBodyTextView.setOnClickListener { Log.d(KEY_TEST, "click test") }  //TODO: listener event

            setUser(body.user)
            setPost(body.bodyImage, body.bodyText)
        }

        private fun setUser(user: TweetUser) {
            if (user.image != null) {
                try {
                    binding.userInterItemImageView.setImageURI(Uri.parse(user.image))
                } catch (e: Exception) {
                    Log.d(KEY_TEST, "set user icon error: $e")
                }
            }
            binding.userInterItemNameTextView.text = user.name
        }

        private fun setPost(uri: String?, post: String) {
            if (uri != null) {
                try {
                    binding.userInterItemImageView.setImageURI(Uri.parse(uri))
                } catch (e: Exception) {
                    Log.d(KEY_TEST, "set user icon error: $e")
                }
            }
            binding.userInterItemBodyTextView.text = post
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserInterfaceItemBinding.inflate(inflater, parent, false) //ItemBinding.inflate(): 把ItemBinding放到inflater裡面, false不設置parent view
        val aaa = inflater.inflate(R.layout.user_interface_item, parent, false) //inflater.inflate(): 膨脹layout.xxx
        return TweetHolder(binding)
    }

    override fun onBindViewHolder(holder: TweetHolder, position: Int) {
        holder.bind(tweets[position])
    }

    override fun getItemCount(): Int {
        return tweets.size
    }
}