package com.markoapps.feeds.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.markoapps.feeds.repositories.Feed
import com.markoapps.feeds.repositories.FeedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeedsViewModel(private val feedRepository: FeedRepository): BaseViewModel() {

    private val feedsMutableLiveData: MutableLiveData<List<Feed>> = MutableLiveData()

    private val filterQuery: MutableLiveData<String?> = MutableLiveData()

    private val mediatorLiveData: MediatorLiveData<List<Feed>> = MediatorLiveData()

    init {

        mediatorLiveData.addSource(feedsMutableLiveData, ::onChange)
        mediatorLiveData.addSource(filterQuery, ::onChange)

        refreshFeeds()
    }

    private fun onChange(value: Any?){
        mediatorLiveData.value = if (filterQuery.value?.length ?:0 > 0) {
            feedsMutableLiveData.value?.filter { it.title.contains(filterQuery.value!!) }
        } else {
            feedsMutableLiveData.value
        }

    }

    fun getFeedsLiveData() = mediatorLiveData as LiveData<List<Feed>>

    fun setFilter(query: String?){
        filterQuery.value = query
    }

    private fun refreshFeeds(){

        launch {
            feedsMutableLiveData.postValue(listOf())

            async {
                withContext (Dispatchers.Main) {

                    val feedVideos = feedRepository.getVideoFeeds()

                    feedsMutableLiveData.value = feedsMutableLiveData.value?.toMutableList()?.apply {
                        addAll(feedVideos)
                    }
                }
            }
            async {
                withContext (Dispatchers.Main) {

                    val feedLinks = feedRepository.getLinkFeeds()

                    feedsMutableLiveData.value = feedsMutableLiveData.value?.toMutableList()?.apply {
                        addAll(feedLinks)
                    }
                }
            }
        }
    }

}