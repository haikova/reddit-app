package olyarisu.github.com.myapplication.data

import olyarisu.github.com.myapplication.data.api.NetworkService
import olyarisu.github.com.myapplication.data.dto.PostDataJson
import olyarisu.github.com.myapplication.data.dto.SubredditTopJson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RemoteRedditDataSource(
    private val networkService: NetworkService = NetworkService()
) {

    fun loadPosts(
        subreddit: String,
        onSuccess: (posts: List<PostDataJson>) -> Unit
    ) {

        networkService.getRedditService().getTop(
            subreddit = subreddit,
            limit = 25
        ).enqueue(
            object : Callback<SubredditTopJson> {
                override fun onFailure(call: Call<SubredditTopJson>?, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<SubredditTopJson>?,
                    response: Response<SubredditTopJson>
                ) {
                    if (response.isSuccessful) {

                        val data = response.body()?.data
                        val items = data?.children?.map { it.data } ?: emptyList()
                        onSuccess(items)
                    }
                }
            }
        )
    }

    fun loadPostsAfter(
        subreddit: String,
        last: PostDataJson,
        onSuccess: (posts: List<PostDataJson>) -> Unit
    ) {
        networkService.getRedditService().getTopAfter(
            subreddit = subreddit,
            limit = 25,
            after = last.name
        ).enqueue(
            object : Callback<SubredditTopJson> {
                override fun onFailure(call: Call<SubredditTopJson>?, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<SubredditTopJson>?,
                    response: Response<SubredditTopJson>
                ) {
                    if (response.isSuccessful) {

                        val data = response.body()?.data
                        val items = data?.children?.map { it.data } ?: emptyList()
                        onSuccess(items)
                    }
                }
            }
        )
    }
}