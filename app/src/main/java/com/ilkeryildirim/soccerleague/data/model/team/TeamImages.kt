package com.ilkeryildirim.soccerleague.data.model.team


import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class TeamImages(
    @SerializedName("logo_large_url")
    var logoLargeUrl: String?,
    @SerializedName("logo_medium_url")
    var logoMediumUrl: String?,
    @SerializedName("logo_thumbnail_url")
    var logoThumbnailUrl: String?
):Parcelable