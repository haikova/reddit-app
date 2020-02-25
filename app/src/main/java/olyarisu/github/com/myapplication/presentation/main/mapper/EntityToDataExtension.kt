package olyarisu.github.com.myapplication.presentation.main.mapper

import olyarisu.github.com.myapplication.domain.entity.Post
import olyarisu.github.com.myapplication.presentation.main.viewdata.PostView

fun Post.map() = PostView(
    id = id,
    title = title,
    subreddit = subreddit,
    score = if (score > 999) "%.1f".format(score.toFloat() / 1000) + "k" else score.toString(),
    link = link
)