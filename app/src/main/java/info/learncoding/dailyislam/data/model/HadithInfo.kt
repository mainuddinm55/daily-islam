package info.learncoding.dailyislam.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HadithInfo(
    val lang: String?,
    val chapterNumber: String?,
    val chapterTitle: String?,
    val urn: String?,
    val body: String?,
    val grades: List<HadithGrade>? = mutableListOf()
):Parcelable
