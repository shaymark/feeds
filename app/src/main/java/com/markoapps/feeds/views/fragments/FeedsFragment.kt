package com.markoapps.feeds.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.markoapps.feeds.R
import com.markoapps.feeds.repositories.LinkFeed
import com.markoapps.feeds.repositories.VideoFeed
import com.markoapps.feeds.utils.Providers
import com.markoapps.feeds.viewModels.AppViewModelFactory
import com.markoapps.feeds.viewModels.FeedsViewModel
import com.markoapps.feeds.views.activities.MainActivity
import com.markoapps.feeds.views.adapters.FeedsAdapter
import kotlinx.android.synthetic.main.feeds_fragment.*
import kotlinx.android.synthetic.main.feeds_fragment.view.*

class FeedsFragment : Fragment(), FeedsAdapter.FeedAdapterListener, SearchView.OnQueryTextListener {


    lateinit var feedsAdapter: FeedsAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager

    lateinit var feedsViewModel: FeedsViewModel

    private val viewModelFactory: AppViewModelFactory by lazy {
        Providers.viewModelFactory
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.feeds_fragment, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        feedsAdapter = FeedsAdapter(context!!, this)
        layoutManager = LinearLayoutManager(context!!)

        view.listView.layoutManager = layoutManager
        view.listView.adapter = feedsAdapter
        view.listView.setEmptyView(emptyListView)

        feedsViewModel =  ViewModelProviders.of(this, this.viewModelFactory).get(FeedsViewModel::class.java)

        feedsViewModel.getFeedsLiveData().observe(this, Observer {
            feedsAdapter.loadFeeds(it)
        })

        searchView.setOnQueryTextListener(this)
        searchView.queryHint = getString(R.string.filter_query_hint)

    }

    override fun onPlayVideoClicked(feed: VideoFeed) {
        (activity as MainActivity).openVideo(feed)
    }

    override fun onLinkClicked(feed: LinkFeed) {
        (activity as MainActivity).openLink(feed)
    }

    override fun onQueryTextSubmit(str: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(str: String?): Boolean {
        feedsViewModel.setFilter(str)
        return false
    }


}