package olyarisu.github.com.myapplication.data

import androidx.paging.DataSource
import androidx.lifecycle.MutableLiveData
import olyarisu.github.com.myapplication.data.dto.PostDataJson


class RemoteRedditDataSourceFactory : DataSource.Factory<String, PostDataJson>() {

    private val mutableLiveData = MutableLiveData<RemoteRedditDataSource>()

    override fun create(): DataSource<String, PostDataJson> {
        val remoteRedditDataSource = RemoteRedditDataSource()
        mutableLiveData.postValue(remoteRedditDataSource)
        return remoteRedditDataSource
    }

}