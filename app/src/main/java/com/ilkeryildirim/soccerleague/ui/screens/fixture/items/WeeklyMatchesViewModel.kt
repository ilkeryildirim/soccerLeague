package com.ilkeryildirim.soccerleague.ui.screens.fixture.items

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilkeryildirim.soccerleague.data.model.fixture.Match
import com.ilkeryildirim.soccerleague.data.model.team.Teams


class WeeklyMatchesViewModel : ViewModel() {

    private val name = MutableLiveData<String?>()
    fun bind(match: Match,teams: Teams) {
       teams.teams!!.forEach {
           if(it.team_id == match.teamGuestId)   name.value = it.name
        }
    }

    fun getName() = name

}