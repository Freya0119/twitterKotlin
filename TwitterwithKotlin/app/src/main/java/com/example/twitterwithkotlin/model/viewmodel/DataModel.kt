package com.example.twitterwithkotlin.model.viewmodel

import com.example.twitterwithkotlin.model.TweetBody

class DataModel() {
    val allItems= mutableListOf<TweetBody>()

    fun add(body: TweetBody){
        allItems.add(body)
    }
}