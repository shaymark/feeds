package com.markoapps.feeds.utils

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.markoapps.feeds.repositories.FeedRepositoryImpl
import com.markoapps.feeds.services.FeedService
import com.markoapps.feeds.viewModels.AppViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Providers {

    private val feedService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        retrofit.create(FeedService::class.java)
    }

    private val feedsRepository by lazy {
        FeedRepositoryImpl(feedService)
    }

    val viewModelFactory by lazy {
        AppViewModelFactory(feedsRepository)
    }

}