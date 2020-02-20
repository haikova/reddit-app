package olyarisu.github.com.myapplication.presentation

import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import olyarisu.github.com.myapplication.data.RemoteRedditDataSourceFactory
import java.util.concurrent.Executors

class RedditPostsViewModel : ViewModel() {

    private val remoteRedditDataSourceFactory = RemoteRedditDataSourceFactory()

    private val config: PagedList.Config = PagedList.Config.Builder()
        .setPageSize(25)
        .build()

    val pagedListLiveData = LivePagedListBuilder(remoteRedditDataSourceFactory, config)
        .setFetchExecutor(Executors.newSingleThreadExecutor())
        .build()
}