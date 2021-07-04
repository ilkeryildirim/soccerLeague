package com.ilkeryildirim.soccerleague.data.remote.api

import com.ilkeryildirim.soccerleague.data.remote.model.team.Teams
import com.ilkeryildirim.soccerleague.util.ApiConstants
import retrofit2.http.GET


interface SoccerLeagueApi {
    @GET(ApiConstants.Endpoints.TEAMS)
    suspend fun getTeams():Teams
}

