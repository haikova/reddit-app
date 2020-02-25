package olyarisu.github.com.myapplication.data.datasource

import olyarisu.github.com.myapplication.domain.entity.Post

interface RemoteRedditDataSource {

    fun retryFailed()
    fun loadPosts(
        subreddit: String,
        onSuccess: (posts: List<Post>) -> Unit,
        onError: (error: String) -> Unit
    )
    fun loadPostsAfter(
        subreddit: String,
        last: String,
        onSuccess: (posts: List<Post>) -> Unit,
        onError: (error: String) -> Unit
    )
}
