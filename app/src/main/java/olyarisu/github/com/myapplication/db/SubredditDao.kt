package olyarisu.github.com.myapplication.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import olyarisu.github.com.myapplication.data.dto.PostDataJson

@Dao
interface SubredditDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<PostDataJson>)

    @Query("SELECT * FROM posts")
    fun postsBySubreddit(): DataSource.Factory<Int, PostDataJson>
}