package olyarisu.github.com.myapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import olyarisu.github.com.myapplication.data.dto.PostDataJson

@Database(
    entities = [PostDataJson::class],
    version = 1,
    exportSchema = false
)
abstract class SubredditDatabase : RoomDatabase() {

    abstract fun subredditDao(): SubredditDao

    companion object {

        @Volatile
        private var INSTANCE: SubredditDatabase? = null

        fun getInstance(context: Context): SubredditDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                SubredditDatabase::class.java, "Reddit.db"
            )
                .build()
    }
}