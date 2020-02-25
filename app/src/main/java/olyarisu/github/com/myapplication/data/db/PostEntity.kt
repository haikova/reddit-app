package olyarisu.github.com.myapplication.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: String,
    val link: String,
    val score: Int,
    val subreddit: String,
    val title: String,
    val name: String
)