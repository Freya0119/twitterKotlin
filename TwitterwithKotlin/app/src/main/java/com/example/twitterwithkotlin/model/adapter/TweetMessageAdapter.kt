package com.example.twitterwithkotlin.model.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.twitterwithkotlin.KEY_TEST
import com.example.twitterwithkotlin.databinding.MessageItemBinding
import com.example.twitterwithkotlin.model.TweetUser

class TweetMessageAdapter(private val testUser: TweetUser) : RecyclerView.Adapter<TweetMessageAdapter.TweetMessageHolder>() {
    class TweetMessageHolder(private val binding: MessageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(testUser: TweetUser) {
            Log.d(KEY_TEST, "${testUser.name}")
            binding.messageItemTextView.text = testUser.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetMessageHolder {
        val binding = MessageItemBinding.inflate(LayoutInflater.from(parent.context))
        return TweetMessageHolder(binding)
    }

    override fun onBindViewHolder(holder: TweetMessageHolder, position: Int) {
        holder.bind(testUser)
    }

    override fun getItemCount(): Int {
        return 3
    }
}