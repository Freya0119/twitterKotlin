package com.example.twitterwithkotlin.model.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.twitterwithkotlin.KEY_TEST
import com.example.twitterwithkotlin.R
import com.example.twitterwithkotlin.databinding.UserInterfaceItemBinding
import com.example.twitterwithkotlin.model.TweetBody
import com.example.twitterwithkotlin.model.TweetUser
import com.example.twitterwithkotlin.send
import com.google.firebase.firestore.auth.User

class TweetAdapter(private val tweets: List<TweetBody>) : RecyclerView.Adapter<TweetAdapter.TweetHolder>() {
    class TweetHolder(private val binding: UserInterfaceItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(body: TweetBody) { //TODO: change to class User or ???
            binding.userInterItemNameTextView.text = body.user!!.name   //TODO: sure user!!=null???
            binding.userInterItemBodyTextView.text = body.bodyText

            val bodyClicker = binding.userInterItemBodyTextView
            bodyClicker.setOnClickListener {
                Log.d(KEY_TEST, "set clicker")
                showMessageAdapter(TweetUser())
            }
        }

        private fun showMessageAdapter(user: TweetUser) {
            binding.interBodyTextMsgRecyclerView.adapter = TweetMessageAdapter(user)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetHolder {
        val binding = UserInterfaceItemBinding.inflate(LayoutInflater.from(parent.context))
        return TweetHolder(binding)
    }

    override fun onBindViewHolder(holder: TweetHolder, position: Int) {
        holder.bind(tweets[position])
    }

    override fun getItemCount(): Int {
        return tweets.size
    }
}