package com.ilkeryildirim.soccerleague.ui.screens.fixture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilkeryildirim.soccerleague.data.local.SoccerLeagueDao
import com.ilkeryildirim.soccerleague.data.model.fixture.Week
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class FixtureViewModel @Inject constructor(
    private val soccerLeagueDao: SoccerLeagueDao
) : ViewModel() {


    private val _uiState = MutableStateFlow<FixtureFragmentUIState>(FixtureFragmentUIState.Initial)
    val uiState: StateFlow<FixtureFragmentUIState> = _uiState

    init {
        viewModelScope.launch {
            getFixtureFromDB()
        }
    }

    suspend fun getFixtureFromDB() {
        withContext(Dispatchers.IO) {
            val fixture = soccerLeagueDao.getFixture()
          _uiState.value=FixtureFragmentUIState.FixtureLoaded(fixture)
        }

    }

    fun onRefresh() {
        _uiState.value = FixtureFragmentUIState.Loading
    }


}

sealed class FixtureFragmentUIState {
    object Initial : FixtureFragmentUIState()
    object Loading : FixtureFragmentUIState()
    data class FixtureLoaded(var fixture: List<Week?>) : FixtureFragmentUIState()
    data class Error(val message: String) : FixtureFragmentUIState()
}