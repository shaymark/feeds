package com.markoapps.feeds.repositories

import com.markoapps.feeds.services.Entry
import com.markoapps.feeds.services.FeedService

interface FeedRepository {
    suspend fun getVideoFeeds(): List<Feed>
    suspend fun getLinkFeeds(): List<Feed>
}

sealed class Feed(open val id: String, open val title: String, open val summary: String, open val image: String)
data class VideoFeed(
    override val id: String,
    override val title: String,
    override val summary: String,
    override val image: String,
    val videoLink: String
) : Feed(id, title, summary, image)

data class LinkFeed(
    override val id: String,
    override val title: String,
    override val summary: String,
    override val image: String,
    val link: String
) : Feed(id, title, summary, image)


fun convertEntryToFeed(entry: Entry) : Feed? = when (entry.type.value){
        "video" ->  VideoFeed(entry.id, entry.title, entry.summary, entry.mediaGroup[0].mediaItem[0].src, entry.content.src)
        "link" ->  LinkFeed(entry.id, entry.title, entry.summary, entry.mediaGroup[0].mediaItem[0].src, entry.link.href)
        else -> null
    }

class FeedRepositoryImpl(private val feedService: FeedService): FeedRepository{

    override suspend fun getLinkFeeds(): List<Feed>  =
        try {
            val response = feedService.getLinksAsync().await()

            if (response.isSuccessful) {
                response.body()?.entry?.mapNotNull { convertEntryToFeed(it) } ?: listOf()
            } else {
                listOf()
            }

        } catch (e: Exception) {
            listOf()
        }





    override suspend fun getVideoFeeds(): List<Feed> =
        try {
            val response = feedService.getVideosAsync().await()

            if (response.isSuccessful) {
                response.body()?.entry?.mapNotNull { convertEntryToFeed(it) } ?: listOf()
            } else {
                listOf()
            }

        } catch (e: Exception) {
            listOf()
        }

}