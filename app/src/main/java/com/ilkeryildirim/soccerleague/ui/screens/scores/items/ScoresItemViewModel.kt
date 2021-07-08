package com.ilkeryildirim.soccerleague.ui.screens.scores.items

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilkeryildirim.soccerleague.data.model.fixture.Match
import com.ilkeryildirim.soccerleague.data.model.team.Team

class ScoresItemViewModel : ViewModel() {

    private val teamName = MutableLiveData<String?>()
    private val hostTeamName = MutableLiveData<String?>()
    private val hostTeamImage = MutableLiveData<String?>()
    private val guestTeamName = MutableLiveData<String?>()
    private val guestTeamImage = MutableLiveData<String?>()

    fun bind(team: Team) {
teamName.value = team.name
    }

    fun getTeamName() = teamName
}