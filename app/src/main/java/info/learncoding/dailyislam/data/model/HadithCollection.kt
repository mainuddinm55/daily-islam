package info.learncoding.dailyislam.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "hadith_collection")
@Parcelize
data class HadithCollection(
    @PrimaryKey
    val name: String,
    val hasBooks: Boolean?,
    val hasChapters: Boolean?,
    val collection: List<HadithCollectionInfo>? = mutableListOf(),
    val totalHadith: String,
    val totalAvailableHadith: String
):Parcelable
