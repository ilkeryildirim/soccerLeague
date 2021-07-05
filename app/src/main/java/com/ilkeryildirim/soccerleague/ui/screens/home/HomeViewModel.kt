package com.ilkeryildirim.soccerleague.ui.screens.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilkeryildirim.soccerleague.R
import com.ilkeryildirim.soccerleague.data.remote.api.SoccerLeagueApiResult
import com.ilkeryildirim.soccerleague.data.remote.model.team.Teams
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

    fun onRefresh() {
        _uiState.value = HomeFragmentUIState.Loading
        getTeamsAndFixture()
    }

    fun onFixtureFragmentDestination() {

        val bundle = Bundle()
        bundle.putParcelable("Teams",teams)
        _uiState.value = HomeFragmentUIState.Navigate(R.id.action_homeFragment_to_fixtureFragment, bundle)

    }

    fun getTeamsAndFixture() {
        _uiState.value = HomeFragmentUIState.Loading
        viewModelScope.launch {

            val flowA = flowOf(homeDataRepository.getTeams())
            val flowB = flowOf(homeDataRepository.getTeams())
            val zippedFlow = flowA.zip(flowB) { flowAResult, flowBResult ->
                println("+++++++")
                when (flowAResult) {
                    is SoccerLeagueApiResult.Error -> {
                        flowAResult.message?.let { errorMessage ->
                            _uiState.value = HomeFragmentUIState.Error(errorMessage)
                        }
                    }
                    is SoccerLeagueApiResult.Success -> {
                        teams = flowAResult.data
                        _uiState.value = HomeFragmentUIState.TeamsLoaded(flowAResult.data)
                    }
                }
                when (flowBResult) {
                    is SoccerLeagueApiResult.Error -> {
                        flowBResult.message?.let { errorMessage ->
                            _uiState.value = HomeFragmentUIState.Error(errorMessage)
                        }
                    }
                    is SoccerLeagueApiResult.Success -> {
                        _uiState.value = HomeFragmentUIState.TeamsLoaded(flowBResult.data)
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
    data class FixtureLoaded(var discoverData2: Teams) : HomeFragmentUIState()
    data class Error(val message: String) : HomeFragmentUIState()
}