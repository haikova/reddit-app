package olyarisu.github.com.myapplication.data.mapper

import olyarisu.github.com.myapplication.data.db.PostEntity
import olyarisu.github.com.myapplication.domain.entity.Post

fun PostEntity.map() = Post(
    id = id,
    title = title,
    subreddit = subreddit,
    score = score,
    link = link,
    name = name
)