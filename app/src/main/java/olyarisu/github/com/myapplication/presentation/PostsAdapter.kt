package olyarisu.github.com.myapplication.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_post.view.*
import olyarisu.github.com.myapplication.R
import olyarisu.github.com.myapplication.data.dto.PostDataJson

class PostsAdapter(
    private val onItemClick: ((String) -> Unit)? = null
) : PagedListAdapter<PostDataJson, PostViewHolder>(DIFF_CALLBACK) {

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
        getItem(position)?.apply {
            holder.titlePost.text = title
            holder.scorePost.text = score.toString()
            holder.subredditPost.text = subreddit_name_prefixed
            holder.itemView.setOnClickListener { onItemClick?.invoke(permalink) }
        }
    }


    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<PostDataJson>() {
            override fun areItemsTheSame(
                oldPost: PostDataJson,
                newPost: PostDataJson
            ) = oldPost.id == newPost.id

            override fun areContentsTheSame(
                oldPost: PostDataJson,
                newPost: PostDataJson
            ) = oldPost == newPost
        }
    }
}

class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titlePost: TextView = itemView.title_post
    val scorePost: TextView = itemView.score_post
    val subredditPost: TextView = itemView.subreddit_post
}