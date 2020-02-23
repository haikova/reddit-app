package olyarisu.github.com.myapplication.presentation

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import olyarisu.github.com.myapplication.R
import olyarisu.github.com.myapplication.data.SubredditRepository
import olyarisu.github.com.myapplication.data.dto.PostDataJson

class MainActivity : AppCompatActivity() {

    private lateinit var snackbar: Snackbar
    private lateinit var viewModel: RedditPostsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = getViewModel()
        initRecyclerView()
        snackbar = Snackbar.make(container, "", Snackbar.LENGTH_INDEFINITE)
    }

    private fun initRecyclerView() {
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        list_posts.addItemDecoration(decoration)

        list_posts.layoutManager = LinearLayoutManager(this)

        val adapter = PostsAdapter { url ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.reddit.com$url"))
            intent.resolveActivity(packageManager)?.let {
                startActivity(intent)
            }
        }
        list_posts.adapter = adapter

        viewModel.posts.observe(this,
            Observer<PagedList<PostDataJson>> { t ->
                adapter.submitList(t)
            })

        viewModel.isLoading.observe(this,
            Observer<Boolean> { boolean ->
                if (boolean!!) {
                    showProgressBar()
                } else {
                    hideProgressBar()
                }
            })

        swipe_refresh_layout.setOnRefreshListener {
            if (snackbar.isShown) snackbar.dismiss()
            viewModel.refresh()
        }

        viewModel.isRefreshing.observe(this, Observer<Boolean> { isRefreshing: Boolean ->
            swipe_refresh_layout.isRefreshing = isRefreshing
        })

        viewModel.networkErrors.observe(this, Observer<String?> { error ->
            error?.let{
                snackbar = Snackbar.make(container, error, Snackbar.LENGTH_INDEFINITE)
                snackbar.setAction("Try again") {
                    viewModel.retryLastFailedOperation()
                }
                snackbar.show()}
        })

    }

    private fun getViewModel(): RedditPostsViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repo =
                    SubredditRepository(this@MainActivity)
                return RedditPostsViewModel(repo) as T
            }
        })[RedditPostsViewModel::class.java]
    }

    fun showProgressBar() {
        progress_bar.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        progress_bar.visibility = View.GONE
    }
}
