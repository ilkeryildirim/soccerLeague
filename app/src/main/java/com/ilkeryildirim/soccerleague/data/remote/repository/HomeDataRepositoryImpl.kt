package com.ilkeryildirim.soccerleague.data.remote.repository

import com.ilkeryildirim.soccerleague.data.remote.api.SoccerLeagueApi
import com.ilkeryildirim.soccerleague.data.remote.api.SoccerLeagueApiResult
import com.ilkeryildirim.soccerleague.data.remote.model.team.Teams

import javax.inject.Inject

class HomeDataRepositoryImpl @Inject constructor(private val soccerLeagueApi: SoccerLeagueApi) :
    HomeDataRepository {

    override suspend fun getTeams(): SoccerLeagueApiResult<Teams> {
        return try {
            val response = soccerLeagueApi.getTeams()
            SoccerLeagueApiResult.Success(response)
        } catch (e: Exception) {
            SoccerLeagueApiResult.Error(e.localizedMessage)
        }
    }

}