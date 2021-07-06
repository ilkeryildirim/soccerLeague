package com.ilkeryildirim.soccerleague.data.model.team


import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Team(
    @NonNull
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerializedName("goals_conceded")
    var goalsConceded: String?,
    @SerializedName("goals_scored")
    var goalsScored: String?,
    @SerializedName("id")
    var team_id: String?,
    @SerializedName("teamImages")
   var teamImages: TeamImages?,
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
):Parcelable