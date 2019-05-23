package com.markoapps.feeds

import android.app.Application
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.markoapps.feeds.services.FeedService
import com.markoapps.feeds.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApplicationFeeds () : Application() {

    lateinit var feedService: FeedService

    override fun onCreate() {
        super.onCreate()

        initRetrofit()
    }

    fun initRetrofit(){
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        feedService = retrofit.create(FeedService::class.java)
    }

}