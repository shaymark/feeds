package com.markoapps.feeds.services

import com.squareup.moshi.Json

data class FeedLinkResponse(
    @field:Json(name = "entry")     val entry: List<Entry>
)

data class Entry(
    @field:Json(name = "id")            val id: String,
    @field:Json(name = "type")          val type: EntryType,
    @field:Json(name = "title")         val title: String,
    @field:Json(name = "summary")       val summary: String,
    @field:Json(name = "link")          val link: Link,
    @field:Json(name = "content")       val content: Content,
    @field:Json(name = "media_group")   val mediaGroup: List<MediaGroup>
)

data class EntryType(
    @field:Json(name = "value")   val value: String
)

data class Link (
    @field:Json(name = "rel")   val rel: String,
    @field:Json(name = "type")  val type: String,
    @field:Json(name = "href")  val href: String

)

data class Content(
    @field:Json(name = "src")   val src: String,
    @field:Json(name = "type")  val type: String
)

data class MediaGroup(
    @field:Json(name = "type")          val type: String,
    @field:Json(name = "media_item")    val mediaItem: List<MediaItem>
)

data class MediaItem(
    @field:Json(name = "src")   val src: String,
    @field:Json(name = "type")  val type: String,
    @field:Json(name = "scale") val scale: String,
    @field:Json(name = "key")   val key: String
)