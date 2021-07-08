package com.ilkeryildirim.soccerleague.data.local

import androidx.room.*
import com.ilkeryildirim.soccerleague.data.model.fixture.Week
import com.ilkeryildirim.soccerleague.data.model.team.Team


@Dao
interface SoccerLeagueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTeams(teams: List<Team>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFixtureWeeks(weeks: List<Week?>)

    @Query("SELECT * FROM team")
    fun getTeams(): List<Team?>

    @Query("SELECT * FROM week")
    fun getFixture(): List<Week?>

    @Query("DELETE FROM week")
    fun deleteFixture()

    @Query("DELETE FROM team")
    fun deleteTeams()

}