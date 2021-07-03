package com.ilkeryildirim.soccerleague.ui.screens.home

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilkeryildirim.soccerleague.data.remote.api.SoccerLeagueApiResult
import com.ilkeryildirim.soccerleague.data.remote.model.EmptyDataModel
import com.ilkeryildirim.soccerleague.data.remote.repository.HomeDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeDataRepository: HomeDataRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow<HomeFragmentUIState>(HomeFragmentUIState.Initial)
    val uiState: StateFlow<HomeFragmentUIState> = _uiState
    private lateinit var teams: ArrayList<EmptyDataModel>
    var isLoading = MutableLiveData<Boolean>()

    fun onRefresh() {
        _uiState.value = HomeFragmentUIState.Loading
        isLoading.value = true
        getTeams()
    }

    fun getTeams() {
        isLoading.value = true
        _uiState.value = HomeFragmentUIState.Loading
        viewModelScope.launch {
            homeDataRepository.getTeams().let { result ->
                when (result) {
                    is SoccerLeagueApiResult.Success -> {
                        isLoading.value = false
                        _uiState.value = HomeFragmentUIState.Success(result.data)
                    }
                    is SoccerLeagueApiResult.Error -> {
                        result.message?.let {
                            _uiState.value = HomeFragmentUIState.Error(result.message)
                        }
                    }
                }
            }
        }
    }
}


sealed class HomeFragmentUIState {
    object Initial : HomeFragmentUIState()
    object Loading : HomeFragmentUIState()
    data class Navigate(var destinationId: Int, var bundle: Bundle) : HomeFragmentUIState()
    data class Success(var discoverData: ArrayList<EmptyDataModel>) : HomeFragmentUIState()
    data class Error(val message: String) : HomeFragmentUIState()
}