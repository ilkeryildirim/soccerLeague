package com.ilkeryildirim.soccerleague.data.remote.repository

import com.ilkeryildirim.soccerleague.data.remote.api.ApiResult
import com.ilkeryildirim.soccerleague.data.model.fixture.Fixture
import com.ilkeryildirim.soccerleague.data.model.team.Teams

interface HomeDataRepository {
    suspend fun getTeams() : ApiResult<Teams>
    suspend fun getFixture() : ApiResult<Fixture>
}