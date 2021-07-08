package com.ilkeryildirim.soccerleague.ui.screens.scores.items

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilkeryildirim.soccerleague.data.model.fixture.Match
import com.ilkeryildirim.soccerleague.data.model.team.Team

class ScoresItemViewModel : ViewModel() {

    private val teamName = MutableLiveData<String?>()
    private val goalsScored = MutableLiveData<String?>()
    private val goalsConceded = MutableLiveData<String?>()
    private val win = MutableLiveData<String?>()
    private val lose = MutableLiveData<String?>()
    private val leagueScore = MutableLiveData<String?>()
    private val teamImageUrl= MutableLiveData<String?>()
    private val teamPosition = MutableLiveData<String?>()

    fun bind(team: Team,position:Int) {
        teamName.value = team.name
        goalsScored.value = team.goals_scored
        goalsConceded.value = team.goals_conceded
        win.value = team.matches_won
        lose.value = team.matches_lose
        leagueScore.value = team.league_score
        teamPosition.value = "$position"
        teamImageUrl.value = team.image?.logo_medium_url
    }

    fun getTeamName() = teamName

    fun getGoalsScored() = goalsScored

    fun getGoalsConceded() = goalsConceded

    fun getWin() = win

    fun getLose() = lose

    fun getLeagueScore() = leagueScore

    fun getTeamImageUrl() = teamImageUrl

    fun getTeamPosition() = teamPosition


}