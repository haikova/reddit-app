package olyarisu.github.com.myapplication.domain.repository

interface SubredditRepository {
    fun getPosts(subreddit: String): ResultPost
    fun refresh(subredditName: String)
    fun retryLastFailedOperation()
}