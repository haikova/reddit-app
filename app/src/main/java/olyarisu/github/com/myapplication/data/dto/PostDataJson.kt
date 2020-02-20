package olyarisu.github.com.myapplication.data.dto

data class PostDataJson(
    val id: String,
    val permalink: String,
    val score: Int,
    val subreddit_name_prefixed: String,
    val title: String

)