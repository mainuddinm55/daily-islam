package info.learncoding.dailyislam.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "hadith")
@Parcelize
data class Hadith(
    var collection: String,
    var bookNumber: String?,
    var chapterId: String?,
    @PrimaryKey
    var hadithNumber: String,
    var hadith: List<HadithInfo>? = mutableListOf()
):Parcelable
