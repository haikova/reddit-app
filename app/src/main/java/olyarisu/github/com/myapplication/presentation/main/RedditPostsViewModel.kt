package olyarisu.github.com.myapplication.presentation.main

import androidx.lifecycle.ViewModel
import olyarisu.github.com.myapplication.domain.repository.SubredditRepository

private const val SUBREDDIT_NAME = "gaming"

class RedditPostsViewModel(
    private val repository: SubredditRepository
) : ViewModel() {



    private val resutPosts = repository.getPosts(SUBREDDIT_NAME)
    val posts = resutPosts.pagedListPost
    val networkError = resutPosts.error
    val isLoading = resutPosts.isLoading
    val isRefreshing = resutPosts.isRefreshing

    fun retryLastFailedOperation() = repository.retryLastFailedOperation()

    fun refresh() = repository.refresh(SUBREDDIT_NAME)
}