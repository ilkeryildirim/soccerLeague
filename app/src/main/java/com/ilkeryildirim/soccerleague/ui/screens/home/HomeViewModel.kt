package com.ilkeryildirim.soccerleague.ui.screens.home

import android.os.Bundle
import androidx.databinding.Bindable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilkeryildirim.soccerleague.R
import com.ilkeryildirim.soccerleague.data.local.SoccerLeagueDao
import com.ilkeryildirim.soccerleague.data.model.fixture.Fixture
import com.ilkeryildirim.soccerleague.data.model.team.Team
import com.ilkeryildirim.soccerleague.data.model.team.Teams
import com.ilkeryildirim.soccerleague.data.remote.api.ApiResult
import com.ilkeryildirim.soccerleague.data.remote.repository.HomeDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeDataRepository: HomeDataRepository,
    private val soccerLeagueDao: SoccerLeagueDao
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeFragmentUIState>(HomeFragmentUIState.Initial)
    val uiState: StateFlow<HomeFragmentUIState> = _uiState

    init {
        getTeams()
    }

    fun onFixtureFragmentDestination() {
        _uiState.value = HomeFragmentUIState.Navigate(R.id.action_homeFragment_to_fixtureFragment, Bundle.EMPTY)
    }
    fun onScoresFragmentDestination() {
        _uiState.value = HomeFragmentUIState.Navigate(R.id.action_homeFragment_to_scoresFragment, Bundle.EMPTY)
    }

    private fun getTeams() {
        viewModelScope.launch {
            homeDataRepository.getTeams().let { teamsResult ->
                when (teamsResult) {
                    is ApiResult.Error -> {
                        teamsResult.message?.let { errorMessage ->
                            println(errorMessage)
                            _uiState.value = HomeFragmentUIState.Error(errorMessage)
                        }
                    }
                    is ApiResult.Success -> {
                        _uiState.value = HomeFragmentUIState.TeamsLoaded(teamsResult.data)
                        println(teamsResult.data)
                        addTeamsToDB(teamsResult.data)
                        getFixture()
                    }
                }
            }
        }
    }

    private fun getFixture() {
        viewModelScope.launch {
            homeDataRepository.getFixture().let { fixtureResult ->
                when (fixtureResult) {
                    is ApiResult.Error -> {
                        fixtureResult.message?.let { errorMessage ->
                            println(errorMessage)
                            _uiState.value = HomeFragmentUIState.Error(errorMessage)
                        }
                    }
                    is ApiResult.Success -> {
                        _uiState.value = HomeFragmentUIState.FixtureLoaded(fixtureResult.data)
                        addFixtureToDB(fixtureResult.data)
                    }
                }
            }
        }
    }

    private suspend fun addTeamsToDB(teams: Teams) {
        withContext(Dispatchers.IO) {
            teams.teams?.let { teamList ->
                soccerLeagueDao.insertTeams(teamList)
            }
        }
    }

    private suspend fun addFixtureToDB(fixture: Fixture) {
        withContext(Dispatchers.IO) {
            fixture.week.let { fixtureWeekList ->
                soccerLeagueDao.insertFixtureWeeks(fixtureWeekList)
            }
        }

    }

}

sealed class HomeFragmentUIState {
    object Initial : HomeFragmentUIState()
    object Loading : HomeFragmentUIState()
    data class Navigate(var destinationId: Int, var bundle: Bundle) : HomeFragmentUIState()
    data class TeamsLoaded(var teams: Teams) : HomeFragmentUIState()
    data class FixtureLoaded(var fixture: Fixture) : HomeFragmentUIState()
    data class Error(val message: String) : HomeFragmentUIState()
}