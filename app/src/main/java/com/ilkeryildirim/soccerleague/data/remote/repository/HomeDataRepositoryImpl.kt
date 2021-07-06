package com.ilkeryildirim.soccerleague.data.remote.repository

import com.ilkeryildirim.soccerleague.data.remote.api.SoccerLeagueApi
import com.ilkeryildirim.soccerleague.data.remote.api.ApiResult
import com.ilkeryildirim.soccerleague.data.model.fixture.Fixture
import com.ilkeryildirim.soccerleague.data.model.team.Teams

import javax.inject.Inject

class HomeDataRepositoryImpl @Inject constructor(private val soccerLeagueApi: SoccerLeagueApi) :
    HomeDataRepository {

    override suspend fun getTeams(): ApiResult<Teams> {
        return try {
            val response = soccerLeagueApi.getTeams()
            ApiResult.Success(response)
        } catch (e: Exception) {
            ApiResult.Error(e.localizedMessage)
        }
    }

    override suspend fun getFixture(): ApiResult<Fixture> {
        return try {
            val response = soccerLeagueApi.getFixture()
            ApiResult.Success(response)
        } catch (e: Exception) {
            ApiResult.Error(e.localizedMessage)
        }
    }

}