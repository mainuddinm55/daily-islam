package info.learncoding.dailyislam.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HadithGrade(
    val graded_by: String?,
    val grade: String?
):Parcelable
