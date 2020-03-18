package olyarisu.github.com.myapplication.data.datasource

import androidx.paging.DataSource
import olyarisu.github.com.myapplication.domain.entity.Post

interface LocalRedditDatasource {

    fun postsBySubreddit(subreddit: String): DataSource.Factory<Int, Post>
    suspend fun insert(posts: List<Post>)
    suspend fun updateBySubreddit(subreddit: String, posts: List<Post>)
}
