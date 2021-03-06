package com.ilkeryildirim.soccerleague.ui.screens.home.items.leaderBoad


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilkeryildirim.soccerleague.data.model.team.Team

class LeaderBoardItemViewModel : ViewModel() {


    private val imageUrl = MutableLiveData<String?>()
    private val name = MutableLiveData<String?>()
    private val position = MutableLiveData<String?>()

    fun bind(team: Team, position: Int) {
        name.value = team.name
        this.position.value = (position + 1).toString()
        imageUrl.value = team.image?.logo_medium_url

    }

    fun getImageUrl() = imageUrl

    fun getName() = name

    fun getPosition() = position


}
