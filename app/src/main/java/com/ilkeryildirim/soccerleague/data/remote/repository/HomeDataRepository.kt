package com.ilkeryildirim.soccerleague.data.remote.repository

import com.ilkeryildirim.soccerleague.data.remote.api.SoccerLeagueApiResult
import com.ilkeryildirim.soccerleague.data.remote.model.team.Teams

interface HomeDataRepository {
    suspend fun getTeams() : SoccerLeagueApiResult<Teams>
}