package com.ilkeryildirim.soccerleague.data.model.fixture


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Match(
    @SerializedName("team_guest_id")
    var teamGuestId: String?,
    @SerializedName("team_host_id")
    var teamHostId: String?,
    @SerializedName("time")
    var time: String?
):Parcelable