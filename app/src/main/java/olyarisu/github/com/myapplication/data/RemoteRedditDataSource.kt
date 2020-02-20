package olyarisu.github.com.myapplication.data

import androidx.paging.PageKeyedDataSource
import olyarisu.github.com.myapplication.data.api.NetworkService
import olyarisu.github.com.myapplication.data.dto.PostDataJson
import java.io.IOException


class RemoteRedditDataSource(
    private val networkService: NetworkService = NetworkService()
) : PageKeyedDataSource<String, PostDataJson>() {

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, PostDataJson>
    ) {
        val request = networkService.getRedditService().getTop(
            subreddit = "gaming",
            limit = 25
        )
        try {
            val response = request.execute()
            val data = response.body()?.data
            val items = data?.children?.map { it.data } ?: emptyList()
            callback.onResult(items, data?.before, data?.after)
        } catch (ioException: IOException) {
        }

    }

    override fun loadAfter(
        params: LoadParams<String>,
        callback: LoadCallback<String, PostDataJson>
    ) {
        val request = networkService.getRedditService().getTopAfter(
            subreddit = "gaming",
            limit = 25,
            after = params.key
        )
        try {
            val response = request.execute()
            val data = response.body()?.data
            val items = data?.children?.map { it.data } ?: emptyList()
            callback.onResult(items, data?.after)
        } catch (ioException: IOException) {
        }
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, PostDataJson>
    ) {
    }
}