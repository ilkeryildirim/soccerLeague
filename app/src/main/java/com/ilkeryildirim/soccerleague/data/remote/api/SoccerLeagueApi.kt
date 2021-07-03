package com.ilkeryildirim.soccerleague.data.remote.api

import com.ilkeryildirim.soccerleague.data.remote.model.EmptyDataModel
import retrofit2.http.GET


interface SoccerLeagueApi {
    @GET("/teams")
    suspend fun getTeams():ArrayList<EmptyDataModel>
}

