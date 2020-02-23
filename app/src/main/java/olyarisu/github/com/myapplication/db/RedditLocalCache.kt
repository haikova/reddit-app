package olyarisu.github.com.myapplication.db

import android.content.Context
import androidx.paging.DataSource
import olyarisu.github.com.myapplication.data.dto.PostDataJson
import java.util.concurrent.Executors

class RedditLocalCache(context: Context) {

    private val subredditDao = SubredditDatabase.getInstance(context).subredditDao()

    fun insert(posts: List<PostDataJson>) {
        Executors.newSingleThreadExecutor().execute {
            subredditDao.insert(posts)
        }
    }

    fun postsBySubreddit(subreddit: String): DataSource.Factory<Int, PostDataJson> {
        return subredditDao.postsBySubreddit()
    }

    fun updateBySubreddit(subreddit: String, posts: List<PostDataJson>, onEnd: (Boolean) -> Unit) {
        Executors.newSingleThreadExecutor().execute {
            subredditDao.deleteBySubreddit()
            subredditDao.insert(posts)
            onEnd(false)
        }
    }
}