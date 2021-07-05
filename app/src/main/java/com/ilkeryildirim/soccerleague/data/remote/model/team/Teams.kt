package com.ilkeryildirim.soccerleague.data.remote.model.team


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Teams(
    @SerializedName("teams")
    var teams: List<Team>?
):Parcelable