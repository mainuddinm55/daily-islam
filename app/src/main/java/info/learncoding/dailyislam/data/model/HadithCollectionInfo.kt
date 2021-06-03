package info.learncoding.dailyislam.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HadithCollectionInfo(
    val lang: String?,
    val title: String?,
    val shortIntro: String?
):Parcelable
