package com.markoapps.feeds.views.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.markoapps.feeds.R
import kotlinx.android.synthetic.main.video_fragment.*
import android.app.ProgressDialog
import android.content.res.Configuration

import java.io.IOException


class VideoFragment : Fragment() {


    companion object {
        private const val VIDEO_URL_KEY = "video_src_key"

        fun newInstance(videoUrl: String) = VideoFragment().apply {
                arguments = bundleOf(Pair(VIDEO_URL_KEY, videoUrl) )
        }

    }

    var progressDialog: ProgressDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.video_fragment, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressDialog = ProgressDialog.show(context, "", getString(R.string.buffer_video_loading_message), true);
        progressDialog?.setCancelable(true);

        if (savedInstanceState == null) {
            playVideo(arguments?.getString(VIDEO_URL_KEY) ?: "")
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)

        videoView.resize();

    }

    private fun playVideo(videoUrl: String){
        val videoUri = Uri.parse(videoUrl)
        try {
            videoView.setVideoURI(videoUri)
            videoView.setOnPreparedListener {
                progressDialog?.dismiss();
                videoView.start()
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}


