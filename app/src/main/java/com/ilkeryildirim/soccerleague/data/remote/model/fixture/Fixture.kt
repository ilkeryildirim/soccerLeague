package com.ilkeryildirim.soccerleague.data.remote.model.fixture


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Fixture(
    @SerializedName("week")
    var week: List<Week>?
): Parcelable