package com.ilkeryildirim.soccerleague.ui.screens.scores

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
class ScoresViewModel @Inject constructor(
    private val soccerLeagueDao: SoccerLeagueDao
) : ViewModel() {

    private val _uiState = MutableStateFlow<ScoresFragmentUIState>(ScoresFragmentUIState.Initial)
    val uiState: StateFlow<ScoresFragmentUIState> = _uiState

    init {
        viewModelScope.launch {
            getTeamsFromDB()
        }
    }

    suspend fun getTeamsFromDB() {
        withContext(Dispatchers.IO) {
            val teams = soccerLeagueDao.getTeams()
          _uiState.value=ScoresFragmentUIState.TeamsLoaded(teams)
        }
    }
}

sealed class ScoresFragmentUIState {
    object Initial : ScoresFragmentUIState()
    object Loading : ScoresFragmentUIState()
    data class TeamsLoaded(var teams: List<Team?>) : ScoresFragmentUIState()
    data class Error(val message: String) : ScoresFragmentUIState()
}