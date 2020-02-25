package olyarisu.github.com.myapplication.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import olyarisu.github.com.myapplication.data.datasource.LocalRedditDatasource
import olyarisu.github.com.myapplication.data.datasource.RemoteRedditDataSource
import olyarisu.github.com.myapplication.domain.repository.ResultPost
import olyarisu.github.com.myapplication.domain.repository.SubredditRepository

class DefaultSubredditRepository(
    private val localRedditDatasource: LocalRedditDatasource,
    private val remoteRedditDataSource: RemoteRedditDataSource
) : SubredditRepository {

    private val isRefreshing = MutableLiveData<Boolean>()
    private val networkError = MutableLiveData<String?>()

    override fun getPosts(subreddit: String): ResultPost {
        val dataSourceFactory = localRedditDatasource.postsBySubreddit(subreddit)

        val config = PagedList.Config.Builder()
            .setPageSize(25)
            .setInitialLoadSizeHint(25)
            .setPrefetchDistance(8)
            .build()

        val boundaryCallback =
            RedditBoundaryCallback(
                subreddit,
                remoteRedditDataSource,
                localRedditDatasource,
                this::handleError
            )

        return ResultPost(
            pagedListPost = LivePagedListBuilder(dataSourceFactory, config)
                .setBoundaryCallback(boundaryCallback)
                .build(),
            error = networkError,
            isLoading = boundaryCallback.isLoading,
            isRefreshing = isRefreshing
        )
    }

    override fun refresh(subredditName: String) {
        remoteRedditDataSource.loadPosts(subredditName, { posts ->
            localRedditDatasource.updateBySubreddit(subredditName, posts)
            networkError.postValue(null)
            isRefreshing.postValue(false)
        }, { error ->
            networkError.postValue(error)
            isRefreshing.postValue(false)
        })
    }

    override fun retryLastFailedOperation() = remoteRedditDataSource.retryFailed()

    private fun handleError(error: String?) {
        networkError.postValue(error)
    }
}