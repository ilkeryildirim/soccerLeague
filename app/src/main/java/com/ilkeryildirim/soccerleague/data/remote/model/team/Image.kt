package com.ilkeryildirim.soccerleague.data.remote.model.team


import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("logo_large_url")
    var logoLargeUrl: String?,
    @SerializedName("logo_medium_url")
    var logoMediumUrl: String?,
    @SerializedName("logo_thumbnail_url")
    var logoThumbnailUrl: String?
)