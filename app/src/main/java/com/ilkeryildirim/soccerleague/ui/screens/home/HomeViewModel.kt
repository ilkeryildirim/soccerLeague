package com.ilkeryildirim.soccerleague.ui.screens.home

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilkeryildirim.soccerleague.R
import com.ilkeryildirim.soccerleague.data.remote.api.SoccerLeagueApiResult
import com.ilkeryildirim.soccerleague.data.model.fixture.Fixture
import com.ilkeryildirim.soccerleague.data.model.team.Teams
import com.ilkeryildirim.soccerleague.data.remote.repository.HomeDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
        private val homeDataRepository: HomeDataRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow<HomeFragmentUIState>(HomeFragmentUIState.Initial)
    val uiState: StateFlow<HomeFragmentUIState> = _uiState
    private var teams:Teams?=null
    private var fixture:Fixture?=null

    init {
        getTeamsAndFixture()
    }

    fun onRefresh() {
        _uiState.value = HomeFragmentUIState.Loading
        getTeamsAndFixture()
    }

    fun onFixtureFragmentDestination() {
        val bundle = Bundle()
        bundle.putParcelable("Teams",teams)
        bundle.putParcelable("Fixture",fixture)
        _uiState.value = HomeFragmentUIState.Navigate(R.id.fixtureFragment, bundle)
    }

    fun getTeamsAndFixture() {
        _uiState.value = HomeFragmentUIState.Loading
        viewModelScope.launch {
            val teamsFlow = flowOf(homeDataRepository.getTeams())
            val fixtureFlow = flowOf(homeDataRepository.getFixture())
            val zippedFlow = teamsFlow.zip(fixtureFlow) { teamsFlowResult, fixtureFlowResult ->
                when (teamsFlowResult) {
                    is SoccerLeagueApiResult.Error -> {
                        teamsFlowResult.message?.let { errorMessage ->
                            _uiState.value = HomeFragmentUIState.Error(errorMessage)
                        }
                    }
                    is SoccerLeagueApiResult.Success -> {
                        teams = teamsFlowResult.data
                        _uiState.value = HomeFragmentUIState.TeamsLoaded(teamsFlowResult.data)
                    }
                }
                when (fixtureFlowResult) {
                    is SoccerLeagueApiResult.Error -> {
                        fixtureFlowResult.message?.let { errorMessage ->
                            _uiState.value = HomeFragmentUIState.Error(errorMessage)
                        }
                    }
                    is SoccerLeagueApiResult.Success -> {
                        fixture = fixtureFlowResult.data
                        _uiState.value = HomeFragmentUIState.FixtureLoaded(fixtureFlowResult.data)
                    }
                }
            }
            zippedFlow.collect()

        }
    }
}




sealed class HomeFragmentUIState {
    object Initial : HomeFragmentUIState()
    object Loading : HomeFragmentUIState()
    data class Navigate(var destinationId: Int, var bundle: Bundle) : HomeFragmentUIState()
    data class TeamsLoaded(var teams: Teams) : HomeFragmentUIState()
    data class FixtureLoaded(var discoverData2: Fixture) : HomeFragmentUIState()
    data class Error(val message: String) : HomeFragmentUIState()
}