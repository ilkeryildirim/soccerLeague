package com.ilkeryildirim.soccerleague.data.remote.model.fixture


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Week(
        @SerializedName("description")
    var description: String?,
        @SerializedName("matches")
    var matches: List<Match>?,
        @SerializedName("week_number")
    var weekNumber: String?
):Parcelable