package olyarisu.github.com.myapplication.data.repository

import androidx.paging.PagedList
import olyarisu.github.com.myapplication.domain.entity.Post

class RedditBoundaryCallback(
    private val zeroItemsLoaded: () -> Unit,
    private val itemAtEndLoaded: (String) -> Unit
) : PagedList.BoundaryCallback<Post>() {

    override fun onZeroItemsLoaded() {
        zeroItemsLoaded()

    }

    override fun onItemAtEndLoaded(itemAtEnd: Post) {
        itemAtEndLoaded(itemAtEnd.name)
    }
}