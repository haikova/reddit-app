package olyarisu.github.com.myapplication.domain.repository

interface SubredditRepository {
    fun getPosts(): ResultPost
    suspend fun refresh()
    suspend fun retryLastFailedOperation()
}