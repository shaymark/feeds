package com.markoapps.feeds.views.fragments

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.markoapps.feeds.R
import kotlinx.android.synthetic.main.webview_fragment.*
import kotlinx.android.synthetic.main.webview_fragment.view.*
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ProgressBar


class WebFragment : Fragment() {


    companion object {
        private const val URL_KEY = "url_key"

        fun newInstance(url: String) = WebFragment().apply {
                arguments = bundleOf(Pair(URL_KEY, url) )
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.webview_fragment, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.post {  initWebView() }
        if (savedInstanceState == null){
            view.webView.loadUrl(arguments?.getString(URL_KEY))
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webView.saveState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        webView.restoreState(savedInstanceState)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(){
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                if (progress < 100 && progressView.visibility === ProgressBar.GONE) {
                    progressView.visibility = ProgressBar.VISIBLE
                    loadingView.visibility = View.VISIBLE
                }

                progressView.progress = progress
                if (progress == 100) {
                    progressView.visibility = ProgressBar.GONE
                    loadingView.visibility = View.GONE
                }
            }
        }

        webView.settings.setAppCacheEnabled(true)
        webView.settings.allowFileAccess = true
        webView.settings.javaScriptEnabled = true
    }

}