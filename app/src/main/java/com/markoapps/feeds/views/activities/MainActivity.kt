package com.markoapps.feeds.views.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.markoapps.feeds.R
import com.markoapps.feeds.repositories.LinkFeed
import com.markoapps.feeds.repositories.VideoFeed
import com.markoapps.feeds.views.fragments.FeedsFragment
import com.markoapps.feeds.views.fragments.VideoFragment
import com.markoapps.feeds.views.fragments.WebFragment

class MainActivity : BaseActivity(), Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null){
            openFragmentWithoutBack(FeedsFragment())
        }
    }

    private fun openFragmentWithoutBack(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }

    private fun openFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit()
    }

    override fun openVideo(feed: VideoFeed){
        openFragment(VideoFragment.newInstance(feed.videoLink))
    }

    override fun openLink(feed: LinkFeed){
        openFragment(WebFragment.newInstance(feed.link))
    }

}

interface Navigator {
    fun openVideo(feed: VideoFeed)
    fun openLink(feed: LinkFeed)
}
