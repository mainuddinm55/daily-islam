package info.learncoding.dailyislam.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HadithBookInfo(
    val lang: String?,
    val name: String?
):Parcelable
