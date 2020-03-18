package olyarisu.github.com.myapplication.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import olyarisu.github.com.myapplication.domain.repository.SubredditRepository
import org.koin.core.parameter.parametersOf
import org.koin.core.KoinComponent
import org.koin.core.inject

private const val SUBREDDIT_NAME = "gaming"

class RedditPostsViewModel(
) : ViewModel(), KoinComponent {

    private val repository: SubredditRepository by inject {
        parametersOf(
            this.viewModelScope,
            SUBREDDIT_NAME
        )
    }

    private val resutPosts = repository.getPosts()
    val posts = resutPosts.pagedListPost
    val networkError = resutPosts.error
    val isLoading = resutPosts.isLoading
    val isRefreshing = resutPosts.isRefreshing


    fun retryLastFailedOperation() = viewModelScope.launch {
        repository.retryLastFailedOperation()
    }

    fun refresh() = viewModelScope.launch {
        repository.refresh()
    }
}