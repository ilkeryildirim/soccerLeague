package com.ilkeryildirim.soccerleague.ui.screens.splash


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilkeryildirim.soccerleague.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    val destination = MutableLiveData<Int>()

    init {
       destination.postValue(R.id.action_splashFragment_to_homeFragment)
    }

}
