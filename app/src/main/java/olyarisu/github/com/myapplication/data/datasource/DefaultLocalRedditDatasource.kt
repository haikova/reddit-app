package olyarisu.github.com.myapplication.data.datasource

import androidx.paging.DataSource
import olyarisu.github.com.myapplication.data.db.SubredditDao
import olyarisu.github.com.myapplication.data.mapper.map
import olyarisu.github.com.myapplication.domain.entity.Post
import java.util.concurrent.Executors

class DefaultLocalRedditDatasource(
    private val subredditDao: SubredditDao
) : LocalRedditDatasource {

    override fun insert(posts: List<Post>) {
        Executors.newSingleThreadExecutor().execute {
            subredditDao.insert(posts.map { it.map() })
        }
    }

    override fun postsBySubreddit(subreddit: String): DataSource.Factory<Int, Post> {
        return subredditDao.postsBySubreddit().map { it.map() }
    }

    override fun updateBySubreddit(subreddit: String, posts: List<Post>) {
        Executors.newSingleThreadExecutor().execute {
            subredditDao.deleteBySubreddit()
            subredditDao.insert(posts.map { it.map() })
        }
    }
}