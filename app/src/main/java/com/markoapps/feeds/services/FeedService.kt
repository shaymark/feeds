package com.markoapps.feeds.services

import kotlinx.coroutines.Deferred
import retrofit2.Response

import retrofit2.http.GET

interface FeedService{

    @GET("/applicaster-employees/israel_team/Elad/assignment/video_json.json")
    fun getVideosAsync(): Deferred<Response<FeedLinkResponse>>

    @GET("/applicaster-employees/israel_team/Elad/assignment/link_json.json")
    fun getLinksAsync(): Deferred<Response<FeedLinkResponse>>

}