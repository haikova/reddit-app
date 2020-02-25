package olyarisu.github.com.myapplication.data.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import olyarisu.github.com.myapplication.data.api.dto.PostDataJson

@Dao
interface SubredditDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<PostEntity>)

    @Query("SELECT * FROM posts")
    fun postsBySubreddit(): DataSource.Factory<Int, PostEntity>

    @Query("DELETE FROM posts")
    fun deleteBySubreddit()
}