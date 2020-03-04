package olyarisu.github.com.myapplication.di

import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import olyarisu.github.com.myapplication.data.api.RedditApi
import olyarisu.github.com.myapplication.data.datasource.DefaultLocalRedditDatasource
import olyarisu.github.com.myapplication.data.datasource.DefaultRemoteRedditDataSource
import olyarisu.github.com.myapplication.data.datasource.LocalRedditDatasource
import olyarisu.github.com.myapplication.data.datasource.RemoteRedditDataSource
import olyarisu.github.com.myapplication.data.db.SubredditDatabase
import olyarisu.github.com.myapplication.data.repository.DefaultSubredditRepository
import olyarisu.github.com.myapplication.domain.repository.SubredditRepository
import olyarisu.github.com.myapplication.presentation.main.RedditPostsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://www.reddit.com/"
private const val DB_NAME = "Reddit.db"

val viewModelModule = module {
    viewModel {
        RedditPostsViewModel()
    }
}

val repositoryModule = module {
    factory { (coroutineScope: CoroutineScope, subredditName: String) ->
        DefaultSubredditRepository(
            get(),
            get(),
            coroutineScope,
            subredditName
        ) as SubredditRepository
    }
    factory {
        DefaultLocalRedditDatasource(get()) as LocalRedditDatasource
    }
    factory {
        DefaultRemoteRedditDataSource(get()) as RemoteRedditDataSource
    }
}

val moduleDb = module {
    single {
        Room.databaseBuilder(androidApplication(), SubredditDatabase::class.java, DB_NAME)
            .build()
    }
    single { get<SubredditDatabase>().subredditDao() }
}


val apiModule = module {
    fun provideUseApi(retrofit: Retrofit): RedditApi =
        retrofit.create(RedditApi::class.java)

    factory { provideUseApi(get()) }

    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { provideRetrofit() }
}
