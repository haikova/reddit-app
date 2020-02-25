package olyarisu.github.com.myapplication.data.mapper

import olyarisu.github.com.myapplication.data.api.dto.PostDataJson
import olyarisu.github.com.myapplication.domain.entity.Post

fun PostDataJson.map() = Post(
    id = id,
    title = title,
    subreddit = subreddit_name_prefixed,
    score = score,
    link = permalink,
    name = name
)