package com.ilkeryildirim.soccerleague.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ilkeryildirim.soccerleague.data.model.fixture.Match
import com.ilkeryildirim.soccerleague.data.model.team.TeamImages

@ProvidedTypeConverter
class TypeConverter {
    @TypeConverter
    fun toMatch(json: String): List<Match> {
        val type = object : TypeToken<List<Match>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toWeekString(match: List<Match>): String {
        val type = object: TypeToken<List<Match>>() {}.type
        return Gson().toJson(match, type)
    }

    @TypeConverter
    fun toTeamImage(json: String):TeamImages {
        val type = object : TypeToken<TeamImages>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toTeamImageString(week: TeamImages): String {
        val type = object: TypeToken<TeamImages>() {}.type
        return Gson().toJson(week, type)
    }

}