package olyarisu.github.com.myapplication.domain.entity

data class Post (
    val id: String,
    val title: String,
    val subreddit: String,
    val score: Int,
    val link: String,
    val name: String
)