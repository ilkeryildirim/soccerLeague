package com.ilkeryildirim.soccerleague.ui.screens.fixture.items

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilkeryildirim.soccerleague.data.remote.model.fixture.Match
import com.ilkeryildirim.soccerleague.data.remote.model.team.Teams


class WeeklyMatchesViewModel : ViewModel() {

    private val name = MutableLiveData<String?>()
    fun bind(match: Match,teams: Teams) {
       teams.teams!!.forEach {
           if(it.id == match.teamGuestId)   name.value = it.name
        }
    }

    fun getName() = name

}