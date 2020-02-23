package olyarisu.github.com.myapplication.data

import androidx.paging.PagedList
import olyarisu.github.com.myapplication.data.dto.PostDataJson
import olyarisu.github.com.myapplication.db.RedditLocalCache

class RedditBoundaryCallback(
    private val subreddit: String,
    private val service: RemoteRedditDataSource,
    private val cache: RedditLocalCache,
    private val handleLoading: (Boolean) -> Unit,
    private val onError: (String?) -> Unit
) : PagedList.BoundaryCallback<PostDataJson>() {

    override fun onZeroItemsLoaded() {
        handleLoading(true)
        service.loadPosts(subreddit, { posts ->
            cache.insert(posts)
            handleLoading(false)
            onError(null)
        }, { error ->
            onError(error)
            handleLoading(false)
        })
    }

    override fun onItemAtEndLoaded(itemAtEnd: PostDataJson) {
        handleLoading(true)
        service.loadPostsAfter(subreddit, itemAtEnd, { posts ->
            cache.insert(posts)
            handleLoading(false)
            onError(null)
        }, { error ->
            onError(error)
            handleLoading(false)
        })
    }


}