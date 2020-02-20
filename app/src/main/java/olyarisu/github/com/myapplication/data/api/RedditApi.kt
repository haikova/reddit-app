package olyarisu.github.com.myapplication.data.api

import olyarisu.github.com.myapplication.data.dto.SubredditTopJson
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RedditApi {
    @GET("/r/{subreddit}/top.json")
    fun getTop(
        @Path("subreddit") subreddit: String,
        @Query("limit") limit: Int): Call<SubredditTopJson>

    @GET("/r/{subreddit}/top.json")
    fun getTopAfter(
        @Path("subreddit") subreddit: String,
        @Query("after") after: String,
        @Query("limit") limit: Int): Call<SubredditTopJson>
}