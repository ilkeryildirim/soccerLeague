package com.ilkeryildirim.soccerleague.data.remote.repository

import com.ilkeryildirim.soccerleague.data.remote.api.SoccerLeagueApiResult
import com.ilkeryildirim.soccerleague.data.model.fixture.Fixture
import com.ilkeryildirim.soccerleague.data.model.team.Teams

interface HomeDataRepository {
    suspend fun getTeams() : SoccerLeagueApiResult<Teams>
    suspend fun getFixture() : SoccerLeagueApiResult<Fixture>
}