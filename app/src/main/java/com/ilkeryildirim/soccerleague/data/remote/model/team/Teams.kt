package com.ilkeryildirim.soccerleague.data.remote.model.team


import com.google.gson.annotations.SerializedName

data class Teams(
    @SerializedName("teams")
    var teams: List<Team>?
)