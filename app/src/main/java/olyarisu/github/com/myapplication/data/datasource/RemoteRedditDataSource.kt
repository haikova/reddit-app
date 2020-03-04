package olyarisu.github.com.myapplication.data.datasource

import olyarisu.github.com.myapplication.data.api.dto.SubredditTopJson
import olyarisu.github.com.myapplication.domain.entity.Post

interface RemoteRedditDataSource {
    suspend fun loadPosts(
        subreddit: String
    ): List<Post>

    suspend fun loadPostsAfter(
        subreddit: String,
        last: String
    ): List<Post>
}
