package olyarisu.github.com.myapplication.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.*
import olyarisu.github.com.myapplication.data.datasource.LocalRedditDatasource
import olyarisu.github.com.myapplication.data.datasource.RemoteRedditDataSource
import olyarisu.github.com.myapplication.domain.entity.Post
import olyarisu.github.com.myapplication.domain.repository.ResultPost
import olyarisu.github.com.myapplication.domain.repository.SubredditRepository

class DefaultSubredditRepository(
    private val localRedditDatasource: LocalRedditDatasource,
    private val remoteRedditDataSource: RemoteRedditDataSource,
    private val coroutineScope: CoroutineScope,
    private val subreddit: String
) : SubredditRepository {

    private val isRefreshing = MutableLiveData<Boolean>()
    private val networkError = MutableLiveData<String?>()
    private val isLoading = MutableLiveData<Boolean>()

    private var retry: (suspend () -> Any)? = null

    override fun getPosts(): ResultPost {
        val dataSourceFactory = localRedditDatasource.postsBySubreddit(subreddit)

        val config = PagedList.Config.Builder()
            .setPageSize(25)
            .setInitialLoadSizeHint(25)
            .setPrefetchDistance(8)
            .build()

        val boundaryCallback =
            RedditBoundaryCallback(
                this::onZeroItemsLoaded,
                this::onItemAtEndLoaded
            )

        return ResultPost(
            pagedListPost = LivePagedListBuilder(dataSourceFactory, config)
                .setBoundaryCallback(boundaryCallback)
                .build(),
            error = networkError,
            isLoading = isLoading,
            isRefreshing = isRefreshing
        )
    }

    override suspend fun refresh() {
        try {
            networkError.postValue(null)
            val posts = remoteRedditDataSource.loadPosts(subreddit)
            localRedditDatasource.updateBySubreddit(subreddit, posts)
        } catch (error: Throwable) {
            retry = { refresh() }
            networkError.postValue(error.message)
        } finally {
            isRefreshing.postValue(false)
        }
    }

    private fun onZeroItemsLoaded() =
        launchBoundaryCallback { remoteRedditDataSource.loadPosts(subreddit) }

    private fun onItemAtEndLoaded(after: String) =
        launchBoundaryCallback { remoteRedditDataSource.loadPostsAfter(subreddit, after) }

    private fun launchBoundaryCallback(request: suspend () -> List<Post>) {
        coroutineScope.launch {
            try {
                networkError.postValue(null)
                isLoading.postValue(true)
                val posts = request()
                localRedditDatasource.insert(posts)
            } catch (error: Throwable) {
                retry = { launchBoundaryCallback(request) }
                networkError.postValue(error.message)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    override suspend fun retryLastFailedOperation() {
        val prevRetry = retry
        retry = null
        prevRetry?.invoke()
    }
}