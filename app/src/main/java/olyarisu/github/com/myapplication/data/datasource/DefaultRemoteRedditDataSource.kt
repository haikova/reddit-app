package olyarisu.github.com.myapplication.data.datasource

import olyarisu.github.com.myapplication.data.api.RedditApi
import olyarisu.github.com.myapplication.data.api.dto.SubredditTopJson
import olyarisu.github.com.myapplication.data.mapper.map
import olyarisu.github.com.myapplication.domain.entity.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DefaultRemoteRedditDataSource(
    private val redditApi: RedditApi
) : RemoteRedditDataSource{

    private var retry: (() -> Any)? = null

    override fun retryFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.invoke()
    }

    override fun loadPosts(
        subreddit: String,
        onSuccess: (posts: List<Post>) -> Unit,
        onError: (error: String) -> Unit
    ) {
        redditApi.getTop(
            subreddit = subreddit,
            limit = 25
        ).enqueue(
            object : Callback<SubredditTopJson> {
                override fun onFailure(call: Call<SubredditTopJson>?, t: Throwable) {
                    retry = { loadPosts(subreddit, onSuccess, onError) }
                    onError(t.message ?: "Unknown error")
                }

                override fun onResponse(
                    call: Call<SubredditTopJson>?,
                    response: Response<SubredditTopJson>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()?.data
                        val items = data?.children?.map { it.data } ?: emptyList()
                        onSuccess(items.map { it.map() })
                    } else {
                        retry = { loadPosts(subreddit, onSuccess, onError) }
                        onError(response.errorBody()?.string() ?: "Unknown error")
                    }
                }
            }
        )
    }

    override fun loadPostsAfter(
        subreddit: String,
        last: String,
        onSuccess: (posts: List<Post>) -> Unit,
        onError: (error: String) -> Unit
    ) {
        redditApi.getTopAfter(
            subreddit = subreddit,
            limit = 25,
            after = last
        ).enqueue(
            object : Callback<SubredditTopJson> {
                override fun onFailure(call: Call<SubredditTopJson>?, t: Throwable) {
                    retry = { loadPostsAfter(subreddit, last, onSuccess, onError) }
                    onError(t.message ?: "Unknown error")
                }

                override fun onResponse(
                    call: Call<SubredditTopJson>?,
                    response: Response<SubredditTopJson>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()?.data
                        val items = data?.children?.map { it.data } ?: emptyList()
                        onSuccess(items.map { it.map() })
                    } else {
                        retry = { loadPostsAfter(subreddit, last, onSuccess, onError) }
                        onError(response.errorBody()?.string() ?: "Unknown error")
                    }
                }
            }
        )
    }
}