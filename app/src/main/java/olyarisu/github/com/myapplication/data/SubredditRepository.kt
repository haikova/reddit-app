package olyarisu.github.com.myapplication.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import olyarisu.github.com.myapplication.data.dto.PostDataJson
import olyarisu.github.com.myapplication.db.RedditLocalCache

class SubredditRepository(context: Context) {
    private val redditLocalCache = RedditLocalCache(context)
    private val remoteRedditDataSource = RemoteRedditDataSource()

    val isNewDataLoading = MutableLiveData<Boolean>()
    val isRefreshing = MutableLiveData<Boolean>()
    val networkError = MutableLiveData<String?>()

    fun getPosts(subreddit: String): LiveData<PagedList<PostDataJson>> {
        val dataSourceFactory = redditLocalCache.postsBySubreddit(subreddit)
        val config = PagedList.Config.Builder()
            .setPageSize(25)
            .setInitialLoadSizeHint(25)
            .setPrefetchDistance(8)
            .build()
        val boundaryCallback =
            RedditBoundaryCallback(
                subreddit,
                remoteRedditDataSource,
                redditLocalCache,
                this::handleLoading,
                this::handleError
            )
        return LivePagedListBuilder(dataSourceFactory, config)
            .setBoundaryCallback(boundaryCallback)
            .build()
    }

    fun handleError(error: String?) {
        networkError.postValue(error)
    }

    fun handleLoading(isLoading: Boolean) {
        isNewDataLoading.postValue(isLoading)
    }

    fun handleRefreshing(isLoading: Boolean) {
        isRefreshing.postValue(isLoading)
    }

    fun refresh() {
        remoteRedditDataSource.loadPosts("gaming", { posts ->
            redditLocalCache.updateBySubreddit("gaming", posts, this::handleRefreshing)
            networkError.postValue(null)
        }, { error ->
            networkError.postValue(error)
            handleRefreshing(false)
        })
    }

    fun retryLastFailedOperation() = remoteRedditDataSource.retryFailed()
}