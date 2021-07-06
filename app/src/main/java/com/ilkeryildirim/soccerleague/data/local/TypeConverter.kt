package com.ilkeryildirim.soccerleague.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ilkeryildirim.soccerleague.data.model.fixture.Match
import com.ilkeryildirim.soccerleague.data.model.fixture.Week
import com.ilkeryildirim.soccerleague.data.model.team.TeamImages

@ProvidedTypeConverter
class TypeConverter {
    @TypeConverter
    fun toMatch(json: String): List<Match> {
        val type = object : TypeToken<List<Match>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toMatchString(match: List<Match>): String {
        val type = object: TypeToken<List<Match>>() {}.type
        return Gson().toJson(match, type)
    }

    @TypeConverter
    fun toTeamImage(json: String):TeamImages {
        val type = object : TypeToken<TeamImages>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toTeamImageString(teamImages: TeamImages): String {
        val type = object: TypeToken<TeamImages>() {}.type
        return Gson().toJson(teamImages, type)
    }

    @TypeConverter
    fun toWeeks(json: String):Week {
        val type = object : TypeToken<Week>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toWeeksString(weeks: List<Week>): String {
        val type = object: TypeToken<List<Week>>() {}.type
        return Gson().toJson(weeks, type)
    }

}