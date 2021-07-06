package com.ilkeryildirim.soccerleague.ui.screens.fixture.items

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilkeryildirim.soccerleague.data.model.fixture.Match
import com.ilkeryildirim.soccerleague.data.model.team.Team


class WeeklyMatchesViewModel : ViewModel() {

    private val name = MutableLiveData<String?>()
    fun bind(match: Match, teams: List<Team?>) {
       teams.forEach {
           if(it?.id == match.team_guest_id)   name.value = it.name
        }
    }

    fun getName() = name

}