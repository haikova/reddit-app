package olyarisu.github.com.myapplication.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import olyarisu.github.com.myapplication.data.api.dto.PostDataJson

@Database(
    entities = [PostEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SubredditDatabase : RoomDatabase() {

    abstract fun subredditDao(): SubredditDao
}