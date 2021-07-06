package com.ilkeryildirim.soccerleague.data.remote.api

sealed class ApiResult<out T : Any> {
  data class Success<out T : Any>(val data: T) : ApiResult<T>()
  data class Error(val message: String?, val statusCode: Int? = null) : ApiResult<Nothing>()
}