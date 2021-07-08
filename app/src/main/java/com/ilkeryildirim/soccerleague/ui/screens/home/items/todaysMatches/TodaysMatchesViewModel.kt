package com.ilkeryildirim.soccerleague.ui.screens.home.items.todaysMatches

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilkeryildirim.soccerleague.data.model.fixture.Match
import com.ilkeryildirim.soccerleague.data.model.team.Team


class TodaysMatchesViewModel : ViewModel() {

    private val matchDate = MutableLiveData<String?>()
    private val hostTeamName = MutableLiveData<String?>()
    private val hostTeamImage = MutableLiveData<String?>()
    private val guestTeamName = MutableLiveData<String?>()
    private val guestTeamImage = MutableLiveData<String?>()
    fun bind(match: Match, teams: List<Team?>) {
        matchDate.value = match.time
        teams.forEach { team ->
            when (team?.id) {
                match.team_host_id -> {
                    hostTeamName.value = team.name
                    hostTeamImage.value = team.image?.logo_medium_url
                }
                match.team_guest_id -> {
                    guestTeamName.value = team.name
                    guestTeamImage.value = team.image?.logo_medium_url
                }
            }
        }
    }

    fun getHostTeamName() = hostTeamName

    fun getGuestTeamName() = guestTeamName

    fun getHostTeamImageURl() = hostTeamImage

    fun getGuestTeamImageUrl() = guestTeamImage

    fun getMatchDate() = matchDate

}