package olyarisu.github.com.myapplication.presentation.main.mapper

import olyarisu.github.com.myapplication.domain.entity.Post
import olyarisu.github.com.myapplication.presentation.main.viewdata.PostView
import java.util.*

fun Int.convertScoreToReadableString(): String =
    if (this > 999) "%.1f".format(Locale.US,this.toFloat() / 1000) + "k" else this.toString()

fun Post.map() = PostView(
    id = id,
    title = title,
    subreddit = subreddit,
    score = score.convertScoreToReadableString(),
    link = link
)