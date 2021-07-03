package com.ilkeryildirim.soccerleague.data.remote.api

sealed class SoccerLeagueApiResult<out T : Any> {
  data class Success<out T : Any>(val data: T) : SoccerLeagueApiResult<T>()
  data class Error(val message: String?, val statusCode: Int? = null) : SoccerLeagueApiResult<Nothing>()
}