package olyarisu.github.com.myapplication.presentation

import androidx.lifecycle.ViewModel
import olyarisu.github.com.myapplication.data.SubredditRepository

class RedditPostsViewModel(
    private val repository: SubredditRepository
) : ViewModel() {

    fun refresh() = repository.refresh()

    val networkErrors = repository.networkError

    val posts = repository.getPosts("gaming")

    val isLoading = repository.isNewDataLoading

    val isRefreshing = repository.isRefreshing

    fun retryLastFailedOperation() = repository.retryLastFailedOperation()
}