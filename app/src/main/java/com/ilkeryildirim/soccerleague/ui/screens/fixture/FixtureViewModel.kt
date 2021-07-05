package com.ilkeryildirim.soccerleague.ui.screens.fixture

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilkeryildirim.soccerleague.data.remote.api.SoccerLeagueApiResult
import com.ilkeryildirim.soccerleague.data.remote.model.team.Teams
import com.ilkeryildirim.soccerleague.data.remote.repository.HomeDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FixtureViewModel @Inject constructor(
        private val homeDataRepository: HomeDataRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow<FixtureFragmentUIState>(FixtureFragmentUIState.Initial)
    val uiState: StateFlow<FixtureFragmentUIState> = _uiState

    fun onRefresh() {
        _uiState.value = FixtureFragmentUIState.Loading
    }


}


sealed class FixtureFragmentUIState {
    object Initial : FixtureFragmentUIState()
    object Loading : FixtureFragmentUIState()
    data class Navigate(var destinationId: Int, var bundle: Bundle) : FixtureFragmentUIState()
    data class Error(val message: String) : FixtureFragmentUIState()
}