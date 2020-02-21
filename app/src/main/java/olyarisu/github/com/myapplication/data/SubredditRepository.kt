package olyarisu.github.com.myapplication.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import olyarisu.github.com.myapplication.data.dto.PostDataJson
import olyarisu.github.com.myapplication.db.RedditLocalCache

class SubredditRepository(context: Context) {
    private val redditLocalCache = RedditLocalCache(context)
    private val remoteRedditDataSource = RemoteRedditDataSource()

    fun getPosts(subreddit: String): LiveData<PagedList<PostDataJson>> {
        val dataSourceFactory = redditLocalCache.postsBySubreddit(subreddit)

        val boundaryCallback =
            RedditBoundaryCallback(
                subreddit,
                remoteRedditDataSource,
                redditLocalCache
            )
        return LivePagedListBuilder(dataSourceFactory, 25)
            .setBoundaryCallback(boundaryCallback)
            .build()
    }

}