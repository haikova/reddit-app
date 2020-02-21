package olyarisu.github.com.myapplication.presentation

import androidx.lifecycle.ViewModel
import olyarisu.github.com.myapplication.data.SubredditRepository

class RedditPostsViewModel(
    repository: SubredditRepository
) : ViewModel() {

    val posts = repository.getPosts("gaming")
}