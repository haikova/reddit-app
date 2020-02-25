package olyarisu.github.com.myapplication.presentation.main

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import olyarisu.github.com.myapplication.R
import olyarisu.github.com.myapplication.util.CustomTabsHelper
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private lateinit var snackbar: Snackbar
    private val redditPostsViewModel by viewModel<RedditPostsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
        initProgressBar()
        initSnackBar()
        initSwipeToRefresh()
        initErrorView()
    }

    private fun initRecyclerView() {
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        list_posts.addItemDecoration(decoration)
        list_posts.layoutManager = LinearLayoutManager(this)

        val adapter = PostsAdapter { url ->
            openUrl(url)
        }
        list_posts.adapter = adapter

        redditPostsViewModel.posts.observe(this,
            Observer { pagedList ->
                adapter.submitList(pagedList)
            })
    }

    private fun initSnackBar() {
        snackbar = Snackbar.make(container, "", Snackbar.LENGTH_INDEFINITE)
    }

    private fun initSwipeToRefresh() {
        swipe_refresh_layout.setOnRefreshListener {
            if (snackbar.isShown) snackbar.dismiss()
            redditPostsViewModel.refresh()
        }
        redditPostsViewModel.isRefreshing.observe(this, Observer { isRefreshing: Boolean ->
            swipe_refresh_layout.isRefreshing = isRefreshing
        })
    }

    private fun initProgressBar() {
        redditPostsViewModel.isLoading.observe(this,
            Observer { boolean ->
                if (boolean) {
                    showProgressBar()
                } else {
                    hideProgressBar()
                }
            })
    }

    private fun initErrorView() {
        redditPostsViewModel.networkError.observe(this, Observer { error ->
            error?.let {
                snackbar = Snackbar.make(container, error, Snackbar.LENGTH_INDEFINITE)
                snackbar.setAction(getString(R.string.try_again)) {
                    redditPostsViewModel.retryLastFailedOperation()
                }
                snackbar.show()
            }
        })
    }

    private fun openUrl(url: String) {
        val packageName = CustomTabsHelper.getPackageNameToUse(this)

        val uri = Uri.parse(BASE_URL+url)
        //If we cant find a package name, it means theres no browser that supports
        //Chrome Custom Tabs installed
        if (packageName == null) {
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.resolveActivity(packageManager)?.let {
                startActivity(intent)
            }
        } else {
            val builder = CustomTabsIntent.Builder()
            builder.setToolbarColor(resources.getColor(R.color.colorPrimary))
            val customTabsIntent = builder.build()
            customTabsIntent.intent.setPackage(packageName)
            customTabsIntent.launchUrl(this, uri)
        }
    }

    private fun showProgressBar() {
        progress_bar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progress_bar.visibility = View.GONE
    }
}

private const val BASE_URL = "https://www.reddit.com"
