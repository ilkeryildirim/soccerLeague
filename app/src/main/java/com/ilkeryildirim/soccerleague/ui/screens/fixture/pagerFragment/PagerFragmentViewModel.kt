package com.ilkeryildirim.soccerleague.ui.screens.fixture.pagerFragment

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
class PagerFragmentViewModel @Inject constructor(
        private val homeDataRepository: HomeDataRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow<PagerFragmentUIState>(PagerFragmentUIState.Initial)
    val uiState: StateFlow<PagerFragmentUIState> = _uiState

    fun onRefresh() {
        _uiState.value = PagerFragmentUIState.Loading
    }


}


sealed class PagerFragmentUIState {
    object Initial : PagerFragmentUIState()
    object Loading : PagerFragmentUIState()
    data class Navigate(var destinationId: Int, var bundle: Bundle) : PagerFragmentUIState()
    data class Error(val message: String) : PagerFragmentUIState()
}