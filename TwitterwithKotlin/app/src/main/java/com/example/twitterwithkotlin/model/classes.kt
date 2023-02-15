package com.example.twitterwithkotlin.model

class TweetUser(
    val uid: String = "no uid",
    val name: String = "no name",
    val image: String? = null
)

class TweetBody(
    val user: TweetUser = TweetUser(),
    val bodyImage: String? = null,
    val bodyText: String = "no post",
    val bodyTime: String? = null
)