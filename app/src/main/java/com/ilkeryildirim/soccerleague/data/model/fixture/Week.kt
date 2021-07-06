package com.ilkeryildirim.soccerleague.data.model.fixture


import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Week(
    @NonNull
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerializedName("description")
    var description: String?,
    @SerializedName("matches")
    var matches: List<Match>?,
    @SerializedName("week_number")
    var weekNumber: String?
):Parcelable