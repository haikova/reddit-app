package olyarisu.github.com.myapplication.db

import android.content.Context
import androidx.paging.DataSource
import olyarisu.github.com.myapplication.data.dto.PostDataJson
import java.util.concurrent.Executors

class RedditLocalCache(context: Context) {

    private val subredditDao = SubredditDatabase.getInstance(context).subredditDao()

    fun insert(repos: List<PostDataJson>) {
        Executors.newSingleThreadExecutor().execute {
            subredditDao.insert(repos)
        }
    }

    fun postsBySubreddit(subreddit: String): DataSource.Factory<Int, PostDataJson> {
        return subredditDao.postsBySubreddit()
    }
}