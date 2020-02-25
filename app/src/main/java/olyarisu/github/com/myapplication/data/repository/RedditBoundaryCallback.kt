package olyarisu.github.com.myapplication.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import olyarisu.github.com.myapplication.data.datasource.LocalRedditDatasource
import olyarisu.github.com.myapplication.data.datasource.RemoteRedditDataSource
import olyarisu.github.com.myapplication.domain.entity.Post

class RedditBoundaryCallback(
    private val subreddit: String,
    private val remoteRedditDataSource: RemoteRedditDataSource,
    private val localRedditDatasource: LocalRedditDatasource,
    private val handleError: (String?) -> Unit
) : PagedList.BoundaryCallback<Post>() {

    val isLoading = MutableLiveData<Boolean>()

    override fun onZeroItemsLoaded() {
        isLoading.postValue(true)
        remoteRedditDataSource.loadPosts(subreddit, { posts ->
            localRedditDatasource.insert(posts)
            isLoading.postValue(false)
            handleError(null)
        }, { error ->
            handleError(error)
            isLoading.postValue(false)
        })
    }

    override fun onItemAtEndLoaded(itemAtEnd: Post) {
        isLoading.postValue(true)
        remoteRedditDataSource.loadPostsAfter(subreddit, itemAtEnd.name, { posts ->
            localRedditDatasource.insert(posts)
            isLoading.postValue(false)
            handleError(null)
        }, { error ->
            handleError(error)
            isLoading.postValue(false)
        })
    }


}