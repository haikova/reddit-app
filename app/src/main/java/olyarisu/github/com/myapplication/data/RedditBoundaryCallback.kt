package olyarisu.github.com.myapplication.data

import androidx.paging.PagedList
import olyarisu.github.com.myapplication.data.dto.PostDataJson
import olyarisu.github.com.myapplication.db.RedditLocalCache

class RedditBoundaryCallback(
    private val subreddit: String,
    private val service: RemoteRedditDataSource,
    private val cache: RedditLocalCache
) : PagedList.BoundaryCallback<PostDataJson>() {

    override fun onZeroItemsLoaded() {
        service.loadPosts(subreddit){posts -> cache.insert(posts) }
    }

    override fun onItemAtEndLoaded(itemAtEnd: PostDataJson) {
        service.loadPostsAfter(subreddit, itemAtEnd) { posts ->
            cache.insert(posts)
        }
    }

}