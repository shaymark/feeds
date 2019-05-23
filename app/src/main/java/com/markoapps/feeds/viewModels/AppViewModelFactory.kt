package com.markoapps.feeds.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.markoapps.feeds.repositories.FeedRepository

class AppViewModelFactory constructor(private val feedsRepository: FeedRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FeedsViewModel::class.java)) {
            FeedsViewModel(feedsRepository) as T
        }
        else  {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}