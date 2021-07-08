package com.ilkeryildirim.soccerleague.data.model.fixture

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Week(
        @NonNull
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        var description: String?,
        var matches: List<Match>,
        var week_number: String?
)