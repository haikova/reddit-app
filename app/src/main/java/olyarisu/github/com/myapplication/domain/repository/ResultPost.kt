package olyarisu.github.com.myapplication.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import olyarisu.github.com.myapplication.domain.entity.Post

data class ResultPost (
    val pagedListPost: LiveData<PagedList<Post>>,
    val error: LiveData<String?>,
    val isLoading: LiveData<Boolean>,
    val isRefreshing:  LiveData<Boolean>
)
