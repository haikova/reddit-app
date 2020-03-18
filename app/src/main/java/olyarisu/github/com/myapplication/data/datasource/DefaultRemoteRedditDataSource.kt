package olyarisu.github.com.myapplication.data.datasource

import olyarisu.github.com.myapplication.data.api.RedditApi
import olyarisu.github.com.myapplication.data.mapper.map
import olyarisu.github.com.myapplication.domain.entity.Post


class DefaultRemoteRedditDataSource(
    private val redditApi: RedditApi
) : RemoteRedditDataSource {

    override suspend fun loadPosts(
        subreddit: String
    ): List<Post> {
        try {
            val result = redditApi.getTop(
                subreddit = subreddit,
                limit = 25
            )
            return (result.data.children.map { it.data }).map { it.map() }
        } catch (error: Throwable) {
            throw error
        }
    }

    override suspend fun loadPostsAfter(
        subreddit: String,
        last: String
    ): List<Post> {
        try {
            val result = redditApi.getTopAfter(
                subreddit = subreddit,
                limit = 25,
                after = last
            )
            return (result.data.children.map { it.data }).map { it.map() }
        } catch (error: Throwable) {
            throw error
        }
    }
}