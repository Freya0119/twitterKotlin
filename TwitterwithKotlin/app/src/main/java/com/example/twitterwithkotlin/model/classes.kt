package com.example.twitterwithkotlin.model

class TweetUser(
    val uid: String? = "no create uid",
    val name: String? = "no create name"
)

class TweetBody(
    val user: TweetUser? = TweetUser("user no uid", "user no name"),
    val bodyText: String? = "no body",
    val msgText: TweetMessage? = null
)

class TweetMessage(
    val message: String? = "no message",
    val whoName: String? = "who no name"
)