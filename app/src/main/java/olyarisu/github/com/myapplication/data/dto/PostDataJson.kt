package olyarisu.github.com.myapplication.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostDataJson(
    @PrimaryKey val id: String,
    val permalink: String,
    val score: Int,
    val subreddit_name_prefixed: String,
    val title: String,
    val name: String

)