package com.markoapps.feeds.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.markoapps.feeds.R
import com.markoapps.feeds.repositories.Feed
import com.markoapps.feeds.repositories.LinkFeed
import com.markoapps.feeds.repositories.VideoFeed

import kotlinx.android.synthetic.main.feeds_video_item.view.*

class FeedsAdapter(val context: Context, private val feedAdapterListener: FeedAdapterListener?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface FeedAdapterListener{
        fun onPlayVideoClicked(feed: VideoFeed)
        fun onLinkClicked(feed: LinkFeed)
    }

    var feeds: List<Feed> = listOf()

    fun loadFeeds(feedList: List<Feed>) {
        feeds = feedList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int =  feeds.size

    override fun getItemViewType(position: Int) =
        when (feeds[position]){
            is VideoFeed -> 1
            is LinkFeed -> 2
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            1 -> VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.feeds_video_item, parent, false))
            2 -> LinkViewHolder(LayoutInflater.from(context).inflate(R.layout.feeds_link_item, parent, false))
            else -> VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.feeds_video_item, parent, false))
        }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            1 -> (holder as VideoViewHolder).bind(feeds[position], feedAdapterListener)
            2 -> (holder as LinkViewHolder).bind(feeds[position], feedAdapterListener)
        }
    }

}

class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        const val STROKE_WIDTH = 20f
        const val CENTER_RADIUS = 60f
    }

    private val circularProgressDrawable: CircularProgressDrawable = CircularProgressDrawable(view.context)

    init{
        circularProgressDrawable.strokeWidth = STROKE_WIDTH
        circularProgressDrawable.centerRadius = CENTER_RADIUS
        circularProgressDrawable.start()
    }

    fun bind(feed: Feed, listener: FeedsAdapter.FeedAdapterListener?){
        itemView.title.text = feed.title
        itemView.summary.text = feed.summary
        Glide
            .with(itemView)
            .load(feed.image)
            .centerCrop()
            .placeholder(circularProgressDrawable)
            .into(itemView.image);

        itemView.playButton.setOnClickListener { listener?.onPlayVideoClicked(feed as VideoFeed) }
    }
}

class LinkViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        const val STROKE_WIDTH = 20f
        const val CENTER_RADIUS = 60f
    }

    private val circularProgressDrawable: CircularProgressDrawable = CircularProgressDrawable(view.context)

    init{
        circularProgressDrawable.strokeWidth = STROKE_WIDTH
        circularProgressDrawable.centerRadius = CENTER_RADIUS
        circularProgressDrawable.start()
    }

    fun bind(feed: Feed, listener: FeedsAdapter.FeedAdapterListener?){
        itemView.title.text = feed.title
        itemView.summary.text = feed.summary
        Glide
            .with(itemView)
            .load(feed.image)
            .centerCrop()
            .placeholder(circularProgressDrawable)
            .into(itemView.image);

        itemView.setOnClickListener { listener?.onLinkClicked(feed as LinkFeed) }
    }
}
