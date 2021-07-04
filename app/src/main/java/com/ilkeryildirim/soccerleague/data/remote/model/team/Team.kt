package com.ilkeryildirim.soccerleague.data.remote.model.team


import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("goals_conceded")
    var goalsConceded: String?,
    @SerializedName("goals_scored")
    var goalsScored: String?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("image")
    var image: Image?,
    @SerializedName("matches_lose")
    var matchesLose: String?,
    @SerializedName("league_score")
    var leagueScore: String?,
    @SerializedName("matches_played")
    var matchesPlayed: String?,
    @SerializedName("matches_won")
    var matchesWon: String?,
    @SerializedName("name")
    var name: String?
)