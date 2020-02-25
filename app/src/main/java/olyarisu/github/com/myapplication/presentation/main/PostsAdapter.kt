package olyarisu.github.com.myapplication.presentation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_post.view.*
import olyarisu.github.com.myapplication.R
import olyarisu.github.com.myapplication.data.api.dto.PostDataJson
import olyarisu.github.com.myapplication.domain.entity.Post
import olyarisu.github.com.myapplication.presentation.main.mapper.map
import olyarisu.github.com.myapplication.presentation.main.viewdata.PostView

class PostsAdapter(
    private val onItemClick: ((String) -> Unit)? = null
) : PagedListAdapter<Post, PostViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_post,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        getItem(position)?.map()?.apply {
            holder.titlePost.text = title
            holder.scorePost.text = score
            holder.subredditPost.text = subreddit
            holder.itemView.setOnClickListener { onItemClick?.invoke(link) }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(
                oldPost: Post,
                newPost: Post
            ) = oldPost.id == newPost.id

            override fun areContentsTheSame(
                oldPost: Post,
                newPost: Post
            ) = oldPost == newPost
        }
    }
}

class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titlePost: TextView = itemView.title_post
    val scorePost: TextView = itemView.score_post
    val subredditPost: TextView = itemView.subreddit_post
}