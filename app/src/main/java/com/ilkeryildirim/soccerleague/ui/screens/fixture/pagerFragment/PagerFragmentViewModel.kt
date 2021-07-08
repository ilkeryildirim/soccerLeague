package com.ilkeryildirim.soccerleague.ui.screens.fixture.pagerFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilkeryildirim.soccerleague.data.local.SoccerLeagueDao
import com.ilkeryildirim.soccerleague.data.model.fixture.Week
import com.ilkeryildirim.soccerleague.data.model.team.Team
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class PagerFragmentViewModel @Inject constructor(
        private val soccerLeagueDao: SoccerLeagueDao
) : ViewModel() {


    private val _uiState = MutableStateFlow<PagerFragmentUIState>(PagerFragmentUIState.Initial)
    val uiState: StateFlow<PagerFragmentUIState> = _uiState

    init {
        viewModelScope.launch {
            getTeamsAndFixturesFromDB()
        }
    }

    private suspend fun getTeamsAndFixturesFromDB() {
        withContext(Dispatchers.IO) {
            val fixtureFlow = flowOf(soccerLeagueDao.getFixture())
            val teamsFlow = flowOf(soccerLeagueDao.getTeams())
            fixtureFlow.zip(teamsFlow) { fixtureResult, teamsResult ->
                _uiState.value = PagerFragmentUIState.FixtureAndTeamsLoaded(fixtureResult, teamsResult)
            }.collect()
        }
    }
}


sealed class PagerFragmentUIState {
    object Initial : PagerFragmentUIState()
    object Loading : PagerFragmentUIState()
    data class FixtureAndTeamsLoaded(var fixture: List<Week?>, var teams: List<Team?>) : PagerFragmentUIState()
    data class Error(val message: String) : PagerFragmentUIState()
}