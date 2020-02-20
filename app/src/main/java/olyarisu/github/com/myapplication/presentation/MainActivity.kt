package olyarisu.github.com.myapplication.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import olyarisu.github.com.myapplication.R
import olyarisu.github.com.myapplication.data.dto.PostDataJson

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: RedditPostsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(RedditPostsViewModel::class.java)
        initRecyclerView()
    }

    private fun initRecyclerView(){
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        list_posts.addItemDecoration(decoration)

        list_posts.layoutManager = LinearLayoutManager(this)

        val adapter = PostsAdapter()
        list_posts.adapter = adapter

        viewModel.pagedListLiveData.observe(this,
            Observer<PagedList<PostDataJson>> { t ->
                adapter.submitList(t)
            })
    }
}
