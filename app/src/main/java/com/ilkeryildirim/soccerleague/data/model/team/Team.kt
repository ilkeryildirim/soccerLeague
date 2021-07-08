package com.ilkeryildirim.soccerleague.data.model.team


import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Team(
        @NonNull
        @PrimaryKey(autoGenerate = true)
        val db_id: Int,
        var goals_conceded: String?,
        var goals_scored: String?,
        var id: String?,
        var image: TeamImages?,
        var matches_lose: String?,
        var league_score: String?,
        var matches_played: String?,
        var matches_won: String?,
        var name: String?
)